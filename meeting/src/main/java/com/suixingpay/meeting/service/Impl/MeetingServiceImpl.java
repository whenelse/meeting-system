package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingMapper meetingMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    Result result;


    @Override
    public Result selectAll() {
        List<Meeting> list = meetingMapper.selectAll();
        result.set(200,"查询成功",list);
        return result;
    }
    /**
     * 管理员审核会议通过
     * @param meetingId
     * @return
     */
    @Override
    public Result auditPass(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
        if(meeting.getMeetingAuditStatus()==0){
            meeting.setMeetingAuditStatus(2);
            if(meetingMapper.updateMeeting(meeting)<1){
                result.set(400,"审核通过失败",null);
                return result;
            }else {
                result.set(200,"审核通过成功",null);
                return result;
            }
        }else{
            result.set(400,"该会议不需要审核",null);
            return result;
        }
    }

    /**
     * 鑫管家查看任务
     * @param userId
     * @return
     */
    @Override
    public Result queryMeetingByPUser(int userId) {
        List<Meeting> list = new ArrayList();
        User user = userMapper.selectUserByUserId(userId);
        List<Meeting> meeting = meetingMapper.queryMeetingByUserId(userId);
        list.addAll(meeting);
        if (user.getRootUserId() != user.getPUserId()){
            user = userMapper.selectUserByUserId(user.getPUserId());
            meeting = meetingMapper.queryMeetingByUserId(user.getUserId());
            list.addAll(meeting);
        }

        result.set(200,"查询成功",list);
        return result;
    }

    /**
     * 查看任务详情
     * @param meetingId
     * @return
     */
    @Override
    public Result selectMeetingById(int meetingId) {
        Meeting meeting = meetingMapper.selectMeetingById(meetingId);
        result.set(200,"查询成功",meeting);
        return result;
    }

    /**
     * 多项模糊查询所有会议
     * @param meeting
     * @return
     */
    @Override
    public Result selectAllMeeting(Meeting meeting) {
        meetingMapper.queryAllMeeting(meeting);

        return null;
    }

    /**
     * 管理员审核会议驳回
     * @param meetingId
     * @return
     */
    @Override
    public Result auditReject(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
        System.out.println("++++++++++++++++++++++++++++++++++++");
        System.out.println(meeting.getMeetingAuditStatus());
        if(meeting.getMeetingAuditStatus()==0){
            meeting.setMeetingAuditStatus(1);
            if(meetingMapper.updateMeeting(meeting)<1){
                result.set(400,"审核驳回失败",null);
                return result;
            }else {
                result.set(200,"审核驳回成功",null);
                return result;
            }
        }else{
            result.set(400,"该会议不需要审核",null);
            return result;
        }
    }

    /**
     * @Description 查询鑫管家自己创建的会议列表
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId: 用户Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/20 9:52
     */
    @Override
    public Result selectMeetingByUserId(int userId) {
        Result result = new Result();
        try {
            List<Meeting> list = meetingMapper.selectMeetingByUserId(userId);
            result.set(200, "查询成功", list);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "查询异常，请稍后", null);
        }
        return result;
    }

    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId:
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:12
     */
    @Override
    public Result selectMeetingDetails(int meetingId) {
        Result result = new Result();
        try {
            Meeting meeting = meetingMapper.selectMeetingDetails(meetingId);
            result.set(200, "查询成功", meeting);
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "查询异常，请稍后", null);
        }
        return result;
    }
    /**
     * @description 导出会议信息到EXCEL表
     * @author Huang Yafeng
     * @date 2019/12/19 11:20
     * @param
     * @return
     */
    @Override
    public void exportMeetingInfo(HttpServletResponse response) throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row = null;

        row = sheet.createRow(0); //创建第一个单元格
        row.setHeight((short) (27 * 20));
        row.createCell(0).setCellValue("会议信息"); //为第一行单元格设值
        //合并单元格  CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);
        //设置表头
        row = sheet.createRow(1);
        row.setHeight((short) (23 * 20)); //设置行高
        row.createCell(0).setCellValue("会议ID"); //为第一个单元格设值
        row.createCell(1).setCellValue("发起类型"); //为第二个单元格设值
        row.createCell(2).setCellValue("发起管家ID");
        row.createCell(3).setCellValue("会议名称");
        row.createCell(4).setCellValue("会议类型");
        row.createCell(5).setCellValue("主办方");
        row.createCell(6).setCellValue("是否收费");
        row.createCell(7).setCellValue("开始日期");
        row.createCell(8).setCellValue("会议地点");
        row.createCell(9).setCellValue("详细地址");
        row.createCell(10).setCellValue("报名截至时间");
        row.createCell(11).setCellValue("会议描述");
        row.createCell(12).setCellValue("会议审核状态");

        /*for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = users.get(i);
            row.createCell(0).setCellValue(user.getUid());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());
        }*/
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
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
