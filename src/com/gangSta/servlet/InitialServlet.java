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
import com.gangSta.pojo.Disk;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;

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
			if(persontemp.getEmail()==null||persontemp.getState()!=1)
				return;
			//管理员和普通用户的操作不同
			FileFectory filefectory=new FileFectory();
			filefectory.setFileFectory();
			PersonDao personDao = new PersonDaoImpl();
			if(persontemp.getIdentity()==-1){
				Disk disk=filefectory.getDiskInfo();
				int pagenumber=personDao.getPages(personDao.selectAllCounts());
				List<Person> list=personDao.selectAllFy(1);
				result=JSONObject.fromObject(new Admin(pagenumber, persontemp, list, disk)).toString();
			}else{
				Disk disk=filefectory.getPersonDiskUsed(persontemp);
				int pagenumber=filefectory.selectFilePage(persontemp);
				List<MyFile> list=filefectory.selectPersonFiles(persontemp, 1);
				result=JSONObject.fromObject(new User(pagenumber, persontemp, list, disk)).toString();
			}
			filefectory.closeConnection();
		}catch(Exception e){
			e.printStackTrace();
			result="出错啦";
		}finally{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		}
	}
	class Admin{
		private int page;
		private Person person;
		private List<Person> list;
		private Disk disk;
		public Admin(int page,Person person,List<Person> list,Disk disk) {
			this.disk = disk;this.page = page;this.person = person;this.list = list;
		}
		public Disk getDisk() {
			return disk;
		}
		public void setDisk(Disk disk) {
			this.disk = disk;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public Person getPerson() {
			return person;
		}
		public void setPerson(Person person) {
			this.person = person;
		}
		public List<Person> getList() {
			return list;
		}
		public void setList(List<Person> list) {
			this.list = list;
		}
		
	}
	class User{
		private int page;
		private Person person;
		private List<MyFile> list;
		private Disk disk;
		public User(int page,Person person,List<MyFile> list,Disk disk) {
			this.disk = disk;this.page = page;this.person = person;this.list = list;
		}
		public Disk getDisk() {
			return disk;
		}
		public void setDisk(Disk disk) {
			this.disk = disk;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public Person getPerson() {
			return person;
		}
		public void setPerson(Person person) {
			this.person = person;
		}
		public List<MyFile> getList() {
			return list;
		}
		public void setList(List<MyFile> list) {
			this.list = list;
		}
		
	}

}
