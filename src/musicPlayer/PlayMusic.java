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

	private TimeProgressBar time;//计时器面板
	private JSlider goingMusic;//进度条
	private int goingMusicWZ;//移动进度条前的位置
	
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

	private SourceDataLine sourceDataLine;// 源数据行是可以写入数据的数据行。源数据行可能充当音频数据的目标。
	private AudioInputStream audioInputStream;// 音频输入流
	private URL audio;// 音乐位置
	public Thread playThread;// 播放音乐的线程

	public boolean IsPause = false;// 是否处于暂停播放状态
	public boolean IsComplete = false;// 是否完成
	public boolean IsEnd = false;// 是否终止

	// 检测输入流是否阻塞
	private boolean IsChoke;
	
	// 判断是否按下上一曲下一曲按钮
	private static AtomicBoolean IsPushNOrP = new AtomicBoolean();

	private float nowByte=0;//现在运行到的文件大小
	private float fullByte=0;//文件大小
	
	public boolean isPushNext=false;//向后调滚动条
	public boolean isPushPre=false;//向前调滚动条
	
	public PlayMusic() {
		IsPushNOrP.set(false);
	}

	public static Boolean isIsPushNOrP() {
		return IsPushNOrP.get();
	}

	public static void setIsPushNOrP(Boolean isPushNOrP) {
		IsPushNOrP.set(isPushNOrP);
	}

	// 当前播放音乐的位置
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

	// 音量控制
	private static FloatControl floatVoiceControl;


	private int thisMusicTime;//当前歌曲的时间
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
	 * 第一次读取歌曲时需要先进行一次空播放获取文件大小
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
	 * 播放
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
				// 获取本地音频输入流
				audioInputStream = AudioSystem.getAudioInputStream(audio);
				System.out.println("audioClear");
				// 获取音频编码格式
				AudioFormat audioFormat = audioInputStream.getFormat();
				System.out.println("audioFormatClear");
				// MPEG1L3转PCM_SIGNED
				if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16,
							audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
							false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
				}

				// 根据上面的音频格式获取输出设备信息
				DataLine.Info info = new Info(SourceDataLine.class, audioFormat);
				System.out.println("infoClear");
				// 获取输出设备对象
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
				System.out.println("输出设备对象Clear");
				// 打开输出管道
				sourceDataLine.open();
				// 允许此管道执行数据 I/O
				sourceDataLine.start();
				System.out.println("管道执行数据Clear");
				// 获取总音量的控件
				floatVoiceControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
				System.out.println("总音量的控件Clear");
				// 音量minValue -80 maxValue 6
				// 设合适的初始音量
				floatVoiceControl.setValue(0);
				//设置滚动条的最大值最小值
				goingMusic.setMinimum(0);
				goingMusic.setMaximum(100);
				//单缓冲区
				byte[] buf = new byte[1024];
				int onceReadDataSize = 0;
	
				nowByte=0;

				System.out.println(fullByte);
				while ((onceReadDataSize = audioInputStream.read(buf, 0, buf.length)) != -1) {
					//System.out.println("进入流");
					// 输入流没有阻塞
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

					// 是否暂停
					if (IsPause)
						pause();


					if (IsPushNOrP.get()) {
						//System.out.println("停住了");
						CheckNOP();
						//System.out.println("出来了");
						break;
					}
					// 将数据写入混频器中 至输出端口写完前阻塞
					//System.out.println("出音频");
					sourceDataLine.write(buf, 0, onceReadDataSize);
					nowByte+=1024;
					//System.out.println("111");
					// 预设输入流阻塞
					//System.out.println(nowByte);
					//System.out.println(nowByte/fullByte);
					//System.out.println(goingMusic.getValue());
					IsChoke = true;
				}
				System.out.println("出循环Clear");
				IsChoke = false;
				// 冲刷缓冲区数据
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
	 * 加载歌曲
	 */
	public void load(URL url) {
		this.audio = url;
	}

	/**
	 * 继续播放
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
	 * 暂停
	 */
	private synchronized void pause() throws InterruptedException {
		TimeProgressBar.setTimerPause(true);
		this.wait();

	}

	private synchronized void CheckNOP() throws InterruptedException {
		IsPushNOrP.set(false);
	}

	/**
	 * 结束
	 */
	public void end(){
		try {
			if (playThread == null)
				return;
			/**
			 * 结束后初始化
			 */
			IsPause = true;
			IsComplete = false;
			IsEnd = true;
			isFirstStart=true;
			isPushNext=false;
			isPushPre=false;
			//清空时间计时器
			time.cleanTimer();
			// 关闭当前数据输入管道
			sourceDataLine.close();
			audioInputStream.close();
			//关闭播放线程
			playThread = null;
			//进度条回0
			goingMusic.setValue(0);
			goingMusicWZ=0;

		} catch (Exception e) {
			System.out.println("中断播放当前歌曲");
			IsPause = true;
			IsComplete = false;
			IsEnd = true;
		}
	}
}