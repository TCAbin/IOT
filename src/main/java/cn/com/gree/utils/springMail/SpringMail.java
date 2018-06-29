package cn.com.gree.utils.springMail;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @autor 260172
 * @date 2018/6/25 15:12
 */
public class SpringMail{
    /**
     * 禁止创建对象
     */
    private SpringMail(){}

    /**
     * 邮件发送器
     */
    private static JavaMailSenderImpl mailSender;
    /**
     * 初始化
     */
    static{
        init();
    }
    /**
     * sender发送器初始化
     */
    private  static void init(){
        Properties properties = null;
        InputStream in = null;
        mailSender = new JavaMailSenderImpl();
        try {
            in = SpringMail.class.getResourceAsStream("/mail.properties");
            properties = new Properties();
            properties.load(in);
            in.close();
            //设置邮件服务器
            mailSender.setHost(properties.getProperty("mailHost"));
            //服务器端口
            mailSender.setPort(Integer.parseInt(properties.getProperty("mailPort")));
            //发送邮箱者
            mailSender.setUsername(properties.getProperty("sender"));
            //发送者密码
            mailSender.setPassword(properties.getProperty("senderPassword"));
            //默认编码
            mailSender.setDefaultEncoding("UTF-8");
            //设置邮箱协议
            mailSender.setProtocol(JavaMailSenderImpl.DEFAULT_PROTOCOL);

            // SSL验证
            properties.clear();
            properties.setProperty("mail.smtp.ssl.enable", "true"); // 启用ssl
            properties.setProperty("mail.smtp.auth","true"); // 开启账户验证
            mailSender.setJavaMailProperties(properties);

        } catch (IOException e) {
            throw new ExceptionInInitializerError("mail config init failed.");
        }
    }
    /**
     * 文本发送
     */
    public static void sendMail(String to,String title,String text){
        senderTextMail(to,null,title,text,true);
    }
    /**
     * 文本发送  带抄送人
     */
    public static void sendMail(String to,String cc,String title,String text){
        senderTextMail(to,cc,title,text,false);
    }
    /**
     * 带附件的文本发送
     */
    public static void sendMail(String to, String title, String text, List<File> files){
        senderAttachMentMail(to,null,title,text,files,true);
    }
    /**
     * 带附件的文本发送 带抄送人
     */
    public static void sendMail(String to,String cc, String title, String text, List<File> files){
        senderAttachMentMail(to,cc,title,text,files,false);
    }


    /**
     * @autor 260172
     * @date 2018/6/25 15:14
     * 文本发送函数
     */
    private static void senderTextMail(String to, String cc, String title, String text, boolean ccIsNull) {
        //创建邮件实体
        SimpleMailMessage smm = new SimpleMailMessage();
        //邮件发送者
        smm.setFrom(mailSender.getUsername());
        //邮件接收者
        if(to.contains(",")){
            smm.setTo(to.split(","));
        }else{
            smm.setTo(to);
        }
        //邮件抄送
        if(!ccIsNull){
            if(cc.contains(",")){
                smm.setCc(cc.split(","));
            }else{
                smm.setCc(cc);
            }
        }
        //设置标题
        smm.setSubject(title);
        //邮件正文
        smm.setText(text);
        //邮件发送
        mailSender.send(smm);
    }
    /**
     * @autor 260172
     * @date 2018/6/25 15:27
     * 附件发送函数
     */
    private static void senderAttachMentMail(String to, String cc, String title, String text, List<File> files, boolean ccIsNull) {
        //  mime类型的邮件实体
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //由于MimeMessage API复杂，故使用MimeMessageHelper简化代码
        try {
            MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //设置邮件发送人
            mmh.setFrom(mailSender.getUsername());
            //设置接收人
            if(to.contains(",")){
                mmh.setTo(to.split(","));
            }else{
                mmh.setTo(to);
            }
            //设置抄送人
            if(!ccIsNull){
                if(cc.contains(",")){
                    mmh.setCc(cc.split(","));
                }else{
                    mmh.setCc(cc);
                }
            }
            //设置标题
            mmh.setSubject(title);
            //设置邮件内容
            mmh.setText(text,true);
            //设置附件  并且附件总大小不能超过5M
            if(files != null && files.size() > 0){
                for(File file : files){
                    if(file.exists()){
                        mmh.addAttachment(file.getName(),file);
                    }
                }
            }
            //发送
            mailSender.send(mimeMessage);
        } catch (MailSendException e){
            throw new IllegalArgumentException(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException("send mail failed.");
        }
    }
}
