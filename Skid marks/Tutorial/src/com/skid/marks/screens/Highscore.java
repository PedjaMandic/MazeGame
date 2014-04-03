package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class Highscore implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	private static Preferences prefs;
	
	private float cx;
	private float cy;
	
	public static final String SCORES_FILE = "score_file";
	public static final String KEY_FIRST = "first";
	public static final String KEY_SECOND= "second";
	public static final String KEY_THIRD= "third";
	
	private static int scores_first;
	private static int scores_second;
	private static int scores_third;

	private Sprite highscore_list;
	private Sprite main_menu_button;
	private Sprite backgroundSprite;
	private Sprite resetSprite;
	
	private float buttonSize;
	
	private boolean reset;
	private Sprite resetYes;
	private Sprite resetNo;
	
	public Highscore(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		cx = TutorialGame.screen_width/2;
		cy = TutorialGame.screen_height/2;
		
		buttonSize = TutorialGame.screen_height * 0.15f;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/menu_backround2.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
		
		highscore_list= game.Textures.getSprite("data/gfx/menu/button.png");
		highscore_list.setSize(TutorialGame.screen_width * 0.6f, TutorialGame.screen_height * 0.8f);
		highscore_list.setPosition(cx - (highscore_list.getWidth() / 2) , cy - (highscore_list.getHeight() / 2));
		
		TextBounds tb = game.main_menu_font.getBounds("RESET");
		resetSprite = game.Textures.getSprite("data/gfx/menu/button.png");
		resetSprite.setSize(tb.width * 1.2f, buttonSize);
		resetSprite.setPosition(TutorialGame.screen_width * 0.025f, TutorialGame.screen_height*0.95f - buttonSize);
		
		main_menu_button = game.Textures.getSprite("data/gfx/menu/button_back.png");
		main_menu_button.setSize(buttonSize, buttonSize);
		main_menu_button.setPosition(TutorialGame.screen_width * 0.975f - buttonSize, TutorialGame.screen_height*0.95f - buttonSize);
		
		resetYes = game.Textures.getSprite("data/gfx/menu/button_replay.png");
		resetYes.setSize(buttonSize, buttonSize);
		resetYes.setPosition(TutorialGame.screen_width * 0.4f - buttonSize / 2, TutorialGame.screen_height / 2 - buttonSize / 2);
		
		resetNo = game.Textures.getSprite("data/gfx/menu/button_back.png");
		resetNo.setSize(buttonSize, buttonSize);
		resetNo.setPosition(TutorialGame.screen_width * 0.6f - buttonSize / 2, TutorialGame.screen_height / 2 - buttonSize / 2);
		
		Highscore.LoadPrefs();
		
		InitHighscore();
	}
	
	private static int GetScore(String file_key){
		
		int value;
		value = prefs.getInteger(file_key, 0);

		return value;
	}
	
	public static void SaveScore(int place, int score){
		
		if(place == 1){
			scores_third = scores_second;
			scores_second = scores_first;
			scores_first = score;
			prefs.putInteger(KEY_FIRST, score);
			prefs.putInteger(KEY_SECOND, scores_second);
			prefs.putInteger(KEY_THIRD, scores_third);
		}
		if(place == 2){
			scores_third = scores_second;
			scores_second = score;
			prefs.putInteger(KEY_SECOND, score);
			prefs.putInteger(KEY_THIRD, scores_third);
		}
		if(place == 3){
			scores_third = score;
			prefs.putInteger(KEY_THIRD, score);
		}
		prefs.flush();
	}
	
	public static int CheckScore(int score){
		
		if(score>scores_first){
			return 1;
		}
		if(score>scores_second){
			return 2;
		}
		if(score>scores_third){
			return 3;
		}
		return 0;
	}
	
	public static void InitHighscore(){
		
		scores_first = GetScore(KEY_FIRST);
		scores_second = GetScore(KEY_SECOND);
		scores_third = GetScore(KEY_THIRD);
	}
	
	public static void ResetScores(){
		
		prefs.clear();
		prefs.flush();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.Batch.begin();
		backgroundSprite.draw(game.Batch);
		main_menu_button.draw(game.Batch);
		resetSprite.draw(game.Batch);
		BitmapFont.TextBounds t = game.highscores_font.getBounds("1st : " + scores_first);
		game.highscores_font.draw(game.Batch, "1st : " + scores_first, TutorialGame.screen_width/2 - t.width/2, highscore_list.getY() + highscore_list.getHeight()*0.4f - t.height/2);
		t = game.highscores_font.getBounds("2nd : " + scores_second);
		game.highscores_font.draw(game.Batch, "2nd : " + scores_second, TutorialGame.screen_width/2 - t.width/2, highscore_list.getY() + highscore_list.getHeight()*0.6f - t.height/2);
		t = game.highscores_font.getBounds("3rd : " + scores_third);
		game.highscores_font.draw(game.Batch, "3rd : " + scores_third, TutorialGame.screen_width/2 - t.width/2, highscore_list.getY() + highscore_list.getHeight()*0.8f - t.height/2);
		t = game.title_font.getBounds("HIGHSCORES");
		game.title_font.draw(game.Batch, "HIGHSCORES", TutorialGame.screen_width/2 - t.width/2, TutorialGame.screen_height*0.1f);
		
		t = game.main_menu_font.getBounds("RESET");
		game.main_menu_font.draw(game.Batch, "RESET", (resetSprite.getX() + resetSprite.getWidth()/2) - t.width/2, (resetSprite.getY() + resetSprite.getHeight()/2) - t.height/2);
		
		if(reset) {
			resetYes.draw(game.Batch);
			resetNo.draw(game.Batch);
		}
		
		game.Batch.end();
	}
	
	public static void LoadPrefs(){
		prefs = Gdx.app.getPreferences(SCORES_FILE);
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
		if(main_menu_button.getBoundingRectangle().contains(screenX, screenY)){
			game.setScreen(new MainMenu(game));
		}
		if(resetSprite.getBoundingRectangle().contains(screenX, screenY)) {
			reset = true;
		}
		if(reset) {
			if(resetYes.getBoundingRectangle().contains(screenX, screenY)) {
				ResetScores();
				InitHighscore();
				reset = false;
			} else if(resetNo.getBoundingRectangle().contains(screenX, screenY)) {
				reset = false;
			}
		}
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
