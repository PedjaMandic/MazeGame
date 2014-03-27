package com.skid.marks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.skid.marks.tutorial.TutorialGame;

public class HowToPlay implements Screen, InputProcessor {
	
	enum Page {
		what0,
		what1,
		what2,
		how0
	}
	
	private Sprite image;
	
	private String howTo1;
	private String howTo2;
	
	private final TutorialGame game;
	
	private Page currentPage;
	
	public HowToPlay(TutorialGame game){
		
		this.game = game;
		
		Gdx.input.setInputProcessor(this);
		
		currentPage = Page.what0;
		
		//forward_button = game.Textures.getSprite("tbd");
		//back_button = game.Textures.getSprite("tbd");
		image = game.Textures.getSprite("data/gfx/ss2.png");
		image.setBounds(Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.8f);
		
		howTo1 = "This is you";
		howTo2 = "Avoid these";
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Batch.begin();
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
		
		game.Batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
