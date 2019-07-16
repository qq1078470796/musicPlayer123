package musicPlayer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Music;

public class PlayList {
	private JScrollPane scrollSongList; //歌曲目录的滚动面板,在本类中仅用于测试
	private JList mlist ;//歌曲放入一个列表以供选择
	private DefaultListModel<String> musicModel;//列表模型，只有拥有模型的列表才允许添加元素
	/**
	 * 把歌曲信息放入列表模型
	 * @param musicList
	 * @param lenth
	 */
	public void MusicInit(Music [] musicList,int lenth){
		for(int i=0;i<lenth;i++){
			musicModel.addElement(musicList[i].getSong());
		}
	}
	
	public JScrollPane getScrollSongList() {
		return scrollSongList;
	}
	public void setScrollSongList(JScrollPane scrollSongList) {
		this.scrollSongList = scrollSongList;
	}
	public JList getMlist() {
		return mlist;
	}

	public void setMlist(JList mlist) {
		this.mlist = mlist;
	}

	public DefaultListModel<String> getMusicModel() {
		return musicModel;
	}
	public void setMusicModel(DefaultListModel<String> musicModel) {
		this.musicModel = musicModel;
	}
	/**
	 * 模型中加入一首歌
	 * @param m
	 */
	public void addNewMusicInMusicList(Music m){
			musicModel.addElement(m.getSong());
	}
	/**
	 * 待播放列表的模块测试
	 * @param args
	 */
	public static void main(String [] args){
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
	
}  