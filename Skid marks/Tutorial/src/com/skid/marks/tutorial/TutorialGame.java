package com.skid.marks.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.manager.SoundManager;
import com.skid.marks.manager.TextureManager;
import com.skid.marks.manager.particle.ParticleManager;
import com.skid.marks.manager.particle.PedjaStars;
import com.skid.marks.manager.particle.Star;

public class TutorialGame extends Game {

	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ArrayList<GameObject> gameObjects;
	private Player player;
	private int score = 0;
	
	private CrazyBackgroundColor crazy;
	private ParticleManager particleManager;
	private Level level;
	
	private boolean gameOver;
	
	private Menu menu;
	
	public enum States{
		Play,
		Menu,
		Settings,
		Highscore
		
	}
	
	public static States state = States.Menu;
	
	
	@Override
	public void create() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameObjects = new ArrayList<GameObject>();
		player = new Player();
		player.init();
		
		crazy = new CrazyBackgroundColor();
		particleManager = new ParticleManager();
		level = new Level();
		menu = new Menu();
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		TextureManager.dispose();
		SoundManager.dispose();
	}

	@Override
	public void render() {
		float time = Gdx.graphics.getDeltaTime();
		
//		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		crazy.glClear(time);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		switch(state){
		case Menu:
			spawnStar(time);
			particleManager.update(time);
			menu.update(time);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			particleManager.draw(batch);
			batch.end();
			menu.draw(batch);
			break;
		case Play:
			// UPDATE
			particleManager.update(time);
			if(!gameOver) {
				for (int i = 0; i < gameObjects.size(); i++) {
					GameObject go = gameObjects.get(i);
					go.update(time);
					if(go instanceof Wall) {
						if(((Wall)go).isAlive() == false) {
							gameObjects.remove(i);
						}
					}
				}
				player.update(time);
				checkCollisions(player, gameObjects);
				spawnWall(time);
				spawnStar(time);
				level.update(time);
			}
			// DRAW
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
//			font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 20, 20);
			font.draw(batch, String.format("Score: %d", score), 20, 20);
			for(GameObject go : gameObjects) {
				go.draw(batch);
			}
			player.draw(batch);
			particleManager.draw(batch);
			level.draw(batch);
			
			if(gameOver) {
				font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
				font.draw(batch, "Press Space", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 20);
				if(Gdx.input.isKeyPressed(Keys.SPACE)) {
					reset();
				}
			}
			
			Logger.render(batch);
			batch.end();
			break;
		case Settings:
			
			break;
		case Highscore:
			
			break;
		default:
			
			break;
		
		}
	
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	void reset() {
		score = 0;
		gameOver = false;
		player.reset();
		gameObjects.clear();
	}
	
	private final float SPAWN_TIMER = 0.5f; // 1 sec
	private float spawnCounter = 0.0f;
	void spawnWall(float delta) {
		spawnCounter += delta;
		if(spawnCounter >= SPAWN_TIMER) {
			GameObject wall = new Wall();
			wall.init();
			gameObjects.add(wall);
			spawnCounter = 0;
		}
	}
	
	private final float SPAWN_TIMER_STAR = 0.03f; // 1 sec
	private float spawnCounterStar = 0.0f;
	void spawnStar(float delta) {
		spawnCounterStar += delta;
		if(spawnCounterStar >= SPAWN_TIMER_STAR) {
			particleManager.add(new Star());
			spawnCounterStar = 0;
		}
	}
	
	private void checkCollisions(GameObject player, List<GameObject> walls) {
		score += 10;
		for(GameObject go : walls) {
			if(player.getBounds().overlaps(go.getBounds())) {
				SoundManager.play("explosion");
				particleManager.add(new PedjaStars(player.getPosition()));
				gameOver = true;
			}
		}
	}
}
