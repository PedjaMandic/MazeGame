package com.skid.marks.menu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skid.marks.tutorial.TutorialGame;

public class Highscore {
	
	private TutorialGame game;
	
	private float sw;
	private float sh;
	
	public static final String SCORES_FILE = "score_file";
	public static final String LOCAL_SCORES = "local_score_file";
	public static final String WEEKLY_SCORES = "weekly_score_file";
	public static final String MONTHLY_SCORES = "monthly_score_file";
	public static final String ALL_TIME_SCORES = "all_time_score_file";
	
	private float sw_center;
	private float sh_center;
	
	private Texture texture;
	private Texture buttons;
	
	private Sprite background_texture;
	private Sprite local_button_texture;
	private Sprite weekly_button_texture;
	private Sprite monthly_button_texture;
	private Sprite all_time_button_texture;
	
	private final float BACKGROUND_WIDTH = 300f;
	private final float BACKGROUND_HEIGHT = 400f;
	
	private float BUTTON_WIDTH;
	private float BUTTON_HEIGHT;
	
	private Rectangle local_button_rect;
	private Rectangle weekly_button_rect;
	private Rectangle monthly_button_rect;
	private Rectangle all_time_button_rect;
	
	public Highscore(TutorialGame game){
		
		this.game = game;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		sw_center = sw/2;
		sh_center = sh/2;
		
		BUTTON_WIDTH = sw/4;
		BUTTON_HEIGHT = BUTTON_WIDTH*0.5f;
		
		buttons = game.Textures.getTexture("data/gfx/highscore_buttons.png");
		texture = game.Textures.getTexture("data/gfx/menu_textures.png");
		
		SetPosition();
		
		game.Sounds.play("menu", true);
		
		//TA BORT SEN
//		Preferences prefs = Gdx.app.getPreferences("score_file");
//		prefs.flush();
	}
	
	private void SetPosition(){
		//BACKGROUND RUTA
		background_texture = new Sprite(texture);
		background_texture.setRegion(0, 0, 300, 400);
		background_texture.setSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		background_texture.flip(false, true);
		background_texture.setPosition(sw_center - BACKGROUND_WIDTH/2, sh_center - BACKGROUND_HEIGHT/2);
		
		//KNAPP LOCAL
		local_button_texture = new Sprite(buttons);
		local_button_texture.setRegion(0, 0, 256, 128);
		local_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		local_button_texture.flip(false, true);
		local_button_texture.setPosition(0,0);
		
		local_button_rect = new Rectangle(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//KNAPP WEEKLY
		weekly_button_texture = new Sprite(buttons);
		weekly_button_texture.setRegion(0, 128, 256, 128);
		weekly_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		weekly_button_texture.flip(false, true);
		weekly_button_texture.setPosition(BUTTON_WIDTH,0);
		
		weekly_button_rect = new Rectangle(BUTTON_WIDTH, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//KNAPP MONTHLY
		monthly_button_texture = new Sprite(buttons);
		monthly_button_texture.setRegion(0, 256, 256, 128);
		monthly_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		monthly_button_texture.flip(false, true);
		monthly_button_texture.setPosition(BUTTON_WIDTH*2,0);
		
		monthly_button_rect = new Rectangle(BUTTON_WIDTH*2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		//KNAPP ALL TIME
		all_time_button_texture = new Sprite(buttons);
		all_time_button_texture.setRegion(0, 384, 256, 128);
		all_time_button_texture.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		all_time_button_texture.flip(false, true);
		all_time_button_texture.setPosition(BUTTON_WIDTH*3,0);
		
		all_time_button_rect = new Rectangle(BUTTON_WIDTH*3, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	private void ResetValues(){
		
		local_button_texture.setRegion(0, 0, 256, 128);
		local_button_texture.flip(false, true);
	}
	
	public void update(float time){
		
		ResetValues();
		
		if(local_button_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				
				Preferences prefs = Gdx.app.getPreferences(SCORES_FILE);
//				prefs.putString(LOCAL_SCORES, "HEJSAN");
				ArrayList<String> test = Scores(LOCAL_SCORES);
				for (int i = 0; i < test.size(); i++) {
					System.out.println(test.get(i).toString());
				}
			}
		}
		if(weekly_button_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				
			}
		}
		if(monthly_button_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				
			}
		}
		if(all_time_button_rect.contains(Gdx.input.getX(), Gdx.input.getY())){
			if(Gdx.input.isTouched()){
				
			}
		}
			
	}
	
	public void Draw(SpriteBatch batch){
		
		batch.begin();
		background_texture.draw(batch);
		local_button_texture.draw(batch);
		weekly_button_texture.draw(batch);
		monthly_button_texture.draw(batch);
		all_time_button_texture.draw(batch);
		batch.end();
	}
	
	private ArrayList<String> Scores(String file_key){
		
		Preferences scores = Gdx.app.getPreferences(SCORES_FILE);
		ArrayList<String> values = new ArrayList<String>();
		values.add(scores.getString(file_key, "empty"));

		return values;
	}
	
	private void PrintScores(){
		
		
	}

}
