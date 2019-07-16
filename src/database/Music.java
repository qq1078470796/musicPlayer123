package database;

import java.io.Serializable;
/**
 * 歌曲信息类
 * @author asus   pc
 *
 */
public class Music implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6392077073813254302L;
	private String song;//歌曲名1
	private String singer;//歌手3
	private String album;//专辑5

	private int totalTime;//时间
	private float dataSize;//文件大小7

	private String songDataUrl;//歌曲位置2
	private String lrcUrl;//歌词位置4
	private int bitRate;//大概的比特率6
	
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

	public Music(String song, String songDataUrl) {// 本地歌曲只需要初始化歌曲名字和歌曲目录
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
	 * 网络歌曲直接在爬取的时候就完成所有数据的初始化
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