package musicPlayer;

public class PreMusicAndNextMusic {
	/**
	 * ��һ�׸���
	 */
	public void getPreMusic() {
		// System.out.println("ǰһ��"+PlayMusic.getNowsMusic());
		if (PlayMusic.getNowsMusic() - 1 >= 0) {
			PlayMusic.setNowsMusic(PlayMusic.getNowsMusic() - 1);
			// System.out.println(PlayMusic.getNowsMusic());
			PlayMusic.setIsPushNOrP(true);
		}

		else
			System.out.println("��ǰΪ��һ��");// ����ǵ�һ����ʲôҲ����
		System.out.println(PlayMusic.getNowsMusic());
	}

	/**
	 * ��һ�׸���
	 */
	public void getNextMusic() {
		System.out.println("��һ��" + PlayMusic.getNowsMusic());
		System.out.println("�ܳ�" + OpenMusic.getLenth());
		if (PlayMusic.getNowsMusic() + 1 < OpenMusic.getLenth()) {// ��������һ����ʲôҲ����
			PlayMusic.setNowsMusic(PlayMusic.getNowsMusic() + 1);
			//System.out.println(PlayMusic.getNowsMusic());
			PlayMusic.setIsPushNOrP(true);
		} else {
			System.out.println("���һ��");
		}
		//System.out.println(PlayMusic.getNowsMusic());
	}
}
