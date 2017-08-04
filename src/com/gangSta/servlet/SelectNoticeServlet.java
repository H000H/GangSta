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

import com.gangSta.pojo.Notice;
import com.gangSta.service.PersonService;

import net.sf.json.JSONArray;

/**
 * 查询最新的10条公告数据
 * @author Administrator
 *
 */
@WebServlet("/SelectNoticeServlet")
public class SelectNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");// 请求编码(post)
		response.setContentType("text/html;charset=utf-8");// 响应编码
		PersonService personService = new PersonService();
		List<Notice> list = new ArrayList<Notice>();
		list = personService.selectNotice();
		JSONArray json = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.write("{\"content\":"+json.toString()+"}");
		System.out.println("servlet中的："+json.toString());
		out.flush();
		out.close();
	}

}
