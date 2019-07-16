package musicPlayer;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * ���Ž���������ʾ��ǰ���Ž��ȣ����ɵ�Ϸ��
 * @author asus   pc
 *
 */

public class TimeProgressBar extends JProgressBar {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8892832435628927155L;
	
	private  static boolean timerPause;//��ͣʱ��
	
	private SongInfos infos;//ʱ�������ʲ���ͬ��
	
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

	private int audioTotalTime;//������ʱ��
	
	private int counter;//���ż���

	private Timer timer;//��ʱ��
	private Task task;//��ʱ������ִ�е�����
	
	private JLabel [] infoss;//��ʾ��ʵ����
	public JLabel[] getInfoss() {
		return infoss;
	}

	public void setInfoss(JLabel[] infoss) {
		this.infoss = infoss;
	}


	private AtomicInteger infossNum=new AtomicInteger(0);//��ǰչʾ�������λ��

	//��ʾ��ǰ����ʱ���label
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

	// ������ʱ��
	public void startTimer() {

		timer = new Timer(true);
		
		task = new Task();
		
		timer.schedule(task, 500, 1000);
//		��һ���������� TimerTask �࣬ʹ����Ҫ�̳и��࣬��ʵ��public void run() ��������Ϊ TimerTask �� ʵ���� Runnable �ӿڡ�
//		�ڶ�����������˼�ǣ�������ø÷����󣬸÷�����Ȼ����� TimerTask �� TimerTask �� �е� run()����
//		�������������������֮��Ĳ�ֵ��ת���ɺ������˼����˵
//		���û����� schedule() ������Ҫ�ȴ���ô����ʱ��ſ��Ե�һ��ִ��run() ������
//		��������������˼���ǣ���һ�ε���֮�󣬴ӵڶ��ο�ʼÿ���೤��ʱ�����һ�� run() ������

	}

	// ������ʱ��
	public void resumeTimer() {
		synchronized (timer) {
			timer.notify();
		}
	}

	// ���ü�ʱ��
	public void cleanTimer() {
		counter = 0;
		if (timer != null) {
			//����֮������Timer���̶߳��������
			timer.cancel();
			//purge�������������ͷ��ڴ����õ�
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
					// ��ֹ������ ����ǰ���񻹻ᱻִ��
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
				//���Ž�������ֵ����Ϊ��ǰ��value
				TimeProgressBar.this.setValue(counter);
				//��ʾʱ���JLabelҲ����counter�ĸı���ı�
				TimeProgressBar.this.currentTimeCountLabel
						.setText(getCurrentTime(counter));
				
				
				//�����ӡ���˵����JLabel������һ����ӡ�ľ��ǵ�һ��
				if(infossNum.get()>4)infossNum.set(0);
				//�����Ӧʱ����ڸ�ʲ��Ҹ�ʲ��ǿմ�
				if(infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
							.getText())!=null&&infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
									.getText())!=""){
				//��һ��JLabel��ʾ��ǰʱ��ĸ��
				infoss[infossNum.get()].setText(infos.getInfos().get(TimeProgressBar.this.currentTimeCountLabel
							.getText()));
				/**
				 * ���ú�ɫ�߿������ǰ���
				 */
				infoss[infossNum.get()].setBorder(BorderFactory.createLineBorder(Color.red));
				//ȡ����һ��JLabel�ĺ�ɫ�߿�
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

		// ת��ʱ��
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
		//���¿�ʼһ����ʱ��
		public void StartNewMusicTime(int thisMusicTime){
			for(int i=0;i<5;i++){
				infoss[i].setText("");
				infoss[i].setBorder(null);
			}
			// ���ü�ʱ��
			cleanTimer();
			infossNum.set(0);
			// �����µļ�ʱ��
			setAudioTotalTime(thisMusicTime);
			startTimer();
		}
	}
