package com.start.util;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;
import java.util.Properties;

@Component
public class MailUtil {
    @Value("${mail.smtp.host}")
    private String smtpHost;
    @Value("${mail.sender.addr}")
    private String mailSenderAddr;
    @Value("${mail.sender.pwd}")
    private String mailSenderPwd;
    @Value("${mail.sender.name}")
    private String mailSenderName;
    @Value("${mail.exchange.domain}")
    private String exchangeDomain;
    @Value("${mail.exchange.user}")
    private String exchangeUser;
    @Value("${mail.exchange.password}")
    private String exchangePassword;
    @Value("${mail.exchange.server:outlook.live.com}")
    private String exchangeServer;

    /**
     * 发送邮件
     *
     * @param receiverAddr 收件人邮箱
     * @param content 邮件内容
     * @throws Exception
     */
    public void sendMail(String receiverAddr, String subject, String content) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", smtpHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        // SSL
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, mailSenderAddr, receiverAddr, subject, content);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        transport.connect(mailSenderAddr, mailSenderPwd);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    private MimeMessage createMimeMessage(Session session, String senderAddr, String receiverAddr, String subject, String content) throws UnsupportedEncodingException, MessagingException {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(senderAddr, mailSenderName, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverAddr, "用户", "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    // 创建邮件服务 Exchange2010_SP2 Exchange2007_SP1
    private ExchangeService getExchangeService() {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
//        //用户认证信息
        ExchangeCredentials credentials;
//        credentials = new WebCredentials("martin@biteinvestments.com", "Hpl2473095423");
        if (StringUtils.isEmpty(exchangeDomain)) {
            credentials = new WebCredentials(exchangeUser, exchangePassword);
        } else {
            credentials = new WebCredentials(exchangeUser, exchangePassword, exchangeDomain);
        }
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI("https://" + exchangeServer + "/ews/Exchange.asmx"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return service;
    }

    // 通过exchange协议向多人发送邮件
    public void sendMailByExchangeToMany(String subject, String[] to, String[] cc, String bodyText, String[] attachmentPaths)
            throws Exception {
        ExchangeService service = getExchangeService();

        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(subject);
        MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        for (String toPerson : to) {
            msg.getToRecipients().add(toPerson);
        }
        if (cc != null) {
            for (String ccPerson : cc) {
                msg.getCcRecipients().add(ccPerson);
            }
        }
        if (attachmentPaths != null) {
            for (String attachmentPath : attachmentPaths) {
                msg.getAttachments().addFileAttachment(attachmentPath);
            }
        }
        msg.send();
    }

    // 通过exchange协议向一人发送邮件
    public void sendMailByExchangeToOne(String subject, String to, String bodyText, String[] attachmentPaths)
            throws Exception {
        ExchangeService service = getExchangeService();

        EmailMessage msg = new EmailMessage(service);
        msg.setSubject(subject);
        MessageBody body = MessageBody.getMessageBodyFromText(bodyText);
        body.setBodyType(BodyType.HTML);
        msg.setBody(body);
        msg.getToRecipients().add(to);
        if (attachmentPaths != null) {
            for (String attachmentPath : attachmentPaths) {
                msg.getAttachments().addFileAttachment(attachmentPath);
            }
        }
        msg.send();
    }

}
