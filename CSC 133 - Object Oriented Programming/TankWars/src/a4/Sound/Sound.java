package a4.Sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Sound {
	AudioClip clip;
	boolean muted;
	
	public Sound(String fileName) {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				clip = Applet.newAudioClip(file.toURI().toURL());
			}
			else {
				throw new RuntimeException("Sound file not found: " + fileName);
			}
		}
		catch (MalformedURLException mEx) {
			throw new RuntimeException("Malformed sound URL: " + mEx);
		}
	}

	public void play() {
		if (!isMuted())
			clip.play();
	}

	public void loop() {
		clip.loop();
	}

	public void stop() {
		clip.stop();
	}
	
	private boolean isMuted() {
		return muted;
	}
	
	public void setMuted(boolean b) {
		muted = b;
	}
}
