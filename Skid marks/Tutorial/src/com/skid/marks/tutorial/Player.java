package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements GameObject {

	private TutorialGame game;
	
	private Rectangle bounds;
	private Vector2 position;
	private Vector2 gasPosition;
	private Sprite[] sprites;
	private int spriteIndex;
	
	private final float MOVE_SPEED = 1000;
	public static float SIZE = 50;
	
	// Screen dimensions
	private float screenWidth;
	private float screenHeight;
	
	// Mouse click X
	private float mouseTochedY;
	// Mouse touch
	private boolean isMouseToched;
	
	private float rotation;
	
	private ParticleEffect gasEffect;
	
	public Player(TutorialGame game) {
		this.game = game;
	}

	@Override
	public void init() {
		SIZE = Gdx.graphics.getHeight() / 16;
		
		bounds = new Rectangle();
		bounds.setSize(SIZE, SIZE);
		
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		
//		position = new Vector2((screenWidth / 2) - (SIZE / 2), screenHeight * 3/4f);
		
		sprites = new Sprite[4];
		sprites[0] = game.Textures.getSprite("data/gfx/player_green.png");
		sprites[1] = game.Textures.getSprite("data/gfx/player_B.png");
		sprites[2] = game.Textures.getSprite("data/gfx/player_C.png");
		sprites[3] = game.Textures.getSprite("data/gfx/player_D.png");
		
		gasEffect = new ParticleEffect();
		gasEffect.load(Gdx.files.internal("data/particle/gasParticle.p"),
					   Gdx.files.internal("data/particle/"));
		
		this.reset();
	}
	
	
	@Override
	public void dispose() {
		gasEffect.dispose();
	}
	
	@Override
	public void reset() {
//		position = new Vector2((screenWidth / 2) - (SIZE / 2), screenHeight * 3/4f);
		position = new Vector2(100 * 3/4f, (screenHeight / 2) - (SIZE / 2));
		gasPosition = new Vector2(position);
		isMouseToched = false;
		mouseTochedY = (screenHeight / 2) - (SIZE / 2);
	}

	@Override
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.NUM_1)) {
			spriteIndex = 0;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_2)) {
			spriteIndex = 1;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_3)) {
			spriteIndex = 2;
		} else if(Gdx.input.isKeyPressed(Keys.NUM_4)) {
			spriteIndex = 3;
		}
		
//		if(Gdx.input.isKeyPressed(Keys.UP)) {
//			position.y -= 100 * delta;
//		} else if(Gdx.input.isKeyPressed(Keys.DOWN)) {
//			position.y += 100 * delta;
//		}
		
		// isTouched fungerar både till Andriod och Desktop
		isMouseToched = Gdx.input.isTouched();
		if(isMouseToched) {
			mouseTochedY = Gdx.input.getY() - (SIZE / 2);
		}
		
		float fm = MOVE_SPEED * delta;
//		if(position.x < (mouseTochedY - fm) || position.x > (mouseTochedY + fm))
//		{
//			if(position.x < mouseTochedY) {
//				position.x += fm;
//			} else if(position.x > mouseTochedY) {
//				position.x -= fm;
//			}
//		}
		if(position.y < (mouseTochedY - fm) || position.y > (mouseTochedY + fm))
		{
			if(position.y < mouseTochedY) {
				position.y += fm;
			} else if(position.y > mouseTochedY) {
				position.y -= fm;
			}
		}
		
		// TODO: Fixa rotationen
		float tempRot = ((mouseTochedY - position.y) / (screenHeight / 4)) * 45;
		rotation = MathUtils.clamp(tempRot, -45, 45);
		// Temp så länge
		rotation = 0;
		
//		if(position.x < 0) {
//			position.x = 0;
//		} else if(position.x + SIZE > screenWidth) {
//			position.x = screenWidth - SIZE;
//		}
		if(position.y < 0) {
			position.y = 0;
		} else if(position.y + SIZE > screenHeight) {
			position.y = screenHeight - SIZE;
		}
		
		gasEffect.setPosition(position.x, position.y + SIZE / 2);
		gasEffect.update(delta);
		
		// Uppdatera boundingbox
		bounds.setPosition(position);
		bounds.x+= SIZE/2;
		bounds.y+=SIZE/2;
	}

	@Override
	public void draw(SpriteBatch batch) {
		gasEffect.draw(batch);
		
		sprites[spriteIndex].setOrigin(sprites[spriteIndex].getWidth() / 2,
									   sprites[spriteIndex].getHeight());
		sprites[spriteIndex].setRotation(rotation);
		
		sprites[spriteIndex].setSize(SIZE, SIZE);
		sprites[spriteIndex].setPosition(position.x, position.y);
		sprites[spriteIndex].draw(batch);
//		sprites[2].setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
//		sprites[2].draw(batch);
	}
	
	@Override
	public Rectangle getBounds() {
		return this.bounds;
	}

	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
}