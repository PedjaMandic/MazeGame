package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	private final TutorialGame game;
	
	private Color[] colors = {
			new Color(255/256.0f, 42/256.0f, 127/256.0f, 1.0f), // Rosa
			new Color(89/256.0f, 255/256.0f, 127/256.0f, 1.0f), // Grön
			new Color(144/256.0f, 130/256.0f, 255/256.0f, 1.0f), // Blå
			new Color(255/256.0f, 242/256.0f, 107/256.0f, 1.0f), // Gul
	};
	
	public static Color currentColor;
	private Color targetColor;
	
	private boolean doLerp;
	private float lerpTimer;
	
	private final float LERP_TIME = 3.0f; // Hur länge vi ska lerpa (Färgövergång i en sek)
//	private final float COLOR_TIME = 3.0f; // Hur länge en färg ska visas (Varje färg visa i tre sek)
	
	private boolean pause;
	
	private Sprite background;
	private Sprite tint;
	private float positionX;
	private float screenWidth;
	private float screenHeight;
	
	private float speed;
	private final float BASE_SPEED = 50.0f;
	
	public Background(final TutorialGame game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
//		tint = game.Textures.getSprite("data/gfx/tint_white2.png");
		tint = game.Textures.getSprite("data/gfx/background_tint2.png");

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		background.setSize(screenWidth, screenHeight);
		tint.setSize(screenWidth, screenHeight);
		this.speed = BASE_SPEED;
		
//		this.currentColor = new Color(colors[colorIndex]);
		this.currentColor = new Color(255/256.0f, 42/256.0f, 127/256.0f, 1.0f);
		this.targetColor = new Color();
	}
	
	float timer;
	public void update(float time) {
		if(pause)
			return;
		
		if(doLerp) {
			float lerpValue = lerpTimer / LERP_TIME;
//			Debug.log("Lerp value: " + lerpValue);
//			Debug.log("Lerp Timer: " + lerpTimer);
			
//			this.currentColor.set(colors[colorIndex]);
//			this.targetColor.set(colors[(colorIndex + 1) % colors.length]);
			this.currentColor.lerp(targetColor, lerpValue);
			game.ingame_font.setColor(currentColor);

			lerpTimer += time;
			if(lerpTimer >= LERP_TIME) {
				lerpTimer -= LERP_TIME;
				doLerp = false;
//				if(++colorIndex >= colors.length) {
//					colorIndex = 0;
//				}
			}
		}
//		else {
//			timer += time;
//			if(timer >= COLOR_TIME) {
//				timer -= COLOR_TIME;
//				doLerp = true;
//			}
//		}
		
		positionX -= speed * time;
		if(positionX < -background.getWidth()) {
			positionX = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
		background.setColor(currentColor);
		background.setPosition(positionX + background.getWidth() - 1, 0);
		background.draw(batch);
		background.setPosition(positionX, 0);
		background.draw(batch);
		tint.draw(batch);
	}
	
	public void setColor(Color color) {
		doLerp = true;
		targetColor.set(color);
	}
	
	public void setColorRandom() {
		lerpTimer = 0;
		doLerp = true;
		int index = new Random().nextInt(colors.length);
		targetColor.set(colors[index]);
	}
	
	public void pause() {
		pause = true;
	}
	
	public void resume() {
		pause = false;
	}
	
}