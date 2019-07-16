package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import database.DbUtil;
import ui.MainFrame;
import ui.NewMainFrame;

public class Main {
	public static void main(String [] args){
			/**
			 * 预设一个主题
			 */
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

		JFrame m=new NewMainFrame();
		
		FrameChange.f=m;//将改变窗口的类与主类绑定
		TakeFrameSmall takeFSmall=new TakeFrameSmall();//窗口最小化到任务栏的实例
		//给窗口添加最小化
		m.addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				m.setVisible(false);
				takeFSmall.miniTray(m);
			}
		});
		
		m.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DbUtil.getInstance().closeConnection();//关闭数据库
				System.out.println("出关闭");
				System.exit(0);
			}
		});
		m.setVisible(true);
	}
	
}
