package com.gangSta.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gangSta.dao.FileDao;
import com.gangSta.pojo.Disk;
import com.gangSta.pojo.MyFile;
import com.gangSta.pojo.Person;
import com.gangSta.util.CreatMD5;
import com.gangSta.util.DBConnection;

/*
 * 使用后需要手动的关闭conn
 */
public class FileFectory implements FileDao{
	//web的根目录绝对地址
	public static final String WebServiceConentPath="C:\\Users\\new\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\GangSta";
	public Connection conn;
	public FileFectory() {
	}
	public void setFileFectory() throws SQLException{
		conn=DBConnection.getConnection();
	}
	public void closeConnection() throws SQLException{
		conn.close();
	}
	protected void  closefun1(ResultSet rs,PreparedStatement pstt) throws SQLException{
		if(rs!=null)
			rs.close();
		pstt.close();
	}
	
	//查询数据库，返回一个用户的所有文件 需要使用person中的email
	@Override
	public List<MyFile> selectPersonFiles(Person person,int page) throws SQLException {
		int thispage=page*5;
		int ppage=thispage-4;
		
		String sql="SELECT t.*,GangStaID FROM "
				+ "(SELECT A.*, A.rowid GangStaID,rownum RN FROM (SELECT * FROM GangStaFile) A WHERE rownum <=?) t "
				+ " WHERE RN >= ? and email=?";
		PreparedStatement pst=null;
		List<MyFile> list=new ArrayList<>();
		MyFile file;
		pst=conn.prepareStatement(sql);
		pst.setInt(1, thispage);
		pst.setInt(2,ppage);
		pst.setString(3, person.getEmail());
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			file=new MyFile();
			file.setEmail(rs.getString(1));
			file.setFilename(rs.getString(2));
			file.setSize(rs.getLong(3));
//			file.setUpdate(new java.util.Date(rs.getDate(4).getTime()));
			file.setUpdate(rs.getTimestamp(4).toString());
			file.setShareverify(rs.getString(6));
			file.setFileid(rs.getString(7));
			list.add(file);
		}
		closefun1(rs, pst);
		return list;
	}
	
	//查询数据库，返回用户的文件夹，需要使用person中的email
	/*
	 * 第一步：获取用户对应的文件夹地址
	 * 第二步：判断是否该文件夹存在，如果不存在则添加数据库并且创建
	 * 第三步：获取fileFectory
	 * 第四步：对文件流进行处理，判断是否超出大小
	 * 第五步：判断上传之后讲该信息填入数据库
	 * @see com.gangSta.dao.FileDao#selectPersonFile(com.gangSta.pojo.Person)
	 */
	@Override
	public File selectPersonFile(Person person) throws SQLException {
		String sql="select fpath from GangStaFileTable where email=?";
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1, person.getEmail());
		ResultSet rs=pst.executeQuery();
		String fpath=null;
		File file=null;
		if(rs.next()){
			fpath=rs.getString(1);
			//根据文件地址获得文件
			file= new File(WebServiceConentPath+"\\"+fpath);
		}
		closefun1(rs, pst);
		return file;
	}

	
	//上传文件，需要获取request和person中的email，返回一个int成果或者错误信息
	/*返回1 文件上传类型不对
	 * 返回2 超出大小
	 * 返回3
	 */
	@SuppressWarnings("unchecked")
	public int upFile(HttpServletRequest request,Person person) throws SQLException, FileUploadException, IOException {
		
		//保存路径
		File ttfile=selectPersonFile(person);
		String savePath=ttfile.getAbsolutePath();
		//临时路径,这个需要后期设置
		String tempPath=WebServiceConentPath+"\\temp";
		//这个是一个特殊的值。
		String special=null;
		//这个file对象用来存储东西
		MyFile dbfile=new MyFile();
		dbfile.setEmail(person.getEmail());
//		//该表示用来标示错误
//		int worry=0;
		//文件上传操作
		//上传时生成的临时文件保存目录
		File tmpfile = new File(tempPath);
		System.out.println("建立了临时目录");
		//判断存储的文件情况
		if(savePath==null||savePath.equals("")){
			MyFile tpfile=new MyFile();
			tpfile.setEmail(person.getEmail());
			insertFileInfo(tpfile);
			savePath=WebServiceConentPath+"\\"+person.getEmail().substring(0,person.getEmail().indexOf("@"));
			File tmpsavepath=new File(savePath);
			tmpsavepath.mkdir();
		}
		//判断上传文件的临时目录是否存在
		if (!tmpfile.exists() ) {
			System.out.println(savePath+"目录不存在，需要创建");
			//创建目录
			tmpfile.mkdir();
		}
		//使用Apache文件上传组件处理文件上传步骤：
		//1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024*50);
		factory.setRepository(tmpfile);
		//2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		//解决上传文件名的中文乱码
		upload.setProgressListener(new ProgressListener(){
			public void update(long pBytesRead, long pContentLength, int arg2) {
				System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);}
			});			
		upload.setHeaderEncoding("UTF-8"); 
		//3、判断提交上来的数据是否是上传表单的数据
		if(!ServletFileUpload.isMultipartContent(request)){
			//按照传统方式获取数据
			System.out.println("不是上传表单的数据，上传类型不对");
			//错误返回 1-上传数据类型不对		
			return 1;
		}
			//最大大小为100MB
		upload.setFileSizeMax(1024*1024*100);
		upload.setSizeMax(1024*1024*100);
		//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		List<FileItem> list = upload.parseRequest(request);
		for(FileItem item : list){
			//如果fileitem中封装的是普通输入项的数据
//			if(item.isFormField()){
//				String name = item.getFieldName();
//				//解决普通输入项的数据的中文乱码问题
//				special = item.getString("UTF-8");
//				//value = new String(value.getBytes("iso8859-1"),"UTF-8");
//			}
//			else{//如果fileitem中封装的是上传文件
				//得到上传的文件名称，
				String filename = item.getName();
				System.out.println("weishamieyou zheg ");
				System.out.println(filename);
//				if(filename==null || filename.trim().equals("")){
//					continue;
//				}
				Disk disk=getPersonDiskUsed(person);
				//超出大小,返回错误码为2
				if(disk.getAll()<=disk.getUsed()+item.getSize()){
					return 2;
				}
				dbfile.setSize(item.getSize());
				
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
			    filename = filename.substring(filename.lastIndexOf("\\")+1);
			    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
			    dbfile.setFilename(filename);
			    //这里需要一个文件修改；
			    
			    
			    //
			    //获取item中的上传文件的输入流
			    InputStream in = item.getInputStream();
			    //创建一个文件输出流             
			    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
			    //创建一个缓冲区
			     byte buffer[] = new byte[1024];
			     //判断输入流中的数据是否已经读完的标识        
			     int len = 0;
			     //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
			     while((len=in.read(buffer))>0){
			            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
			            out.write(buffer, 0, len);
			     }
			          //关闭输入流
			     in.close();
			     //关闭输出流
			     out.close();
			     //这个是用来处理用来修改数据库
			     System.out.print(insertFileInfo(dbfile));
//				}
			}
		//正确返回结果为3
		return 3;
	}

	/*
	 * 第一步：查询数据库该文文件的fileid，文件路径
	 * 第二部：下载该文件
	 * 第三部返回错误信息
	 */
	@Override
	public int downFile(MyFile myfile, String userAgent,HttpServletResponse response) throws SQLException, IOException {
		//查询数据库该文文件的fileid，文件路径
		String filepath=getFilePathwithid(myfile);
		//第二部：下载该文件
		File file = new File(filepath);
		if(!file.exists()){
			return 1;
		}
		//获取header中的浏览器类型
//		String userAgent = request.getHeader("USER-AGENT"); 
		String filename=filepath.substring(filepath.lastIndexOf("\\")+1);
		filename=URLEncoder.encode(filename, "UTF8");
		String realname=null;
		if (userAgent != null)  
		{  
		     userAgent = userAgent.toLowerCase();  
		      // IE浏览器，只能采用URLEncoder编码  
		     if (userAgent.indexOf("msie") != -1)  
		    {  
		       realname = "=\"" + filename + "\"";  
		    }  
		     // Opera浏览器只能采用filename*  
		     else if (userAgent.indexOf("opera") != -1)  
		     {  
		    	 realname = "*=UTF-8''" +filename;  
		    }  
		    // Safari浏览器，只能采用ISO编码的中文输出  
		      else if (userAgent.indexOf("safari") != -1 )  
		      {  
		    	  realname = "=\"" + new String(filename.getBytes("UTF-8"),"ISO8859-1") + "\"";  
		      }  
		      // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出  
		      else if (userAgent.indexOf("chrome") != -1 )  
		       {  
		    	  filename = MimeUtility.encodeText(filename, "UTF8", "B");  
		         realname = "=\"" + filename + "\"";  
		       }  
		      // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出  
		       else if (userAgent.indexOf("firefox") != -1)  
		       {  
		    	   realname = "*=UTF-8''" + filename;  
		      }  
		   }  
//		System.out.println(fileName);
//		System.out.println(userAgent);
//		System.out.println(realname);
		 response.setHeader("content-disposition", "attachment;filename" +realname );
		 FileInputStream in = new FileInputStream(filepath);
		 OutputStream out = response.getOutputStream();
		 byte buffer[] = new byte[1024];
		 int len = 0;
		 while((len=in.read(buffer))>0){
			 //输出缓冲区的内容到浏览器，实现文件下载
			 out.write(buffer, 0, len);
			 }
		 //关闭文件输入流
		 in.close();
		 //关闭输出流
		 out.close();
		//第三部返回错误信息
		return 0;
	}

	/*
	 * 第一步：先加密
	 * 第二部：将加密的代码输入到数据库中，
	 * 最后：返回加密的代码
	 * 从数据库获取文件的验证码
	 */
	@Override
	public MyFile getFileVerify(MyFile file) throws SQLException {
		//获得md5加密代码
		file.setShareverify(CreatMD5.getMd5(file.getFileid()));
		//加入数据库中
		int result=updateFileVerify(file);
		//返回代码
		if(result==0){
			System.out.print("it is worry");
			file=null;
		}
		return file;
	}
	
	/*
	 * 更新分享文件的的验证码,使用的是file.rowid
	 */
	protected int updateFileVerify(MyFile myfile) throws SQLException{
		String sql="update GangStaFile set shareverify=? where rowid=?";
		PreparedStatement pst=null;
		int result;
		pst=conn.prepareStatement(sql);
		pst.setString(1, myfile.getShareverify());
		pst.setString(2, myfile.getFileid());
		result=pst.executeUpdate();
		closefun1(null, pst);
		return result;
	}
	
	/*
	 * 该方法用来通过文件id获取文件路径
	 */
	public String getFilePathwithid(MyFile file) throws SQLException{
		String sql="select f.filename,t.fpath from GangStaFile f,GangStaFileTable t where t.email=f.email and f.rowid=?";
		PreparedStatement pst=null;
		String path=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1, file.getFileid());
		ResultSet rs=pst.executeQuery();
		while(rs.next()){
			path=WebServiceConentPath+"\\"+rs.getString(2)+"\\"+rs.getString(1);
		}
		closefun1(rs, pst);
		return path;
	}

	@Override
	public Disk getDiskInfo() {
		Disk disk=new Disk();
		File diskfile=new File(FileFectory.WebServiceConentPath);
		disk.setAll(1024*1024*1024*100);
		disk.setUsed(diskfile.length());
		return disk;
	}
	
	/*
	 * 这个函数用来向讲上传的文件信息输入到file表，修改table表的文件夹大小
	 */
	protected int insertFileInfo(MyFile myfile) throws SQLException{
		String sqlInsert="insert into GangStaFile values(?,?,?, SYSDATE,SYSDATE,null)";
		String sqlUpdate="update GangStaFileTable set fsize=fsize+? where email=?";
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		pst1=conn.prepareStatement(sqlInsert);
		pst1.setString(1, myfile.getEmail());
		pst1.setString(2,myfile.getFilename());
		pst1.setLong(3, myfile.getSize());
		int a=0;
		a=pst1.executeUpdate();
		pst2=conn.prepareStatement(sqlUpdate);
		pst2.setLong(1, myfile.getSize());
		pst2.setString(2, myfile.getEmail());
		int b=10;
		b=pst2.executeUpdate();
		closefun1(null, pst1);
		closefun1(null, pst2);
		return a+b;
	}

	@Override
	public String getFilePathWithVerify(String verify) throws SQLException {
		String sql="select rowid from GangStaFile where shareverify=?";
		String result=null;
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1, verify);
		ResultSet rs=pst.executeQuery();
		rs.next();
		result=rs.getString(1);
		closefun1(rs, pst);
		return result;
	}
	
	@Override
	public Disk getPersonDiskUsed(Person person) throws SQLException {
		String sql="select t.fsize,p.identity from GangStaFileTable t, GangStaPerson p where t.email=p.email and p.email=?";
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1, person.getEmail());
		ResultSet rs=pst.executeQuery();
		Disk disk=new Disk();
		int identify=-1;
		File file=null;
		if(rs.next()){
			identify=rs.getInt(2);
			disk.setUsed(rs.getLong(1));
			//根据文件地址获得文件
			switch(identify){
			case 0:disk.setAll(1024*1024*1024);break;
			case 1:disk.setAll(1024*1024*1024);break;
			case 2:disk.setAll(1024*1024*1024*5);break;
			}
		}
		closefun1(rs, pst);
		return disk;
	}
	/*
	 *第一步：检查是否已经有该邮箱
	 *第二步：get邮箱的前几位作为文件名
	 *第三部：建立文件夹
	 *第四部：insert语句
	 */
	@Override
	public int insertPersonFileInfo(Person person) throws SQLException {
		String sql="select * from GangStaPerson where email=?";
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1, person.getEmail());
		ResultSet rs=pst.executeQuery();
		int result=-1;
		if(rs.next()){
			String path=createFile(person.getEmail());
			path=path.substring(path.lastIndexOf("\\")+1);
			String insertSql="insert into GangStaFileTable values(?,?,0)";
			PreparedStatement pst1=null;
			pst1=conn.prepareStatement(insertSql);
			pst1.setString(1, person.getEmail());
			pst1.setString(2, path);
			result=pst1.executeUpdate();
			System.out.print(result);
			closefun1(null, pst1);
		}
		closefun1(rs, pst);
		return result;
	}
	
	protected String createFile(String email){
		String path=WebServiceConentPath+"\\"+email.substring(0,email.indexOf("@"));
		File file=new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		return path;	
	}
	@Override
	public int deleteFile(MyFile myfile) throws SQLException {
		String path=getFilePathwithid(myfile);
		System.out.print(path);
		int ruselt=0;
		if(path==null){
			ruselt= 1;
		}
		if(2!=deleteFileDB(myfile))
			ruselt= 3;
		File file=new File(path);
		boolean f=file.delete();
		if(f)
			ruselt= 0;
		else
			ruselt= 2;
		return ruselt;
	}
	
	public int deleteFileDB(MyFile myfile)throws SQLException{
		String sql="delete gangStaFile where rowid=?";
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1,myfile.getFileid());
		int result=0+pst.executeUpdate();
		closefun1(null, pst);
		
		String sql1="update GangstaFileTable set fsize=fsize-(select fsize from Gangstafile where rowid=?) where email in(select email from GangStaFile where rowid=?)";
		PreparedStatement pst1=null;
		pst1=conn.prepareStatement(sql1);
		pst1.setString(1,myfile.getFileid());
		pst1.setString(2,myfile.getFileid());
		result+=pst1.executeUpdate();
		closefun1(null, pst1);
		return result;
	}
	@Override
	public int selectFilePage(Person person) throws SQLException {
		String sql="select count(rownum) from gangStaFile where email=?";
		PreparedStatement pst=null;
		pst=conn.prepareStatement(sql);
		pst.setString(1,person.getEmail());
		int result;
		ResultSet rs=pst.executeQuery();
		rs.next();
		result=rs.getInt(1);
		result/=10;
		closefun1(null, pst);
		return ++result;
	}
	
}
