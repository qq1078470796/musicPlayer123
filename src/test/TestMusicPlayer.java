package test;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.junit.Test;

import database.Music;
import musicPlayer.OpenMusic;
import musicPlayer.SongInfos;

public class TestMusicPlayer {

	/**
	 * 测试是否能正确的返回音乐文件时间
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetSongTime() throws MalformedURLException {
		OpenMusic om=new OpenMusic();
		int i=om.getAudioTrackLength(new URL("file:/C:/CloudMusic/Revo%20&%20梶浦由記%20-%20砂塵の彼方へ.mp3"));
		assertEquals(443,i);
	}
	/**
	 * 测试能否将时间正确的转化成分钟
	 * @throws MalformedURLException
	 */
	@Test
	public void testTimeChange() throws MalformedURLException{
		OpenMusic om=new OpenMusic();
		int i=om.getAudioTrackLength(new URL("file:/C:/CloudMusic/Revo%20&%20梶浦由記%20-%20砂塵の彼方へ.mp3"));
		String t=om.getAudioTotalTime(i);
		assertEquals("7:23",t);
	}
	/**
	 * 测试打开文件窗口能正常的打开文件
	 */
	@Test
	public void testOpemMusic(){
		OpenMusic om=new OpenMusic();
		om.getFile();
		System.out.println(om.tempFile);
	}
	/**
	 * 待播放列表的模块测试
	 * 
	 */
	@Test
	public void playListTest(){
		JFrame f = new JFrame();  
        f.setSize(600, 500);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        JPanel panel = new JPanel();  
        JScrollPane scrollPane = new JScrollPane();  
        scrollPane.setPreferredSize(new Dimension(200,100));  
        final DefaultListModel d = new DefaultListModel();//只有默认的模型有添加/删除方法  
        d.addElement("aa");  
        d.addElement("bb");  
        d.addElement(new Music());
        final JList jList = new JList(d);  
        jList.addListSelectionListener(new ListSelectionListener(){  
            @Override  
            public void valueChanged(ListSelectionEvent e) {  
                if(!jList.getValueIsAdjusting()){   //设置只有释放鼠标时才触发  
                    System.out.println(jList.getSelectedValue());  
                }  
            }  
        });  
        JButton  p =new JButton("添加");
        p.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				d.addElement("bb");  
				
			}
		}); 
        scrollPane.setViewportView(jList);  
        panel.add(scrollPane);  
        panel.add(p); 
        f.getContentPane().add(panel);  
        f.setVisible(true);
	}
	/**
	 * 测试是否能正确的判断歌词文件的编码
	 */
	@Test
	public void testGetSongCharset(){
		
		File file=new File("C:\\CloudMusic\\MKJ - Time.lrc");
		String temp=SongInfos.getCharset(file);
		assertEquals("GBK",temp);
	}

}
