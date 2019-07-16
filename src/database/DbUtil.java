package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	//����ģʽ
	private static DbUtil instance = new DbUtil();
	private Connection conn;
	private DbUtil(){}
	//�ھ�̬���ڳ�ʼ������
	static{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public Connection getConnection(){
		try {
			conn= DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=music", "sa", "haoxinyu123");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("���ݿ��ʧ��");
			return null;
		}
		
	}
	/**
	 * �ر����ݿ�
	 */
	public void closeConnection(){
		try {
			if(conn!=null&&conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			System.err.println("�ر����ݿ�ʧ��");
			e.printStackTrace();
		}
	}
	public static DbUtil getInstance(){
		return instance;
	}
	
}
