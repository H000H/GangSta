package com.gangSta.service;

import com.gangSta.dao.PersonDao;
import com.gangSta.daoImpl.PersonDaoImpl;
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
			if (person!=null) {
				i = 2;
				return i;
			}
			i = personDao.registPerson(form);//返回插入结果,成功为3
			
			return i;
		}
		
		
		public Person findByEmail(Person form) {
			
			Person person = personDao.findByEmail(form.getEmail());// _user对应数据库的user
			if (person == null)
			if (!form.getPassword().equals(person.getPassword())) {
				
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
	}

