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

@WebServlet("/SaveCodeServlet")
public class SaveCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");//请求编码(post)
		response.setContentType("text/html;charset=utf-8");//响应编码
		PersonVerify personVerify = new PersonVerify();//实例化人验证码类
		PersonService personService = new PersonService();
		VerifyCode verifyCode = new VerifyCode();//实例化验证码类
    	verifyCode.getImage();
       // 验证码文本信息(后台产生)
        String vText = verifyCode.getText();
        //获取前端传的email
        String email = request.getParameter("r_email");
        try {
			MessageSend.sendMessage(vText, email);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        personVerify.setT_email(email);
        personVerify.setT_verify(vText);
        personService.saveCode(personVerify);
        
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
