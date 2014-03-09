package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private TutorialGame game;
	
	private boolean pause;
	
	private Sprite background;
	private float positionY;
	private float screenHeight;
	
	private final float SPEED = 50.0f;
	
	private CrazyBackgroundColor crazy;
	
	public Background(TutorialGame game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
		crazy = new CrazyBackgroundColor();
		
		int sx = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		background.setSize(sx, screenHeight);
	}
	
	public void update(float time) {
		if(pause)
			return;
		
		crazy.update(time);
		
		positionY += SPEED * time;
		if(positionY >= screenHeight) {
			positionY = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
		background.setColor(crazy.getColor());
		background.setPosition(0, positionY - background.getHeight() + 1);
		background.draw(batch);
		background.setPosition(0, positionY);
		background.draw(batch);
	}
	
	public void pause() {
		pause = true;
	}
	
	public void resume() {
		pause = false;
	}
	
}