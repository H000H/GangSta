package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.Person;
import com.gangSta.service.PersonService;

import net.sf.json.JSONArray;

/**
 * 注册功能
 * @author Administrator
 *
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		int i = 0;
		// 获取表单数据
		String password = request.getParameter("password");
		String name = request.getParameter("username");
		String email = request.getParameter("email");
		// 前端获取的验证码
		String vCode = request.getParameter("code");
		Person form = new Person();
		// 由表单获取的信息建立的Person
		form.setName(name);
		form.setEmail(email);
		form.setPassword(password);
		form.setIdentity(0);
		form.setState(0);
		form.setVerify(vCode);
		System.out.println("表单："+form);
		try {
			i = personService.registPerson(form);
			FileFectory filefectory=new FileFectory();
			filefectory.setFileFectory();
			filefectory.insertPersonFileInfo(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] array = new int[] { i };
		JSONArray json = JSONArray.fromObject(array);
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		out.flush();
		out.close();
		
	}

}
