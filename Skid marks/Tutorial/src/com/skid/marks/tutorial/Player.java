package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.TextureManager;

public class Player implements GameObject {

	// Can use a real BoundingBox object but fuck it
	private Rectangle bounds;
	private Vector2 position;
	private Texture texture;
	private Sprite sprite;
	
	private final float MOVE_SPEED = 1000;
	private final float SIZE = 50;
	
	// Screen dimensions
	private float sw;
	private float sh;
	
	// Mouse click X
	private float mx;
	// Mouse touch
	private boolean mt;

	@Override
	public void init() {
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		position = new Vector2((sw / 2) - (SIZE / 2), sh - SIZE);
		texture = TextureManager.getTexture("data/player.png");
		
		sprite = new Sprite(texture);
		sprite.flip(false, true); // OBS!
		sprite.setSize(SIZE, SIZE);
	}

	@Override
	public void update(float delta) {
		// isTouched fungerar både till Andriod och Desktop
		mt = Gdx.input.isTouched();
		if(mt) {
			mx = Gdx.input.getX() - (SIZE / 2);
		}
		
		float fm = MOVE_SPEED * delta;
		if(position.x < (mx - fm) || position.x > (mx + fm))
		{
			if(position.x < mx) {
				position.x += fm;
			} else if(position.x > mx) {
				position.x -= fm;
			}
		}
//		position.x = mx;
		
		
		if(position.x < 0) {
			position.x = 0;
		} else if(position.x + SIZE > sw) {
			position.x = sw - SIZE;
		}
		
		// Uppdatera boundingbox
		bounds.setPosition(position);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
	
	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}
	
}