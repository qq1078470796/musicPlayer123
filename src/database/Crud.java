package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * ���ݿ������
 * @author asus   pc
 *
 */
public class Crud {
	
	//���ݿ��и�������
	private int lenth;
	
	public int getLenth() {
		return lenth;
	}

	public void setLenth(int lenth) {
		this.lenth = lenth;
	}
	/**
	 * �����ݿ��в���һ�׸���
	 * @param m
	 */
	public void insertMusic(Music m){
		try {
			PreparedStatement pstat = DbUtil.getInstance().getConnection().prepareStatement("insert into musicList values(?,?,?,?,?,?,?)");
			pstat.setString(1, m.getSong());
			pstat.setString(2, m.getSongDataUrl());
			pstat.setString(3, m.getSinger());
			pstat.setString(4, m.getLrcUrl());
			pstat.setString(5, m.getAlbum());
			pstat.setInt(6, m.getBitRate());
			pstat.setFloat(7, m.getDataSize());
			System.out.println(m.getSongDataUrl());
			pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ���ݸ�����ɾ������
	 * @param song
	 */
	public void delete(String song){
		try {
			PreparedStatement pstat = DbUtil.getInstance().getConnection().prepareStatement("delete from musicList where song=?");
			pstat.setString(1, song);
			pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ѯ���и�����¼������һ��List��
	 * @return
	 */
	public List<Music> queryAll(){
		List<Music> result = new ArrayList<Music>();
		try {
			PreparedStatement pstat = DbUtil.getInstance().getConnection().prepareStatement("select * from musicList");
			ResultSet rs = pstat.executeQuery();
			Music temp;
			while(rs.next()){
				temp = new Music();
				temp.setSong(rs.getString("song"));
				temp.setSongDataUrl(rs.getString("songDataUrl"));
				temp.setSinger(rs.getString("singer"));
				temp.setLrcUrl(rs.getString("lrcUrl"));
				temp.setAlbum(rs.getString("album"));
				temp.setDataSize(rs.getInt("dataSize"));
				temp.setBitRate(rs.getInt("bitRate"));
				result.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * ���ݸ��������һ�׸���
	 * @param m
	 * @return
	 */
	public Music get(Music m){
		
		try {
			PreparedStatement pstat = DbUtil.getInstance().getConnection().prepareStatement("select * from musicList where song=?");
			pstat.setString(1, m.getSong());
			ResultSet rs = pstat.executeQuery();
			
			while(rs.next()){
				m.setSong(rs.getString("song"));
				m.setSongDataUrl(rs.getString("songDataUrl"));
				m.setSinger(rs.getString("singer"));
				m.setLrcUrl(rs.getString("lrcUrl"));
				m.setAlbum(rs.getString("album"));
				m.setDataSize(rs.getInt("dataSize"));
				m.setBitRate(rs.getInt("bitRate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	/**
	 * ��ȡ���и���������һ�����飩
	 * @return
	 */
	public Music [] getAll(){
		Music m[] = new Music[100];
		int now=0;
		try {
			PreparedStatement pstat = DbUtil.getInstance().getConnection().prepareStatement("select * from musicList");
			ResultSet rs = pstat.executeQuery();
			while(rs.next()){
				m[now]=new Music();
				m[now].setSong(rs.getString("song"));
				m[now].setSongDataUrl(rs.getString("songDataUrl"));
				m[now].setSinger(rs.getString("singer"));
				m[now].setLrcUrl(rs.getString("lrcUrl"));
				m[now].setAlbum(rs.getString("album"));
				m[now].setDataSize(rs.getInt("dataSize"));
				m[now].setBitRate(rs.getInt("bitRate"));
				now++;
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Music m1[] =new Music[now];
		m1=m;
		lenth=now;
		return m1;
	}
	/**
	 * ���ݸ������¸�����ʵ��С
	 * @param m
	 * @return
	 */
	public Music update(Music m) {
		String sql="update musicList set dataSize=? where song=?";
			PreparedStatement pstat;
			try {
				pstat = DbUtil.getInstance().getConnection().prepareStatement(sql);
				pstat.setFloat(1, m.getDataSize());
				pstat.setString(2, m.getSong());
				if(pstat.executeUpdate()>=0)
				{
					return m;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}


}
