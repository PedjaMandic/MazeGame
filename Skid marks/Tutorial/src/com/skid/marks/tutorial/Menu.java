package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Menu {
	
	private TutorialGame game;
	
	private float sw;
	private float sh;
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	
	private Sprite menu_texture;
	private Sprite play_texture;
	private Sprite highscore_texture;
	private Sprite settings_texture;
	
	private final float BACKGROUND_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 400f;
	
	private final float BUTTON_SIZE = 80f;
	
	private Rectangle play_rect;
	private Rectangle highscore_rect;
	private Rectangle settings_rect;
	
	public Menu(TutorialGame game){
		this.game = game;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		texture = game.Textures.getTexture("data/gfx/menu_textures.png");	
		
		SetPosition();
		
		game.Sounds.play("menu", true);
	}
	
	public void update(float time){

		ResetValues();
		
		if(play_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				TutorialGame.state = TutorialGame.States.Play;
				game.Sounds.play("background", true);
			}
			play_texture.setRegion(380, 0, 80, 80);
			play_texture.flip(false, true);
			
		}
		
		if(highscore_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				TutorialGame.state = TutorialGame.States.Play;
				game.Sounds.play("background", true);
			}
			highscore_texture.setRegion(380, 80, 80, 80);
			highscore_texture.flip(false,true);
		}
		
		if(settings_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				TutorialGame.state = TutorialGame.States.Play;
				game.Sounds.play("background", true);
			}
			settings_texture.setRegion(380, 160, 80, 80);
			settings_texture.flip(false, true);
			
		}

		
	}
	
	public void draw(SpriteBatch batch){
		
		batch.begin();
		menu_texture.draw(batch);
		play_texture.draw(batch);
		highscore_texture.draw(batch);
		settings_texture.draw(batch);
		batch.end();
		
	}
	/*
	 * Används för att sätta startposition på alla knappar 
	 * TODO uträkning bör göras finare
	 */
	private void SetPosition(){
		//Bakgrunden
		menu_texture = new Sprite(texture);
		menu_texture.setRegion(0, 0, 300, 400);
		menu_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		menu_texture.flip(false, true);
		menu_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
		//PLAY BUTTON
		play_texture = new Sprite(texture);
		play_texture.setRegion(300, 0, 80, 80);
		play_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		play_texture.flip(false, true);
		play_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2));
		
		play_rect = new Rectangle(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 67 - (BUTTON_SIZE/2), BUTTON_SIZE, BUTTON_SIZE);
		
		//HISCHORE BUTTON
		highscore_texture = new Sprite(texture);
		highscore_texture.setRegion(300, 80, 80, 80);
		highscore_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		highscore_texture.flip(false, true);
		highscore_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2));
		
		highscore_rect = new Rectangle(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 200 - (BUTTON_SIZE/2), BUTTON_SIZE, BUTTON_SIZE);
		
		//SETTINGS BUTTON
		settings_texture = new Sprite(texture);
		settings_texture.setRegion(300, 160, 80, 80);
		settings_texture.setSize(BUTTON_SIZE, BUTTON_SIZE);
		settings_texture.flip(false, true);
		settings_texture.setPosition(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 333 - (BUTTON_SIZE/2));
		
		settings_rect = new Rectangle(sw_center - BUTTON_SIZE/2, sh_center - (BACKGROUND_HEIGHT/2) + 333 - (BUTTON_SIZE/2), BUTTON_SIZE, BUTTON_SIZE);
		
	}
	
	/*
	 * Återställer värdena på texture region så att texturerna inte fastnar
	 * Andvänds i början av varje update
	 */
	private void ResetValues(){
		play_texture.setRegion(300, 0, 80, 80);
		play_texture.flip(false, true);
		
		highscore_texture.setRegion(300, 80, 80, 80);
		highscore_texture.flip(false, true);
		
		settings_texture.setRegion(300, 160, 80, 80);
		settings_texture.flip(false, true);	
	}

}
