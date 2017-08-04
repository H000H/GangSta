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

import net.sf.json.JSONObject;

/**
 * 登录功能
 * @author Administrator
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		Person form = new Person();
		// 获取表单数据
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		form.setEmail(email);
		form.setPassword(password);
		System.out.println("表单：" + form);
		Person person = personService.loginPerson(form);
		if (person.getName() != null) {
			HttpSession session = request.getSession();
			session.setAttribute("person", person);
		}
		// request.getRequestDispatcher("index.html").forward(request,
		// response);
		// }else{
		JSONObject json = JSONObject.fromObject(person);
		PrintWriter out = response.getWriter();
		out.write(json.toString());
		// out.write("worry");
		out.flush();
		out.close();
		// }

	}
}
