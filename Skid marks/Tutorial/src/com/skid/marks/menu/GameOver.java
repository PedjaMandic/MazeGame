package com.skid.marks.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.tutorial.TutorialGame;

public class GameOver implements InputProcessor {
	
	private final TutorialGame game;
		
	private Sprite backSprite;
	private Sprite playSprite;
	
	private float buttonSize;
	
	private float sw;
	private float sh;
	
	public GameOver(final TutorialGame game){
		this.game = game;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		buttonSize = sh * 0.15f;
		
		backSprite = game.Textures.getSprite("data/gfx/background_sheet.png");
		backSprite.setSize(buttonSize, buttonSize);
		backSprite.setPosition(sw - buttonSize * 1.2f, sh - buttonSize * 1.2f);
		backSprite.setRegion(256, 128, 64, 64);
		backSprite.flip(false, true);
		
		playSprite = game.Textures.getSprite("data/gfx/background_sheet.png");
		playSprite.setSize(buttonSize, buttonSize);
		playSprite.setPosition(buttonSize * 0.2f, sh - buttonSize * 1.2f);
		playSprite.setRegion(256, 192, 64, 64);
		playSprite.flip(false, true);
	}
	
	public void update(float time){
	}
	
	public void draw(SpriteBatch batch){
		playSprite.draw(batch);
		backSprite.draw(batch);	
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
		if(backSprite.getBoundingRectangle().contains(screenX, screenY)) {
			backSprite.setRegion(320, 128, 64, 64);
			backSprite.flip(false, true);
		} else if(playSprite.getBoundingRectangle().contains(screenX, screenY)) {
			playSprite.setRegion(320, 192, 64, 64);
			playSprite.flip(false, true);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		backSprite.setRegion(256, 128, 64, 64);
		backSprite.flip(false, true);
		
		playSprite.setRegion(256, 192, 64, 64);
		playSprite.flip(false, true);
		
		if(backSprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.Sounds.play("menu", true);
			TutorialGame.state = TutorialGame.States.Menu;
			game.reset();
		} else if(playSprite.getBoundingRectangle().contains(screenX, screenY)) {
			TutorialGame.state = TutorialGame.States.Play;
			game.reset();
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
