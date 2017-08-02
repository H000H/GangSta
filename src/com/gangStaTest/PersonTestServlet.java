package com.gangStaTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gangSta.dao.PersonDao;
import com.gangSta.daoImpl.PersonDaoImpl;
import com.gangSta.pojo.Notice;
import com.gangSta.pojo.Person;

@WebServlet("/PersonTestServlet")
public class PersonTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("进入serlvet");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PersonDao personDao = new PersonDaoImpl();
		Person person = new Person();
		Notice notice = new Notice();
		/**
		 * 注册验证
		 */
//		person.setEmail("1062646428@qq.com");
//		person.setName("梁煜卓");
//		person.setPassword("123456");
//		try {
//			int i= personDao.registPerson(person);
//			System.out.println(i);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		/**
		 * 登录验证
		 */
//		person.setEmail("1062646428@qq.com");
//		person.setName("梁煜卓");
//		person.setPassword("123456");
//		Person p = personDao.findByEmail(person.getEmail());
//		System.out.println(p);
//		request.getRequestDispatcher("welcome.jsp").forward(request, response);
		
		/**
		 * 改变用户状态(成功为1)
		 */
//		person.setEmail("1062646428@qq.com");
//		int i = personDao.changeState(person.getEmail());
//		System.out.println(i);
		
		/**
		 * 验证码存储
		 */
//		PersonVerify personVerify = new PersonVerify();//实例化人验证码类
//		VerifyCode verifyCode = new VerifyCode();//实例化验证码类
//    	verifyCode.getImage();
//       // 验证码文本信息(后台产生)
//        String vText = verifyCode.getText();
//        //获取前端传的email
//        String email = "565123258@qq.com";
//        try {
//			MessageSend.sendMessage(vText, email);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//        personVerify.setT_email(email);
//        personVerify.setT_verify(vText);
//        int i = personDao.saveCode(personVerify);
//        System.out.println("验证码："+vText+"  操作为： "+i);
		
		/**
		 * 验证码的获取
		 */
//		String str = personDao.checkCode("565123258@qq.com");
//		System.out.println(str);
		
		/**
		 * 根据邮箱修改密码
		 */
//		person.setEmail("978875111@qq.com");
//		String password = EncryptionPassword.getPwd("123456");
//		person.setPassword(password);
//		int i =personDao.updatePassword(person);
//		System.out.println(i);
		
		/**
		 * 根据传入的email更改其身份id 由0（普通用户）改为1（申请成为会员）
		 */
//		int i = personDao.applyForVip("1062646428@qq.com");
//		System.out.println(i);
		
		/**
		 * 根据传入的email更改其身份id 由1（申请成为会员）改为2（正式会员）
		 */
//		int i = personDao.agreeForVip("1062646428@qq.com");
//		System.out.println(i);
		
		/**
		 * (管理员功能)分页查询所有用户信息(需要传入页数)
		 */
//		List<Person> list = new ArrayList<Person>();
//		int counts = personDao.selectAllCounts();//查询总记录数
//		System.out.println("总记录数："+counts);
//		int page = personDao.getPages(counts);//得到总页数
//		System.out.println("总页数："+page);
//		list = personDao.selectAllFy(1);
//		for (Person person2 : list) {
//			System.out.println(person2);
//		}
		
		/**
		 * (管理员功能)根据email主键查询单个用户的信息
		 */
//		List<Person> list = new ArrayList<Person>();
//		list = personDao.selectPerson("974237105@qq.com");
//		for (Person person2 : list) {
//			System.out.println(person2);
//		}
		
		/**
		 * (管理员功能)管理员添加公告
		 */
//		notice.setTitle("大喜事");;
//		notice.setContent("瓜皮鑫的电脑今天中病毒了");
//		notice.setAuthor("瓜皮鑫");
//		notice.setUrl("https://www.geren.com");
//		int i =personDao.insertNotice(notice);
//		System.out.println(i);
		
		/**
		 * 所用用户的操作：查询公告(搜索最新的5条公告数据)
		 */
//		List<Notice> list = new ArrayList<>();
//		list = personDao.selectNotice();
//		for (Notice notice2 : list) {
//			System.out.println(notice2);
//		}
		
		/**
		 * 根据主键email删除用户(同时删除属于用户的file表)
		 */
//		int i = personDao.deletePerson("asdasd");
//		System.out.println(i);
	}

}
