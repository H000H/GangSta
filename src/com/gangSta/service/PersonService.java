package com.gangSta.service;

import java.util.ArrayList;
import java.util.List;

import com.gangSta.dao.PersonDao;
import com.gangSta.daoImpl.PersonDaoImpl;
import com.gangSta.pojo.Notice;
import com.gangSta.pojo.Person;
import com.gangSta.pojo.PersonVerify;

public class PersonService {

		//实例化一个PersonDaoImpl，供一些方法操作
		private PersonDao personDao = new PersonDaoImpl();
		
		/**
		 * 注册方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>0:添加失败</p>
		 * <p>1：验证码错误</p>
		 * <p>2：邮箱已经注册</p>
		 * <p>3：添加成功</p>
		 */
		public int registPerson(Person form) throws Exception {

			Person person = new Person();
			int i = 0;
			//对前端传送的vCode进行判断,若验证码不同，返回1
			String t_mail = personDao.checkCode(form.getEmail());
			if (!t_mail.equals(form.getVerify())) {
				i = 1;
				return i;
			}
			// person对应从数据库查询出来的的person
			//不为空表示该email已经被注册,返回参数2
			person = personDao.findByEmail(form.getEmail());
			if (person.getEmail()!=null) {
				i = 2;
				return i;
			}
			i = personDao.registPerson(form);//返回插入结果,成功为3
			
			return i;
		}
		
		/**
		 * 登录方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>0:添加失败</p>
		 * <p>1：验证码错误</p>
		 * <p>2：邮箱已经注册</p>
		 * <p>3：添加成功</p>
		 */
		public Person loginPerson(Person form) {
			
			Person person = personDao.findByEmail(form.getEmail());// _user对应数据库的user
			//判断email是否存在
			if (person.getEmail() == null) {
				person.setEmail("用户不存在");
				return person;
			}
			//判断用户密码是否错误
			if (!form.getPassword().equals(person.getPassword())) {
				person.setEmail("用户密码错误");
				return person;
			}
			String email = person.getEmail();
			int state = personDao.getState(email);
			//判断用户是否已经登录
			if (state==1) {
				person.setEmail("用户已经登录，不能重复登录！");
			}
			return person;
		}
		
		
		/**
		 * 存储验证码至数据库
		 * @param personVerify
		 * @return
		 */
		public PersonVerify saveCode(PersonVerify personVerify) {
			
			personDao.saveCode(personVerify);
			return personVerify;
		}
		
		/**
		 * 改变用户的登录状态
		 * <p>
		 * 在登录后将0改为1，退出登录后将1改为0
		 * </p>
		 */
		public int changeState(String email) {
			
			personDao.changeState(email);
			return 1;
		}
		
		/**
		 * 更改信息方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>0:添加失败</p>
		 * <p>1：更改成功</p>
		 */
		public int updateMsg(Person person) {
			
			int i = 0;
			i = personDao.updateMsg(person);
			return i;
		}
		
		/**
		 * 申请成为会员方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>-1:管理员</p>
		 * <p>0：普通用户</p>
		 * <p>1：申请会员用户</p>
		 * <p>2：正式会员</p>
		 */
		public String applyForVip(String email,int state) {
			int i = 0;
			String str = null;
			//管理员为-1,只有0(普通用户才能申请)
			switch (state) {
			case 0:
				i = personDao.applyForVip(email);
				if (i>0) {
					str = "{\"message\":\"申请成功\"}";
					return str;
				}
				break;
			case 1:
				str = "{\"message\":\"你已经申请过了，待管理员处理\"}";
				break;
			case 2:
				str = "{\"message\":\"你已经已经是会员了\"}";
				break;
			}
			return str;
		}
		
		/**
		 * 管理员批准成为会员方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>-1:管理员</p>
		 * <p>0：普通用户</p>
		 * <p>1：申请会员用户</p>
		 * <p>2：正式会员</p>
		 */
		public String agreeForVip(String email,int state) {
			String str = null;
			//管理员为-1,只有0(普通用户才能申请)
			if (state == -1) {
				int i = personDao.agreeForVip(email);
				if (i>0) {
					str = "{\"message\":\"批准成功\"}";
					return str;
				}else {
					str = "{\"message\":\"批准失败\"}";
					return str;
				}
			}else {
				str = "{\"message\":\"你不是管理员不能修改\"}";
			}
			return str;
		}
		
		/**
		 * 管理员添加公告方法
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>-1:管理员</p>
		 * <p>0：普通用户</p>
		 * <p>1：申请会员用户</p>
		 * <p>2：正式会员</p>
		 */
		public String insertNotice(Notice notice,int state) {
			String str = null;
			//管理员为-1,只有0(普通用户才能申请)
			if (state == -1) {
				int i = personDao.insertNotice(notice);
				if (i>0) {
					str = "{\"message\":\"添加成功\"}";
					return str;
				}else {
					str = "{\"message\":\"添加失败\"}";
					return str;
				}
			}else {
				str = "{\"message\":\"你不是管理员不能添加\"}";
			}
			return str;
		}
		
		/**
		 * 用户获取公告方法
		 * 获取最新的10条数据
		 * @param form
		 * @return
		 * @throws Exception
		 */
		public List<Notice> selectNotice(){
			
			List<Notice> list = new ArrayList<Notice>();
			list = personDao.selectNotice();
			return list;
		}
		
		/**
		 * 管理员删除用户方法
		 * 同时删除属于用户的file表
		 * @param form
		 * @return
		 * @throws Exception
		 * <错误提示>
		 * <p>-1:管理员</p>
		 * <p>0：普通用户</p>
		 * <p>1：申请会员用户</p>
		 * <p>2：正式会员</p>
		 */
		public String deletePerson(String email,int state) {
			String str = null;
			//管理员为-1,只有0(普通用户才能申请)
			if (state == -1) {
				int i = personDao.agreeForVip(email);
				if (i>0) {
					str = "{\"message\":\"删除成功\"}";
					return str;
				}else {
					str = "{\"message\":\"删除失败\"}";
					return str;
				}
			}else {
				str = "{\"message\":\"你不是管理员不能删除\"}";
			}
			return str;
		}
	}

