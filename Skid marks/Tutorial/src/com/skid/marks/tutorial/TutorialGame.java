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
import com.skid.marks.manager.TextureManager;

public class TutorialGame extends Game {

	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ArrayList<GameObject> gameObjects;
	private int score = 0;
	
	@Override
	public void create() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new Player());
		
		for(GameObject go : gameObjects) {
			go.init();
		}
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		TextureManager.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		float time = Gdx.graphics.getDeltaTime();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			go.update(time);
			if(go instanceof Wall) {
				if(((Wall)go).isAlive() == false) {
					gameObjects.remove(i);
				}
			}
		}
		checkCollisions(gameObjects.get(0), gameObjects.subList(1, gameObjects.size()));
		spawnWall(time);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 20, 20);
		font.draw(batch, String.format("Score: %d", score), 20, 50);
		for(GameObject go : gameObjects) {
			go.draw(batch);
		}
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
	
	private final float SPAWN_TIMER = 0.3f; // 1 sec
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
	
	private void checkCollisions(GameObject player, List<GameObject> walls) {
		score += 1;
		for(GameObject go : walls) {
			if(player.getBounds().overlaps(go.getBounds())) {
				score -= 1000;
			}
		}
	}
}
