package com.gangSta.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gangSta.dao.PersonDao;
import com.gangSta.pojo.Notice;
import com.gangSta.pojo.Person;
import com.gangSta.pojo.PersonVerify;
import com.gangSta.util.DBConnection;

public class PersonDaoImpl implements PersonDao {
	

	/**
	 * 用户注册（添加用户）
	 * 
	 * 注册成功返回i，否则返回0
	 */
	@Override
	public int registPerson(Person person) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 3;
		String sql = "insert into GANGSTAPERSON(NAME,EMAIL,PASSWORD,IDENTITY,VERIFY,STATE) " + "values (?,?,?,?,?,?)";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, person.getName());
			pstmt.setString(2, person.getEmail());
			pstmt.setString(3, person.getPassword());
			pstmt.setInt(4, 0);
			pstmt.setString(5, person.getVerify());
			pstmt.setInt(6, 0);
			i = pstmt.executeUpdate();
			// 如果sql语句执行不成功，则返回0
			if (i < 0) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 如果sql语句执行成功，则返回i
		return i;

	}

	/**
	 * 用户登录功能，根据email查询该用户的信息
	 * 
	 * 返回一个person对象
	 */
	@Override
	public Person findByEmail(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Person person = new Person();
		try {
			con = DBConnection.getConnection();
			String sql = "SELECT * FROM GANGSTAPERSON WHERE EMAIL=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
//			/*
//			 * 把rs转换成User类型；返回
//			 */
//			if (rs == null) {
//				return null;
//			}
			while (rs.next()) {
				// 转换成Person对象并返回
				person.setName(rs.getString("NAME"));
				person.setEmail(rs.getString("EMAIL"));
				person.setPassword(rs.getString("PASSWORD"));
				person.setIdentity(rs.getInt("IDENTITY"));
				person.setVerify(rs.getString("VERIFY"));
				person.setState(rs.getInt("STATE"));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return person;
	}

	/**
	 * 改变用户的登录状态
	 * <p>
	 * 在登录后将0改为1，退出登录后将1改为0
	 * </p>
	 */
	@SuppressWarnings("resource")
	@Override
	public int changeState(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int state = 0;
		try {
			con = DBConnection.getConnection();
			// 搜索出email对应的state的状态
			String sql = "select state from GANGSTAPERSON where email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				state = Integer.parseInt(rs.getString(1));
			}
			sql = "update GANGSTAPERSON set  state = ? where email = ?";
			pstmt = con.prepareStatement(sql);
			if (state == 0) {
				pstmt.setInt(1, 1);
			} else {
				pstmt.setInt(1, 0);
			}
			pstmt.setString(2, email);
			pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return 1;
	}

	/**
	 * 查询用户的登录状态(若为1则不能登录，若为0则可以登录)
	 */
	@Override
	public int getState(String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int state = 0;
		try {
			con = DBConnection.getConnection();
			// 搜索出email对应的state的状态
			String sql = "select state from GANGSTAPERSON where email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				state = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return state;
	}
	
	
	/**
	 * 验证码的获取，将发送的验证码存储进数据库以方便注册使用
	 * 成功返回1
	 */
	@SuppressWarnings("resource")
	@Override
	public int saveCode(PersonVerify personVerify) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String email = personVerify.getT_email();
		String vText = personVerify.getT_verify();
		try {
			con = DBConnection.getConnection();
			String sql = "select * from GANGSTATEMP where t_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			//若email已经存在，则a为true，不存在为false
			boolean a = rs.next();
			System.out.println("rs.next():"+a);
			if (a) {
				//email已经存在，进行更新操作
				sql = "update GANGSTATEMP set T_VERIFY = ? where t_email = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, vText);
				pstmt.setString(2, email);
				pstmt.execute();
				System.out.println("更新操作");
			}else {
				//email不存在，进行插入操作
				sql = "insert into GANGSTATEMP(t_email,T_VERIFY) values(?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				pstmt.setString(2, vText);
				pstmt.execute();
				System.out.println("插入操作");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return 1;

	}

	/**
	 * 根据用户输入的email对验证码表进行搜索 返回一个字符串（验证码）
	 */
	@Override
	public String checkCode(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String t_email = null;
		try {
			con = DBConnection.getConnection();

			String sql = "select t_verify from GANGSTATEMP where t_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs == null) {
				return null;
			}
			if (rs.next()) {
				t_email = rs.getString(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}
		return t_email;
	}

	/**
	 * 根据传入的email更改其身份id 由0（普通用户）改为1（申请成为会员）
	 */
	@Override
	public int applyForVip(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int i= 0;
		try {
			con = DBConnection.getConnection();
			String sql = "UPDATE GANGSTAPERSON SET identity = 1 " + "WHERE email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}

		return i;
	}

	/**
	 * 根据传入的email更改其身份id 由1（申请成为会员）改为2（正式会员）
	 */
	public int agreeForVip(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			con = DBConnection.getConnection();
			String sql = "UPDATE GANGSTAPERSON SET identity = 2 " + "WHERE email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}

		return i;

	}

	/**
	 * (管理员功能)分页查询所有用户信息
	 */
	@Override
	public List<Person> selectAllFy(int page) {
		System.out.println("进入dao");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int k = page * 5;
		int j = k - 5;
		List<Person> list = new ArrayList<Person>();
		String sql = "select t.* from (select rownum num,GANGSTAPERSON.* from GANGSTAPERSON where identity ！=-1 and rownum <= ?) t where num > ? ";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, k);
			pstmt.setInt(2, j);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Person person = new Person();
				person.setName(rs.getString(2));
				person.setEmail(rs.getString(3));
				person.setIdentity(rs.getInt(5));
				person.setState(rs.getInt(7));
				list.add(person);
			}
			System.out.println("数据库中搜索的："+list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}

		}
		return list;
	}

	/**
	 * 查询总记录数 返回Int类型的总记录数counts
	 */
	@Override
	public int selectAllCounts() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int counts = 0;
		String sql = "select count(*) from GANGSTAPERSON";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				counts = rs.getInt(1);
				System.out.println("总个数："+counts);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}

		}
		return counts;
	}

	/**
	 * 根据总记录数查询总的页数 返回pages
	 */
	@Override
	public int getPages(int counts) {
		int pages = 0;
		if (counts % 5 == 0) {
			pages = counts % 5;
		} else {
			pages = counts / 5 + 1;
		}
		return pages;

	}

	/**
	 * 根据email主键查询单个用户的信息
	 */
	@Override
	public List<Person> selectPerson(String email) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Person p = new Person();
		List<Person> list = new ArrayList<Person>();
		String sql = "select * from GANGSTAPERSON where email = ?";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 获取数据库单行信息
				p.setName(rs.getString(1));
				p.setEmail(rs.getString(2));
				p.setPassword(rs.getString(3));
				p.setIdentity(rs.getInt(4));
				p.setVerify(rs.getString(5));
				p.setState(rs.getInt(6));
				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}

		}
		return list;
	}

	/**
	 * (管理员功能)管理员添加公告
	 * 成功返回1，失败返回0
	 */
	@Override
	public int insertNotice(Notice notice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		String sql = "insert into GANGSTANOTICE(TITLE,CONTENT,UpdateDate,Author,Url) values (?,?,"
				+ "sysdate,?,?)";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getContent());
			pstmt.setString(3, notice.getAuthor());
			pstmt.setString(4, notice.getUrl());
			i = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 如果sql语句执行成功，则返回i
		return i;
	}

	/**
	 * 所用用户的操作：查询公告(搜索最新的5条公告数据)
	 */
	@Override
	public List<Notice> selectNotice() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Notice> list = new ArrayList<Notice>();
		String sql = "SELECT b.* FROM (SELECT ROWNUM RN1,n.* FROM GANGSTANOTICE n ORDER BY RN1 DESC) b "
				+ "WHERE ROWNUM<=10";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Notice notice = new Notice();
				// 获取数据库单行信息
				notice.setTitle(rs.getString(2));
				notice.setContent(rs.getString(3));
