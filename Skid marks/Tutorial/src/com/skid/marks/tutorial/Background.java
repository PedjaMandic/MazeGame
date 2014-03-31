package com.skid.marks.tutorial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	private final TutorialGame game;
	
	private Color[] colors = {
			new Color(0/255.0f, 0/255.0f, 0/255.0f, 1.0f), 			// Svart	-	Testning
			new Color(255/255.0f, 0/255.0f, 204/255.0f, 1.0f), 	// Rosa
			new Color(31/255.0f, 255/255.0f, 46/255.0f, 1.0f), 		// Grön
			new Color(42/255.0f, 31/255.0f, 255/255.0f, 1.0f), 		// Blå
			new Color(255/255.0f, 31/255.0f, 68/255.0f, 1.0f),		// Röd
			new Color(31/255.0f, 255/255.0f, 218/255.0f, 1.0f),		// Teal
			new Color(255/255.0f, 124/255.0f, 31/255.0f, 1.0f),		// Orange
			new Color(142/255.0f, 56/255.0f, 255/255.0f, 1.0f),		// Lila
	};
	
	public static Color currentColor;
	private Color targetColor;
	private int colorIndex = 0;
	
	private boolean doLerp;
	private float lerpTimer;
	
	private final float LERP_TIME = 3.0f; // Hur länge vi ska lerpa (Färgövergång i en sek)
	
	private boolean pause;
	
	private Sprite background;
	private Sprite tint;
	private float positionX;

	
	private float speed;
	private final float BASE_SPEED = 50.0f;
	
	public Background(final TutorialGame game) {
		this.game = game;
		
		background = game.Textures.getSprite("data/gfx/background.png");
		tint = game.Textures.getSprite("data/gfx/background_tint2.png");
		
		background.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		tint.setSize(TutorialGame.screen_width, TutorialGame.screen_height);
		this.speed = BASE_SPEED;
		
		Background.currentColor = new Color(colors[colorIndex]);

		this.targetColor = new Color();
	}
	
	float timer;
	public void update(float time) {
		if(pause)
			return;
		
		if(doLerp) {
			float lerpValue = lerpTimer / LERP_TIME;

			Background.currentColor.lerp(targetColor, lerpValue);

			lerpTimer += time;
			if(lerpTimer >= LERP_TIME) {
				lerpTimer -= LERP_TIME;
				doLerp = false;

			}
		}

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
		targetColor.set(colors[++colorIndex]);
	}
	
	public void pause() {
		pause = true;
	}
	
	public void resume() {
		pause = false;
	}
	
}