package com.gangSta.util;

import java.util.Calendar;
import java.util.Date;

public class DateFormat {

	public static String formatDate(Date date) {

		String dateStr = null;
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// 获取年份
		int month = ca.get(Calendar.MONTH)+1;// 获取月份
		int day = ca.get(Calendar.DATE);// 获取日
		int minute = ca.get(Calendar.MINUTE);// 分
		int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
		int second = ca.get(Calendar.SECOND);// 秒
//	System.out.println("用Calendar.getInstance().getTime()方式显示时间: " + ca.getTime());
//	System.out.println("用Calendar获得日期是：" + year + "-" + month + "-" + day + "-");
//	System.out.println("用Calendar获得时间是：" + hour + "时" + minute + "分" + second + "秒");
		dateStr = year + "-" + month + "-" + day +" " + hour + "-" + minute + "-" + second;
//	System.out.println("dateStr日期是："+dateStr);
		return dateStr;

	}
	
	public static void main(String[] args) {
		Date date = new Date();
		DateFormat.formatDate(date);
	}
}
