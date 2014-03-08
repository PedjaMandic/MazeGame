package com.skid.marks.tutorial;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface GameObject {

	void init();
	
	void reset();
	
	void update(float delta);
	
	void draw(SpriteBatch batch);
	
	Rectangle getBounds();
	
	Vector2 getPosition();
	
}