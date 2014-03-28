package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.Background;
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
	
	private float buttonSize;
	
	private Background background;
	
	public Highscore(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		background = new Background(game);
		background.setColorRandom();
		
		cx = TutorialGame.screen_width/2;
		cy = TutorialGame.screen_height/2;
		
		buttonSize = TutorialGame.screen_height * 0.15f;
		
		highscore_list= game.Textures.getSprite("data/gfx/menu/button.png");
		highscore_list.setSize(TutorialGame.screen_width * 0.6f, TutorialGame.screen_height * 0.8f);
		highscore_list.setPosition(cx - (highscore_list.getWidth() / 2) , cy - (highscore_list.getHeight() / 2));
		
		main_menu_button = game.Textures.getSprite("data/gfx/menu/button_back.png");
		main_menu_button.setSize(buttonSize, buttonSize);
		main_menu_button.setPosition(TutorialGame.screen_width - buttonSize * 1.2f, TutorialGame.screen_height - buttonSize * 1.2f);
		
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
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.update(delta);
		game.Batch.begin();
		background.draw(game.Batch);
		highscore_list.draw(game.Batch);
		main_menu_button.draw(game.Batch);
		game.highscores_font.draw(game.Batch, "1st : " + scores_first, highscore_list.getOriginX() + (highscore_list.getWidth()/4), TutorialGame.screen_height/3);
		game.highscores_font.draw(game.Batch, "2nd : " + scores_second, highscore_list.getOriginX() + (highscore_list.getWidth()/4), TutorialGame.screen_height/2);
		game.highscores_font.draw(game.Batch, "3rd : " + scores_third, highscore_list.getOriginX() + (highscore_list.getWidth()/4), TutorialGame.screen_height/1.5f);
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
