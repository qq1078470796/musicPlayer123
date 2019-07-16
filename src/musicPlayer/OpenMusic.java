package musicPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import database.Crud;
import database.Music;

public class OpenMusic {
	
	SongInfos infos=new SongInfos();//打开歌曲的同时读入歌词
	
	private Music [] songInformation=new Music[20];//存放读取的歌曲信息
	
	public File tempFile;//临时存放读取的歌曲目录
	
	private static int lenth;//待播放歌曲列表的长度
	
	public static int getLenth() {
		return lenth;
	}
	public static void setLenth(int lenth) {
		OpenMusic.lenth = lenth;
	}
	/**
	 * 构造函数初始化歌曲列表长度为0
	 */
	public OpenMusic(){
		lenth=0;
	}
	/**
	 * 获得歌曲的总时间
	 * @param url
	 * @return
	 */
	public int getAudioTrackLength(URL url) {
 		try {

 			// 只能获得本地歌曲文件的信息,AudioFile是外部导入的包
 			AudioFile file = AudioFileIO.read(new File(url.toURI()));

 			// 获取音频文件的头信息
 			AudioHeader audioHeader = file.getAudioHeader();
 			// 文件长度 转换成时间
 			return audioHeader.getTrackLength();
 		} catch (CannotReadException | IOException | TagException
 				| ReadOnlyFileException | InvalidAudioFrameException
 				| URISyntaxException e) {
 			e.printStackTrace();
 			return -1;
 		}

 	}
	//通过对话框获取到歌曲的File信息；
		public void  getFile(){
			//文件选择器
			JFileChooser fc = new JFileChooser();
			
			//设置文件选择器的过滤器为.mp3和.wav
			fc.setFileFilter(new FileNameExtensionFilter("音乐文件(.mp3/.wav)", "mp3","wav"));
			//文件选择器的模式(单选)
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//显示出文件选择器的窗口
			int returnVal = fc.showOpenDialog(fc);
			//返回被选中的对象
			File file_choosed = fc.getSelectedFile();
			//放入临时存放文件
			tempFile=file_choosed;
		}
		
		//把歌曲信息放入待播放列表
		public void saveMusicIntoList(){
			
			getSongInformation()[lenth]=new Music();//新建一个歌曲实例
			getSongInformation()[lenth].setSongDataUrl(tempFile.toURI().toString());//在待播放列表中保存歌曲的目录
			setInformations(tempFile, lenth);//保存歌曲信息
			setInfos(tempFile,lenth);//保存歌词位置信息
			
			//System.out.println(getSongInformation()[lenth].getSongDataUrl());
			
			//把新加入的歌放入数据库
			Crud c=new Crud();
			//构造一个临时变量来存放刚读入的歌曲并放入待播放列表
			Music temp=new Music();
			temp.setSongDataUrl(getSongInformation()[lenth].getSongDataUrl());//给临时变量的歌曲路径赋值
			temp.setSong(getSongInformation()[lenth].getSong());//给临时变量的歌名赋值
			temp.setLrcUrl(getSongInformation()[lenth].getLrcUrl());//给临时变量的歌词路径赋值
			
			c.insertMusic(temp);
			//待播放列表长度加1
			lenth++;
			//System.out.println(("Music l"+lenth));
		}
	
		//歌曲真实大小存入数据库
	public void updateDataBase(){
		Crud c=new Crud();
		c.update(getSongInformation()[PlayMusic.getNowsMusic()]);
		System.out.println(PlayMusic.getNowsMusic());
	}
	//设置歌曲信息（此处为本地音乐，只获取歌名）
	public void setInformations(File t,int l){
		/**
		 * 切割方式，切掉路径最后一个\前的东西+.后的东西
		 */
		int firstIndex=t.toString().lastIndexOf("\\");
		int lastIndex=t.toString().lastIndexOf(".");
		String temp=t.toString().substring(firstIndex+1, lastIndex);
		getSongInformation()[l].setSong(temp);
		System.out.println(temp);
	}
	/**
	 * 获取同目录下与歌曲同名的歌词
	 */
	public void setInfos(File t,int l){
		int lastIndex=t.toString().lastIndexOf(".");
		String temp=t.toString().substring(0, lastIndex);
		temp=temp+".lrc";
		getSongInformation()[l].setLrcUrl(temp);
		System.out.println(temp);
	}
	
	public Music [] getSongInformation() {
		return songInformation;
	}
	public void setSongInformation(Music [] songInformation) {
		this.songInformation = songInformation;
	}
	public void sysoAll(){ //测试用，输出全部列表
		for(int i=0;i<lenth;i++){
			System.out.println(songInformation[i].getSongDataUrl());
			System.out.println(songInformation[i].getDataSize());
		}
	}

/**
 * 获得时间的分钟表示
 * @param sec
 * @return
 */
 	public String getAudioTotalTime(int sec) {
 		String time = "0:00";
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

}
