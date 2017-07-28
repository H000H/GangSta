package com.gangStaTest;

import com.gangSta.util.MessageSend;

public class SendMailTest {

	public static void main(String[] args) {
		
		try {
			MessageSend.sendMessage("978875111@qq.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
