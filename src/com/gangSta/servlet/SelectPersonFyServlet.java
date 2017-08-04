package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gangSta.pojo.Person;
import com.gangSta.service.PersonService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class SelectPersonFyServlet
 */
@WebServlet("/SelectPersonFyServlet")
public class SelectPersonFyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		List<Person> list = new ArrayList<Person>();
		int page = Integer.parseInt(request.getParameter("pagenumber"));
		list = personService.selectPersonFy(page);
		JSONArray json = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.write("{\"list\":"+json.toString()+"}");
		out.flush();
		out.close();
		
	}

}
