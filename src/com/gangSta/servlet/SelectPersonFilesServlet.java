package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;
import com.gangSta.pojo.User;

/**
 * Servlet implementation class SelectPersonFilesServlet
 */
@WebServlet("/SelectPersonFilesServlet")
public class SelectPersonFilesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectPersonFilesServlet() {
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
			String type=request.getParameter("type");
			int page=Integer.parseInt((String)request.getParameter("pagenumber"));
			if(type!=null&&!type.equals("all")){
				FileFectory fectory=new FileFectory();
				fectory.setFileFectory();
				User user=fectory.selectFileType(person, type, page);
				result=JSONObject.fromObject(user).toString();
			}else{
				//判断是否有数据
				FileFectory fectory=new FileFectory();
				fectory.setFileFectory();
				int pagenumber=fectory.selectFilePage(person);
				if(page>pagenumber)
					return;
				List<MyFile> list=fectory.selectPersonFiles(person, page);
				result=JSONObject.fromObject(new TempObject(pagenumber, list)).toString();
				fectory.closeConnection();
			}
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
	class TempObject{
		int pagenumber;
		List<MyFile> list;
		public TempObject(int p,List<MyFile> list) {
			this.pagenumber=p;
			this.list=list;
		}
	}
}
