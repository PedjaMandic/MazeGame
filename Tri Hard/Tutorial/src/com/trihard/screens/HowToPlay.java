package com.trihard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trihard.game.TriHard;

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
	private Sprite mainMenuButton;
	
	private String infoText;
	BitmapFont.HAlignment hAlign;
	
	private final TriHard game;
	
	public HowToPlay(TriHard game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		buttonSize = TriHard.screenHeight * 0.15f;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/main_menu.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TriHard.screenWidth, TriHard.screenHeight);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
	
		image = game.Textures.getSprite("data/gfx/menu/how_to_play.png");
		image.setBounds(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.3f, Gdx.graphics.getHeight()*0.7f, Gdx.graphics.getHeight()*0.7f);
		
		infoText = "You are a flying triangle. \nTry avoiding the wall of rectangles. \nYou will follow your touch on the screen.";
		
		mainMenuButton = game.Textures.getSprite("data/gfx/menu/button_back.png");
		mainMenuButton.setSize(buttonSize, buttonSize);
		mainMenuButton.setPosition(TriHard.screenWidth * 0.975f - buttonSize, TriHard.screenHeight*0.95f - buttonSize);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.begin();
		backgroundSprite.draw(game.Batch);
		mainMenuButton.draw(game.Batch);
		game.infoFont.drawMultiLine(game.Batch, infoText, TriHard.screenWidth*0.1f, TriHard.screenHeight*0.1f, -1f, HAlignment.LEFT);
		image.draw(game.Batch);
		game.Batch.end();
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(mainMenuButton.getBoundingRectangle().contains(screenX, screenY)) {
			game.setScreen(new MainMenu(game));
		}
		return false;
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
