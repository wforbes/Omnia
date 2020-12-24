package net.wforbes.platformer.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

	//data structure that holds the decoded audio
	private Clip clip;
	
	public AudioPlayer(String s){
		//load in audio
		try{
			AudioInputStream inputStream =
					AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = inputStream.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
					);
					
			AudioInputStream decodedInputStream =
					AudioSystem.getAudioInputStream(decodeFormat, inputStream);
			
			clip = AudioSystem.getClip();
			clip.open(decodedInputStream);
			
		}catch(Exception e){e.printStackTrace();}
	}
		public void play(){
			//if there is no clip, just return this shit
			if(clip == null)
				return;
			//call stop to stop whatever is already playing
			stop();
			//set it to the beginning of the clip
			clip.setFramePosition(0);
			//start the clip
			clip.start();
		}
		
		public void stop(){
			//since calling clip.stop() while something isnt running might
			//		screw something up, make sure somethings running before calling
			//		stop on the clip
			if(clip.isRunning()) clip.stop();
		}
		
		public void close(){
			stop();
			clip.close();
		}
		
		
}

