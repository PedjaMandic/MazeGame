package com.skid.marks.tutorial;

import com.skid.marks.manager.TextureManager;
import com.skid.marks.tutorial.TutorialGame.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Menu {
	
	private float sw;
	private float sh;
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	
	private Sprite menu_texture;
	private Sprite play_texture;
	private Sprite highscore_texture;
	
	private final float BACKGROUND_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 400f;
	
	private final float BUTTON_SIZE = 80f;
	
	private Rectangle play_rect;
	private Rectangle highscore_rect;
	
	public Menu(){
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		texture = TextureManager.getTexture("data/gfx/menu_textures.png");	
		
		SetPosition();
	}
	
	public void update(float time){
		
		if(play_rect.contains(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.isTouched()){
			TutorialGame.state = TutorialGame.States.Play;
		}
		
		if(highscore_rect.contains(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.isTouched()){
			TutorialGame.state = TutorialGame.States.Play;
		}

	}
	
	public void draw(SpriteBatch batch){
		
		batch.begin();
		menu_texture.draw(batch);
		play_texture.draw(batch);
		highscore_texture.draw(batch);
		batch.end();
		
	}
	
	private void SetPosition(){
		//Bakgrunden
		menu_texture = new Sprite(texture);
		menu_texture.setRegion(0, 0, 300, 400);
		menu_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		menu_texture.flip(false, true);
		menu_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
		//Knapparna
		play_texture = new Sprite(texture);
		play_texture.setRegion(300, 0, 80, 80);
		play_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		play_texture.flip(false, true);
		play_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2));
		
		play_rect = new Rectangle(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2), BUTTON_SIZE, BUTTON_SIZE);
		
		highscore_texture = new Sprite(texture);
		highscore_texture.setRegion(300, 80, 80, 80);
		highscore_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		highscore_texture.flip(false, true);
		highscore_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2));
		
		highscore_rect = new Rectangle(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2), BUTTON_SIZE, BUTTON_SIZE);
		
	}

}
