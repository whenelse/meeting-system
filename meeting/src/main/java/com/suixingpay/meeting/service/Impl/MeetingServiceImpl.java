package com.suixingpay.meeting.service.Impl;

import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.mapper.RecordMapper;
import com.suixingpay.meeting.mapper.UserMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.pojo.Result;
import com.suixingpay.meeting.pojo.User;
import com.suixingpay.meeting.service.MeetingService;
import com.suixingpay.meeting.to.MeetingSel;
import com.suixingpay.meeting.util.MeetingIsEnrollCompare;
import com.suixingpay.meeting.util.MeetingStartTimeCompare;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.suixingpay.meeting.util.MeetingCheck.enrollCheck;
import static com.suixingpay.meeting.util.MeetingCheck.timeCheck;

@Slf4j
@Service
public class    MeetingServiceImpl implements MeetingService {

    @Autowired
    MeetingMapper meetingMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordMapper recordMapper;

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
    public Result queryMeetingByPUser(Integer userId) {
        if (userId == null){
            result.set(200,"参数异常",null);
        }
        List<Meeting> list = new ArrayList();
        //获取当前用户
        User user = userMapper.selectUserByUserId(userId);
        List<Meeting> meeting = null;
        if(user.getUserId()!=user.getPUserId()) {
            //当用户的上级不是根上级时
            while (user.getRootUserId() != user.getPUserId()) {
                //获取用户上级
                user = userMapper.selectUserByUserId(user.getPUserId());
                //获取用户上级的会议
                meeting = meetingMapper.queryMeetingByReferralCode(user.getReferralCode());
                list.addAll(meeting);
            }
            //查询出根上级的会议
            user = userMapper.selectUserByUserId(user.getPUserId());
            meeting = meetingMapper.queryMeetingByReferralCode(user.getReferralCode());
            list.addAll(meeting);
        }

        //查询管理员创建的面向所有人的会议
        meeting = meetingMapper.queryMeetingByReferralCodeIsNull();
        list.addAll(meeting);

        //判断用户是否已报名会议
        for(Meeting m:list){
            m.setRecordId(recordMapper.selectIsEnrollRecordIdByUserIdAndMeetingId(m.getMeetingId(),userId));
        }

        //按开始时间排序
        list.sort(new MeetingStartTimeCompare());
        //按是否已报名排序
        list.sort(new MeetingIsEnrollCompare());

        if (list == null){
            result.set(200,"当前尚无会议",null);
        }else {
            result.set(200,"查询成功",list);
        }

        return result;
    }

    /**
     * 查看任务详情
     * @param meetingId
     * @return
     */
    @Override
    public Result selectMeetingById(Integer meetingId,Integer userId) {
        if (meetingId == null || userId == null){
            result.set(200,"参数异常",null);
            return result;
        }
        System.out.println(meetingId);
        System.out.println(userId);
        try{
            Meeting meeting = meetingMapper.selectMeetingById(meetingId);
            meeting.setRecordId(recordMapper.selectIsEnrollRecordIdByUserIdAndMeetingId(meetingId,userId));
            result.set(200,"查询成功",meeting);
        }catch (Exception e){
            result.set(200,"查询失败",null);
        }

        return result;
    }

    /**
     * 多项模糊查询所有会议
     * @param meetingSel
     * @return
     */
    @Override
    public Result selectAllMeeting(MeetingSel meetingSel) {
        System.out.println(meetingSel.getMeetingCharge());
        try {
            List<Meeting> list = meetingMapper.queryAllMeeting(meetingSel);
            result.set(200,"查询成功",list);
        }catch (Exception e){
            result.set(200,"查询失败",null);
            log.info(e.getMessage());
        }
        return result;
    }


