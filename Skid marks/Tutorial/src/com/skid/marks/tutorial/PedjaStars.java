package com.skid.marks.tutorial;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.skid.marks.manager.TextureManager;

public class PedjaStars implements BaseParticle {

	private Vector2 position;
	private Sprite sprite;
	
	private int index = 0;
	private float currentTimer = 0.0f;
	private final float ANIM_TIMER = 0.2f; // 0.2 sec
	private final int COLS = 5; // Antal bilder i sheeten
	private final int SIZE = 64;
	
	private TextureRegion region;
	
	public PedjaStars(Vector2 position) {
		this.position = new Vector2(position); // För att kopiera...
		
		sprite  = TextureManager.getSprite("data/gfx/explosion_sheet.png");
		sprite.setSize(SIZE, SIZE);
	}
	
	@Override
	public void update(float delta) {
		currentTimer += delta;
		if(currentTimer > (ANIM_TIMER / COLS)) {
			index++;
			currentTimer = 0;
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		// Detta är för sheeten (skalat upp den till 512x512)
		sprite.setRegion((1.0f / COLS) * index, 0.0f, 1.0f / COLS, 1.0f);
		
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return !(index >= COLS);
	}

}
