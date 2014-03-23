package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class Highscore implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	private static Preferences prefs;
	
	private float sw;
	private float sh;
	
	public static final String SCORES_FILE = "score_file";
	public static final String KEY_FIRST = "first";
	public static final String KEY_SECOND= "second";
	public static final String KEY_THIRD= "third";
	
	private static int scores_first;
	private static int scores_second;
	private static int scores_third;
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	private Texture buttons;
	
	private Sprite background_texture;
	private Sprite local_button_texture;
	private Sprite weekly_button_texture;
	private Sprite monthly_button_texture;
	private Sprite all_time_button_texture;
	
	private BitmapFont font;
	
	private final float BACKGROUND_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 400f;
	
	private float BUTTON_WIDTH;
	private float BUTTON_HEIGHT;
	
	public Highscore(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		BUTTON_WIDTH = sw/4;
		BUTTON_HEIGHT = BUTTON_WIDTH*0.5f;
		
		//buttons = game.Textures.getTexture("data/gfx/highscore_buttons.png");
		texture = game.Textures.getTexture("data/gfx/menu_textures.png");
		
		font = new BitmapFont(true);
		
		prefs = Gdx.app.getPreferences(SCORES_FILE);
		
		SetPosition();
		
//		game.Sounds.play("menu", true);
	}
	
	private void SetPosition() {
		//BACKGROUND RUTA
		background_texture = new Sprite(texture);
		background_texture.setRegion(0, 0, 300, 400);
		background_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		background_texture.flip(false, true);
		background_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
//		//KNAPP LOCAL
//		local_button_texture = new Sprite(buttons);
//		local_button_texture.setRegion(0, 0, 256, 128);
//		local_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//		local_button_texture.flip(false, true);
//		local_button_texture.setPosition(0,0);
//		
	}
	
	private void ResetValues() {
		
		local_button_texture.setRegion(0, 0, 256, 128);
		local_button_texture.flip(false, true);
	}
	
	private static int GetScore(String file_key){
		
		int value;
		value = prefs.getInteger(file_key, 0);

		return value;
	}
	
	public static void SaveScore(int place, int score){
		
		if(place == 1){
			scores_first = score;
			prefs.putInteger(KEY_FIRST, score);
		}
		if(place == 2){
			scores_second = score;
			prefs.putInteger(KEY_SECOND, score);
		}
		if(place == 3){
			scores_third = score;
			prefs.putInteger(KEY_THIRD, score);
		}
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
		background_texture.draw(game.Batch);
		font.draw(game.Batch, "1st : " + scores_first, 50, 100);
		font.draw(game.Batch, "2nd : " + scores_second, 50, 130);
		font.draw(game.Batch, "3rd : " + scores_third, 50, 160);
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
