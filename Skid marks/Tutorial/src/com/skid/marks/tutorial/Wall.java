package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.TextureManager;

public class Wall implements GameObject {

	private boolean alive = true;
	private Rectangle bounds;
	private Vector2 position;
	//private Texture texture;
	private Sprite sprite;
	
	private final float MOVE_SPEED = 600;
	
	private static Random _rand = new Random();
	
	private float sh;
	
	@Override
	public void init() {
		float sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		float sizeW = 50 + _rand.nextFloat() * (sw - 100);
		float sizeH = 50;
		int leftOrRight = _rand.nextInt(2);
		
		bounds = new Rectangle();
		bounds.setSize(sizeW, sizeH);
		
//		texture = TextureManager.getTexture("data/wall.png");
//		sprite = new Sprite(texture);
//		sprite.flip(false, true);
		sprite = TextureManager.getSprite("data/wall.png");
		sprite.setSize(sizeW, sizeH);
		
		if(leftOrRight == 0) {
			position = new Vector2(0, 0);
		} else {
			position = new Vector2(sw - sizeW, 0);
		}
	}

	@Override
	public void update(float delta) {
		position.y += MOVE_SPEED * delta;
		bounds.setPosition(position);
		
		if(position.y > sh) {
			alive = false;
		}
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
	
	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	public boolean isAlive() {
		return alive;
	}

}