//				SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss");
//				String dateStr = rs.getString(4);
//				Date date = formatter.parse(dateStr);
//				notice.setUpdateDate(date);
				String date = rs.getTimestamp(4).toString();
				notice.setUpdateDate(date);
//				System.out.println("数据库获取的时间："+rs.getTimestamp(4));
				notice.setAuthor(rs.getString(5));
				notice.setUrl(rs.getString(6));
				list.add(notice);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}

		}
		return list;
	}

	/**
	 * 根据主键email删除用户(同时删除属于用户的file表)
	 */
	@Override
	public int deletePerson(String email) {

		System.out.println("进入删除dao");
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		String sql = "delete from GANGSTAPERSON where email = ? ";
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			i = pstmt.executeUpdate();
			System.out.println("结果"+i);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return i;

	}

	/**
	 * 根据主键email更新信息(名字  或者 密码 或者都改)
	 */
	public int updateMsg(Person person) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = 0;
		try {
			con = DBConnection.getConnection();
			/*
			 * 1.得到sql语句前半部分
			 */
			StringBuilder baseSql = new StringBuilder("UPDATE GANGSTAPERSON SET  ");
			StringBuilder intsertSql = new StringBuilder("");
			StringBuilder comaSql = new StringBuilder(" , ");
			StringBuilder whereSql = new StringBuilder(" where email = ?");
			//书名查询
			String name = person.getName();
			String password = person.getPassword();
			String email = person.getEmail();
			boolean a = name!=null&&!name.trim().isEmpty();
			boolean b = password!=null&&!password.trim().isEmpty();
			//若名字存在则添加该语句
			if (a) {
				intsertSql.append("name= '"+name+"' ");
			}
			//若都存在则添加逗号
			if (a&&b) {
				intsertSql.append(comaSql);
			}
			//若密码存在则添加该语句
			if (b) {
				intsertSql.append(" password = '"+password+"' ");
			}
			String sql = baseSql.append(intsertSql).append(whereSql).toString();
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return i;
	}
	

}
