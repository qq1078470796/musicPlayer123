package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import database.Crud;
import database.DbUtil;
import database.Music;

public class DatabaseTest {

	/**
	 * 测试数据库能正确的连接
	 * @throws SQLException
	 */
	@Test
    public void testConn() throws SQLException {
        Connection conn=DbUtil.getInstance().getConnection();
        assertEquals(false, conn.isClosed());
    }
	/**
	 * 测试能正确的向数据库添加一条记录
	 */
	@Test
	public void testAdd(){
		Crud c=new Crud();
		Music temp=new Music();
		temp.setAlbum("da");
		temp.setBitRate(123);
		temp.setSong("dada");
		c.insertMusic(temp);
	}
	/**
	 * 测试能正确的删除一个记录
	 */
	@Test
	public void testDelete(){
		Crud c=new Crud();
		c.delete("dada");
	}
	/**
	 * 测试能否正确的返回音乐数组
	 */
	@Test
	public void testSelect(){
		Crud c=new Crud();
		Music temp=new Music();
		temp.setAlbum("da");
		temp.setBitRate(123);
		temp.setSong("dada");
		c.insertMusic(temp);
		ArrayList list=(ArrayList) c.queryAll();
		assertEquals(9,list.size());
		c.delete("dada");
	}
	/**
	 * 测试能否成功的更新数据库信息
	 */
	@Test
	public void testUpdate(){
		Crud c=new Crud();
		Music temp=new Music();
		temp.setAlbum("da");
		temp.setBitRate(123);
		temp.setSong("dada");
		c.insertMusic(temp);
		temp.setDataSize(123);
		c.update(temp);
		ArrayList<Music> list=(ArrayList<Music>) c.queryAll();
		int size=(int) list.get(3).getDataSize();
		assertEquals(123,size);
		c.delete("dada");
	}
	

}
