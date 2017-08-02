package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.Disk;
import com.gangSta.pojo.Person;

/**
 * Servlet implementation class GetDiskInfoServlet
 */
@WebServlet("/GetDiskInfoServlet")
public class GetDiskInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDiskInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result="......";
		try {
			HttpSession session=request.getSession();
			Person persontemp=(Person)session.getAttribute("person");
			//判断是否session为空
			if(persontemp.getEmail()==null||persontemp.getState()!=1)
				return;
			FileFectory fectory=new FileFectory();
			fectory.setFileFectory();
			Disk disk=fectory.getDiskInfo();
			result=JSONObject.fromObject(disk).toString();
			fectory.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			result="数据库出错啦";
		}finally{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		}
	}

}
