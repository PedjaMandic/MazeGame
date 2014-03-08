package com.skid.marks.manager.particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface BaseParticle {
	
	void update(float delta);
	
	void draw(SpriteBatch batch);
	
	boolean isAlive();

}
