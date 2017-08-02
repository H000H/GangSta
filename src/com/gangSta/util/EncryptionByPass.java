package com.gangSta.util;

import java.io.UnsupportedEncodingException;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder; 
public class EncryptionByPass {

	 // 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    public static void main(String[] args) {
		String str = "_12_56";
		String str1 = EncryptionByPass.getBase64(str);
		System.out.println("原密码："+str);
		System.out.println("加密后密码："+str1);
		System.out.println("解密后密码："+EncryptionByPass.getFromBase64(str1));
	
	}
	}
