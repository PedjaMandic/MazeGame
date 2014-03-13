package com.skid.marks.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
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
	
	private float totalTime;
	
	// Managers
	public TextureManager Textures;
	public SoundManager Sounds;
	public ParticleManager Particles;
	
//	private Background background;
	private Level level;
	
	private Menu menu;
	private GameMode gameMode;
	private Highscore highscore;
	private Settings settings;
	
	public enum States{
		Play,
		GameOver,
		Menu,
		Settings,
		Highscore,
		Gamemode
		
	}
	
	public static States state = States.Menu;
	private float gameOverTimer;
	
	
	@Override
	public void create() {
		Textures = new TextureManager();
		Sounds = new SoundManager();
		Particles = new ParticleManager();
		
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameObjects = new ArrayList<GameObject>();
		player = new Player(this);
		player.init();

//		background = new Background(this);
		level = new Level(this);
		menu = new Menu(this);
		gameMode = new GameMode(this);
		highscore = new Highscore(this);
		settings = new Settings(this);
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		Textures.dispose();
		Sounds.dispose();
		Particles.dispose();
		
		player.dispose();
		for(GameObject go : gameObjects) {
			go.dispose();
		}
	}

	@Override
	public void render() {
		float time = Gdx.graphics.getDeltaTime();
		
		this.totalTime += time;
		
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
//		crazy.glClear(time);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		switch(state){
		case Menu:
			spawnStar(time);
//			Particles.update(time);
			menu.update(time);
//			background.update(time);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			level.draw(batch);
//			background.draw(batch);
//			Particles.draw(batch);
			Particles.render(batch, time);
			batch.end();
			menu.draw(batch);
			break;
		case Play:
			// UPDATE
//			Particles.update(time);
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
	//				spawnWall(time);
			spawnStar(time);
			level.update(player, time);
			if(level.HasCollided(player.getBounds()))
			{
				state = States.GameOver;
				Sounds.play("explosion");
				Particles.add(new PedjaStars(this, player.getPosition()));
//				background.pause();
			}
//			background.update(time);
			// DRAW
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			level.draw(batch);
//			background.draw(batch);
//			font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 20, 20);
			font.draw(batch, String.format("Score: %d", score), 20, 20);
//			for(GameObject go : gameObjects) {
//				go.draw(batch);
//			}
			player.draw(batch);
//			Particles.draw(batch);
			Particles.render(batch, time);
			
			Debug.render(batch);
			batch.end();
			break;
		case GameOver:
			gameOverTimer += time;
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			level.draw(batch);
//			background.draw(batch);
			font.draw(batch, String.format("Score: %d", score), 20, 20);
			player.draw(batch);
			Particles.render(batch, time);
			font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			font.draw(batch, "Press Space", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 20);
			if(gameOverTimer > 1.0f) {
				if(Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched()) {
					reset();
				}
			}
			
			Debug.render(batch);
			batch.end();
			break;
		case Settings:
			batch.begin();
			settings.update(time);
			settings.draw(batch);
			batch.end();
			break;
		case Highscore:
			highscore.update(time);
			batch.setProjectionMatrix(camera.combined);
			highscore.Draw(batch);
			break;
		case Gamemode:
			spawnStar(time);
			gameMode.update(time);
//			background.update(time);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			level.draw(batch);
//			background.draw(batch);
			Particles.render(batch, time);
			batch.end();
			gameMode.draw(batch);
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
		gameOverTimer = 0;
		state = States.Play;
		score = 0;
		player.reset();
		gameObjects.clear();
		level.reset();
//		background.resume();
	}
	
	private final float SPAWN_TIMER = 0.5f; // 1 sec
	private float spawnCounter = 0.0f;
	void spawnWall(float delta) {
		spawnCounter += delta;
		if(spawnCounter >= SPAWN_TIMER) {
			GameObject wall = new Wall(this);
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
			Particles.add(new Star(this));
			spawnCounterStar = 0;
		}
	}
	
	private void checkCollisions(GameObject player, List<GameObject> walls) {
		score += 10;
		for(GameObject go : walls) {
			if(player.getBounds().overlaps(go.getBounds())) {
				state = States.GameOver;
//				Sounds.play("explosion");
//				Particles.add(new PedjaStars(this, player.getPosition()));
//				background.pause();
			}
		}
	}
	private void SaveScore(){
		
		Preferences prefs = Gdx.app.getPreferences(highscore.SCORES_FILE);
		prefs.putString(highscore.LOCAL_SCORES, ""+score);
	}
}
