package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class Highscore implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	private static Preferences prefs;
	
	private float sw;
	private float sh;
	private float cx;
	private float cy;
	
	public static final String SCORES_FILE = "score_file";
	public static final String KEY_FIRST = "first";
	public static final String KEY_SECOND= "second";
	public static final String KEY_THIRD= "third";
	
	private static int scores_first;
	private static int scores_second;
	private static int scores_third;
	
	private Texture main_menu_button_texture;
	private Texture main_menu_button_textureHL;
	private Texture highscore_background;
	
	private Sprite background;
	private Sprite highscore_list;
	private Sprite main_menu_button;
	
	private float buttonSize;
	
	public Highscore(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		cx = sw/2;
		cy = sh/2;
		
		buttonSize = sh * 0.15f;
		
		background = game.Textures.getSprite("data/gfx/background_A.png");
		background.setSize(sw, sh);
		background.setPosition(0, 0);
		
		highscore_list= game.Textures.getSprite("data/gfx/background_sheet.png");
		highscore_list.setRegion(0, 0, 256, 512);
		highscore_list.setSize(sw * 0.6f, sh * 0.8f);
		highscore_list.setPosition(cx - (highscore_list.getWidth() / 2) , cy - (highscore_list.getHeight() / 2));
		
		main_menu_button = game.Textures.getSprite("data/gfx/background_sheet.png");
		main_menu_button.setSize(buttonSize, buttonSize);
		main_menu_button.setPosition(sw - buttonSize * 1.2f, sh - buttonSize * 1.2f);
		main_menu_button.setRegion(256, 128, 64, 64);
		main_menu_button.flip(false, true);
		
		prefs = Gdx.app.getPreferences(SCORES_FILE);
		
		SetPosition();
		
		InitHighscore();
//		game.Sounds.play("menu", true);
	}
	
	private void SetPosition() {
		//BACKGROUND RUTA
//		background_texture = new Sprite(texture);
//		background_texture.setRegion(0, 0, 300, 400);
//		background_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
//		background_texture.flip(false, true);
//		background_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
//		//KNAPP LOCAL
//		local_button_texture = new Sprite(buttons);
//		local_button_texture.setRegion(0, 0, 256, 128);
//		local_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//		local_button_texture.flip(false, true);
//		local_button_texture.setPosition(0,0);
//		
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
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.begin();
		background.draw(game.Batch);
		highscore_list.draw(game.Batch);
		main_menu_button.draw(game.Batch);
		game.highscores_font.draw(game.Batch, "1st : " + scores_first, highscore_list.getOriginX() + (highscore_list.getWidth()/4), sh/3);
		game.highscores_font.draw(game.Batch, "2nd : " + scores_second, highscore_list.getOriginX() + (highscore_list.getWidth()/4), sh/2);
		game.highscores_font.draw(game.Batch, "3rd : " + scores_third, highscore_list.getOriginX() + (highscore_list.getWidth()/4), sh/1.5f);
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
		
		if(main_menu_button.getBoundingRectangle().contains(screenX, screenY)){
			main_menu_button.setRegion(320, 128, 64, 64);
			main_menu_button.flip(false, true);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		main_menu_button.setRegion(256, 128, 64, 64);
		main_menu_button.flip(false, true);
		
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
