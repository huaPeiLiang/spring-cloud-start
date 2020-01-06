package com.start.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SmsUtil {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    @Value("${sms.app.id:}")
    private String smsAppId;
    @Value("${sms.app.key:}")
    private String smsAppKey;
    @Value("${sms.template.id.bind.authentication:0}")
    private Integer templateId_authentication;
    @Value("${sms.sign:}")
    private String smsSign;

    public void sendAuthentication(String nationCode, String mobile, String code) throws Exception {
        if (templateId_authentication <= 0) {
            logger.warn("未配置 bind WeChat 短信模板");
            return;
        }
        logger.debug("sendAuthentication to {}，验证码={}", mobile, code);
        SmsSingleSender sender = new SmsSingleSender(Integer.parseInt(smsAppId), smsAppKey);
        ArrayList<String> params = new ArrayList<>();
        params.add(code);
        SmsSingleSenderResult result = sender.sendWithParam(nationCode, mobile, templateId_authentication, params, smsSign, "", "");
        logger.debug("短信发送结果：" + result);
    }

}
