/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Crud;
import database.DbUtil;
import main.TakeFrameSmall;
import musicPlayer.OpenMusic;
import musicPlayer.PlayKinds;
import musicPlayer.PlayList;
import musicPlayer.PlayMusic;
import musicPlayer.PreMusicAndNextMusic;
import musicPlayer.SongInfos;
import musicPlayer.TimeProgressBar;
import musicPlayer.VoiceTool;

/**
 *
 * @author asus pc
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        reminderSong = new javax.swing.JLabel();
        songName = new javax.swing.JLabel();
        reminderVoice = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        musicPlay = new javax.swing.JScrollPane();
        reminderPlayList = new javax.swing.JLabel();
        reminderPlayKind = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
       
        
        
        Crud database = new Crud();
		PlayMusic p = new PlayMusic();
		OpenMusic op = new OpenMusic();
		PreMusicAndNextMusic pn = new PreMusicAndNextMusic();
		TakeFrameSmall takeFSmall = new TakeFrameSmall();
		SongInfos infos=new SongInfos();
		VoiceTool vc = new VoiceTool();
		PlayList plist=new PlayList();
		
		 for(int i=0;i<5;i++){
        	infoss[i]=new JLabel("");
        }
		start = new javax.swing.JButton();
        pre = new javax.swing.JButton();
        next = new javax.swing.JButton();
        voiceTool =vc.getVoiceTool();
        startTime = new javax.swing.JLabel();
        endTime = new javax.swing.JLabel();
        noVoice = new javax.swing.JButton();
        PlayBar = new javax.swing.JSlider();
        pause = new javax.swing.JButton();
        resume = new javax.swing.JButton();
        end = new javax.swing.JButton();
        MusicPlayList = new javax.swing.JList();
        fileRead = new javax.swing.JButton();
        select = new javax.swing.JComboBox();
        
        
		op.setSongInformation(database.getAll());
		OpenMusic.setLenth(database.getLenth());// 获取数据库中存放的歌曲信息
		
		DefaultListModel<String> model=new DefaultListModel<String>();
		JList MusicPlayList=new JList(model);
		
		musicPlay.setViewportView(MusicPlayList);  
		plist.setScrollSongList(musicPlay);
		plist.setMlist(MusicPlayList);
		plist.setMusicModel(model);
		plist.MusicInit(database.getAll(),database.getLenth());//数据库信息存入待播放面板
		
		PlayKinds pl = new PlayKinds();
		op.sysoAll();//测试
		
		timerProgressBar = new TimeProgressBar();
		
		p.setTime(timerProgressBar);
		timerProgressBar.setStringPainted(true);
		p.setGoingMusic(PlayBar);
		System.out.println(op.getLenth());
		System.out.println(musicPlayer.PlayMusic.getNowsMusic());
		select.setModel(new DefaultComboBoxModel(new String[] { "单曲播放", "单曲循环", "顺序播放", "列表循环", "随机播放" }));
		timerProgressBar.setCurrentTimeCountLabel(startTime);
        

        
       

        start.setText("播放");
        
        
        start.addActionListener(event -> {
			p.playThread = new Thread(() -> {
				
				infos.setInfos(new HashMap<String,String>());
				URL url = null;
				try {
					url = new URL(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getSongDataUrl());
					System.out.println(url.toString());
				} catch (MalformedURLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				
				int thisMusicTime = op.getAudioTrackLength(url);
				
				op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].setTotalTime(thisMusicTime);
				p.load(url);
				endTime.setText(op.getAudioTotalTime(thisMusicTime));
				
				//System.out.println("当前位置"+musicPlayer.PlayMusic.getNowsMusic());
				System.out.println(musicPlayer.PlayMusic.getNowsMusic()+"size"+op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize());
				if(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize()!=0.0){
					p.setNeedBuildFirst(false);
					System.out.println("进入----");
					p.setFullByte(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getDataSize());
				}
				else p.setNeedBuildFirst(true);
				
				p.setThisMusicTime(thisMusicTime);
				System.out.println("未播放前"+p.getFullByte());
				
				
				infos.read(new File(op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getLrcUrl()));
				
				timerProgressBar.setInfos(infos);//放入歌词
				timerProgressBar.setInfoss(infoss);//放入歌词面板
				
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

        pre.setText("上一曲");
        pre.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				end.doClick();
				pn.getPreMusic();
				start.doClick();
			}
		});

        next.setText("下一曲");
        next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				end.doClick();
				pn.getNextMusic();
				System.out.println("下一曲为"+op.getSongInformation()[musicPlayer.PlayMusic.getNowsMusic()].getSong());
				start.doClick();
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
				System.out.println("现在的播放状态为" + (select.getSelectedIndex() + 1));

			}
		});
		plist.getMlist().addListSelectionListener(new ListSelectionListener(){  
            @Override  
            public void valueChanged(ListSelectionEvent e) { 
                if(!plist.getMlist().getValueIsAdjusting()){   //设置只有释放鼠标时才触发  
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
				System.out.println("摁下前"+p.getGoingMusicWZ());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});

        reminderSong.setText("正在播放");

        songName.setText("歌曲名字");

        startTime.setText("00:00");

        endTime.setText("00:00");

        reminderVoice.setText("音量调节");

        noVoice.setText("静音");

        jLabel6.setText("可调戏的进度条");

        pause.setText("暂停");

        resume.setText("恢复");

        end.setText("结束");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startTime)
                            .addComponent(reminderSong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pre)
                        .addGap(2, 2, 2)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timerProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(start)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pause)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resume)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(PlayBar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(end)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(next)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(voiceTool, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(noVoice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(reminderVoice))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(endTime)))
                    .addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(PlayBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(reminderSong)
                                .addComponent(songName))
                            .addGap(6, 6, 6)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timerProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(startTime)
                                .addComponent(endTime)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(start)
                            .addComponent(pre)
                            .addComponent(next)
                            .addComponent(pause)
                            .addComponent(resume)
                            .addComponent(end)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(reminderVoice)
                        .addComponent(noVoice))
                    .addComponent(voiceTool, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );


        reminderPlayList.setText("歌单");

        fileRead.setText("打开本地文件");


        reminderPlayKind.setText("播放模式");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(reminderPlayKind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(fileRead)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reminderPlayList))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(musicPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reminderPlayList)
                            .addComponent(fileRead)
                            .addComponent(reminderPlayKind))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(musicPlay))
                .addContainerGap())
        );

        for(int i=0;i<5;i++){
        	infoss[i].setText("歌词");
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(infoss[0], javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(infoss[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoss[2], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoss[3], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoss[4], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(infoss[0])
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(infoss[1])
                .addGap(29, 29, 29)
                .addComponent(infoss[2])
                .addGap(29, 29, 29)
                .addComponent(infoss[3])
                .addGap(27, 27, 27)
                .addComponent(infoss[4])
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        
                                  

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JList MusicPlayList;
    private javax.swing.JSlider PlayBar;
    private javax.swing.JButton end;
    private javax.swing.JLabel endTime;
    private javax.swing.JButton fileRead;
    private javax.swing.JLabel [] infoss=new JLabel[5];
    
    
    
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane musicPlay;
    private javax.swing.JButton next;
    private javax.swing.JButton noVoice;
    private javax.swing.JButton pause;
    private javax.swing.JButton pre;
    private javax.swing.JLabel reminderPlayKind;
    private javax.swing.JLabel reminderPlayList;
    private javax.swing.JLabel reminderSong;
    private javax.swing.JLabel reminderVoice;
    private javax.swing.JButton resume;
    private javax.swing.JComboBox select;
    private javax.swing.JLabel songName;
    private javax.swing.JButton start;
    private javax.swing.JLabel startTime;
    private TimeProgressBar timerProgressBar;
    private javax.swing.JSlider voiceTool;
    // End of variables declaration                   
}