package musicPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;
import javax.sound.sampled.DataLine.Info;


public class PlayMusic {

	private TimeProgressBar time;//��ʱ�����
	private JSlider goingMusic;//������
	private int goingMusicWZ;//�ƶ�������ǰ��λ��
	
	public int getGoingMusicWZ() {
		return goingMusicWZ;
	}

	public void setGoingMusicWZ(int goingMusicWZ) {
		this.goingMusicWZ = goingMusicWZ;
	}

	public TimeProgressBar getTime() {
		return time;
	}

	public void setTime(TimeProgressBar time) {
		this.time = time;
	}

	private SourceDataLine sourceDataLine;// Դ�������ǿ���д�����ݵ������С�Դ�����п��ܳ䵱��Ƶ���ݵ�Ŀ�ꡣ
	private AudioInputStream audioInputStream;// ��Ƶ������
	private URL audio;// ����λ��
	public Thread playThread;// �������ֵ��߳�

	public boolean IsPause = false;// �Ƿ�����ͣ����״̬
	public boolean IsComplete = false;// �Ƿ����
	public boolean IsEnd = false;// �Ƿ���ֹ

	// ����������Ƿ�����
	private boolean IsChoke;
	
	// �ж��Ƿ�����һ����һ����ť
	private static AtomicBoolean IsPushNOrP = new AtomicBoolean();

	private float nowByte=0;//�������е����ļ���С
	private float fullByte=0;//�ļ���С
	
	public boolean isPushNext=false;//����������
	public boolean isPushPre=false;//��ǰ��������
	
	public PlayMusic() {
		IsPushNOrP.set(false);
	}

	public static Boolean isIsPushNOrP() {
		return IsPushNOrP.get();
	}

	public static void setIsPushNOrP(Boolean isPushNOrP) {
		IsPushNOrP.set(isPushNOrP);
	}

	// ��ǰ�������ֵ�λ��
	private static int nowsMusic = 0;
	private boolean needBuildFirst=true;
	
	public boolean isNeedBuildFirst() {
		return needBuildFirst;
	}

	public void setNeedBuildFirst(boolean needBuildFirst) {
		this.needBuildFirst = needBuildFirst;
	}

	public static int getNowsMusic() {
		return nowsMusic;
	}

	public static void setNowsMusic(int nowsMusic) {
		PlayMusic.nowsMusic = nowsMusic;
	}


	public float getFullByte() {
		return fullByte;
	}

	public void setFullByte(float fullByte) {
		this.fullByte = fullByte;
	} 

	public void setFullByte(long fullByte) {
		this.fullByte = fullByte;
	}

	// ��������
	private static FloatControl floatVoiceControl;


	private int thisMusicTime;//��ǰ������ʱ��
	public int getThisMusicTime() {
		return thisMusicTime;
	}

	public void setThisMusicTime(int thisMusicTime) {
		this.thisMusicTime = thisMusicTime;
	}
	public static FloatControl getFloatVoiceControl() {
		return floatVoiceControl;
	}

	public static void setFloatVoiceControl(int voice) {
		PlayMusic.floatVoiceControl.setValue(voice);
	}

