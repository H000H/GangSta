package com.gangSta.servlet;

import com.gangSta.pojo.Person;

public class testss {

	public static void main(String[] args) {
		Person person = new Person();
//		person.setEmail("1062626@qq.com");
		person.setName("sadsda");
		person.setPassword("sadsdas");
		StringBuilder baseSql = new StringBuilder("UPDATE GANGSTAPERSON SET  ");
		StringBuilder intsertSql = new StringBuilder("");
		StringBuilder comaSql = new StringBuilder(" , ");
		StringBuilder whereSql = new StringBuilder(" where email = ?");
		//书名查询
		String name = person.getName();
		String password = person.getPassword();
		boolean a = name!=null&&!name.trim().isEmpty();
		boolean b = password!=null&&!password.trim().isEmpty();
		//若名字存在则添加该语句
		if (a) {
			intsertSql.append("name="+name);
		}
		//若都存在则添加逗号
		if (a&&b) {
			intsertSql.append(comaSql);
		}
		//若密码存在则添加该语句
		if (b) {
			intsertSql.append(" password = "+password);
		}
		String sql = baseSql.append(intsertSql).append(whereSql).toString();
		String str = "{\"message\":\"更新成功！\"}";
		System.out.println(sql);
		System.out.println(str);
	}
}
