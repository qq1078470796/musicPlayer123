package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jvnet.substance.SubstanceLookAndFeel;

import database.Crud;
import database.DbUtil;
import musicPlayer.OpenMusic;
import musicPlayer.PlayKinds;
import musicPlayer.PlayList;
import musicPlayer.PlayMusic;
import musicPlayer.PreMusicAndNextMusic;
import musicPlayer.SongInfos;
import musicPlayer.TimeProgressBar;
import musicPlayer.VoiceTool;

public class MainTest {
	public static void main(String[] args) {
//		 try {
//		 // ���ù۸�
//		 UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
//		 // ����ˮӡ
//		 SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceMosaicWatermark");
//		 // ���ý�����Ⱦ
//		 SubstanceLookAndFeel.setCurrentGradientPainter("org.jvnet.substance.painter.WaveGradientPainter");
//		
//		 JFrame.setDefaultLookAndFeelDecorated(true);
//		 JDialog.setDefaultLookAndFeelDecorated(true);
//		 } catch (ClassNotFoundException | InstantiationException |
//		 IllegalAccessException
//		 | UnsupportedLookAndFeelException e) {
//		 e.printStackTrace();
//		 }
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		}
		
		Crud database = new Crud();
		PlayMusic p = new PlayMusic();
		OpenMusic op = new OpenMusic();
		PreMusicAndNextMusic pn = new PreMusicAndNextMusic();
		TakeFrameSmall takeFSmall = new TakeFrameSmall();
		SongInfos infos=new SongInfos();
		VoiceTool vc = new VoiceTool();
		PlayList plist=new PlayList();
		op.setSongInformation(database.getAll());
		OpenMusic.setLenth(database.getLenth());// ��ȡ���ݿ��д�ŵĸ�����Ϣ
		
		JScrollPane musicPlay=new JScrollPane();
		
		DefaultListModel<String> model=new DefaultListModel<String>();
		JList MusicPlayList=new JList(model);
		

		musicPlay.setViewportView(MusicPlayList);  
		plist.setScrollSongList(musicPlay);
		plist.setMlist(MusicPlayList);
		plist.setMusicModel(model);
		plist.MusicInit(database.getAll(),database.getLenth());
		
		PlayKinds pl = new PlayKinds();
		op.sysoAll();
		
		JFrame f = new JFrame();
		JButton fileRead = new JButton("��");
		JButton start = new JButton("��ʼ");
		JButton pause = new JButton("��ͣ");
		JButton resume = new JButton("�ָ�");
		JButton end = new JButton("����");
		JButton next = new JButton("��һ��");
		JButton pre = new JButton("��һ��");
		JPanel pa = new JPanel();
		JSlider voiceTool = vc.getVoiceTool();
		JButton noVoice = new JButton("����");
		JComboBox select = new JComboBox<>();
		
		TimeProgressBar timerProgressBar = new TimeProgressBar();
		p.setTime(timerProgressBar);
		timerProgressBar.setStringPainted(true);
		
		JLabel startTime = new JLabel();
		JTextField endTime = new JTextField();
		endTime.setText("00:00");
		endTime.setEnabled(false);
		JSlider PlayBar = new JSlider();
		p.setGoingMusic(PlayBar);
		System.out.println(op.getLenth());
		System.out.println(musicPlayer.PlayMusic.getNowsMusic());
		select.setModel(new DefaultComboBoxModel(new String[] { "��������", "����ѭ��", "˳�򲥷�", "�б�ѭ��", "�������" }));
		
		pa.setLayout(new GridLayout(2, 5));
		pa.add(start);
		pa.add(pause);
		pa.add(resume);
		pa.add(end);
		pa.add(fileRead);
		pa.add(pre);
		pa.add(next);
		pa.add(voiceTool);
		pa.add(noVoice);
		pa.add(select);
		pa.add(startTime);
		pa.add(endTime);
		pa.add(timerProgressBar);
		pa.add(PlayBar);
		pa.add(musicPlay);
		f.add(pa);
		
