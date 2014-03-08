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
import com.skid.marks.manager.ParticleManager;
import com.skid.marks.manager.SoundManager;
import com.skid.marks.manager.TextureManager;

public class TutorialGame extends Game {

	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<BaseParticle> particles;
	private Player player;
	private int score = 0;
	
	private CrazyBackgroundColor crazy;
	private ParticleManager particleManager;
	
	@Override
	public void create() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		particles = new ArrayList<BaseParticle>();
		
		gameObjects = new ArrayList<GameObject>();
		player = new Player();
		player.init();
		
		crazy = new CrazyBackgroundColor();
		particleManager = new ParticleManager();
		
		SoundManager.play("background");
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
		
		particleManager.update(time);
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
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 20, 20);
		font.draw(batch, String.format("Score: %d", score), 20, 50);
		for(GameObject go : gameObjects) {
			go.draw(batch);
		}
		player.draw(batch);
		particleManager.draw(batch);
		
		Logger.render(batch);
		batch.end();
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
				score -= 1000;
			}
		}
	}
}
