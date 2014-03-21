package com.skid.marks.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.manager.Localization;
import com.skid.marks.manager.SoundManager;
import com.skid.marks.manager.TextureManager;
import com.skid.marks.manager.particle.ParticleManager;
import com.skid.marks.screens.MainMenu;

public class TutorialGame extends Game {

	public BitmapFont Font;
	public SpriteBatch Batch;
	public OrthographicCamera Camera;
	
	// Managers
	public TextureManager Textures;
	public SoundManager Sounds;
	public ParticleManager Particles;
	public Localization Localization;
	
	@Override
	public void create() {
		Textures = new TextureManager();
		Sounds = new SoundManager();
		Particles = new ParticleManager();
		Localization = new Localization();

		Font = new BitmapFont(true);
		Batch = new SpriteBatch();
		Camera = new OrthographicCamera();
		Camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void dispose() {
		Font.dispose();
		Batch.dispose();
		Textures.dispose();
		Sounds.dispose();
		Particles.dispose();
		Localization.dispose();
	}

	@Override
	public void render() {
		super.render();
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
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
	}
}
