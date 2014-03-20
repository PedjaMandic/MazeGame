package com.skid.marks.manager.particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleManager {
	
	private ArrayList<BaseParticle> particles;
	
	public ParticleManager() {
		particles = new ArrayList<BaseParticle>();
	}
	
	public void dispose() {
	}
	
	public void clear() {
		particles.clear();
	}
	
	public void add(BaseParticle particle) {
		particles.add(particle);
	}
	
	public void render(SpriteBatch batch, float time) {
		for(int i = 0; i < particles.size(); i++) {
			BaseParticle part = particles.get(i);
			part.update(time);
			if(part.isAlive() == false) {
				particles.remove(i);
			}
		}
		for(BaseParticle part : particles) {
			part.draw(batch);
		}
	}
	
}
