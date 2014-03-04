package com.skid.marks.manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureManager {
	
	private static Map<String, Texture> _textures = new HashMap<String, Texture>();
	
	public static void dispose() {
		for(Texture tex: _textures.values()) {
			tex.dispose();
		}
	}
	
	public static Texture getTexture(String path) {
		Texture tex = null;
		if(_textures.containsKey(path)) {
			tex = _textures.get(path);
		} else {
			tex = new Texture(Gdx.files.internal(path));
			_textures.put(path, tex);
		}
		return tex;
	}
	
	// Returnera en sprite som är flippad rätt
	// OBS! dispose() efter användning!
	public static Sprite getSprite(String path) {
		Texture tex = getTexture(path);
		Sprite sprite = new Sprite(tex);
		sprite.flip(false, true);
		return sprite;
	}
	
}