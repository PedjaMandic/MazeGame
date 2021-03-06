package com.trihard.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private Map<String, Sound> sounds = new HashMap<String, Sound>();
	private Map<String, Music> music = new HashMap<String, Music>();
	
	private boolean soundOn = true;
	
	public SoundManager() {
		// Sounds
		sounds.put("explosion", Gdx.audio.newSound(Gdx.files.internal("data/sfx/explosion.wav")));
		
		// Music
		music.put("music", Gdx.audio.newMusic(Gdx.files.internal("data/sfx/level_music.ogg")));
		music.put("menu", Gdx.audio.newMusic(Gdx.files.internal("data/sfx/menu_music.ogg")));
	}
	
	public void dispose() {
		if(sounds != null) {
			for(Sound s : sounds.values()) {
				s.dispose();
			}
			sounds.clear();
			sounds = null;
		}
		if(music != null) {
			for(Music m : music.values()) {
				m.dispose();
			}
			music.clear();
			music = null;
		}
	}
	
	/*
	 * Spela upp ett ljud eller musik
	 **/
	public void play(String name) {
		if(!soundOn)
			return;
		if(sounds.containsKey(name)) {
			Sound s = sounds.get(name);
			s.stop();
			s.play(0.5f);
		} else if(music.containsKey(name)) {
			Music m = music.get(name);
			m.setVolume(1.0f);
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
	
	public void setSound(boolean on) {
		if(on) {
			soundOn = true;
		} else {
			soundOn = false;
			stopAll();
		}
	}
	
	public boolean isPlaying(String name) {
		return music.get(name).isPlaying();
	}
}