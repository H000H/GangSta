package com.gangSta.util;

import java.util.Date;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MessageCreate {

	 /**
     * 创建邮件，并添加获得验证码
     * @param session
     * @param sendMail
     * @param receiveMail
     * @return
     * @throws Exception
     */
	public static String myEmailAccount = "1062646428@qq.com";
    public static String myEmailPassword = "ujkhzrhjjyebbfjh";
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static MimeMessage createMimeMessage(String vText,Session session,String receiveMail) throws Exception {
   	 	
    	// 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(myEmailAccount, "GangSta云盘", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "云盘用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("用户验证码验证", "UTF-8");
        
        
        //用于保存最终正文部分
        MimeBodyPart contentBody = new MimeBodyPart();
        // 用于组合文本和图片，"related"型的MimeMultipart对象 
        MimeMultipart contentMulti = new MimeMultipart("related");
        //正文文字部分
    	MimeBodyPart textBody = new MimeBodyPart(); 	
		// setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)    
    	textBody.setContent("此邮件为系统自动发送"+ "<img src= \"cid:b_jpg\"></a>","text/html;charset=utf-8");
    	contentMulti.addBodyPart(textBody);
		//正文图片部分
 		MimeBodyPart img = new MimeBodyPart();
 		/*
 		 * JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
 		 * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的. JavaMail
 		 * API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
 		 */
 		//获取验证码
//    	VerifyCode verifyCode = new VerifyCode();
    	//验证码图片
//        BufferedImage bi = verifyCode.getImage();
        //验证码文本信息
//        String vText = verifyCode.getText();
//        VerifyCode.output(bi, new FileOutputStream("F:\\b.jpg"));
// 		DataHandler dh = new DataHandler(new FileDataSource("F:\\b.jpg"));//图片路径
// 		img.setDataHandler(dh);
 		// 创建图片的一个表示用于显示在邮件中显示
 		img.setContentID("b_jpg");
 		contentMulti.addBodyPart(img);
 		contentBody.setContent(contentMulti);

 		MimeMultipart allPart = new MimeMultipart("mixed");
 		allPart.addBodyPart(contentBody);
 		
 		//将混合的MimeMultipart对象作为邮件内容保存
 		//message.setContent(allPart);
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("GangSta云盘用户你好, 你的验证码是:"+vText, "text/html;charset=UTF-8");
        System.out.println("发送的验证码是： "+ vText);
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
