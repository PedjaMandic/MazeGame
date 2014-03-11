package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameMode {
	
	TutorialGame game;
	
	public Texture texture;
	
	public Sprite backgroundSprite;
	public Sprite standardModeSprite;
	public Sprite endlessModeSprite;
	
	public Rectangle standardModeRect;
	public Rectangle endlessModeRect;
	
	private final float BACKGRONUD_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 267f;
	
	private final float BUTTON_SIZE = 80f;
	
	private float sw, sh, swcenter, shcenter;
	
	public GameMode(TutorialGame game){
		this.game = game;
		
		texture = game.Textures.getTexture("data/gfx/gamemode_textures.png");
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		swcenter = sw/2;
		shcenter = sh/2;
		
		SetPosition();
		
	}
	
	public void update(float time){
		
		ResetValues();
		
		if(standardModeRect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()) {
				TutorialGame.state = TutorialGame.States.Play;
				Level.isRandom = false;
			}
			standardModeSprite.setRegion(380, 0, 80, 80);
			standardModeSprite.flip(false, true);
		}
		
		if(endlessModeRect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()) {
				TutorialGame.state = TutorialGame.States.Play;
				Level.isRandom = true;
			}
			endlessModeSprite.setRegion(380, 80, 80, 80);
			endlessModeSprite.flip(false, true);
		}
		
	}
	
	public void draw(SpriteBatch batch){
		batch.begin();
		backgroundSprite.draw(batch);
		standardModeSprite.draw(batch);
		endlessModeSprite.draw(batch);
		batch.end();
		
	}
	
	private void SetPosition(){
		
		backgroundSprite = new Sprite(texture);
		backgroundSprite.setRegion(0, 0, 300, 267);
		backgroundSprite.setSize(300, 267);
		backgroundSprite.flip(false, true);
		backgroundSprite.setPosition(swcenter - 150, shcenter - 133);
		
		standardModeSprite = new Sprite(texture);
		standardModeSprite.setRegion(300, 0, 80, 80);
		standardModeSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		standardModeSprite.flip(false, true);
		standardModeSprite.setPosition(swcenter - BUTTON_SIZE/2, shcenter - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2));
		
		standardModeRect = new Rectangle(swcenter - BUTTON_SIZE/2, shcenter - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2), 80, 80);
		
		endlessModeSprite = new Sprite(texture);
		endlessModeSprite.setRegion(300, 80, 80, 80);
		endlessModeSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		endlessModeSprite.flip(false, true);
		endlessModeSprite.setPosition(swcenter - BUTTON_SIZE/2, shcenter - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2));
		
		endlessModeRect = new Rectangle(swcenter - BUTTON_SIZE/2, shcenter - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2), 80, 80);
		
		
	}
	
	private void ResetValues(){
		standardModeSprite.setRegion(300, 0, 80, 80);
		standardModeSprite.flip(false, true);
		
		endlessModeSprite.setRegion(300, 80, 80, 80);
		endlessModeSprite.flip(false, true);
	}

}
