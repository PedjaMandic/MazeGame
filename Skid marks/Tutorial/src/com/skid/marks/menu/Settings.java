package com.skid.marks.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.tutorial.TutorialGame;
import com.skid.marks.tutorial.TutorialGame.States;

public class Settings {
	
	private TutorialGame game;
	
	private final String SETTINGS_FILE = "settings";
	
	private BitmapFont font;
	
	private float sw;
	private float sh;
	
	private float sw_center;
	private float sh_center;
	
	private final int BUTTON_SIZE = 128;
	
	private Sprite background_sprite;
	
	private Sprite back_sprite;
	private Sprite music_sprite;
	private Sprite lang_en_sprite;
	private Sprite lang_sv_sprite;
	private Sprite lang_es_sprite;
	
	public static boolean SETTINGS_MUSIC;
	public static String SETTINGS_LANUAGE;
	
	public Settings(TutorialGame game){
		this.game = game;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		font = new BitmapFont(true);
		
		// Background
		background_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		background_sprite.setRegion(0, 0, 512, 256);
		background_sprite.setSize(sw, sh);
		
		// Back
		back_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		back_sprite.setRegion(0, 256, 128, 128);
		back_sprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		back_sprite.setPosition(sw - BUTTON_SIZE - 50, sh - BUTTON_SIZE - 50);
		
		// Music
		music_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		music_sprite.setRegion(128, 256, 128, 128);
		music_sprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		music_sprite.setPosition(50, 50);
		
		// English
		lang_en_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		lang_en_sprite.setRegion(0, 384, 128, 128);
		lang_en_sprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		lang_en_sprite.setPosition(40, sh - BUTTON_SIZE - 40);
		
		// Swedish
		lang_sv_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		lang_sv_sprite.setRegion(0, 384, 128, 128);
		lang_sv_sprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		lang_sv_sprite.setPosition(168, sh - BUTTON_SIZE - 40);
		
		// Spanish
		lang_es_sprite = game.Textures.getSprite("data/gfx/menu/menu_sheet.png");
		lang_es_sprite.setRegion(0, 384, 128, 128);
		lang_es_sprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		lang_es_sprite.setPosition(296, sh - BUTTON_SIZE - 40);
		
		loadSettings();
	}
	
	public void dispose() {
		font.dispose();
	}
	
	public void update(float time) {
		if(Gdx.input.justTouched()) {
			
			int mx = Gdx.input.getX();
			int my = Gdx.input.getY();
			
			if(back_sprite.getBoundingRectangle().contains(mx, my)) {	
				saveSettings();
				TutorialGame.state = States.Menu;
			} else if(music_sprite.getBoundingRectangle().contains(mx, my)){
				SETTINGS_MUSIC = !SETTINGS_MUSIC;
				if(SETTINGS_MUSIC) {
					music_sprite.setRegion(128, 256, 128, 128);
//					game.Sounds.setSound(true);
				} else {
					music_sprite.setRegion(256, 256, 128, 128);
//					game.Sounds.setSound(false);
				}
			} else if(lang_en_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("en_GB");
			} else if(lang_sv_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("sv_SE");
			} else if(lang_es_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("es_ES");
			}
		}
	}
	
	public void draw(SpriteBatch batch){
		background_sprite.draw(batch);
		back_sprite.draw(batch);
		music_sprite.draw(batch);
		lang_en_sprite.draw(batch);
		lang_sv_sprite.draw(batch);
		lang_es_sprite.draw(batch);
		
		font.draw(batch, "English", 60, 220);
		font.draw(batch, "Swedish", 60 + (BUTTON_SIZE), 220);
		font.draw(batch, "Spanish", 60 + (BUTTON_SIZE * 2), 220);
	}
	
	void setLanguage(String lang) {
		SETTINGS_LANUAGE = lang;
		if(lang.equals("en_GB")) {
			lang_en_sprite.setRegion(128, 384, 128, 128);
			lang_sv_sprite.setRegion(0, 384, 128, 128);
			lang_es_sprite.setRegion(0, 384, 128, 128);
		} else if(lang.equals("sv_SE")) {
			lang_en_sprite.setRegion(0, 384, 128, 128);
			lang_sv_sprite.setRegion(128, 384, 128, 128);
			lang_es_sprite.setRegion(0, 384, 128, 128);
		} else if(lang.equals("es_ES")) {
			lang_en_sprite.setRegion(0, 384, 128, 128);
			lang_sv_sprite.setRegion(0, 384, 128, 128);
			lang_es_sprite.setRegion(128, 384, 128, 128);
		}
	}
	
	void saveSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		prefs.putString("language", SETTINGS_LANUAGE);
		prefs.putBoolean("music", SETTINGS_MUSIC);
		prefs.flush();
	}
	
	void loadSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		SETTINGS_LANUAGE = prefs.getString("language");
		SETTINGS_MUSIC = prefs.getBoolean("music");
		
		setLanguage(SETTINGS_LANUAGE);
//		game.Localization.dispose();
//		game.Localization = new Localization(SETTINGS_LANUAGE);
		
		if(SETTINGS_MUSIC) {
			music_sprite.setRegion(128, 256, 128, 128);
//			game.Sounds.setSound(true);
		} else {
			music_sprite.setRegion(256, 256, 128, 128);
//			game.Sounds.setSound(false);
		}
	}

}
