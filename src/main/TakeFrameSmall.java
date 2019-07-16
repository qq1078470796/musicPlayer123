package main;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * ��С�����ڵ�������
 * 
 * @author asus pc
 *
 */
public class TakeFrameSmall {

	private static TrayIcon trayIcon = null;// ϵͳ���̰�ť
	static SystemTray tray = SystemTray.getSystemTray();//��õ�ǰ���õ�ϵͳ����

	public void miniTray(JFrame mainFrame) { // ������С��������������

		ImageIcon trayImg = new ImageIcon("C://CloudMusic//2000.jpg");// ����ͼ��

		PopupMenu pop = new PopupMenu(); // ���������һ��˵�

		MenuItem show = new MenuItem("��ԭ");
		MenuItem exit = new MenuItem("�˳�");

		show.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { // ���»�ԭ��
				tray.remove(trayIcon);
				mainFrame.setVisible(true);
				//���ô��ڵ�״̬
				mainFrame.setExtendedState(JFrame.NORMAL);
				//����˴����ǿɼ��ģ��򽫴˴�������ǰ�ˣ������Խ�����Ϊ���� Window��
				mainFrame.toFront();
			}

		});

		exit.addActionListener(new ActionListener() { // �����˳���
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});

		pop.add(show);
		pop.add(exit);

		trayIcon = new TrayIcon(trayImg.getImage(), "���ֲ�����", pop);
		trayIcon.setImageAutoSize(true);

		trayIcon.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) { // �����˫���¼�

				if (e.getClickCount() == 2) {

					tray.remove(trayIcon); // ��ȥ����ͼ��
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL); // ��ԭ����
					mainFrame.toFront();
				}

			}

		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}
}