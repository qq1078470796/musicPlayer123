package main;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class FrameChange {
	public static JFrame f;
	/**
	 * Ϊ���ڻ�����
	 * @param l
	 */
	public static void FrameC(int l){
		f.setVisible(false);
		if(l==1){
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		else if(l==4){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		else if(l==5){
			try {
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		System.out.println("��ǰ����"+l);
//		/*
//		com.jtattoo.plaf.noire.NoireLookAndFeel  ��ͺ�
//		com.jtattoo.plaf.smart.SmartLookAndFeel ľ�ʸ�+xp���
//		com.jtattoo.plaf.mint.MintLookAndFeel  ��Բ��ť+��ɫ��ť����
//		com.jtattoo.plaf.mcwin.McWinLookAndFeel ��Բ��ť+��ɫ��ť����
//		com.jtattoo.plaf.hifi.HiFiLookAndFeel  ��ɫ���
//		com.jtattoo.plaf.fast.FastLookAndFeel  ��ͨswing���+��ɫ�߿�
//		com.jtattoo.plaf.bernstein.BernsteinLookAndFeel  ��ɫ���
//		com.jtattoo.plaf.aluminium.AluminiumLookAndFeel ��Բ��ť+����ɫ��ť����+�����ʸ�
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
