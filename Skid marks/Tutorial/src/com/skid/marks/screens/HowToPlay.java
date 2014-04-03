package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class HowToPlay implements Screen, InputProcessor {
	
	enum Page {
		what0,
		what1,
		what2,
		how0
	}
	
	private float buttonSize;
	
	private Sprite image;
	private Sprite backgroundSprite;
	private Sprite main_menu_button;
	
	private String info_text;
	BitmapFont.HAlignment hAlign;
	
	private final TutorialGame game;
	
	public HowToPlay(TutorialGame game){
		
		this.game = game;
		
		Gdx.input.setInputProcessor(this);
		
		buttonSize = TutorialGame.screen_height * 0.15f;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/menu_backround2.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
	
		image = game.Textures.getSprite("data/gfx/how_to_play.png");
		image.setBounds(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.3f, Gdx.graphics.getHeight()*0.7f, Gdx.graphics.getHeight()*0.7f);
		
		info_text = "You are a flying triangle. \nTry avoiding the wall of rectangles. \nYou will follow your touch on the screen.";
		
		main_menu_button = game.Textures.getSprite("data/gfx/menu/button_back.png");
		main_menu_button.setSize(buttonSize, buttonSize);
		main_menu_button.setPosition(TutorialGame.screen_width * 0.975f - buttonSize, TutorialGame.screen_height*0.95f - buttonSize);
		
	}

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

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.begin();
		backgroundSprite.draw(game.Batch);
		main_menu_button.draw(game.Batch);
		game.info_font.drawMultiLine(game.Batch, info_text, TutorialGame.screen_width*0.1f, TutorialGame.screen_height*0.1f, -1f, HAlignment.LEFT);
		image.draw(game.Batch);
		game.Batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
