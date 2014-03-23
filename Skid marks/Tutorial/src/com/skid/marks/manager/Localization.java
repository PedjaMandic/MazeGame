package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Localization {
	
	private final String ENGLISH = "en_gb";
	private final String SWEDISH = "sv_se";
	private final String SPANISH = "es_es";
	
	private String filePath;
	private Map<String, Texture> resource;
	
	public String language;
	
	public Localization() {
		this(Locale.getDefault().toString());
	}
	
	public Localization(String language) {
		String lang = validate(language);
		this.resource = new HashMap<String, Texture>();
		this.filePath = "data/locale/" + lang+ "/";
	}
	
	public void dispose() {
		if(resource != null) {
			for(Texture tex : resource.values()) {
				tex.dispose();
			}
			resource.clear();
			resource = null;
		}
	}
	
	private String validate(String lang) {
		if(lang.equals(SWEDISH)) {
			this.language = SWEDISH;
		} else if(lang.equals(SPANISH)) {
			this.language = SPANISH;
		} else {
			this.language = ENGLISH;
		}
		return this.language;
	}
	
	public void setLanguage(String language) {
		this.dispose();
		String lang = validate(language);
		this.resource = new HashMap<String, Texture>();
		this.filePath = "data/locale/" + lang+ "/";
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