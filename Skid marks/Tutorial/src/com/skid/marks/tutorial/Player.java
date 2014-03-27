package com.skid.marks.tutorial;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.particle.BaseParticle;
import com.skid.marks.manager.particle.Trail;

public class Player {

	private TutorialGame game;
	
	private Rectangle bounds;
	private Vector2 position;
	private Sprite sprite;
	
	public static float MOVE_SPEED;
	public static float SIZE;
	
	
	// Mouse click X
	private float mouseTochedY;
	// Mouse touch
	private boolean isMouseToched;
	
	private float rotation;
	
	private ArrayList<BaseParticle> trail;
	
	public Player(TutorialGame game) {
		this.game = game;
	}

	public void init() {
		SIZE = TutorialGame.screen_height / 16;
		MOVE_SPEED = TutorialGame.screen_height;
		
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		sprite = game.Textures.getSprite("data/gfx/player_transparent.png");
		sprite.setSize(SIZE, SIZE);
		sprite.setOrigin(0, sprite.getHeight() / 2);
		
		trail = new ArrayList<BaseParticle>();
		
		this.reset();
	}
	
	public void reset() {
		position = new Vector2(TutorialGame.screen_width * 1/6f, (TutorialGame.screen_height / 2) - (SIZE / 2));
		isMouseToched = false;
		mouseTochedY = (TutorialGame.screen_height / 2) - (SIZE / 2);
	}

	public void update(float delta) {				
		// isTouched fungerar både till Andriod och Desktop
		isMouseToched = Gdx.input.isTouched();
		if(isMouseToched) {
			mouseTochedY = Gdx.input.getY() - (SIZE / 2);
		}
		
		float fm = MOVE_SPEED * delta;
		if(position.y < (mouseTochedY - fm) || position.y > (mouseTochedY + fm))
		{
			if(position.y < mouseTochedY) {
				position.y += fm;
			} else if(position.y > mouseTochedY) {
				position.y -= fm;
			}
		}
		
		float tempRot = ((mouseTochedY - position.y) / (TutorialGame.screen_height / 4)) * 45;
		rotation = MathUtils.clamp(tempRot, -45, 45);
		
		if(position.y < 0) {
			position.y = 0;
		} else if(position.y + SIZE > TutorialGame.screen_height) {
			position.y = TutorialGame.screen_height - SIZE;
		}
		
		UpdateTrail(delta);
		
		// Uppdatera boundingbox
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
	private void UpdateTrail(float time){
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
	private void DrawTrail(SpriteBatch batch){
		for(int i = 0; i < trail.size(); i++){
			Trail t = (Trail) trail.get(i);
			t.draw(batch);
		}
	}
	
}