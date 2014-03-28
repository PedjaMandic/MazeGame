package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.skid.marks.manager.particle.Star;
import com.skid.marks.screens.Game;
import com.skid.marks.tutorial.Background;
import com.skid.marks.tutorial.TutorialGame;

public class MainMenu implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	private Background background;
	
	public static final String SETTINGS_FILE = "settings";
	public static final String SOUND = "sound";
	
	private float cx;
	private float cy;
	
	private Sprite backgroundSprite;
	private Sprite menuSprite;
	private Sprite playSprite;
	private Sprite highscoreSprite;
	private Sprite howToPlaySprite;
	private Sprite soundSprite;
	private Sprite quitSprite;
	
	// Button size och position
	float bw = TutorialGame.screen_width*0.3f;
	float bh = TutorialGame.screen_height*0.23f;
	
	private final float BUTTON_SIZE = 80f;
	
	public static boolean hasSound = true;
	
	public MainMenu(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		background = new Background(game);
		background.setColorRandom();
		
		cx = TutorialGame.screen_width/2;
		cy = TutorialGame.screen_height/2;
		
		Preferences prefs = Gdx.app.getPreferences(SETTINGS_FILE);
		
		hasSound = prefs.getBoolean(SOUND);
		game.Sounds.setSound(hasSound);
		
		if(hasSound) {
			game.Sounds.play("menu", true);
		}
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/menu_backround2.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
		
		menuSprite = game.Textures.getSprite("data/gfx/background_sheet.png");
		menuSprite.setRegion(0, 0, 256, 512);
		menuSprite.setSize(TutorialGame.screen_width * 0.6f, TutorialGame.screen_height * 0.8f);
		menuSprite.setPosition(cx - (menuSprite.getWidth() / 2) , cy - (menuSprite.getHeight() / 2));

		playSprite = game.Textures.getSprite("data/gfx/menu/button.png"); // TEMP så länge
		playSprite.setSize(bw, bh);
		playSprite.setPosition((TutorialGame.screen_width/2) - (bw/2), TutorialGame.screen_height*0.35f);
		playSprite.flip(false, true);
		
		highscoreSprite = game.Textures.getSprite("data/gfx/menu/button.png"); // TEMP så länge
		highscoreSprite.setSize(bw, bh);
		highscoreSprite.setPosition((TutorialGame.screen_width/2) - (bw/2), TutorialGame.screen_height*0.55f);
		highscoreSprite.flip(false, true);

		howToPlaySprite = game.Textures.getSprite("data/gfx/menu/button.png"); // TEMP så länge
		howToPlaySprite.setSize(bw, bh);
		howToPlaySprite.setPosition((TutorialGame.screen_width/2) - (bw/2), TutorialGame.screen_height*0.75f);
		howToPlaySprite.flip(false, true);

		soundSprite = game.Textures.getSprite("data/gfx/background_sheet.png");
		soundSprite.setRegion(256, 320, 64, 64);
		soundSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		soundSprite.setPosition(TutorialGame.screen_width * 0.05f, TutorialGame.screen_height * 0.1f);
		soundSprite.flip(false, true);
		
		quitSprite = game.Textures.getSprite("data/gfx/background_sheet.png");
		quitSprite.setRegion(256, 64, 64, 64);
		quitSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		quitSprite.setPosition(TutorialGame.screen_width * 0.95f - BUTTON_SIZE, TutorialGame.screen_height * 0.1f);
		quitSprite.flip(false, true);
		
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
			soundSprite.setRegion(256, 320, 64, 64);
		} else {
			soundSprite.setRegion(256, 256, 64, 64);
		}
		soundSprite.flip(false, true);
	}
	
	void reset() {
		quitSprite.setRegion(256, 64, 64, 64);
		quitSprite.flip(false, true);
		
		if(hasSound) {
			soundSprite.setRegion(256, 320, 64, 64);
		} else {
			soundSprite.setRegion(256, 256, 64, 64);
		}
		soundSprite.flip(false, true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		background.update(delta);

		game.Batch.setProjectionMatrix(game.Camera.combined);
		game.Batch.begin();

		//background.draw(game.Batch);
		backgroundSprite.draw(game.Batch);
		//menuSprite.draw(game.Batch);
		playSprite.draw(game.Batch);
		highscoreSprite.draw(game.Batch);
		howToPlaySprite.draw(game.Batch);
		soundSprite.draw(game.Batch);
		quitSprite.draw(game.Batch);




		BitmapFont.TextBounds t = game.main_menu_font.getBounds("PLAY");
		game.main_menu_font.draw(game.Batch, "PLAY", (TutorialGame.screen_width/2) - t.width/2, (playSprite.getY() + bh/2) - t.height/2);
		t = game.main_menu_font.getBounds("SCORE");
		game.main_menu_font.draw(game.Batch, "SCORE", (TutorialGame.screen_width/2) - t.width/2, (highscoreSprite.getY() + bh/2) - t.height/2);
		t = game.main_menu_font.getBounds("HOW TO");
		game.main_menu_font.draw(game.Batch, "HOW TO", (TutorialGame.screen_width/2) - t.width/2, (howToPlaySprite.getY() + bh/2) - t.height/2);


		game.Batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(quitSprite.getBoundingRectangle().contains(screenX, screenY)) {
			quitSprite.setRegion(320, 64, 64, 64);
			quitSprite.flip(false, true);
		} else if(soundSprite.getBoundingRectangle().contains(screenX, screenY)) {
			if(hasSound) {
				soundSprite.setRegion(320, 320, 64, 64);
			} else {
				soundSprite.setRegion(320, 256, 64, 64);
			}
			soundSprite.flip(false, true);
		} else if(playSprite.getBoundingRectangle().contains(screenX, screenY)) {
			// TODO
		} else if(highscoreSprite.getBoundingRectangle().contains(screenX, screenY)) {
			// TODO
		} else if(howToPlaySprite.getBoundingRectangle().contains(screenX, screenY)) {
			// TODO
		}
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
			game.setScreen(new Game(game));
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
