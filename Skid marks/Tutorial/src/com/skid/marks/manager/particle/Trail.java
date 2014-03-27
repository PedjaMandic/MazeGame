package com.skid.marks.manager.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.tutorial.Background;
import com.skid.marks.tutorial.Level;
import com.skid.marks.tutorial.TutorialGame;

public class Trail implements BaseParticle{
	
	private float positionX;
	private float positionY;

	private int HEIGHT;
	private int WIDTH;
	
	private Color color;
	private float alpha;
	private final float alpha_reduction = 8;
	
	private Sprite sprite;
	
	private TutorialGame game;
	
	public Trail(TutorialGame game, float pos){
		this.positionY = pos;
		this.game = game;
		Init();
	}
	
	/*
	 * Initializes the main components of the trail.
	 * Loads in the correct texture to the sprite.
	 */
	private void Init(){		
		//Load texture
		sprite = game.Textures.getSprite("data/gfx/particle.png");
		
		//Sets x position, height and width.
		positionX = Gdx.graphics.getWidth()/6f;
		WIDTH = Gdx.graphics.getWidth()/64;	
		HEIGHT = Gdx.graphics.getHeight()/16;
		
		//Reduces height by 4 and center it to the player
		HEIGHT -= 4;
		positionY += 2;
		
		//Sets starting alpha value
		alpha = 100;		
			
	}

	@Override
	public void update(float delta) {
		//Moves the trail backwards by it's width amount
		positionX -= WIDTH+2;
		
		//Reduces the alpha value
		alpha -= alpha_reduction ;
		
		//Gets the current color off the background and sets it to the color of the trail
		Color t = Background.currentColor;
		
		if(Level.currentLevel == 8)
			t = Color.GRAY;
		if(Level.currentLevel == 9)
			t = Color.WHITE;
		color = new Color(t.r, t.g, t.b, alpha/255f);
	}

	@Override
	public void draw(SpriteBatch batch) {
		//Sets the position, size and color of the trail
		sprite.setPosition(positionX, positionY);
		sprite.setSize(WIDTH, HEIGHT);
		sprite.setColor(color);
		
		//Draws the trail
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return (alpha > 0);
	}

}
