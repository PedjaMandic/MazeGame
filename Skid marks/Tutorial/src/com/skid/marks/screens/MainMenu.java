package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.screens.Game;
import com.skid.marks.tutorial.TutorialGame;

public class MainMenu implements Screen, InputProcessor {
	
	private final TutorialGame game;
	
	private float sw;
	private float sh;
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	
	private Sprite menu_texture;
	private Sprite play_texture;
	private Sprite highscore_texture;
	private Sprite settings_texture;
	
	private final float BACKGROUND_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 400f;
	
	private final float BUTTON_SIZE = 80f;
	
	public MainMenu(final TutorialGame game){
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		texture = game.Textures.getTexture("data/gfx/menu_textures.png");
		game.Sounds.play("menu", true);
		
		//Bakgrunden
		menu_texture = new Sprite(texture);
		menu_texture.setRegion(0, 0, 300, 400);
		menu_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		menu_texture.flip(false, true);
		menu_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
		//PLAY BUTTON
		play_texture = new Sprite(texture);
		play_texture.setRegion(300, 0, 80, 80);
		play_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		play_texture.flip(false, true);
		play_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2));

		//HISCHORE BUTTON
		highscore_texture = new Sprite(texture);
		highscore_texture.setRegion(300, 80, 80, 80);
		highscore_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		highscore_texture.flip(false, true);
		highscore_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2));
		
		//SETTINGS BUTTON
		settings_texture = new Sprite(texture);
		settings_texture.setRegion(300, 160, 80, 80);
		settings_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		settings_texture.flip(false, true);
		settings_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 333 - (BUTTON_SIZE/2));
	}
	
	
	void reset() {
		play_texture.setRegion(300, 0, 80, 80);
		play_texture.flip(false, true);
		
		highscore_texture.setRegion(300, 80, 80, 80);
		highscore_texture.flip(false, true);
		
		settings_texture.setRegion(300, 160, 80, 80);
		settings_texture.flip(false, true);	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.setProjectionMatrix(game.Camera.combined);
		game.Batch.begin();
		menu_texture.draw(game.Batch);
		play_texture.draw(game.Batch);
		highscore_texture.draw(game.Batch);
		settings_texture.draw(game.Batch);
		game.Batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(play_texture.getBoundingRectangle().contains(screenX, screenY)) {
			play_texture.setRegion(380, 0, 80, 80);
			play_texture.flip(false, true);
		} else if(highscore_texture.getBoundingRectangle().contains(screenX, screenY)) {
			highscore_texture.setRegion(380, 80, 80, 80);
			highscore_texture.flip(false, true);
		} else if(settings_texture.getBoundingRectangle().contains(screenX, screenY)) {
			settings_texture.setRegion(380, 160, 80, 80);
			settings_texture.flip(false, true);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		reset();
		if(play_texture.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new Game(game));
		} else if(highscore_texture.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new Highscore(game));
		} else if(settings_texture.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new Settings(game));
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
