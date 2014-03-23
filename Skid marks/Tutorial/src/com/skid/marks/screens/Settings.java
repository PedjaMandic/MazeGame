package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class Settings implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	public static final String SETTINGS_FILE = "settings";
	public static final String LANUAGE = "language";
	public static final String SOUND = "sound";

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

	private String language;
	private boolean hasSound;
	
	public Settings(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;

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
	
	void setLanguage(String lang) {
		this.language = lang;
		if(lang.equals("en_gb")) {
			lang_en_sprite.setRegion(128, 384, 128, 128);
			lang_sv_sprite.setRegion(0, 384, 128, 128);
			lang_es_sprite.setRegion(0, 384, 128, 128);
		} else if(lang.equals("sv_se")) {
			lang_en_sprite.setRegion(0, 384, 128, 128);
			lang_sv_sprite.setRegion(128, 384, 128, 128);
			lang_es_sprite.setRegion(0, 384, 128, 128);
		} else if(lang.equals("es_es")) {
			lang_en_sprite.setRegion(0, 384, 128, 128);
			lang_sv_sprite.setRegion(0, 384, 128, 128);
			lang_es_sprite.setRegion(128, 384, 128, 128);
		}
	}
	
	void saveSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		prefs.putString(LANUAGE, language);
		prefs.putBoolean(SOUND, hasSound);
		prefs.flush();
		
		game.Localization.setLanguage(language);
	}
	
	void loadSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		language = prefs.getString(LANUAGE);
		hasSound = prefs.getBoolean(SOUND);
		
		setLanguage(language);
		game.Localization.setLanguage(language);
		
		if(hasSound) {
			music_sprite.setRegion(128, 256, 128, 128);
//			game.Sounds.setSound(true);
		} else {
			music_sprite.setRegion(256, 256, 128, 128);
//			game.Sounds.setSound(false);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Ska till touchUp och touchDown!
		if(Gdx.input.justTouched()) {
			
			int mx = Gdx.input.getX();
			int my = Gdx.input.getY();
			
			if(back_sprite.getBoundingRectangle().contains(mx, my)) {	
				saveSettings();
				game.setScreen(new MainMenu(game));
//				TutorialGame.state = States.Menu;
			} else if(music_sprite.getBoundingRectangle().contains(mx, my)){
				hasSound = !hasSound;
				if(hasSound) {
					music_sprite.setRegion(128, 256, 128, 128);
//					game.Sounds.setSound(true);
				} else {
					music_sprite.setRegion(256, 256, 128, 128);
//					game.Sounds.setSound(false);
				}
			} else if(lang_en_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("en_gb");
			} else if(lang_sv_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("sv_se");
			} else if(lang_es_sprite.getBoundingRectangle().contains(mx, my)) {
				setLanguage("es_es");
			}
		}
		
		game.Batch.begin();
		background_sprite.draw(game.Batch);
		back_sprite.draw(game.Batch);
		music_sprite.draw(game.Batch);
		lang_en_sprite.draw(game.Batch);
		lang_sv_sprite.draw(game.Batch);
		lang_es_sprite.draw(game.Batch);
		
		game.Font.draw(game.Batch, "English", 60, 220);
		game.Font.draw(game.Batch, "Swedish", 60 + (BUTTON_SIZE), 220);
		game.Font.draw(game.Batch, "Spanish", 60 + (BUTTON_SIZE * 2), 220);
		game.Batch.end();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}
	
	@Override
	public void dispose() {}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
