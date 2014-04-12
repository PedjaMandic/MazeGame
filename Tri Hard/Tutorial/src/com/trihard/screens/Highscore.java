package com.trihard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trihard.game.TriHard;

public class Highscore implements Screen, InputProcessor {
	
	private final TriHard game;
	
	private static Preferences prefs;
	
	private float centerX;
	private float centerY;
	
	public static final String SCORES_FILE = "score_file";
	public static final String KEY_FIRST = "first";
	public static final String KEY_SECOND= "second";
	public static final String KEY_THIRD= "third";
	
	private static int scoresFirst;
	private static int scoresSecond;
	private static int scoresThird;

	private Sprite highscoreList;
	private Sprite mainMenuButton;
	private Sprite backgroundSprite;
	private Sprite resetSprite;
	
	private float buttonSize;
	
	private boolean reset;
	private Sprite resetYes;
	private Sprite resetNo;
	
	private TextBounds textBounds;
	
	public Highscore(final TriHard game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		centerX = TriHard.screenWidth / 2;
		centerY = TriHard.screenHeight / 2;
		
		buttonSize = TriHard.screenHeight * 0.15f;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/main_menu.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TriHard.screenWidth, TriHard.screenHeight);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
		
		highscoreList = game.Textures.getSprite("data/gfx/menu/button.png");
		highscoreList.setSize(TriHard.screenWidth * 0.6f, TriHard.screenHeight * 0.8f);
		highscoreList.setPosition(centerX - (highscoreList.getWidth() / 2) , centerY - (highscoreList.getHeight() / 2));
		
		TextBounds tb = game.mainMenuFont.getBounds("RESET");
		resetSprite = game.Textures.getSprite("data/gfx/menu/button.png");
		resetSprite.setSize(tb.width * 1.2f, buttonSize);
		resetSprite.setPosition(TriHard.screenWidth * 0.025f, TriHard.screenHeight*0.95f - buttonSize);
		
		mainMenuButton = game.Textures.getSprite("data/gfx/menu/button_back.png");
		mainMenuButton.setSize(buttonSize, buttonSize);
		mainMenuButton.setPosition(TriHard.screenWidth * 0.975f - buttonSize, TriHard.screenHeight*0.95f - buttonSize);
		
		resetYes = game.Textures.getSprite("data/gfx/menu/button_ok.png");
		resetYes.setSize(buttonSize, buttonSize);
		resetYes.setPosition(TriHard.screenWidth * 0.4f - buttonSize / 2, TriHard.screenHeight / 2 - buttonSize / 2);
		
		resetNo = game.Textures.getSprite("data/gfx/menu/button_exit.png");
		resetNo.setSize(buttonSize, buttonSize);
		resetNo.setPosition(TriHard.screenWidth * 0.6f - buttonSize / 2, TriHard.screenHeight / 2 - buttonSize / 2);
		
		Highscore.loadPrefs();
		
		initHighscore();
	}
	
	private static int getScore(String file_key) {
		int value;
		value = prefs.getInteger(file_key, 0);

		return value;
	}
	
	public static void saveScore(int place, int score) {
		if(place == 1) {
			scoresThird = scoresSecond;
			scoresSecond = scoresFirst;
			scoresFirst = score;
			prefs.putInteger(KEY_FIRST, score);
			prefs.putInteger(KEY_SECOND, scoresSecond);
			prefs.putInteger(KEY_THIRD, scoresThird);
		} else if(place == 2) {
			scoresThird = scoresSecond;
			scoresSecond = score;
			prefs.putInteger(KEY_SECOND, score);
			prefs.putInteger(KEY_THIRD, scoresThird);
		} else if(place == 3) {
			scoresThird = score;
			prefs.putInteger(KEY_THIRD, score);
		}
		prefs.flush();
	}
	
	public static int checkScore(int score) {
		if(score > scoresFirst) {
			return 1;
		} else if(score > scoresSecond) {
			return 2;
		} else if(score > scoresThird) {
			return 3;
		}
		return 0;
	}
	
	public static void initHighscore() {
		scoresFirst = getScore(KEY_FIRST);
		scoresSecond = getScore(KEY_SECOND);
		scoresThird = getScore(KEY_THIRD);
	}
	
	public static void resetScores() {
		prefs.clear();
		prefs.flush();
	}
	
	public static void loadPrefs() {
		prefs = Gdx.app.getPreferences(SCORES_FILE);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.begin();
		backgroundSprite.draw(game.Batch);
		mainMenuButton.draw(game.Batch);
		resetSprite.draw(game.Batch);
		
		textBounds = game.highScoresFont.getBounds("1st : " + scoresFirst);
		game.highScoresFont.draw(game.Batch, "1st : " + scoresFirst,
				TriHard.screenWidth/2 - textBounds.width/2,
				highscoreList.getY() + highscoreList.getHeight()*0.4f - textBounds.height/2);
		
		textBounds = game.highScoresFont.getBounds("2nd : " + scoresSecond);
		game.highScoresFont.draw(game.Batch, "2nd : " + scoresSecond,
				TriHard.screenWidth/2 - textBounds.width/2,
				highscoreList.getY() + highscoreList.getHeight()*0.6f - textBounds.height/2);
		
		textBounds = game.highScoresFont.getBounds("3rd : " + scoresThird);
		game.highScoresFont.draw(game.Batch, "3rd : " + scoresThird,
				TriHard.screenWidth/2 - textBounds.width/2,
				highscoreList.getY() + highscoreList.getHeight()*0.8f - textBounds.height/2);
		
		textBounds = game.titleFont.getBounds("HIGHSCORES");
		game.titleFont.draw(game.Batch, "HIGHSCORES",
				TriHard.screenWidth/2 - textBounds.width/2,
				TriHard.screenHeight*0.1f);
		
		textBounds = game.mainMenuFont.getBounds("RESET");
		game.mainMenuFont.draw(game.Batch, "RESET",
				(resetSprite.getX() + resetSprite.getWidth()/2) - textBounds.width/2,
				(resetSprite.getY() + resetSprite.getHeight()/2) - textBounds.height/2);
		
		if(reset) {
			resetYes.draw(game.Batch);
			resetNo.draw(game.Batch);
			textBounds = game.infoFont.getBounds("Are you sure you wish to reset?");
			game.infoFont.draw(game.Batch, "Are you sure you wish to reset?",
					TriHard.screenWidth/2 - textBounds.width/2,
					TriHard.screenHeight/2 - textBounds.height/2 - buttonSize);
		}
		
		game.Batch.end();
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(mainMenuButton.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new MainMenu(game));
		} else if(resetSprite.getBoundingRectangle().contains(screenX, screenY)) {
			reset = true;
		}
		if(reset) {
			if(resetYes.getBoundingRectangle().contains(screenX, screenY)) {
				resetScores();
				initHighscore();
				reset = false;
			} else if(resetNo.getBoundingRectangle().contains(screenX, screenY)) {
				reset = false;
			}
		}
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
