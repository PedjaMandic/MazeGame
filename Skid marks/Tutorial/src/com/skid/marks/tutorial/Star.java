package com.skid.marks.tutorial;

import java.util.Random;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.TextureManager;

public class Star implements BaseParticle{

	private Rectangle bounds;
	private Vector2 position;
	private Vector2 direction;
	private Texture texture;
	private Sprite sprite;
	
	private final float SIZE = 10;
	private final float SPEED = 100;
	
	// Screen dimensions
	private float sw;
	private float sh;
	
	@Override
	public void init() {
		bounds = new Rectangle();
		bounds.setSize(SIZE);
		
		
		//Random start position;
		Random rand = new Random();
		float x = rand.nextFloat() * sw;
		float y = rand.nextFloat() * sh;
		
		//Random riktning på vart den åker
		direction = new Vector2(rand.nextFloat(), rand.nextFloat());
		
		position = new Vector2(x, y);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		texture = TextureManager.getTexture("data/player.png");
		
		sprite = new Sprite(texture);
		sprite.flip(false, true); // OBS!
		sprite.setSize(SIZE, SIZE);
		
	}

	@Override
	public void update(float delta) {
		position.x += direction.x * SPEED;
		position.y += direction.y * SPEED;
		
		bounds.setPosition(position);
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
