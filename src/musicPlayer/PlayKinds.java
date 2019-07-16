package musicPlayer;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;

public class PlayKinds {
	private int id=1;//播放方式的选择依据
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 单曲循环
	 * @param play
	 */
	public void oneRound(JButton play){
		play.doClick();
	}
	/**
	 * 只播放一次
	 * @param play
	 */
	public void onePlay(JButton play){
	}
	/**
	 * 列表播放
	 * @param play
	 */
	public void ListPlay(JButton next){
		next.doClick();
	}
	/**
	 * 列表循环
	 * @param next
	 */
	public void ListRound(JButton next){
		if(PlayMusic.getNowsMusic()==OpenMusic.getLenth()-1)PlayMusic.setNowsMusic(-1);
		next.doClick();
	}
	/**
	 * 随机播放
	 * @param play
	 */
	public void RandPlay(JButton play){
		int randomMusic=((int)(Math.random()*100))%OpenMusic.getLenth();
		System.out.println("当前随机播放的歌曲为"+randomMusic);
		PlayMusic.setNowsMusic(randomMusic);
		play.doClick();
	}
	public void switchKind(JButton play,JButton next){
		// 初始化播放状态
		switch(id){
		case 1:
			return;
		case 2:
			oneRound(play);break;
		case 3:
			System.out.println("进入列表播放");ListPlay(next);break;
		case 4:
			ListRound(next);break;
		case 5:
			RandPlay(play);break;
		}
		
	}
}
