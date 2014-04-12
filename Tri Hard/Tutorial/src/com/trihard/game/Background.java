package com.trihard.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Used in Level.java for changing the background color
 * when the Player gets to a new level.
 **/
public class Background {
	
	private final TriHard game;
	
	private Color[] colors = {
			new Color(0/255.0f, 0/255.0f, 0/255.0f, 1.0f),		// Black
			new Color(255/255.0f, 0/255.0f, 204/255.0f, 1.0f),	// Pink
			new Color(31/255.0f, 255/255.0f, 46/255.0f, 1.0f),	// Green
			new Color(42/255.0f, 31/255.0f, 255/255.0f, 1.0f),	// Blue
			new Color(255/255.0f, 31/255.0f, 68/255.0f, 1.0f),	// Red
			new Color(31/255.0f, 255/255.0f, 218/255.0f, 1.0f),	// Teal
			new Color(255/255.0f, 124/255.0f, 31/255.0f, 1.0f),	// Orange
			new Color(142/255.0f, 56/255.0f, 255/255.0f, 1.0f),	// Purple
	};
	
	public static Color currentColor;
	private Color targetColor;
	private int colorIndex = 0;
	
	private boolean doLerp;
	private float lerpTimer;
	
	// Interval in i secs between color change.
	private final float LERP_TIME = 3.0f;
	
	// The background being rendered.
	private Sprite background;
	// Used on top of background for nice effect.
	private Sprite tint;
	// Current position of the background.
	private float positionX;
	
	// How fast the background should move.
	// Gives the illusion of moving in space.
	private final float SPEED = 50.0f;
	
	public Background(final TriHard game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
		tint = game.Textures.getSprite("data/gfx/background_tint.png");
		
		background.setSize(TriHard.screenWidth, TriHard.screenHeight);
		tint.setSize(TriHard.screenWidth, TriHard.screenHeight);
		
		Background.currentColor = new Color(colors[colorIndex]);

		this.targetColor = new Color();
	}
	
	public void update(float time) {
		if(doLerp) {
			float lerpValue = lerpTimer / LERP_TIME;

			Background.currentColor.lerp(targetColor, lerpValue);

			lerpTimer += time;
			if(lerpTimer >= LERP_TIME) {
				lerpTimer -= LERP_TIME;
				doLerp = false;

			}
		}

		positionX -= SPEED * time;
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
	
	/*
	 * Sets the background color to specified color.
	 **/
	public void setColor(Color color) {
		doLerp = true;
		targetColor.set(color);
	}
	
	/*
	 * Sets the background color to the next one in the colors array.
	 **/
	public void setColorNext() {
		lerpTimer = 0;
		doLerp = true;
		targetColor.set(colors[++colorIndex]);
	}
	
}