	/**
	 * ��һ�ζ�ȡ����ʱ��Ҫ�Ƚ���һ�οղ��Ż�ȡ�ļ���С
	 */
	public void returnFull() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(audio);
			AudioFormat audioFormat = audioInputStream.getFormat();
			if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16,
						audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
						false);
				audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
			}
			DataLine.Info info = new Info(SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceDataLine.open();
			sourceDataLine.start();
			byte[] buf = new byte[1024];
			int onceReadDataSize = 0;
			nowByte=0;
			while ((onceReadDataSize = audioInputStream.read(buf, 0, buf.length)) != -1) {
				IsChoke = false;
				nowByte+=1024;
				IsChoke = true;
			}
			IsChoke = false;
			sourceDataLine.drain();
			sourceDataLine.close();
			audioInputStream.close();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}
		fullByte=nowByte;
		TimeProgressBar.setTimerPause(false);
	}
	
	public void FirstBuild(boolean find){
		if(find){
			returnFull();
			goingMusic.setValue(0);

		}
		IsPause = false;
		IsComplete = false;
		IsEnd = false;
		IsPushNOrP.set(false);
		

	}
	private boolean isFirstStart=true;
	/**
	 * ����
	 */
	public synchronized void play() {
		FirstBuild(needBuildFirst);
		if(isFirstStart){
			goingMusic.setValue(0);
			isPushNext=false;
			isPushPre=false;
			isFirstStart=false;
			TimeProgressBar.setTimerPause(false);
		}
			try {
				// ��ȡ������Ƶ������
				audioInputStream = AudioSystem.getAudioInputStream(audio);
				System.out.println("audioClear");
				// ��ȡ��Ƶ�����ʽ
				AudioFormat audioFormat = audioInputStream.getFormat();
				System.out.println("audioFormatClear");
				// MPEG1L3תPCM_SIGNED
				if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16,
							audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
							false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
				}

				// �����������Ƶ��ʽ��ȡ����豸��Ϣ
				DataLine.Info info = new Info(SourceDataLine.class, audioFormat);
				System.out.println("infoClear");
				// ��ȡ����豸����
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
				System.out.println("����豸����Clear");
				// ������ܵ�
				sourceDataLine.open();
				// ����˹ܵ�ִ������ I/O
				sourceDataLine.start();
				System.out.println("�ܵ�ִ������Clear");
				// ��ȡ�������Ŀؼ�
				floatVoiceControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
				System.out.println("�������Ŀؼ�Clear");
				// ����minValue -80 maxValue 6
				// ����ʵĳ�ʼ����
				floatVoiceControl.setValue(0);
				//���ù����������ֵ��Сֵ
				goingMusic.setMinimum(0);
				goingMusic.setMaximum(100);
				//��������
				byte[] buf = new byte[1024];
				int onceReadDataSize = 0;
	
				nowByte=0;

				System.out.println(fullByte);
				while ((onceReadDataSize = audioInputStream.read(buf, 0, buf.length)) != -1) {
					//System.out.println("������");
					// ������û������
					IsChoke = false;
					
					if(isPushNext){
						nowByte+=1024;
						if((int)(nowByte*100/fullByte)>=goingMusic.getValue()){
							isPushNext=false;
							time.setCounter((int)(nowByte*thisMusicTime/fullByte));
							//System.out.println(nowByte);
							
						}
						else {TimeProgressBar.setTimerPause(false);continue;}
					}
					if(isPushPre){
						needBuildFirst=false;
						isPushPre=false;
						isPushNext=true;
						sourceDataLine.drain();
						sourceDataLine.close();
						audioInputStream.close();
						time.cleanTimer();
						time.StartNewMusicTime(thisMusicTime);
						this.play();
						needBuildFirst=true;
					}
					if (IsEnd) {
						return;
					}

					// �Ƿ���ͣ
					if (IsPause)
						pause();


					if (IsPushNOrP.get()) {
						//System.out.println("ͣס��");
						CheckNOP();
						//System.out.println("������");
						break;
					}
					// ������д���Ƶ���� ������˿�д��ǰ����
					//System.out.println("����Ƶ");
					sourceDataLine.write(buf, 0, onceReadDataSize);
					nowByte+=1024;
					//System.out.println("111");
					// Ԥ������������
					//System.out.println(nowByte);
					//System.out.println(nowByte/fullByte);
					//System.out.println(goingMusic.getValue());
					IsChoke = true;
				}
				System.out.println("��ѭ��Clear");
				IsChoke = false;
				// ��ˢ����������
				sourceDataLine.drain();
				sourceDataLine.close();
				audioInputStream.close();
				goingMusic.setValue(0);
				isFirstStart=true;

			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {

				e.printStackTrace();
			}
			
		}

	public JSlider getGoingMusic() {
		return goingMusic;
	}

	public void setGoingMusic(JSlider goingMusic) {
		this.goingMusic = goingMusic;
	}

	/**
	 * 
	 * 
	 * ���ظ���
	 */
	public void load(URL url) {
		this.audio = url;
	}

	/**
	 * ��������
	 */
	public synchronized void resume() {
		if (IsPause == false)
			return;
		IsPause = false;
		TimeProgressBar.setTimerPause(false);
		time.resumeTimer();
		this.notify();
		
	}

	/**
	 * ��ͣ
	 */
	private synchronized void pause() throws InterruptedException {
		TimeProgressBar.setTimerPause(true);
		this.wait();

	}

	private synchronized void CheckNOP() throws InterruptedException {
		IsPushNOrP.set(false);
	}

	/**
	 * ����
	 */
	public void end(){
		try {
			if (playThread == null)
				return;
			/**
			 * �������ʼ��
			 */
			IsPause = true;
			IsComplete = false;
			IsEnd = true;
			isFirstStart=true;
			isPushNext=false;
			isPushPre=false;
			//���ʱ���ʱ��
			time.cleanTimer();
			// �رյ�ǰ��������ܵ�
			sourceDataLine.close();
			audioInputStream.close();
			//�رղ����߳�
			playThread = null;
			//��������0
			goingMusic.setValue(0);
			goingMusicWZ=0;

		} catch (Exception e) {
			System.out.println("�жϲ��ŵ�ǰ����");
			IsPause = true;
			IsComplete = false;
			IsEnd = true;
		}
	}
}