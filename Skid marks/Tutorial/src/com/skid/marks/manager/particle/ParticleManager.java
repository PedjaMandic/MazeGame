package com.skid.marks.manager.particle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParticleManager {
	
	private ArrayList<BaseParticle> particles;
	
	public ParticleManager() {
		particles = new ArrayList<BaseParticle>();
	}
	
	public void dispose() {
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
