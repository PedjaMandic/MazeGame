package com.skid.marks.manager.particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.tutorial.Debug;

public class ParticleManager {
	
	private final int LIMIT = 500;
	
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
		if(particle instanceof PedjaStars) {
			particles.add(particle);
		} else {
			if(particles.size() < LIMIT) {
				particles.add(particle);
			}
		}
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
