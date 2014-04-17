package com.trihard.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.trihard.manager.SoundManager;
import com.trihard.manager.TextureManager;
import com.trihard.manager.particle.ParticleManager;
import com.trihard.screens.MainMenu;

public class TriHard extends Game {

	public static float screenWidth;
	public static float screenHeight;
	
	public BitmapFont mainMenuFont;
	public BitmapFont highScoresFont;
	public BitmapFont ingameFont;
	public BitmapFont infoFont;
	public BitmapFont titleFont;

	public SpriteBatch Batch;
	public OrthographicCamera Camera;

	// Managers
	public TextureManager Textures;
	public SoundManager Sounds;
	public ParticleManager Particles;

	@Override
	public void create() {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		Textures = new TextureManager();
		Sounds = new SoundManager();
		Particles = new ParticleManager();

		Batch = new SpriteBatch();
		Camera = new OrthographicCamera();
		Camera.setToOrtho(true, screenWidth, screenHeight);

		// Skapar alla fonts
		// -----------------------------------------------------------------------
		FileHandle fontFile = Gdx.app.getFiles().internal(
				"data/gfx/fonts/Cooper Black.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

		mainMenuFont = generator.generateFont((int)(screenWidth / 18), "PLAYHIGSCOREWT", true);
		mainMenuFont.setColor(new Color(1f,1f,1f,1));

		highScoresFont = generator.generateFont((int) (screenWidth / 20),
				"1234567890stndr:", true);
		highScoresFont.setColor(new Color(1, 1, 1, 1));

		ingameFont = generator.generateFont((int) (screenWidth / 36),
				"1234567890Scor:Llev", true);
		ingameFont.setColor(new Color(1, 1, 1, 1f));

		infoFont = generator.generateFont((int) screenWidth / 36,
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM?", true);
		infoFont.setColor(new Color(1, 1, 1, 1f));

		titleFont = generator.generateFont((int)screenWidth / 12, 
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		titleFont.setColor(new Color(1,1,1,1));
		generator.dispose();
		// -----------------------------------------------------------------------
		
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void dispose() {
		Batch.dispose();
		Textures.dispose();
		Sounds.dispose();
		Particles.dispose();

		mainMenuFont.dispose();
		highScoresFont.dispose();
		ingameFont.dispose();
		infoFont.dispose();
		titleFont.dispose();
	}

	@Override
	public void render() {
		// This renders the current set Screen
		super.render();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
		Camera.setToOrtho(true, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		FileHandle fontFile = Gdx.app.getFiles().internal(
				"data/gfx/fonts/Cooper Black.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

		mainMenuFont = generator.generateFont((int)(screenWidth / 18), "PLAYHIGSCOREWT", true);
		mainMenuFont.setColor(new Color(1f,1f,1f,1));

		highScoresFont = generator.generateFont((int) (screenWidth / 20),
				"1234567890stndr:", true);
		highScoresFont.setColor(new Color(1, 1, 1, 1));

		ingameFont = generator.generateFont((int) (screenWidth / 36),
				"1234567890Scor:Llev", true);
		ingameFont.setColor(new Color(1, 1, 1, 1f));

		infoFont = generator.generateFont((int) screenWidth / 36,
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		infoFont.setColor(new Color(1, 1, 1, 1f));

		titleFont = generator.generateFont((int)screenWidth / 12, 
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		titleFont.setColor(new Color(1,1,1,1));
		generator.dispose();
	}
}
