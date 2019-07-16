package musicPlayer;

public class PreMusicAndNextMusic {
	/**
	 * 上一首歌曲
	 */
	public void getPreMusic() {
		// System.out.println("前一首"+PlayMusic.getNowsMusic());
		if (PlayMusic.getNowsMusic() - 1 >= 0) {
			PlayMusic.setNowsMusic(PlayMusic.getNowsMusic() - 1);
			// System.out.println(PlayMusic.getNowsMusic());
			PlayMusic.setIsPushNOrP(true);
		}

		else
			System.out.println("当前为第一首");// 如果是第一首则什么也不做
		System.out.println(PlayMusic.getNowsMusic());
	}

	/**
	 * 下一首歌曲
	 */
	public void getNextMusic() {
		System.out.println("后一首" + PlayMusic.getNowsMusic());
		System.out.println("总长" + OpenMusic.getLenth());
		if (PlayMusic.getNowsMusic() + 1 < OpenMusic.getLenth()) {// 如果是最后一首则什么也不做
			PlayMusic.setNowsMusic(PlayMusic.getNowsMusic() + 1);
			//System.out.println(PlayMusic.getNowsMusic());
			PlayMusic.setIsPushNOrP(true);
		} else {
			System.out.println("最后一首");
		}
		//System.out.println(PlayMusic.getNowsMusic());
	}
}
