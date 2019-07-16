package database;

import java.io.Serializable;
/**
 * ������Ϣ��
 * @author asus   pc
 *
 */
public class Music implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6392077073813254302L;
	private String song;//������1
	private String singer;//����3
	private String album;//ר��5

	private int totalTime;//ʱ��
	private float dataSize;//�ļ���С7

	private String songDataUrl;//����λ��2
	private String lrcUrl;//���λ��4
	private int bitRate;//��ŵı�����6
	
	public Music() {
		super();
		this.song = "";
		this.singer = "";
		this.album = "";
		this.totalTime = 0;
		this.dataSize = 0;
		this.lrcUrl = "";
		this.bitRate = 0;
		this.songDataUrl="";
	}

	public Music(String song, String songDataUrl) {// ���ظ���ֻ��Ҫ��ʼ���������ֺ͸���Ŀ¼
		super();
		this.song = song;
		this.singer = "";
		this.album = "";
		this.totalTime = 0;
		this.dataSize = 0;
		this.songDataUrl = songDataUrl;
		this.lrcUrl = "";
		this.bitRate = 0;
	}
	/**
	 * �������ֱ������ȡ��ʱ�������������ݵĳ�ʼ��
	 * @param song
	 * @param singer
	 * @param album
	 * @param totalTime
	 * @param dataSize
	 * @param songDataUrl
	 * @param lrcUrl
	 * @param bitRate
	 */
	public Music(String song, String singer, String album, int totalTime, int dataSize, String songDataUrl,
			String lrcUrl, int bitRate) {
		super();
		this.song = song;
		this.singer = singer;
		this.album = album;
		this.totalTime = totalTime;
		this.dataSize = dataSize;
		this.songDataUrl = songDataUrl;
		this.lrcUrl = lrcUrl;
		this.bitRate = bitRate;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}



	public void setDataSize(float f) {
		this.dataSize = f;
	}

	public float getDataSize() {
		return dataSize;
	}

	public String getSongDataUrl() {
		return songDataUrl;
	}

	public void setSongDataUrl(String songDataUrl) {
		this.songDataUrl = songDataUrl;
	}

	public String getLrcUrl() {
		return lrcUrl;
	}

	public void setLrcUrl(String lrcUrl) {
		this.lrcUrl = lrcUrl;
	}

	public int getBitRate() {
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}
}