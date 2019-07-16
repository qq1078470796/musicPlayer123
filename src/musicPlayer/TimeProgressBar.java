package musicPlayer;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * 播放进度条（显示当前播放进度，不可调戏）
 * @author asus   pc
 *
 */

public class TimeProgressBar extends JProgressBar {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8892832435628927155L;
	
	private  static boolean timerPause;//暂停时间
	
	private SongInfos infos;//时间轴与歌词播放同步
	
	public SongInfos getInfos() {
		return infos;
	}

	public void setInfos(SongInfos infos) {
		this.infos = infos;
	}

	public static boolean isTimerPause() {
		return timerPause;
	}

	public static void setTimerPause(boolean timerPause) {
		TimeProgressBar.timerPause = timerPause;
	}

	private int audioTotalTime;//歌曲总时间
	
	private int counter;//播放计数

	private Timer timer;//计时器
	private Task task;//计时器反复执行的任务
	
	private JLabel [] infoss;//显示歌词的面板
	public JLabel[] getInfoss() {
		return infoss;
	}

	public void setInfoss(JLabel[] infoss) {
		this.infoss = infoss;
	}


	private AtomicInteger infossNum=new AtomicInteger(0);//当前展示歌词面板的位置

	//显示当前播放时间的label
	private JLabel currentTimeCountLabel;
	


	public JLabel getCurrentTimeCountLabel() {
		return currentTimeCountLabel;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public TimeProgressBar() {
		counter = 0;
		setMinimum(0);
		setStringPainted(false);

	}

	// 启动计时器
	public void startTimer() {

		timer = new Timer(true);
		
		task = new Task();
		
		timer.schedule(task, 500, 1000);
//		第一个参数，是 TimerTask 类，使用者要继承该类，并实现public void run() 方法，因为 TimerTask 类 实现了 Runnable 接口。
//		第二个参数的意思是，当你调用该方法后，该方法必然会调用 TimerTask 类 TimerTask 类 中的 run()方法
//		，这个参数就是这两者之间的差值，转换成汉语的意思就是说
//		，用户调用 schedule() 方法后，要等待这么长的时间才可以第一次执行run() 方法。
//		第三个参数的意思就是，第一次调用之后，从第二次开始每隔多长的时间调用一次 run() 方法。

	}

	// 重启计时器
	public void resumeTimer() {
		synchronized (timer) {
			timer.notify();
		}
	}

	// 重置计时器
	public void cleanTimer() {
		counter = 0;
		if (timer != null) {
			//调用之后整个Timer的线程都会结束掉
			timer.cancel();
			//purge方法就是用来释放内存引用的
			timer.purge();
		}

		currentTimeCountLabel.setText("0:00");
		this.setValue(0);
		

	}

	public void setAudioTotalTime(int n) {
		audioTotalTime = n;
		super.setMaximum(n);
	}


	public void setCurrentTimeCountLabel(JLabel currentTimeCountLabel) {
		this.currentTimeCountLabel = currentTimeCountLabel;
	}


	class Task extends TimerTask {

		@Override
		public void run() {
			synchronized (timer) {
				if (counter == audioTotalTime) {
					// 终止计数器 但当前任务还会被执行
					cleanTimer();
					return;
				}

				if (timerPause) {
					try {
						timer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				}

				counter += 1;
				//播放进度条的值设置为当前的value
				TimeProgressBar.this.setValue(counter);
				//提示时间的JLabel也跟随counter的改变而改变
				TimeProgressBar.this.currentTimeCountLabel
						.setText(getCurrentTime(counter));
				
				
				//如果打印到了第五个JLabel，那下一个打印的就是第一个
				if(infossNum.get()>4)infossNum.set(0);
				//如果对应时间存在歌词并且歌词不是空串
				if(infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
							.getText())!=null&&infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
									.getText())!=""){
				//让一个JLabel显示当前时间的歌词
				infoss[infossNum.get()].setText(infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
							.getText()));
				/**
				 * 设置红色边框表明当前歌词
				 */
				infoss[infossNum.get()].setBorder(BorderFactory.createLineBorder(Color.red));
				//取消上一个JLabel的红色边框
				if(infossNum.get()>0){
					infoss[infossNum.get()-1].setBorder(null);
				}
				else{
					infoss[4].setBorder(null);
				}
				
				infossNum.set(infossNum.get()+1);
				}
	
				//System.out.println(infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
				//			.getText()));
			}
		}

		// 转换时间
		public String getCurrentTime(int sec) {
			String time = "00:00";

			if (sec <= 0)
				return time;

			int minute = sec / 60;
			int second = sec % 60;
			int hour = minute / 60;

			if (second < 10)
				time = minute + ":0" + second;
			else
				time = minute + ":" + second;

			if (hour != 0)
				time = hour + ":" + time;

			return time;
		}
		//重新开始一个计时器
		public void StartNewMusicTime(int thisMusicTime){
			for(int i=0;i<5;i++){
				infoss[i].setText("");
				infoss[i].setBorder(null);
			}
			// 重置计时器
			cleanTimer();
			infossNum.set(0);
			// 启动新的计时器
			setAudioTotalTime(thisMusicTime);
			startTimer();
		}
	}
