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


import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;

/**
 * Servlet implementation class DeleteFileServlet
 */
@WebServlet("/DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFileServlet() {
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
			MyFile myfile =new MyFile();
			if(person.getIdentity()==-1){
				myfile.setEmail((String)request.getParameter("email"));
			}else{
				myfile.setEmail(person.getEmail());
			}	
			myfile.setFileid((String)request.getParameter("fileid"));
			FileFectory fectory=new FileFectory();
			fectory.setFileFectory();
			System.out.println(myfile.getEmail()+myfile.getFileid());
			System.out.println(myfile.getEmail()+myfile.getFileid());
			switch(fectory.deleteFile(myfile)){
				case 1:result="文件不存在";break;
				case 2:result="删除文件失败";break;
				case 0:result="删除成功";break;
				case 3:result="数据库删除没有成功";break;
			}
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
