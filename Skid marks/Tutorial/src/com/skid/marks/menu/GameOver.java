package com.skid.marks.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skid.marks.tutorial.Level;
import com.skid.marks.tutorial.TutorialGame;

public class GameOver {
	
	TutorialGame game;
	
	public Sprite backgroundSprite;
	public Sprite backSprite;
	public Sprite playSprite;
	
	public Rectangle backRect;
	public Rectangle playRect;
	
	private float BUTTON_SIZE;
	
//	private float BACKGROUND_WIDTH;
//	private float BACKGROUND_HEIGHT;
	
	private float sw, sh, swcenter, shcenter;
	
	public GameOver(TutorialGame game){
		this.game = game;
		
		backgroundSprite = game.Textures.getSprite("data/gfx/background_A.png");
		
		backSprite = game.Textures.getSprite("data/gfx/button_back.png");
		playSprite = game.Textures.getSprite("data/gfx/button_play.png");
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		swcenter = sw/2;
		shcenter = sh/2;
		
		BUTTON_SIZE = sh * 0.15f;
				
		SetPosition();
		
	}
	
	public void update(float time){
		
		if(backRect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()) {
				TutorialGame.state = TutorialGame.States.Menu;
					game.reset();
			}
		}
		
		if(playRect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()) {
				TutorialGame.state = TutorialGame.States.Play;
					game.reset();
			}
		}
	}
	
	public void draw(SpriteBatch batch){
		
//		backgroundSprite.draw(batch);
		playSprite.draw(batch);
		backSprite.draw(batch);
		
	}
	
	private void SetPosition(){
		
//		backgroundSprite.setRegion(0, 0, 300, 267);
//		backgroundSprite.setSize(300, 267);
//		backgroundSprite.flip(false, true);
//		backgroundSprite.setPosition(swcenter - 150, shcenter - 133);
		
		backSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		backSprite.setPosition(swcenter - BUTTON_SIZE*1.5f, shcenter - BUTTON_SIZE/2);
		backSprite.flip(false, true);
		
		backRect = new Rectangle(swcenter - BUTTON_SIZE*1.5f, shcenter - BUTTON_SIZE/2, BUTTON_SIZE, BUTTON_SIZE);
		
		playSprite.setSize(BUTTON_SIZE, BUTTON_SIZE);
		playSprite.setPosition(swcenter + BUTTON_SIZE*0.5f, shcenter - BUTTON_SIZE/2);
		playSprite.flip(false, true);
		
		playRect = new Rectangle(swcenter + BUTTON_SIZE*0.5f, shcenter - BUTTON_SIZE/2, BUTTON_SIZE, BUTTON_SIZE);
		
		
	}
}
