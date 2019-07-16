package musicPlayer;

import javax.swing.JSlider;

public class VoiceTool {
	public JSlider getVoiceTool(){
		JSlider voiceTool=new JSlider(-20, 5);
		return voiceTool;
	}
	/**
	 * æ≤“Ù
	 */
	public void stopMusicVoice(){
		PlayMusic.setFloatVoiceControl(-80);
	}
}
