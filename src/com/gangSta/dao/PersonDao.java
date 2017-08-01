package com.gangSta.dao;

import java.util.List;

import com.gangSta.pojo.Notice;
import com.gangSta.pojo.Person;
import com.gangSta.pojo.PersonVerify;

public interface PersonDao {

	//用户注册
	public int registPerson(Person form) throws Exception;
	//用户登录
	public Person findByEmail(String email);
	//用户登录状态的查询
	public int getState(String email);
	//登录状态的改变（0、1）
	public int changeState(String email);
	//验证码存储
	public int saveCode(PersonVerify personVerify);
	//验证码的搜索
	public String checkCode(String email);
	//申请成为会员
	public int applyForVip(String email);
	//(管理员功能)同意成为会员
	public int agreeForVip(String email);
	//(管理员功能)分页查询所有用户信息
	public List<Person> selectAllFy(int page);
	//根据email主键查询单个用户的信息
	public List<Person> selectPerson(String email);
	//(管理员功能)管理员添加公告
	public int insertNotice(Notice notice);
	//查询公告
	public List<Notice> selectNotice();
	//(管理员功能)根据email删除用户功能(同时删除用户对应的文件表)
	public int deletePerson(String email);
	//查询出所有用户的数目
	public int selectAllCounts();
	//根据上面查询的总记录数获得总页数
	public int getPages(int counts);
	//根据主键email更新信息(名字  或者 密码 或者都改)
	public int updateMsg(Person person);
}