    /**
     * 管理员审核会议驳回
     * @param meetingId
     * @return
     */
    @Override
    public Result auditReject(int meetingId) {
        Meeting meeting = meetingMapper.selectById(meetingId);
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

    @Override
    public Result auditCheck(Meeting meeting) {
        Meeting meeting1 = meetingMapper.selectById(meeting.getMeetingId());
        if(meeting.getMeetingAuditStatus()!= meeting1.getMeetingAuditStatus()){
           try{
                meetingMapper.updateMeeting(meeting);
                result.set(400,"修改成功",null);
                return result;
            } catch (Exception e){
               log.info("会议审核异常",e.getMessage());
                result.set(200,"修改失败",null);
                return result;
            }
        }else{
            result.set(400,"该会议不需要审核",null);
            return result;
        }
    }

    /**
     * @Description 检查鑫管家是否V5即以上并且查看是否有会议
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param userId:  鑫管家Id
     * @return: com.suixingpay.meeting.pojo.Result
     * @Date 2019/12/19 10:42
     */
    @Override
    public Result checkUserHaveAuthority(int userId) {
        Result result = new Result();
        try {
            User user = userMapper.selectUserByUserId(userId);
            if (user != null) {
                if(user.getLevelNo() < 5) {
                    result.set(200, "没有权限", null);
                } else {
                    List<Meeting> list = meetingMapper.selectMeetingByUserId(userId);
                    if (list.size() == 0) {
                        result.set(200, "未发起过会议", null);
                    } else {
                        result.set(200, "有权限", null);
                    }
                }
            } else {
                result.set(200, "用户不存在", null);
            }

        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "系统异常，请稍后", null);
        }
        return result;
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
            if (list.size() == 0) {
                result.set(200, "对不起，您还没有发起过会议", list);
            } else {
                result.set(200, "查询成功", list);
            }
        } catch (Exception e) {
            log.error("数据库查询异常：",e);
            result.set(200, "查询异常，请稍后", null);
        }
        return result;
    }

    /**
     * @Description 查询会议详细信息
     * @Author zhu_jinsheng[zhu_js@suixingpay.com]
     * @Param meetingId: 会议Id
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
     * 查询所有需要审核的会议
     * @return
     */
    @Override
    public Result selectMeetingAudited() {
        List<Meeting> list = meetingMapper.selectMeetingAudited();
        result.set(200,"查询成功",list);
        return result;
    }

    //新增会议 djq
    @Override
    public Result insertMeeting(Meeting meeting) {
        meeting.setMeetingHours(Integer.valueOf(meeting.getMeetingHours()));
        if (meeting.getMeetingReferralCode()!=null){
            if (userMapper.selectUserByMeetingReferralCode(meeting.getMeetingReferralCode())==null){
                result.set(200,"鑫管家推荐码不存在",null);
                return result;
            }
        }
        if (!enrollCheck(meeting)){
            result.set(200,"请检查您输入的时间信息",null);
            return result;
        }
        User user = userMapper.selectUserByUserId(meeting.getMeetingUserId());
        if (user == null){
            result.set(200,"请先登录",null);
            return result;
        }
        if (meeting.getMeetingUserId() !=1 && user.getLevelNo() < 5){
            result.set(200,"对不起，只有V5以上等级的鑫管家才能发起会议",null);
            return result;
        }
        Meeting newMeeting=new Meeting();
        newMeeting.setMeetingUserId(user.getUserId());
        List<Meeting> meetingList= meetingMapper.selectMeetingByUserId(meeting.getMeetingUserId());
        if (user.getUserId()!=1){
            meeting.setMeetingReferralCode(user.getReferralCode());
        }
        boolean b = timeCheck(meetingList,meeting);
        if (!b) {
            result.set(200, "同一时间不能有两场会议", null);
        }
        else if (meetingMapper.insertMeeting(meeting) == 0) {
            result.set(200, "添加会议失败", null);
        }
        else {
            result.set(200, "恭喜你创建成功，审核通过后会展示其他鑫管家查看", null);
        }
        return result;
    }


    /**
     * @description  将该鑫管家创建的所有会议信息导出到EXCEL表
     * @author Huang Yafeng
     * @date 2019/12/19 11:20
     * @param
     * @return
     */
    @Override
    public void exportMeetingInfo(HttpServletResponse response) throws IOException {
        List<Meeting> meetings = meetingMapper.queryAllMeetingTwo();

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("获取会议信息");
        HSSFRow row = null;
        row = sheet.createRow(0); //创建第一个单元格
        row.setHeight((short) (27 * 20));
        CellStyle cellStyle = wb.createCellStyle();
        row.createCell(0).setCellValue("会议信息"); //为第一行单元格设值
        //HSSFCell cell1 = row.createCell(0);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中
        //合并单元格  CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 9);
        sheet.addMergedRegion(rowRegion);
        //设置表头
        row = sheet.createRow(1);
        row.setHeight((short) (23 * 20)); //设置行高
        row.createCell(0).setCellValue("会议名称"); //为第一个单元格设值
        row.createCell(1).setCellValue("会议类型"); //为第二个单元格设值
        row.createCell(2).setCellValue("发起管家姓名");
        row.createCell(3).setCellValue("主办方");
        row.createCell(4).setCellValue("开始时间");
        row.createCell(5).setCellValue("会议时长");
        row.createCell(6).setCellValue("会议地点");
        row.createCell(7).setCellValue("详细地址");
        row.createCell(8).setCellValue("会议描述");
        row.createCell(9).setCellValue("报名截止时间");

        for (int i = 0; i < meetings.size(); i++) {
            row = sheet.createRow(i + 2);
            Meeting meeting = meetings.get(i);
            row.createCell(0).setCellValue(meeting.getMeetingName());
            row.createCell(1).setCellValue(meeting.getMeetingType());
            row.createCell(2).setCellValue(meeting.getMeetingUserName());
            row.createCell(3).setCellValue(meeting.getMeetingSponsor());

         //   row.createCell(4).setCellValue(meeting.getMeetingStartTime());
            //设置单元格时间格式
          //  CellStyle cellStyle = wb.createCellStyle();
            HSSFCell cell = row.createCell(4);
            HSSFDataFormat format = wb.createDataFormat();
            cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(meeting.getMeetingStartTime());
            cell.setCellStyle(cellStyle);

            row.createCell(5).setCellValue(meeting.getMeetingHours());
            row.createCell(6).setCellValue(meeting.getMeetingAddress());
            row.createCell(7).setCellValue(meeting.getMeetingDetailedAddress());
            row.createCell(8).setCellValue(meeting.getMeetingDescribe());
          //  row.createCell(5).setCellValue(meeting.getMeetingEnrollEndTime());
            //设置时间格式
            HSSFCell cell9 = row.createCell(9);
            cell9.setCellValue(meeting.getMeetingStartTime());
            cell9.setCellStyle(cellStyle);
        }
        sheet.setDefaultRowHeight((short) (17 * 20));
        //列宽自适应
        for (int i = 0; i <= 9; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=meetingInfo.xls"); //默认Excel名称
        wb.write(os);
        os.flush();
        os.close();


    }

    //条件查询会议信息
    @Override
    public Result selectMeetingSelective(Meeting meeting) {
        if(meeting == null){
            result.set(400,"查询信息不能为空",null);
            return result;
        }
        List<Meeting> list = meetingMapper.selectMeetingSelective(meeting);
        result.set(200,"查询成功",list);
        return result;
    }


    //修改会议 djq
    @Override
    public Result updateMeeting(Meeting meeting) {
        meeting.setMeetingHours(Integer.valueOf(meeting.getMeetingHours()));
        if (!enrollCheck(meeting)){
            result.set(200,"请检查您修改的时间信息",null);
            return result;
        }
        List<Meeting> meetingList= meetingMapper.selectMeetingByUserId(meeting.getMeetingUserId());

        for (Meeting meeting1:meetingList){
            if (meeting1.getMeetingId()==meeting.getMeetingId()){
                meetingList.remove(meeting1);
                break;
            }
        }
        boolean b = timeCheck(meetingList,meeting);

        if (!b) {
            result.set(200, "同一时间不能有两场会议", null);
        }
        else if (meetingMapper.updateMeeting(meeting) == 0) {
            result.set(200, "修改会议失败", null);
        }
        else {
            result.set(200, "修改成功", null);
        }
        return result;
    }
}
