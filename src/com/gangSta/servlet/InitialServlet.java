package com.gangSta.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.gangSta.dao.PersonDao;
import com.gangSta.daoImpl.FileFectory;
import com.gangSta.daoImpl.PersonDaoImpl;
import com.gangSta.pojo.Admin;
import com.gangSta.pojo.Disk;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;
import com.gangSta.pojo.User;

/**
 * Servlet implementation class InitialServlet
 */
@WebServlet("/InitialServlet")
public class InitialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitialServlet() {
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
		String result="...";
		try{
			HttpSession session=request.getSession();
			Person persontemp=(Person)session.getAttribute("person");
			//判断是否session为空
			System.out.println(persontemp.getEmail()+persontemp.getIdentity());
			;
			//管理员和普通用户的操作不同
			FileFectory filefectory=new FileFectory();
			filefectory.setFileFectory();
			PersonDao personDao = new PersonDaoImpl();
			if(persontemp.getIdentity()==-1){
				Disk disk=filefectory.getDiskInfo();
				int pagenumber=personDao.getPages(personDao.selectAllCounts());
				List<Person> list=personDao.selectAllFy(1);
				Admin admin=new Admin(pagenumber, persontemp, list, disk);
				System.out.println("总页数"+pagenumber);
//				System.out.println(admin.toxxx());
				result=JSONObject.fromObject(admin).toString();
			}else{
				Disk disk=filefectory.getPersonDiskUsed(persontemp);
				int pagenumber=filefectory.selectFilePage(persontemp);
				List<MyFile> list=filefectory.selectPersonFiles(persontemp, 1);
				User user=new User(pagenumber, persontemp, list, disk);
//				System.out.println(user.toxxx());;
				result=JSONObject.fromObject(user).toString();
			}
			filefectory.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			result="出错啦";
		}finally{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			System.out.println(result);
			out.write(result);
			out.flush();
			out.close();
		}
	}

}
