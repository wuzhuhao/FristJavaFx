/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author wuzhuhao
 */
public class JdbcUtil {
	// 这里可以设置数据库名称
	final static String driverName = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
	private static final String USER = "root";
	private static final String PASSWORD = "123123";

	// 静态代码块（将加载驱动、连接数据库放入静态块中）
	static {

	}

	// 对外提供一个方法来获取数据库连接
	public static Connection getConnection() {
		Connection con = null;
		try {
			// 1.加载驱动程序
			Class.forName(driverName);
			// System.out.println("加载驱动成功！");
			// 2.获得数据库的连接
			con = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			// System.out.println("连接数据库成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeJdbc(Connection c, ResultSet rs, Statement statement) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("释放资源出错");
		}
	}
}
