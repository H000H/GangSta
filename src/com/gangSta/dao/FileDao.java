package com.gangSta.dao;

import java.util.List;

import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;

/*
 *该接口提供 文件的各种操作
 */
public interface FileDao {
	//web的根目录绝对地址
	public static final String WebServiceConentPath="";
	//查询
	public List<MyFile> selectPersonFiles(Person person);
	
//	public 
}
