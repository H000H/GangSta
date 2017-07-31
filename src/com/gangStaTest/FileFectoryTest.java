package com.gangStaTest;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.fileupload.FileUploadException;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;
import com.gangSta.util.DBConnection;

public class FileFectoryTest {
	/*
	 * @param 用来测试用户文件列表：selectPersonFiles功能
	 */
//	public static void main(String[] args) {
//		
//	}
	
	/*
	 * @param 测试获得用户文件夹地址功能：selectPersonFile
	 */
//	public static void main(String[] args) {
//		
//	}
	
	/*
	 * @param 测试上传文件操作：upFile
	 */
//	public static void main(String[] args) {
//		File file =new File(FileFectory.WebServiceConentPath+"\\442638790");
//		System.out.print(file.getAbsolutePath());
//	}
	/*
	 * @param 测试下载文件操作：downFile
	 */
//	public static void main(String[] args) {
//		
//	}
	/*
	 * @param 测试获取文件验证码功能：getFileVerify
	 */
//	public static void main(String[] args) {
//		try {
//			FileFectory fectory=new FileFectory();
//			fectory.setFileFectory();
//			MyFile myfile =new MyFile();
//			myfile.setEmail("442638790@qq.com");
//			myfile.setFileid("AAASOAAAEAAAAI2AAA");
//			MyFile vfile=fectory.getFileVerify(myfile);
//			fectory.closeConnection();
//			String link="http://localhost:8080/GangSta/GetFileWithVerifyServletTest?verify="+vfile.getShareverify();
//			System.out.println(link);
//		} catch (SQLException e){
//			System.out.print("数据库查询出错");
//		}
//	}
	/*
	 * @param 测试获取使用验证码get文件路径getFilePathWithVerify
	 */
//	public static void main(String[] args) {
//		
//	}
	/*
	 * @param 测试用户使用网盘使用情况：getPersonDiskUsed
	 */
//	public static void main(String[] args) {
//		
//	}
	/*
	 * @param 测试添加文件信息的功能：insertPersonFileInfo
	 * 该功能成功
	 */
//	public static void main(String[] args) {
//		try{
//			
//			FileFectory fectory=new FileFectory();
//			fectory.setFileFectory();
//			Person person=new Person();
//			person.setEmail("442638790@qq.com");
//			switch(fectory.insertPersonFileInfo(person)){
//			case 1:System.out.println("添加用户文件表成功");break;
//			case -1:System.out.println("添加用户文件表失败");break;
//			case 0:System.out.println("添加用户文件表....");break;
//			}
//			fectory.closeConnection();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	/*
	 * @param 测试删除文件的功能：deleteFile
	 */
//	public static void main(String[] args) {
//		try {
//			FileFectory fectory=new FileFectory();
//			fectory.setFileFectory();
//			MyFile myfile =new MyFile();
//			myfile.setFileid("AAASOAAAEAAAAI2AAC");
//			switch(fectory.deleteFile(myfile)){
//				case 1:System.out.println("文件不存在");break;
//				case 2:System.out.println("删除文件失败");break;
//				case 0:System.out.println("删除成功");break;
//			}
//			fectory.closeConnection();
//		} catch (SQLException e){
//			System.out.print("数据库查询出错");
//		}
//	}
	
//	public static void main(String[] args) throws SQLException {
//		
//		Connection con = DBConnection.getConnection();
//		System.out.println(con);
//		
//		
//	}
//	public static void main(String[] args) {
//		String email="c:\\442638790\\sdads\\qq.com";
//		int end=email.indexOf('@');
//		String path1=email.substring(email.lastIndexOf("\\")+1);
//		String path=email.substring(0,email.indexOf("@"));
//		System.out.print(path1);
//		System.out.print(email);
		
//		String path="C:\\12334";
//		File file=new File(path);
//		if(!file.exists()){
//			file.mkdir();
//		}
//	}
	/*
	 * @param 测试范湖总页数的操作功能：selectFilePage
	 */
//	public static void main(String[] args) {
//		try {
//			FileFectory fectory=new FileFectory();
//			fectory.setFileFectory();
//			Person person=new Person();
//			person.setEmail("442638790@qq.com");
//			int pagenumber=fectory.selectFilePage(person);
//			fectory.closeConnection();
//			System.out.println(pagenumber);
//		} catch (SQLException e){
//			System.out.print("数据库查询出错");
//		}
//	}
	
}
