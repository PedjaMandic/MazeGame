package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.TextureManager;

public class Star implements BaseParticle{
	private boolean alive = true;
	private Rectangle bounds;
	private Vector2 position;
	private Vector2 direction;
	private Texture texture;
	private Sprite sprite;
	private TextureRegion currentFrame;
	
	private final float SIZE = 6;
	private final float SPEED = 10;
	
	//Is alive
	
	
	// Screen dimensions
	private float sw;
	private float sh;
	
	@Override
	public void init() {
		bounds = new Rectangle();
		bounds.setSize(SIZE);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		//Random start position;
		Random rand = new Random();
		float x = rand.nextFloat() * sw;
		float y = 0;
		
		//Random riktning på vart den åker
		direction = new Vector2(rand.nextFloat() - 0.5f, rand.nextFloat() + 0.25f);
		
		position = new Vector2(x, y);
		
		texture = TextureManager.getTexture("data/starSheet.png");
		
		int t = rand.nextInt(4);
		
		currentFrame = new TextureRegion(texture, t*5, 0, 4, 4);
		
		sprite = new Sprite(currentFrame);
		sprite.flip(false, true); // OBS!
		sprite.setSize(SIZE, SIZE);
		
	}

	@Override
	public void update(float delta) {
		position.x += direction.x * SPEED;
		position.y += direction.y * SPEED;
		
		bounds.setPosition(position);
		
		if(position.x > sw || position.x < 0 || position.y > sh || position.y < 0)
			alive = false;
		
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
	
	public Boolean isAlive(){return alive;}

}
