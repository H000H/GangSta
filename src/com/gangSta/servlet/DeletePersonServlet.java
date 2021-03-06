package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gangSta.pojo.Person;
import com.gangSta.service.PersonService;

/**
 * 管路员删除用户
 * @author Administrator
 *
 */
@WebServlet("/DeletePersonServlet")
public class DeletePersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		HttpSession session = request.getSession();
		Person person = (Person) session.getAttribute("person");
		//从session中获取登录人的email和身份信息(state)
		String email = request.getParameter("email");
		int identity = person.getIdentity();
		System.out.println("该用户的身份："+identity);
		String str = personService.deletePerson(email, identity);
		PrintWriter out = response.getWriter();
		out.write(str);
		out.flush();
		out.close();
	}

}
