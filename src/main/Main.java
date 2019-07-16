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
			 * Ԥ��һ������
			 */
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}

		JFrame m=new NewMainFrame();
		
		FrameChange.f=m;//���ı䴰�ڵ����������
		TakeFrameSmall takeFSmall=new TakeFrameSmall();//������С������������ʵ��
		//�����������С��
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
				DbUtil.getInstance().closeConnection();//�ر����ݿ�
				System.out.println("���ر�");
				System.exit(0);
			}
		});
		m.setVisible(true);
	}
	
}
