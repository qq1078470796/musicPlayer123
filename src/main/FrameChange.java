package main;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class FrameChange {
	public static JFrame f;
	/**
	 * 为窗口换主题
	 * @param l
	 */
	public static void FrameC(int l){
		f.setVisible(false);
		if(l==1){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			} catch (ClassNotFoundException e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			} catch (InstantiationException e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			} catch (UnsupportedLookAndFeelException e2) {
				// TODO 自动生成的 catch 块
				e2.printStackTrace();
			}
		}
		else if(l==2){
			try {
				 UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
				 } catch (ClassNotFoundException | InstantiationException |
				 IllegalAccessException
				 | UnsupportedLookAndFeelException e) {
				 e.printStackTrace();
				 }
		}
		else if(l==3){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else if(l==4){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		else if(l==5){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		System.out.println("当前主题"+l);
//		/*
//		com.jtattoo.plaf.noire.NoireLookAndFeel  柔和黑
//		com.jtattoo.plaf.smart.SmartLookAndFeel 木质感+xp风格
//		com.jtattoo.plaf.mint.MintLookAndFeel  椭圆按钮+黄色按钮背景
//		com.jtattoo.plaf.mcwin.McWinLookAndFeel 椭圆按钮+绿色按钮背景
//		com.jtattoo.plaf.hifi.HiFiLookAndFeel  黑色风格
//		com.jtattoo.plaf.fast.FastLookAndFeel  普通swing风格+蓝色边框
//		com.jtattoo.plaf.bernstein.BernsteinLookAndFeel  黄色风格
//		com.jtattoo.plaf.aluminium.AluminiumLookAndFeel 椭圆按钮+翠绿色按钮背景+金属质感
//	*/
		f.setVisible(true);
	}
	public static void main(String [] args){
//		FrameChange fc=new FrameChange();
//		JFrame f=new JFrame();
//		JPanel p=new JPanel();
//		f.setSize(500, 500);
//		p.setLayout(new GridLayout(5, 5));
//		JButton [] b=new JButton[5];
//		for(int i=0;i<5;i++){
//			b[i]=new JButton(i+"");
//			p.add(b[i]);
//		}
//		JTextField tf=new JTextField();
//		p.add(tf);
//		f.add(p);
//		b[0].addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				fc.l=1;
//				fc.FrameC(f);
//			}
//		});
//		b[1].addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				fc.l=2;
//				fc.FrameC(f);
//			}
//		});
//		f.setVisible(true);
//	
//	
//	
	}
}
