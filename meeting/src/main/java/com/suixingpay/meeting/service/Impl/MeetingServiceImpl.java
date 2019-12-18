package com.suixingpay.meeting.service.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.suixingpay.meeting.mapper.MeetingMapper;
import com.suixingpay.meeting.pojo.Meeting;
import com.suixingpay.meeting.service.MeetingService;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

@Service
public class MeetingServiceImpl implements MeetingService {
    MeetingMapper meetingMapper;
    @Override
    public String QRcode(Integer meetingId, HttpServletRequest request) {
        Meeting meeting= meetingMapper.QRcode(meetingId);
        //String meetingName=meeting.getMeetingName();




        String content=""+meetingId;
        int width=200;
        int height=200;
        String resultImage = "";
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            @SuppressWarnings("rawtypes")
            //设置二维码的参数
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距

            try {
                QRCodeWriter writer = new QRCodeWriter();
                // 最终生成 参数列表 （1.内容 2.格式 3.宽度 4.高度 5.二维码参数）
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                /**
                 * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，可以让前端加，也可以在下面加上
                 */
                resultImage = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));

                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return null;
    }
}
