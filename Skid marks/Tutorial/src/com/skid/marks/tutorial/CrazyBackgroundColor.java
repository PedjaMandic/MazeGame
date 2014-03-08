package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class CrazyBackgroundColor {
	
	// Färger
	private float r;
	private float b;
	private float g;
	
	private float mr;
	private float mg;
	private float mb;
	
	private final float MAX = 1.0f;
	
	private Random rand = new Random();
	
	private void randomThatCrazyColor() {
		mr = rand.nextFloat() * MAX;
		mg = rand.nextFloat() * MAX;
		mb = rand.nextFloat() * MAX;
	}
	
	private boolean finished() {
		return r > mr && g > mg && b > mb;
	}
	
	private void increment(float delta) {
		if (r < mr) {
			r += delta;
		} else {
			r -= delta;
		}
		if (g < mg) {
			g += delta;
		} else {
			g -= delta;
		}
		if (b < mb) {
			b += delta;
		} else {
			b -= delta;
		}
	}
	
	public void glClear(float delta) {
		if(finished()) {
			randomThatCrazyColor();
		} else {
			increment(delta);
		}
		Gdx.gl.glClearColor(r, g, b, 1.0f);
	}
	
}
