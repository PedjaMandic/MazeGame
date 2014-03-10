package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements GameObject {

	private TutorialGame game;
	
	// Can use a real BoundingBox object but fuck it
	private Rectangle bounds;
	private Vector2 position;
	//private Texture texture;
	private Sprite[] sprites;
	private int spriteIndex;
	
	private final float MOVE_SPEED = 1000;
	private final float SIZE = 50;
	
	// Screen dimensions
	private float sw;
	private float sh;
	
	// Mouse click X
	private float mx;
	// Mouse touch
	private boolean mt;
	
	public Player(TutorialGame game) {
		this.game = game;
	}

	@Override
	public void init() {
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		position = new Vector2((sw / 2) - (SIZE / 2), sh - SIZE * 2);
		
		sprites = new Sprite[4];
		sprites[0] = game.Textures.getSprite("data/gfx/player_A.png");
		sprites[1] = game.Textures.getSprite("data/gfx/player_B.png");
		sprites[2] = game.Textures.getSprite("data/gfx/player_C.png");
		sprites[3] = game.Textures.getSprite("data/gfx/player_D.png");
	}
	
	@Override
	public void reset() {
		position = new Vector2((sw / 2) - (SIZE / 2), sh - SIZE * 2);
		mt = false;
		mx = (sw / 2) - (SIZE / 2);
	}

	@Override
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.NUM_1)) {
			spriteIndex = 0;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_2)) {
			spriteIndex = 1;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_3)) {
			spriteIndex = 2;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_4)) {
			spriteIndex = 3;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			position.y -= 100 * delta;
		} else if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			position.y += 100 * delta;
		}
		
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
		sprites[spriteIndex].setSize(SIZE, SIZE);
		sprites[spriteIndex].setPosition(position.x, position.y);
		sprites[spriteIndex].draw(batch);
	}
	
	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}

	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
}