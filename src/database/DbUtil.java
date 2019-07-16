package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	//单例模式
	private static DbUtil instance = new DbUtil();
	private Connection conn;
	private DbUtil(){}
	//在静态块内初始化驱动
	static{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取链接
	 * @return
	 */
	public Connection getConnection(){
		try {
			conn= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=music", "sa", "haoxinyu123");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库打开失败");
			return null;
		}
		
	}
	/**
	 * 关闭数据库
	 */
	public void closeConnection(){
		try {
			if(conn!=null&&conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			System.err.println("关闭数据库失败");
			e.printStackTrace();
		}
	}
	public static DbUtil getInstance(){
		return instance;
	}
	
}
