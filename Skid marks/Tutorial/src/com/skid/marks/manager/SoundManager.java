package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private static Map<String, Sound> _sounds = new HashMap<String, Sound>();
	private static Map<String, Music> _music = new HashMap<String, Music>();
	
	/*
	 * Static constructor
	 **/
	static {
		init();
	}
	
	/*
	 * Initar alla ljud i spelet
	 **/
	public static void init() {
		// Sounds
		_sounds.put("explosion", Gdx.audio.newSound(Gdx.files.internal("data/sfx/explosion.wav")));
		
		// Music
		_music.put("background", Gdx.audio.newMusic(Gdx.files.internal("data/sfx/background.mp3")));
	}
	
	public static void dispose() {
		for(Sound sound : _sounds.values()) {
			sound.dispose();
		}
		for(Music music : _music.values()) {
			music.dispose();
		}
	}
	
	/*
	 * Spela upp ett ljud eller musik
	 **/
	public static void play(String name) {
		if(_sounds.containsKey(name)) {
			Sound sound = _sounds.get(name);
			sound.stop();
			sound.play(0.02f);
		} else if(_music.containsKey(name)) {
			Music music = _music.get(name);
			music.setVolume(0.02f);
			music.setLooping(true);
			music.play();
		}
	}
	
}