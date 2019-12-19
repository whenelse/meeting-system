package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.pojo.Record;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.service.RecordService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import com.suixingpay.meeting.util.RecordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Synchronized;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;





import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    Result result;

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
    public void exportEnrollInfo(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取报名信息（EXCEL）");

        HSSFRow row = null;

        row = sheet.createRow(0); //创建第一个单元格
        row.setHeight((short) (27 * 20));
        row.createCell(0).setCellValue("报名信息"); //为第一行单元格设值
        //合并单元格  CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);
        //设置表头
        row = sheet.createRow(1);
        row.setHeight((short) (23 * 20)); //设置行高
        row.createCell(0).setCellValue("姓名"); //为第一个单元格设值
        row.createCell(1).setCellValue("手机号码"); //为第二个单元格设值
        row.createCell(2).setCellValue("签到时间");

        /*for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = users.get(i);
            row.createCell(0).setCellValue(user.getUid());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());
        }*/
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 2; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=user.xls");//默认Excel名称
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
    public void exportSignInInfo(HttpServletResponse response) throws IOException {
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

        /*for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = users.get(i);
            row.createCell(0).setCellValue(user.getUid());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());
        }*/
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 2; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=user.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
    }
}
