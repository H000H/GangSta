package com.gangStaTest;


import com.gangSta.util.MessageSend;
import com.gangSta.util.VerifyCode;

public class SendMailTest {

	public static void main(String[] args) {
		
		try {
			//获取验证码
	    	VerifyCode verifyCode = new VerifyCode();
	    	verifyCode.getImage();
	       // 验证码文本信息
	        String vText = verifyCode.getText();
			MessageSend.sendMessage(vText,"565123258@qq.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
