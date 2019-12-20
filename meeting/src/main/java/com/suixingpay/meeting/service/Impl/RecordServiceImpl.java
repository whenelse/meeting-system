package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.service.RecordService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import com.suixingpay.meeting.util.RecordUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingMapper meetingMapper;
    Result result=new Result();


    /**
     * 二维码签到
     * @param record
     * @return
     */
    @Override
    public Result signIn(Record record) {
//        if (record.getRecordMeetingId()==null) {
//            result.set(400,"-----",null);
//            return result;
//        }
        Meeting meeting=meetingMapper.selectDate(record.getRecordMeetingId());
        if (meeting == null) {
            result.set(400,"会议不存在",null);
            return  result;
        }
//        if (record.getRecordUserId()==null){
//            result.set(200,"异常",null);
//            return result;
//        }

        User user=userMapper.selectUserId(record.getRecordUserId());
        if (user==null) {
            result.set(400,"用户不存在",null);
            return result;
        }

        //判断时间
        Date dates=meeting.getMeetingStartTime();
        //会议开始时间戳 2019-12-19 18:34:30
        Long startTimes =dates.getTime();
        //会议时长 转换毫秒
        Long meetingTimes= (long) meeting.getMeetingHours()*60*60*1000+24*60*60*1000;
        //当前时间
        Date date=new Date();
        //获取当前时间
        Long curreTimes=date.getTime();

        if (curreTimes >= startTimes+meetingTimes) {
            result.set(200,"会议已经截止，不能继续签到",null);
            return  result;
        }

        //根据传值返回数据库信息
        Record records=recordMapper.signIn(record);
        record.setRecordSignInTime(date);

        if (records!=null){
            if (records.getRecordSignInTime()!=null){
                result.set(400,"您已经签到成功，勿重复签到",null);
                return result;
            }
            //修改
            recordMapper.updateSignIn(date, records.getRecordId());
            result.set(200,"您已经报名,参加成功",null);
        }else if (records==null){
            //添加
            recordMapper.insertSingIn(record);
            result.set(200,"您未报名,参加成功",null);
        }
//        log.info();
        return result;
    }






    /**
     * 用户会议报名功能
     * @param userId
     * @param meetingId
     * @return
     */
    @Override
    @Synchronized
    public Result enroll(int userId, int meetingId) {
        //校验该场会议是否已经报名
        List<Record> list = recordMapper.selectByUserId(userId);
        if(!RecordUtil.RecordCheck(list,meetingId)){
            result.set(400,"您已经报名，请不要多次报名",null);
            return result;
        }
        //报名
        Date d = new Date();
        Record record = new Record();
        record.setRecordUserId(userId);
        record.setRecordMeetingId(meetingId);
        record.setRecordEnrollTime(d);
        if (recordMapper.enroll(record)<1){
            result.set(400,"报名失败，请重试",null);
            return result;
        }else{
            result.set(200,"报名成功",null);
            return result;
        }

    }

    /**
     * @Description 查询报名信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:  会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 15:03
     */
    @Override
    public Result selectEnrollList(int meetingId) {
        Result result = new Result();
        try {
            List<Record> list = recordMapper.selectEnrollList(meetingId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "报名信息查询异常，请稍后", null);
        }
        return result;
    }

    /**
     * @Description 查询签到信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId: 会议Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 14:59
     */
    @Override
    public Result selectSignInList(int meetingId) {
        Result result = new Result();
        try {
            List<Record> list = recordMapper.selectSignInList(meetingId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "签到信息查询异常，请稍后", null);
        }
        return result;
    }

    /**
     * @description 导出报名信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:30
     * @param response
     * @return
     */
    @Override
    public void exportEnrollInfo(HttpServletResponse response, int meetingId) throws IOException {
        List<Record> records = recordMapper.selectEnrollList(meetingId);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("获取报名信息（EXCEL）");
        HSSFRow row = null;
        row = sheet.createRow(0); //创建第一个单元格
        row.setHeight((short) (27 * 20));
        row.createCell(0).setCellValue("报名信息"); //为第一行单元格设值
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中
        //合并单元格  CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);
        //设置表头
        row = sheet.createRow(1);
        row.setHeight((short) (23 * 20)); //设置行高
        row.createCell(0).setCellValue("姓名"); //为第一个单元格设值
        row.createCell(1).setCellValue("手机号码"); //为第二个单元格设值
        row.createCell(2).setCellValue("报名时间");

        for (int i = 0; i < records.size(); i++) {
            row = sheet.createRow(i + 2);
            Record record = records.get(i);
            row.createCell(0).setCellValue(record.getUser().getUserName());
            row.createCell(1).setCellValue(record.getUser().getTelephone());

            CellStyle cellStyle = wb.createCellStyle();
            HSSFCell cell = row.createCell(2);
            HSSFDataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(record.getRecordEnrollTime());
            cell.setCellStyle(cellStyle);

            /*cell.setCellValue(new Date(2008,5,5));
            //set date format
            HSSFCellStyle cellStyle = demoWorkBook.createCellStyle();
            HSSFDataFormat format= demoWorkBook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
            cell.setCellStyle(cellStyle);*/

        }
        sheet.setDefaultRowHeight((short) (17 * 20));
        //列宽自适应
        for (int i = 0; i <= 2; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=enrollExcel.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * @description 导出签到信息
     * @author Huang Yafeng
     * @date 2019/12/19 15:52
     * @param response
     * @return
     */
    @Override
    public void exportSignInInfo(HttpServletResponse response, int meetingId) throws IOException {
        List<Record> records = recordMapper.selectSignInList(meetingId);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("获取签到信息（EXCEL）");
        HSSFRow row = null;
        row = sheet.createRow(0); //创建第一个单元格
        row.setHeight((short) (27 * 20));
        row.createCell(0).setCellValue("签到信息"); //为第一行单元格设值
        //合并单元格  CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);
        //设置表头
        row = sheet.createRow(1);
        row.setHeight((short) (23 * 20)); //设置行高
        row.createCell(0).setCellValue("姓名"); //为第一个单元格设值
        row.createCell(1).setCellValue("手机号码"); //为第二个单元格设值
        row.createCell(2).setCellValue("报名时间");

        for (int i = 0; i < records.size(); i++) {
            row = sheet.createRow(i + 2);
            Record record = records.get(i);
            row.createCell(0).setCellValue(record.getUser().getUserName());
            row.createCell(1).setCellValue(record.getUser().getTelephone());

            CellStyle cellStyle = wb.createCellStyle();
            HSSFCell cell = row.createCell(2);
            HSSFDataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(record.getRecordSignInTime());
            cell.setCellStyle(cellStyle);
        }
        sheet.setDefaultRowHeight((short) (17 * 20));
        //列宽自适应
        for (int i = 0; i <= 2; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=signInExcel.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
    }
}
