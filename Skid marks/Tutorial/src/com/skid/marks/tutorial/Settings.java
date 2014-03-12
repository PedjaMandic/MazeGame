package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Settings {
	
	private TutorialGame game;
	
	private float sw;
	private float sh;
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	
	private float button_size;
	
	private Sprite background_sprite;
	
	private Sprite music_sprite;
	private Sprite sfx_sprite;
	private Sprite auto_retry_sprite;

	private Rectangle music_rectangle;
	private Rectangle sfx_rectangle;
	private Rectangle auto_retry_rectangle;
	
	public static boolean SETTINGS_MUSIC 		= true;
	public static boolean SETTINGS_SFX			= true;
	public static boolean SETTINGS_AUTO_RETRY	= true;
	
	private float background_width;
	private float background_height;
	
	public Settings(TutorialGame game){
		this.game = game;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		background_width = sw/2;
		background_height = background_width;
		
		button_size = background_width/3;
		
		texture = game.Textures.getTexture("data/gfx/settings_textures.png");	
		
		SetPosition();
		
		game.Sounds.play("menu", true);
	}
	
	public void update(float time){

		SetTextureRegion();
		
		if(music_rectangle.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				SETTINGS_MUSIC = !SETTINGS_MUSIC;
			}
		}
		
		if(sfx_rectangle.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				SETTINGS_SFX = !SETTINGS_SFX;
			}
		}
		
		if(auto_retry_rectangle.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				SETTINGS_AUTO_RETRY = !SETTINGS_AUTO_RETRY;
			}
		}
	}
	
	public void draw(SpriteBatch batch){

		background_sprite.draw(batch);
		music_sprite.draw(batch);
		sfx_sprite.draw(batch);
		auto_retry_sprite.draw(batch);
		
	}
	/*
	 * Används för att sätta startposition på alla knappar 
	 * TODO uträkning bör göras finare
	 */
	private void SetPosition(){
		//Bakgrunden
		background_sprite = new Sprite(texture);
		background_sprite.setRegion(0, 0, 512, 512);
		background_sprite.setSize(background_width, background_height);
		background_sprite.flip(false, true);
		background_sprite.setPosition(sw_center - background_width/2, sh_center - background_height/2);
		
		//MUSIC CHECKBOX
		music_sprite = new Sprite(texture);
		music_sprite.setRegion(512, 0, 128, 128);
		music_sprite.setSize(button_size, button_size);
		music_sprite.flip(false, true);
		music_sprite.setPosition(sw_center - background_width/2 + button_size/3, sh_center - background_height/2 + button_size/3);

		//SFX CHECKBOX
		sfx_sprite = new Sprite(texture);
		sfx_sprite.setRegion(512, 0, 128, 128);
		sfx_sprite.setSize(button_size, button_size);
		sfx_sprite.flip(false, true);
		sfx_sprite.setPosition(sw_center - background_width/2 + (button_size/3)*2 + button_size, sh_center - background_height/2 + button_size/3);
			
		//AUTO_RETRY CHECKBOX
		auto_retry_sprite = new Sprite(texture);
		auto_retry_sprite.setRegion(512, 0, 128, 128);
		auto_retry_sprite.setSize(button_size, button_size);
		auto_retry_sprite.flip(false, true);
		auto_retry_sprite.setPosition(sw_center - background_width/2 + button_size/3, sh_center - background_height/2 + (button_size/3)*2 + button_size);
			
		//RECTANGLES
		music_rectangle = new Rectangle(sw_center - background_width/2 + button_size/3, sh_center - background_height/2 + button_size/3, button_size, button_size);
		sfx_rectangle = new Rectangle(sw_center - background_width/2 + (button_size/3)*2 + button_size, sh_center - background_height/2 + button_size/3,button_size, button_size );
		auto_retry_rectangle = new Rectangle(sw_center - background_width/2 + button_size/3, sh_center - background_height/2 + (button_size/3)*2 + button_size, button_size, button_size);
	}
	
	/*
	 * Återställer värdena på texture region så att texturerna inte fastnar
	 * Andvänds i början av varje update
	 */
	private void SetTextureRegion(){
		if(SETTINGS_MUSIC){
			music_sprite.setRegion(512, 0, 128, 128);
			music_sprite.flip(false, true);
		} else {
			music_sprite.setRegion(512, 128, 128, 128);
			music_sprite.flip(false, true);
			
		}
		
		if(SETTINGS_SFX){
			sfx_sprite.setRegion(512, 0, 128, 128);
			sfx_sprite.flip(false, true);
		} else {
			sfx_sprite.setRegion(512, 128, 128, 128);
			sfx_sprite.flip(false, true);
			
		}
		
		if(SETTINGS_AUTO_RETRY){
			auto_retry_sprite.setRegion(512, 0, 128, 128);
			auto_retry_sprite.flip(false, true);
		} else {
			auto_retry_sprite.setRegion(512, 128, 128, 128);
			auto_retry_sprite.flip(false, true);
			
		}
			
	}

}
