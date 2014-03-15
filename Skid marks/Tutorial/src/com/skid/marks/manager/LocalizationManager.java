package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LocalizationManager {
	
	public static final String ENGLISH = "en-GB";
	public static final String SWEDISH = "sv-SE";
	public static final String SPANISH = "es-ES";
	
	private String filePath;
	private Map<String, Texture> resource;
	
	public LocalizationManager() {
		this.resource = new HashMap<String, Texture>();
		this.filePath = "data/locale/en-GB/";
	}
	
	public LocalizationManager(String language) {
		this.resource = new HashMap<String, Texture>();
		this.filePath = "data/locale/" + language + "/";
	}
	
	public void dispose() {
		for(Texture tex : resource.values()) {
			tex.dispose();
		}
	}
	
	// OBS! inte hela filvägen, bara namnet på filen
	// såviden den inte är i en undermapp
	public Texture getTexture(String name) {
		String path = filePath + name;
		Texture tex = null;
		if(resource.containsKey(path)) {
			tex = resource.get(path);
		} else {
			tex = new Texture(Gdx.files.internal(path));
			resource.put(path, tex);
		}
		return tex;
	}

	public Sprite getSprite(String name) {
		Texture tex = getTexture(name);
		Sprite sprite = new Sprite(tex);
		sprite.flip(false, true);
		return sprite;
	}
	
}