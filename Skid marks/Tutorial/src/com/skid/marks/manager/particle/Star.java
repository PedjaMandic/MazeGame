package com.skid.marks.manager.particle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.tutorial.TutorialGame;

public class Star implements BaseParticle{

	private TutorialGame game;
	
	private Rectangle bounds;
	private Vector2 position;
	private Vector2 direction;
	private Sprite sprite;
	
//	private final float SIZE = 10;
	private float speed = 10;
	
	// Screen dimensions
	private float sw;
	private float sh;
	
	public Star(TutorialGame game) {
		this.game = game;
		init();
	}
	
	private void init() {		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		//Random start position;
		Random rand = new Random();
		float x = sw;
		float y = rand.nextFloat() * sh;
		float scale = 3 + rand.nextInt(4);
		float alpha = rand.nextFloat();
		speed = 10 * rand.nextFloat();
		
		//Random riktning på vart den åker
		direction = new Vector2(-rand.nextFloat(), 0);
		
		position = new Vector2(x, y);
		
		sprite = game.Textures.getSprite("data/gfx/star_particle.png");
		sprite.setSize(scale, scale);
		sprite.setColor(
				sprite.getColor().r,
				sprite.getColor().g,
				sprite.getColor().b,
				alpha
		);
		
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
		return !(position.x > sw ||
				position.x < 0 ||
				position.y > sh ||
				position.y < 0);
	}

}
