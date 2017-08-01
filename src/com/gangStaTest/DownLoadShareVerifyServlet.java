package com.gangStaTest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gangSta.daoImpl.FileFectory;
import com.gangSta.pojo.MyFile;

/**
 * Servlet implementation class DownLoadShareVerifyServlet
 */
@WebServlet("/DownLoadShareVerifyServlet")
public class DownLoadShareVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadShareVerifyServlet() {
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
		try {
			FileFectory fectory=new FileFectory();
			fectory.setFileFectory();
			MyFile myfile=new MyFile();
			myfile.setShareverify("9702b3d784fde4b79f8b01dfd938e445");
			myfile.setEmail("442638790@qq.com");
			myfile.setFileid(fectory.getFilePathWithVerify(myfile.getShareverify()));
			String userAgent = request.getHeader("USER-AGENT"); 
			fectory.downFile(myfile, userAgent, response);
			fectory.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