		f.setSize(500, 500);
		
		timerProgressBar.setCurrentTimeCountLabel(startTime);
		start.addActionListener(event -> {
			p.playThread = new Thread(() -> {
				infos.setInfos(new HashMap<String,String>());
				URL url = null;
				try {
					url = new URL(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getSongDataUrl());
					System.out.println(url.toString());
				} catch (MalformedURLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				int thisMusicTime = op.getAudioTrackLength(url);
				op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].setTotalTime(thisMusicTime);
				p.load(url);
				endTime.setText(op.getAudioTotalTime(thisMusicTime));
				
				//System.out.println("��ǰλ��"+musicPlayer.PlayMusic.getNowsMusic());
				System.out.println(musicPlayer.PlayMusic.getNowsMusic()+"size"+op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize());
				if(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize()!=0.0){
					p.setNeedBuildFirst(false);
					System.out.println("����----");
					p.setFullByte(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize());
				}
				else p.setNeedBuildFirst(true);
				
				p.setThisMusicTime(thisMusicTime);
				System.out.println("δ����ǰ"+p.getFullByte());
				
				
				infos.read(new File(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getLrcUrl()));
				timerProgressBar.setInfos(infos);
				
				timerProgressBar.StartNewMusicTime(thisMusicTime);
				TimeProgressBar.setTimerPause(true);
				p.play();
				
				op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].setDataSize(p.getFullByte());
				System.out.println(p.getFullByte());
				op.updateDataBase();
				
				pl.switchKind(start, next);
			});
			p.playThread.start();
		});

		pause.addActionListener(event -> {
			System.out.println("pause");
			p.IsPause = true;
		});
		resume.addActionListener(event -> {
			System.out.println("resume");
			p.resume();
		});
		end.addActionListener(event -> {
			System.out.println("end");
			p.end();
			p.setFullByte(0);
		});
		fileRead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				op.getFile();
				op.saveMusicIntoList();
				int l=op.getLenth()-1;
				System.out.println(l);
				plist.addNewMusicInMusicList(op.getSongInformation()[l]);
			}
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				end.doClick();
				pn.getNextMusic();
				start.doClick();
			}
		});
		pre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				end.doClick();
				pn.getPreMusic();
				start.doClick();
			}
		});
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				end.doClick();
				DbUtil.getInstance().closeConnection();
				System.out.println("���ر�");
				System.exit(0);
			}
		});
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				f.setVisible(false);
				takeFSmall.miniTray(f);
			}
		});
		
		voiceTool.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				PlayMusic.setFloatVoiceControl(voiceTool.getValue());

			}
		});
		noVoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vc.stopMusicVoice();

			}
		});
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.setId(select.getSelectedIndex() + 1);
				System.out.println("���ڵĲ���״̬Ϊ" + (select.getSelectedIndex() + 1));

			}
		});
		plist.getMlist().addListSelectionListener(new ListSelectionListener(){  
            @Override  
            public void valueChanged(ListSelectionEvent e) { 
                if(!plist.getMlist().getValueIsAdjusting()){   //����ֻ���ͷ����ʱ�Ŵ���  
                    System.out.println(plist.getMlist().getSelectedValue()); 
                    for(int i=0;i<OpenMusic.getLenth();i++){
                    	if(plist.getMlist().getSelectedValue().equals(op.getSongInformation()[i].getSong())){
                    		end.doClick();
                    		PlayMusic.setNowsMusic(i);
                    		start.doClick();
                    	}
                    }
                    
                }  
            }  
        });
		PlayBar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(p.getGoingMusicWZ()<PlayBar.getValue())
				p.isPushNext=true;
				else p.isPushPre=true;
				TimeProgressBar.setTimerPause(true);
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				p.setGoingMusicWZ(PlayBar.getValue());
				System.out.println("����ǰ"+p.getGoingMusicWZ());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
		f.setVisible(true);
	}
}
