package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private TutorialGame game;
	
	private Sprite background;
	private float positionY;
	private float screenHeight;
	
	private final float SPEED = 50.0f;
	
	public Background(TutorialGame game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
		
		int sx = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		background.setSize(sx, screenHeight);
	}
	
	public void update(float time) {
		positionY += SPEED * time;
		if(positionY >= screenHeight) {
			positionY = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
		background.setPosition(0, positionY - background.getHeight());
		background.draw(batch);
		background.setPosition(0, positionY);
		background.draw(batch);
	}
	
}
