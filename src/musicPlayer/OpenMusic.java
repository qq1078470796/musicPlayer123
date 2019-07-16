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
	
	SongInfos infos=new SongInfos();//�򿪸�����ͬʱ������
	
	private Music [] songInformation=new Music[20];//��Ŷ�ȡ�ĸ�����Ϣ
	
	public File tempFile;//��ʱ��Ŷ�ȡ�ĸ���Ŀ¼
	
	private static int lenth;//�����Ÿ����б�ĳ���
	
	public static int getLenth() {
		return lenth;
	}
	public static void setLenth(int lenth) {
		OpenMusic.lenth = lenth;
	}
	/**
	 * ���캯����ʼ�������б���Ϊ0
	 */
	public OpenMusic(){
		lenth=0;
	}
	/**
	 * ��ø�������ʱ��
	 * @param url
	 * @return
	 */
	public int getAudioTrackLength(URL url) {
 		try {

 			// ֻ�ܻ�ñ��ظ����ļ�����Ϣ,AudioFile���ⲿ����İ�
 			AudioFile file = AudioFileIO.read(new File(url.toURI()));

 			// ��ȡ��Ƶ�ļ���ͷ��Ϣ
 			AudioHeader audioHeader = file.getAudioHeader();
 			// �ļ����� ת����ʱ��
 			return audioHeader.getTrackLength();
 		} catch (CannotReadException | IOException | TagException
 				| ReadOnlyFileException | InvalidAudioFrameException
 				| URISyntaxException e) {
 			e.printStackTrace();
 			return -1;
 		}

 	}
	//ͨ���Ի����ȡ��������File��Ϣ��
		public void  getFile(){
			//�ļ�ѡ����
			JFileChooser fc = new JFileChooser();
			
			//�����ļ�ѡ�����Ĺ�����Ϊ.mp3��.wav
			fc.setFileFilter(new FileNameExtensionFilter("�����ļ�(.mp3/.wav)", "mp3","wav"));
			//�ļ�ѡ������ģʽ(��ѡ)
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//��ʾ���ļ�ѡ�����Ĵ���
			int returnVal = fc.showOpenDialog(fc);
			//���ر�ѡ�еĶ���
			File file_choosed = fc.getSelectedFile();
			//������ʱ����ļ�
			tempFile=file_choosed;
		}
		
		//�Ѹ�����Ϣ����������б�
		public void saveMusicIntoList(){
			
			getSongInformation()[lenth]=new Music();//�½�һ������ʵ��
			getSongInformation()[lenth].setSongDataUrl(tempFile.toURI().toString());//�ڴ������б��б��������Ŀ¼
			setInformations(tempFile, lenth);//���������Ϣ
			setInfos(tempFile,lenth);//������λ����Ϣ
			
			//System.out.println(getSongInformation()[lenth].getSongDataUrl());
			
			//���¼���ĸ�������ݿ�
			Crud c=new Crud();
			//����һ����ʱ��������Ÿն���ĸ���������������б�
			Music temp=new Music();
			temp.setSongDataUrl(getSongInformation()[lenth].getSongDataUrl());//����ʱ�����ĸ���·����ֵ
			temp.setSong(getSongInformation()[lenth].getSong());//����ʱ�����ĸ�����ֵ
			temp.setLrcUrl(getSongInformation()[lenth].getLrcUrl());//����ʱ�����ĸ��·����ֵ
			
			c.insertMusic(temp);
			//�������б��ȼ�1
			lenth++;
			//System.out.println(("Music l"+lenth));
		}
	
		//������ʵ��С�������ݿ�
	public void updateDataBase(){
		Crud c=new Crud();
		c.update(getSongInformation()[PlayMusic.getNowsMusic()]);
		System.out.println(PlayMusic.getNowsMusic());
	}
	//���ø�����Ϣ���˴�Ϊ�������֣�ֻ��ȡ������
	public void setInformations(File t,int l){
		/**
		 * �иʽ���е�·�����һ��\ǰ�Ķ���+.��Ķ���
		 */
		int firstIndex=t.toString().lastIndexOf("\\");
		int lastIndex=t.toString().lastIndexOf(".");
		String temp=t.toString().substring(firstIndex+1, lastIndex);
		getSongInformation()[l].setSong(temp);
		System.out.println(temp);
	}
	/**
	 * ��ȡͬĿ¼�������ͬ���ĸ��
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
	public void sysoAll(){ //�����ã����ȫ���б�
		for(int i=0;i<lenth;i++){
			System.out.println(songInformation[i].getSongDataUrl());
			System.out.println(songInformation[i].getDataSize());
		}
	}

/**
 * ���ʱ��ķ��ӱ�ʾ
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
