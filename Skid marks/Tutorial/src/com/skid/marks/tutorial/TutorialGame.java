package com.skid.marks.tutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.skid.marks.manager.SoundManager;
import com.skid.marks.manager.TextureManager;
import com.skid.marks.manager.particle.ParticleManager;
import com.skid.marks.screens.MainMenu;

public class TutorialGame extends Game {

	public BitmapFont main_menu_font;
	public BitmapFont highscores_font;
	public BitmapFont ingame_font;
	public BitmapFont info_font;
	public BitmapFont title_font;

	public static float screen_width, screen_height;

	public SpriteBatch Batch;
	public OrthographicCamera Camera;

	// Managers
	public TextureManager Textures;
	public SoundManager Sounds;
	public ParticleManager Particles;

	@Override
	public void create() {

		screen_width = Gdx.graphics.getWidth();
		screen_height = Gdx.graphics.getHeight();

		Textures = new TextureManager();
		Sounds = new SoundManager();
		Particles = new ParticleManager();

		Batch = new SpriteBatch();
		Camera = new OrthographicCamera();
		Camera.setToOrtho(true, screen_width, screen_height);

		// Skapar alla fonts
		// -----------------------------------------------------------------------
		FileHandle fontFile = Gdx.app.getFiles().internal(
				"data/gfx/fonts/Cooper Black.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

		main_menu_font = generator.generateFont((int)(screen_width / 18), "PLAYHIGSCOREWT", true);
		main_menu_font.setColor(new Color(1f,1f,1f,1));

		highscores_font = generator.generateFont((int) (screen_width / 20),
				"1234567890stndr:", true);
		highscores_font.setColor(new Color(1, 1, 1, 1));

		ingame_font = generator.generateFont((int) (screen_width / 36),
				"1234567890Scor:Llev", true);
		ingame_font.setColor(new Color(1, 1, 1, 1f));

		info_font = generator.generateFont((int) screen_width / 36,
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM?", true);
		info_font.setColor(new Color(1, 1, 1, 1f));

		title_font = generator.generateFont((int)screen_width / 12, 
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		title_font.setColor(new Color(1,1,1,1));
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

		main_menu_font.dispose();
		highscores_font.dispose();
		ingame_font.dispose();
		info_font.dispose();
		title_font.dispose();
	}

	@Override
	public void render() {
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

		main_menu_font = generator.generateFont((int)(screen_width / 18), "PLAYHIGSCOREWT", true);
		main_menu_font.setColor(new Color(1f,1f,1f,1));

		highscores_font = generator.generateFont((int) (screen_width / 20),
				"1234567890stndr:", true);
		highscores_font.setColor(new Color(1, 1, 1, 1));

		ingame_font = generator.generateFont((int) (screen_width / 36),
				"1234567890Scor:Llev", true);
		ingame_font.setColor(new Color(1, 1, 1, 1f));

		info_font = generator.generateFont((int) screen_width / 36,
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		info_font.setColor(new Color(1, 1, 1, 1f));

		title_font = generator.generateFont((int)screen_width / 12, 
				"qwertyuioplkjhgfdsazxcvbnm.,QWERTYUIOPLKJHGFDSAZXCVBNM", true);
		title_font.setColor(new Color(1,1,1,1));
		generator.dispose();
	}
}
