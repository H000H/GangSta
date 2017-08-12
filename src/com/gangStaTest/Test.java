package com.gangStaTest;

public class Test {
	public static void main(String[] args) {
		String file="C://hollle.ppt//hoole.ppt";
		file=file.substring(file.lastIndexOf('/')+1);
		System.out.println(file.substring(0, file.lastIndexOf('.')));
	}
}
