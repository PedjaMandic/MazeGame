package com.trihard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trihard.game.Background;
import com.trihard.game.TutorialGame;

public class HowToPlay implements Screen, InputProcessor {
	
	enum Page {
		what0,
		what1,
		what2,
		how0
	}
	
	private Background background;
	
	private float buttonSize;
	
	private Sprite image;
	private Sprite backgroundSprite;
	private Sprite main_menu_button;
	
	private String howTo1;
	private String howTo2;
	
	private String info_text;
	BitmapFont.HAlignment hAlign;
	
	private final TutorialGame game;
	
	private Page currentPage;
	
	public HowToPlay(TutorialGame game){
		
		this.game = game;
		
		Gdx.input.setInputProcessor(this);
		
		currentPage = Page.what0;
		
		buttonSize = TutorialGame.screen_height * 0.15f;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/menu/menu_backround2.png");
		backgroundSprite.setRegion(0, 0, 1280, 720);
		backgroundSprite.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		backgroundSprite.setPosition(0, 0);
		backgroundSprite.flip(false, true);
		
		//forward_button = game.Textures.getSprite("tbd");
		//back_button = game.Textures.getSprite("tbd");
		image = game.Textures.getSprite("data/gfx/how_to_play.png");
		image.setBounds(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.3f, Gdx.graphics.getHeight()*0.7f, Gdx.graphics.getHeight()*0.7f);
		
		howTo1 = "This is you";
		howTo2 = "Avoid these";
		
		info_text = "You are the flying triangle. \nTry to avoid the wall of rectangles. \nYou will follow your touch on the screen.";
		
		main_menu_button = game.Textures.getSprite("data/gfx/menu/button_back.png");
		main_menu_button.setSize(buttonSize, buttonSize);
		main_menu_button.setPosition(TutorialGame.screen_width - buttonSize * 1.2f, TutorialGame.screen_height - buttonSize * 1.2f);
		
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
		/*
		switch(currentPage){
		case what0:
			currentPage = Page.what1;
			game.info_font.setColor(new Color(0,0.7f,0, 0.7f));
			break;
		case what1:
			currentPage = Page.what2;
			game.info_font.setColor(new Color(0.7f,0,0, 0.9f));
			break;
		case what2:
			currentPage = Page.how0;
			game.info_font.setColor(Color.WHITE);
			image = game.Textures.getSprite("data/gfx/how_to_play.png");
			image.setBounds(Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight()*0.8f);
			break;
		case how0:
			game.setScreen(new MainMenu(game));
			break;
		default:
			
			break;
		}
		*/
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
		/*
		switch(currentPage){
		
		case what0:
			image.draw(game.Batch);
			break;
		case what1:
			image.draw(game.Batch);
			game.info_font.draw(game.Batch, howTo1, Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight()*0.35f);
			break;
		case what2:
			image.draw(game.Batch);
			game.info_font.draw(game.Batch, howTo2, Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight()*0.15f);
			game.info_font.draw(game.Batch, howTo2, Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight()*0.8f);
			game.info_font.draw(game.Batch, howTo2, Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.8f);
			break;
		case how0:
			game.info_font.draw(game.Batch, "Control yourself by touching the screen", Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight()*0.2f);
			game.info_font.draw(game.Batch, "You will follow your own finger", Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight()*0.6f);
			image.draw(game.Batch);
			break;
		default:
			break;
		}
		*/
		
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
