package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	
	private Color[] colors = {
			new Color(255/256.0f, 42/256.0f, 127/256.0f, 1.0f), // Rosa
			new Color(89/256.0f, 255/256.0f, 127/256.0f, 1.0f), // Grön
			new Color(144/256.0f, 130/256.0f, 255/256.0f, 1.0f), // Blue
			new Color(255/256.0f, 242/256.0f, 107/256.0f, 1.0f), // Gul
	};
	
	private Color currentColor;
	private int colorIndex = 0;
	
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
		
		this.currentColor = new Color(colors[colorIndex].r,
				colors[colorIndex].b,
				colors[colorIndex].g,
				colors[colorIndex].a);
	}
	
	float timer;
	public void update(float time) {
		if(pause)
			return;
		
		timer += time;
		if(timer > 1.0f) {
			timer = timer - 1.0f;
			if(++colorIndex == colors.length)
				colorIndex = 0;
		}
		currentColor = currentColor.lerp(colors[colorIndex % colors.length], timer);
//		crazy.update(time);
		
		positionY += speed * time;
		if(positionY >= screenHeight) {
			positionY = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
//		background.setColor(crazy.getColor());
//		background.setColor(255/256.0f, 42/256.0f, 127/256.0f, 1.0f);
		background.setColor(currentColor);
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