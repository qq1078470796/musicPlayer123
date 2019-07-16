package musicPlayer;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

public class PlayKinds {
	private int id=1;//���ŷ�ʽ��ѡ������
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * ����ѭ��
	 * @param play
	 */
	public void oneRound(JButton play){
		play.doClick();
	}
	/**
	 * ֻ����һ��
	 * @param play
	 */
	public void onePlay(JButton play){
	}
	/**
	 * �б���
	 * @param play
	 */
	public void ListPlay(JButton next){
		next.doClick();
	}
	/**
	 * �б�ѭ��
	 * @param next
	 */
	public void ListRound(JButton next){
		if(PlayMusic.getNowsMusic()==OpenMusic.getLenth()-1)PlayMusic.setNowsMusic(-1);
		next.doClick();
	}
	/**
	 * �������
	 * @param play
	 */
	public void RandPlay(JButton play){
		int randomMusic=((int)(Math.random()*100))%OpenMusic.getLenth();
		System.out.println("��ǰ������ŵĸ���Ϊ"+randomMusic);
		PlayMusic.setNowsMusic(randomMusic);
		play.doClick();
	}
	public void switchKind(JButton play,JButton next){
		// ��ʼ������״̬
		switch(id){
		case 1:
			return;
		case 2:
			oneRound(play);break;
		case 3:
			System.out.println("�����б���");ListPlay(next);break;
		case 4:
			ListRound(next);break;
		case 5:
			RandPlay(play);break;
		}
		
	}
}
