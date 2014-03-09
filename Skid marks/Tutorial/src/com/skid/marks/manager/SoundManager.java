package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private Map<String, Sound> sounds = new HashMap<String, Sound>();
	private Map<String, Music> music = new HashMap<String, Music>();
	
	public SoundManager() {
		// Sounds
		sounds.put("explosion", Gdx.audio.newSound(Gdx.files.internal("data/sfx/explosion.wav")));
		
		// Music
		music.put("background", Gdx.audio.newMusic(Gdx.files.internal("data/sfx/background.mp3")));
		music.put("menu", Gdx.audio.newMusic(Gdx.files.internal("data/sfx/menu.mp3")));
	}
	
	public void dispose() {
		for(Sound s : sounds.values()) {
			s.dispose();
		}
		for(Music m : music.values()) {
			m.dispose();
		}
	}
	
	/*
	 * Spela upp ett ljud eller musik
	 **/
	public void play(String name) {
		if(sounds.containsKey(name)) {
			Sound s = sounds.get(name);
			s.stop();
			s.play(0.02f);
		} else if(music.containsKey(name)) {
			Music m = music.get(name);
			m.setVolume(0.02f);
			m.setLooping(true);
			m.play();
		}
	}
	
	public void play(String name, boolean stopAll) {
		if(stopAll) {
			stopAll();
		}
		play(name);
	}
	
	public void stop(String name) {
		if(sounds.containsKey(name)) {
			Sound s = sounds.get(name);
			s.stop();
		} else if(music.containsKey(name)) {
			Music m = music.get(name);
			m.stop();
		}
	}
	
	public void stopAll() {
		for(Sound s : sounds.values()) {
			s.stop();
		}
		for(Music m : music.values()) {
			m.stop();
		}
	}
}