package com.trihard.manager.particle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.trihard.game.Level;
import com.trihard.game.TriHard;

public class Star implements BaseParticle {

	private final TriHard game;
	
	private Rectangle bounds;
	private Vector2 position;
	private Vector2 direction;
	private Sprite sprite;
	
	private float speed = 10;
	
	public Star(final TriHard game) {
		this.game = game;
		init();
	}
	
	private void init() {
		//Random start position;
		Random rand = new Random();
		float x = TriHard.screenWidth;
		float y = rand.nextFloat() * TriHard.screenHeight;
		float scale = 3 + rand.nextInt(4);
		float alpha = rand.nextFloat();
		speed = 10 * rand.nextFloat();
		
		//Random riktning på vart den åker
		direction = new Vector2(-rand.nextFloat() - 0.2f, 0);
		
		position = new Vector2(x, y);
		
		sprite = game.Textures.getSprite("data/gfx/star_particle.png");
		sprite.setSize(scale, scale);
		sprite.setColor(
				sprite.getColor().r,
				sprite.getColor().g,
				sprite.getColor().b,
				alpha
		);
		
		if(Level.currentLevel == 8)
			sprite.setColor(0, 0, 0, alpha);
		
		bounds = new Rectangle();
		bounds.setSize(scale);
	}

	@Override
	public void update(float delta) {
		position.x += direction.x * speed;
		position.y += direction.y * speed;
		
		bounds.setPosition(position);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return !(position.x > TriHard.screenWidth ||
				position.x < 0 ||
				position.y > TriHard.screenHeight ||
				position.y < 0);
	}

}
