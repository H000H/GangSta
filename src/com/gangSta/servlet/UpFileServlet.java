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
import com.gangSta.pojo.Person;

/**
 * Servlet implementation class UpFileServlet
 */
@WebServlet("/UpFileServlet")
public class UpFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpFileServlet() {
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
		String result="";
		try {
			HttpSession session=request.getSession();
			Person person=(Person)session.getAttribute("person");
			if(person==null||person.getState()!=1){
				return;
			}
			FileFectory fectory=new FileFectory();
			fectory.setFileFectory();
			switch(fectory.upFile(request, person)){
			case 3:result="添加文件成功";break;
			case 1:result="文件类型不对";break;
			case 2:result="超出大小..";break;
			}
			fectory.closeConnection();
		} catch (SQLException | FileUploadException e) {
			e.printStackTrace();
		}finally{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		}
	}

}
