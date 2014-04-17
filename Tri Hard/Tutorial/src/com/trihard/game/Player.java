package com.trihard.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.trihard.manager.particle.BaseParticle;
import com.trihard.manager.particle.Trail;

/*
 * The Player!
 **/
public class Player {

	private final TriHard game;
	
	// How fast the player can move up and down
	public static float TURN_SPEED;
	
	// Size of the player in pixels
	public static float SIZE;
	
	// Used for collision
	private Rectangle bounds;
	
	// Position of the player
	private Vector2 position;
	
	// Image of the player (triangle)
	private Sprite sprite;
	
	// Where on the screen we have touched (Y-coord only)
	private float mouseTochedY;
	
	// If we have touched(clicked) the screen
	private boolean isMouseToched;
	
	// The current rotation of the player (triangle)
	private float rotation;
	
	private ArrayList<BaseParticle> trail;
	
	public Player(final TriHard game) {
		this.game = game;
		this.init();
	}

	void init() {
		SIZE = TriHard.screenHeight / 16;
		TURN_SPEED = TriHard.screenHeight;
		
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		sprite = game.Textures.getSprite("data/gfx/player_transparent.png");
		sprite.setSize(SIZE, SIZE);
		sprite.setOrigin(0, sprite.getHeight() / 2);
		
		trail = new ArrayList<BaseParticle>();
		
		this.reset();
	}
	
	public void reset() {
		position = new Vector2(TriHard.screenWidth * 1/6f, (TriHard.screenHeight / 2) - (SIZE / 2));
		isMouseToched = false;
		mouseTochedY = (TriHard.screenHeight / 2) - (SIZE / 2);
	}

	public void update(float delta) {
		isMouseToched = Gdx.input.isTouched();
		if(isMouseToched) {
			mouseTochedY = Gdx.input.getY() - (SIZE / 2);
		}
		
		// This is to prevent flickering when the player moves
		float fm = TURN_SPEED * delta;
		if(position.y < (mouseTochedY - fm) || position.y > (mouseTochedY + fm)) {
			if(position.y < mouseTochedY) {
				position.y += fm;
			} else if(position.y > mouseTochedY) {
				position.y -= fm;
			}
		}
		
		float tempRot = ((mouseTochedY - position.y) / (TriHard.screenHeight / 4)) * 45;
		rotation = MathUtils.clamp(tempRot, -45, 45);
		
		if(position.y < 0) {
			position.y = 0;
		} else if(position.y + SIZE > TriHard.screenHeight) {
			position.y = TriHard.screenHeight - SIZE;
		}
		
		UpdateTrail(delta);
		
		// Update collision box
		bounds.setPosition(position);
	}

	public void draw(SpriteBatch batch) {
		DrawTrail(batch);
		
		sprite.setRotation(rotation);
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);

	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	/*
	 * Updates the trail behind the player.
	 * Removes the trail if alive is false
	 * Adds a new trail every update
	 */
	private void UpdateTrail(float time) {
		Trail t = new Trail(game, getPosition().y);
		trail.add(t);
		
		for(int i = 0; i < trail.size(); i++){
			t = (Trail) trail.get(i);
			t.update(time);
			if(!t.isAlive())
				trail.remove(i);
		}	
	}
	/*
	 * Draws the trail behind the player
	 */
	private void DrawTrail(SpriteBatch batch) {
		for(int i = 0; i < trail.size(); i++) {
			Trail t = (Trail) trail.get(i);
			t.draw(batch);
		}
	}
	
}