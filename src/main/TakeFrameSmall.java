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
 * 最小化窗口到任务栏
 * 
 * @author asus pc
 *
 */
public class TakeFrameSmall {

	private static TrayIcon trayIcon = null;// 系统托盘按钮
	static SystemTray tray = SystemTray.getSystemTray();//获得当前可用的系统托盘

	public void miniTray(JFrame mainFrame) { // 窗口最小化到任务栏托盘

		ImageIcon trayImg = new ImageIcon("C://CloudMusic//2000.jpg");// 托盘图标

		PopupMenu pop = new PopupMenu(); // 增加托盘右击菜单

		MenuItem show = new MenuItem("还原");
		MenuItem exit = new MenuItem("退出");

		show.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { // 按下还原键
				tray.remove(trayIcon);
				mainFrame.setVisible(true);
				//设置窗口的状态
				mainFrame.setExtendedState(JFrame.NORMAL);
				//如果此窗口是可见的，则将此窗口置于前端，并可以将其设为焦点 Window。
				mainFrame.toFront();
			}

		});

		exit.addActionListener(new ActionListener() { // 按下退出键
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});

		pop.add(show);
		pop.add(exit);

		trayIcon = new TrayIcon(trayImg.getImage(), "音乐播放器", pop);
		trayIcon.setImageAutoSize(true);

		trayIcon.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) { // 鼠标器双击事件

				if (e.getClickCount() == 2) {

					tray.remove(trayIcon); // 移去托盘图标
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL); // 还原窗口
					mainFrame.toFront();
				}

			}

		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
}