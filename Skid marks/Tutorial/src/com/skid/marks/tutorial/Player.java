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
	public static float SIZE = 50;
	
	// Screen dimensions
	private float screenWidth;
	private float screenHeight;
	
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
		SIZE = Gdx.graphics.getHeight() / 16;
		MOVE_SPEED = Gdx.graphics.getHeight();
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
		sprite = game.Textures.getSprite("data/gfx/player_transparent.png");
		sprite.setSize(SIZE, SIZE);
		sprite.setOrigin(0, sprite.getHeight() / 2);
		
		trail = new ArrayList<BaseParticle>();
		
		this.reset();
	}
	
	public void reset() {
		position = new Vector2(screenWidth * 1/6f, (screenHeight / 2) - (SIZE / 2));
		isMouseToched = false;
		mouseTochedY = (screenHeight / 2) - (SIZE / 2);
	}

	public void update(float delta) {				
		// isTouched fungerar b�de till Andriod och Desktop
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
		
		// TODO: Fixa rotationen
		float tempRot = ((mouseTochedY - position.y) / (screenHeight / 4)) * 45;
		rotation = MathUtils.clamp(tempRot, -45, 45);
		// Temp s� l�nge
//		rotation = 0;
		
		if(position.y < 0) {
			position.y = 0;
		} else if(position.y + SIZE > screenHeight) {
			position.y = screenHeight - SIZE;
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
	
	private void UpdateTrail(float time){
		Trail t = new Trail(game, getPosition().y, rotation);
		trail.add(t);
		
		for(int i = 0; i < trail.size(); i++){
			t = (Trail) trail.get(i);
			t.update(time);
			if(!t.isAlive())
				trail.remove(i);
		}	
	}
	
	private void DrawTrail(SpriteBatch batch){
		
		for(int i = 0; i < trail.size(); i++){
			Trail t = (Trail) trail.get(i);
			t.draw(batch);
		}
	}
	
}