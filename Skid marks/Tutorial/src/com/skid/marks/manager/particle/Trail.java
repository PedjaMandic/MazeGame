package com.skid.marks.manager.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.tutorial.Background;
import com.skid.marks.tutorial.TutorialGame;

public class Trail implements BaseParticle{
	
	private float positionX;
	private float positionY;
	private float rotation;

	private int HEIGHT;
	private int WIDTH;
	
	private Color color;
	private float alpha;
	
	private Sprite sprite;
	
	private TutorialGame game;
	
	public Trail(TutorialGame game, float pos, float rot){
		this.positionY = pos;
		this.rotation = rot;
		this.game = game;
		Init();
	}
	
	private void Init(){
		
		sprite = game.Textures.getSprite("data/gfx/particle.png");
		sprite.setOrigin(0, sprite.getHeight() / 2);
//		sprite.rotate(rotation);
		
		positionX = Gdx.graphics.getWidth()/6f;
		HEIGHT = Gdx.graphics.getHeight()/16;
		HEIGHT -= 4;
		positionY += 2;
		
		WIDTH = 20;	
		alpha = 100;
		
		color = new Color(255, 0, 0, alpha);
		
		
	}

	@Override
	public void update(float delta) {
		positionX -= WIDTH;
//		HEIGHT -=4;
//		positionY += 2;
		alpha -= 7 ;
		Color t = Background.currentColor;
		color = new Color(t.r, t.g, t.b, alpha/255f);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(positionX, positionY);
		sprite.setSize(WIDTH, HEIGHT);
		sprite.setColor(color);
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return (alpha > 0);
	}

}
