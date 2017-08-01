package com.gangSta.dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.gangSta.pojo.Disk;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;

/*
 *该接口提供 文件的各种操作
 */
public interface FileDao {
	//查询数据库，返回一个用户的所有文件 需要使用person中的email
	public List<MyFile> selectPersonFiles(Person person,int page) throws SQLException;
	//查询数据库，返回用户的文件夹，需要使用person中的email
	public File selectPersonFile(Person person) throws SQLException;
	//上传文件，需要获取request，返回一个int成果或者错误信息
	//测试通过了
	public int upFile(HttpServletRequest request,Person person) throws SQLException, FileUploadException, IOException;
	//文件下载,需要获取文件file的id和response，返回int结果
	//测试成功
	public int downFile(MyFile file,String Un,HttpServletResponse response) throws SQLException, IOException;
	//获取文件的验证码,需要文件的id。返回一个链接。
	//测试通过
	public MyFile getFileVerify(MyFile file) throws SQLException;
	//检查系统盘情况，返回一个disk
	//测试成功
	public Disk getDiskInfo();
	//返回验证码对应的文件id
	//
	public String getFilePathWithVerify(String verify) throws SQLException;
	//获得用户文件的使用情况
	public Disk getPersonDiskUsed(Person person) throws SQLException;
	//该函数用来设置用户的文件夹,用来初始化
	//测试通过
	public int insertPersonFileInfo(Person person) throws SQLException;
	//删除文件信息
	//测试成功
	public int deleteFile(MyFile myfile) throws SQLException;
	//查询总页数
	//测试成功
	public int selectFilePage(Person person)throws SQLException;
}
