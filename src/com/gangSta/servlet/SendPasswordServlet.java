package com.gangSta.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gangSta.pojo.PersonVerify;
import com.gangSta.service.PersonService;
import com.gangSta.util.MessageSend;
import com.gangSta.util.VerifyCode;

@WebServlet("/SendPasswordServlet")
public class SendPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//请求编码(post)
		response.setContentType("text/html;charset=utf-8");//响应编码
		PersonService personService = new PersonService();
        //获取前端传的email
        String email = request.getParameter("******");
        String pwd = personService.getPassword(email);
        try {
			MessageSend.sendMessage(pwd, email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
