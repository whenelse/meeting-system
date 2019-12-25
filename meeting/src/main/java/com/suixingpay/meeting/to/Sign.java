package com.suixingpay.meeting.to;

import com.suixingpay.meeting.groups.SelectById;
import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Component
public class Sign {
    @NotNull(message = "手机号不能为空", groups = SelectById.class)
    String tel;
    @NotNull(message = "会议Id异常", groups = SelectById.class)
    @Min(value = 0, message = "会议Id不能小于0", groups = SelectById.class)
    Integer meetingId;
}
