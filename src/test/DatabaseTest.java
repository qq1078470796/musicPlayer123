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
	 * �������ݿ�����ȷ������
	 * @throws SQLException
	 */
	@Test
    public void testConn() throws SQLException {
        Connection conn=DbUtil.getInstance().getConnection();
        assertEquals(false, conn.isClosed());
    }
	/**
	 * ��������ȷ�������ݿ����һ����¼
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
	 * ��������ȷ��ɾ��һ����¼
	 */
	@Test
	public void testDelete(){
		Crud c=new Crud();
		c.delete("dada");
	}
	/**
	 * �����ܷ���ȷ�ķ�����������
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
	 * �����ܷ�ɹ��ĸ������ݿ���Ϣ
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
