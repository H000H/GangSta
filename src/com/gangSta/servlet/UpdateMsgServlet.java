package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gangSta.pojo.Person;
import com.gangSta.service.PersonService;

/**
 * 更新名字、密码或者两个都更新
 * @author Administrator
 *
 */
@WebServlet("/UpdateMsgServlet")
public class UpdateMsgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		Person form = new Person();
		//从前端获取email、name、password元素
		String email = request.getParameter("email");
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("更新servlet:"+email+" "+name+" "+password);
		//封装成person对象
		form.setEmail(email);
		form.setName(name);
		form.setPassword(password);
		System.out.println("更新servlet:"+form);
		int i = personService.updateMsg(form);
		System.out.println("i的值："+i);
		if (i>0) {
			String str = "{\"message\":\"更新成功！\"}";
			PrintWriter out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		}else {
			String str = "{\"message\":\"更新失败！\"}";
			PrintWriter out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		}
	}

}
