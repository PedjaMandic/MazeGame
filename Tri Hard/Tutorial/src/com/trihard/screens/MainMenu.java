package com.trihard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trihard.game.TriHard;

public class MainMenu implements Screen, InputProcessor {
	
	private final TriHard game;
	
	public static final String SETTINGS_FILE = "settings";
	public static final String SOUND = "sound";
	
	private float cx;
	
	private Texture texSoundOn;
	private Texture texSoundOff;
	
	private Sprite backgroundSprite;
	private Sprite playSprite;
	private Sprite highscoreSprite;
	private Sprite howToPlaySprite;
	private Sprite soundSprite;
	private Sprite quitSprite;
	
	// Storlek på de stora knapparna i menyn
	float bw = TriHard.screenWidth*0.3f;
	float bh = TriHard.screenHeight*0.23f;
	
	// Storlek på de små knapparna i menyn
	private final float BUTTON_SIZE = TriHard.screenHeight*0.15f;
	
	public static boolean hasSound = true;
	
	private TextBounds textBounds;
	
	public MainMenu(final TriHard game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		cx = TriHard.screenWidth/2;
		
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		
		hasSound = prefs.getBoolean(SOUND);
		game.Sounds.setSound(hasSound);
		
		if(hasSound) {
			if(game.Sounds.isPlaying("menu") == false) {
				game.Sounds.play("menu", true);
			}
		}
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/main_menu.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TriHard.screenWidth, TriHard.screenHeight);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);

		playSprite = game.Textures.getSprite("data/gfx/menu/button.png");
		playSprite.setSize(bw, bh);
		playSprite.setPosition(cx - (bw/2), TriHard.screenHeight*0.35f);
		playSprite.flip(false, true);
		
		highscoreSprite = game.Textures.getSprite("data/gfx/menu/button.png"); // TEMP så länge
		highscoreSprite.setSize(bw, bh);
		highscoreSprite.setPosition(cx - (bw/2), TriHard.screenHeight*0.55f);
		highscoreSprite.flip(false, true);

		howToPlaySprite = game.Textures.getSprite("data/gfx/menu/button.png"); // TEMP så länge
		howToPlaySprite.setSize(bw, bh);
		howToPlaySprite.setPosition(cx - (bw/2), TriHard.screenHeight*0.75f);
		howToPlaySprite.flip(false, true);

		texSoundOn = game.Textures.getTexture("data/gfx/menu/button_sound_on.png");
		texSoundOff = game.Textures.getTexture("data/gfx/menu/button_sound_off.png");
		
		soundSprite = new Sprite(texSoundOn);
		soundSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		soundSprite.setPosition(TriHard.screenWidth * 0.025f, TriHard.screenHeight * 0.05f);
		soundSprite.flip(false, true);
		
		quitSprite = game.Textures.getSprite("data/gfx/menu/button_exit.png");
		quitSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		quitSprite.setPosition(TriHard.screenWidth * 0.975f - BUTTON_SIZE, TriHard.screenHeight * 0.05f);
		
		reset();
		loadSettings();
	}
	
	void saveSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		prefs.putBoolean(SOUND, hasSound);
		prefs.flush();
	}
	
	void loadSettings() {
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		hasSound = prefs.getBoolean(SOUND);
		
		if(hasSound) {
			soundSprite.setTexture(texSoundOn);
		} else {
			soundSprite.setTexture(texSoundOff);
		}
	}
	
	void reset() {
		if(hasSound) {
			soundSprite.setTexture(texSoundOn);
		} else {
			soundSprite.setTexture(texSoundOff);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.Batch.setProjectionMatrix(game.Camera.combined);
		game.Batch.begin();

		backgroundSprite.draw(game.Batch);
		playSprite.draw(game.Batch);
		highscoreSprite.draw(game.Batch);
		howToPlaySprite.draw(game.Batch);
		soundSprite.draw(game.Batch);
		quitSprite.draw(game.Batch);

		textBounds = game.mainMenuFont.getBounds("PLAY");
		game.mainMenuFont.draw(game.Batch, "PLAY",
				(TriHard.screenWidth/2) - textBounds.width/2,
				(playSprite.getY() + bh/2) - textBounds.height/2);
		
		textBounds = game.mainMenuFont.getBounds("SCORE");
		game.mainMenuFont.draw(game.Batch, "SCORE",
				(TriHard.screenWidth/2) - textBounds.width/2,
				(highscoreSprite.getY() + bh/2) - textBounds.height/2);
		
		textBounds = game.mainMenuFont.getBounds("HOW TO");
		game.mainMenuFont.draw(game.Batch,
				"HOW TO", (TriHard.screenWidth/2) - textBounds.width/2,
				(howToPlaySprite.getY() + bh/2) - textBounds.height/2);
		
		textBounds = game.titleFont.getBounds("Tri Hard");
		game.titleFont.draw(game.Batch, "Tri Hard",
				TriHard.screenWidth/2 - textBounds.width/2,
				TriHard.screenHeight*0.1f);

		game.Batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(quitSprite.getBoundingRectangle().contains(screenX, screenY)) {
			Gdx.app.exit();
		} else if(soundSprite.getBoundingRectangle().contains(screenX, screenY)) {
			hasSound = !hasSound;
			game.Sounds.setSound(hasSound);
			
			if(hasSound) {
				game.Sounds.play("menu", true);
			}
			
			saveSettings();
			
		} else if(playSprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new Play(game));
		} else if(highscoreSprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new Highscore(game));
		} else if(howToPlaySprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new HowToPlay(game));
		}
		reset();
		return false;
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
