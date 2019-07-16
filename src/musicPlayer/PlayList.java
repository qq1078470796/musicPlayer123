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
	private JScrollPane scrollSongList; //����Ŀ¼�Ĺ������,�ڱ����н����ڲ���
	private JList mlist ;//��������һ���б��Թ�ѡ��
	private DefaultListModel<String> musicModel;//�б�ģ�ͣ�ֻ��ӵ��ģ�͵��б���������Ԫ��
	/**
	 * �Ѹ�����Ϣ�����б�ģ��
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
	 * ģ���м���һ�׸�
	 * @param m
	 */
	public void addNewMusicInMusicList(Music m){
			musicModel.addElement(m.getSong());
	}
	/**
	 * �������б��ģ�����
	 * @param args
	 */
	public static void main(String [] args){
		JFrame f = new JFrame();  
        f.setSize(600, 500);  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        JPanel panel = new JPanel();  
        JScrollPane scrollPane = new JScrollPane();  
        scrollPane.setPreferredSize(new Dimension(200,100));  
        final DefaultListModel d = new DefaultListModel();//ֻ��Ĭ�ϵ�ģ�������/ɾ������  
        d.addElement("aa");  
        d.addElement("bb");  
        d.addElement(new Music());
        final JList jList = new JList(d);  
        jList.addListSelectionListener(new ListSelectionListener(){  
            @Override  
            public void valueChanged(ListSelectionEvent e) {  
                if(!jList.getValueIsAdjusting()){   //����ֻ���ͷ����ʱ�Ŵ���  
                    System.out.println(jList.getSelectedValue());  
                }  
            }  
        });  
        JButton  p =new JButton("���");
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