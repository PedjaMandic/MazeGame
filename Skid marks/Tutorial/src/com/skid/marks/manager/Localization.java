package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Localization {
	
	private final String ENGLISH_CODE = "en_GB";
	private final String SWEDISH_CODE = "sv_SE";
	private final String SPANISH_CODE = "es_ES";
	
	public static Localization ENGLISH = new Localization("en_GB");
	public static Localization SWEDISH = new Localization("sv_SE");
	public static Localization SPANISH = new Localization("es_ES");
	
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
	
	private String validate(String language) {
		if(language.equals(SWEDISH_CODE)) {
			language = SWEDISH_CODE;
			return SWEDISH_CODE;
		} else if(language.equals(SPANISH_CODE)) {
			language = SPANISH_CODE;
			return SPANISH_CODE;
		} else {
			language = ENGLISH_CODE;
			return ENGLISH_CODE;
		}
	}
	
	public void dispose() {
		if(resource != null) {
			for(Texture tex : resource.values()) {
				tex.dispose();
			}
			resource.clear();
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