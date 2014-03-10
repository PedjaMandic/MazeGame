package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private TutorialGame game;
	
	private boolean pause;
	
	private Sprite background;
	private Sprite tint;
	private float positionY;
	private float screenHeight;
	
	private float speed;
	private final float BASE_SPEED = 50.0f;
	
//	private CrazyBackgroundColor crazy;
	
	public Background(TutorialGame game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
		tint = game.Textures.getSprite("data/gfx/background_tint2.png");
//		crazy = new CrazyBackgroundColor();
		
		int sx = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		background.setSize(sx, screenHeight);
		tint.setSize(sx, screenHeight);
		this.speed = BASE_SPEED;
	}
	
	public void update(float time) {
		if(pause)
			return;
		
//		crazy.update(time);
		
		positionY += speed * time;
		if(positionY >= screenHeight) {
			positionY = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
//		background.setColor(crazy.getColor());
		background.setColor(255/256.0f, 42/256.0f, 127/256.0f, 1.0f);
		background.setPosition(0, positionY - background.getHeight() + 1);
		background.draw(batch);
		background.setPosition(0, positionY);
		background.draw(batch);
		tint.draw(batch);
	}
	
	public void pause() {
		pause = true;
	}
	
	public void resume() {
		pause = false;
	}
	
}