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

import org.apache.commons.fileupload.FileUploadException;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;

/**
 * Servlet implementation class DownLoadFileServlet
 */
@WebServlet("/DownLoadFileServlet")
public class DownLoadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadFileServlet() {
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
			Person person=(Person)session.getAttribute("person");
			//判断是否session为空
			if(person==null||person.getState()!=1)
				return;
			MyFile myfile=new MyFile();
			myfile.setFileid((String)request.getParameter("fileid"));
			myfile.setEmail(person.getEmail());
			//判断是否有数据
			if(myfile.getEmail()==null||myfile.getFileid()==null)
				return;
			FileFectory fectory=new FileFectory();
			fectory.setFileFectory();
			String userAgent = request.getHeader("USER-AGENT"); 
			fectory.downFile(myfile, userAgent, response);
			fectory.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			result="数据库出错啦";
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		}
	}
}
