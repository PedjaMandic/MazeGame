package com.trihard.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.trihard.game.Level;
import com.trihard.game.Player;
import com.trihard.game.TriHard;

public class GameOver implements Screen, InputProcessor {
	
	private final TriHard game;
	
	private Level level;

	private int score;
	
	private Sprite backSprite;
	private Sprite playSprite;
	private Sprite score_background;
	
	private float buttonSize;
	
	private float sw;
	private float sh;
	
	private ParticleEffect explosion;
	
	public GameOver(final TriHard game, Level level, Player player, int score) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		
		this.level = level;
		this.score = score;
		
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		
		buttonSize = sh * 0.15f;
		
		backSprite = game.Textures.getSprite("data/gfx/menu/button_back.png");
		backSprite.setSize(buttonSize, buttonSize);
		backSprite.setPosition(buttonSize * 0.2f, sh - buttonSize * 1.2f);
		
		playSprite = game.Textures.getSprite("data/gfx/menu/button_replay.png");
		playSprite.setSize(buttonSize, buttonSize);
		playSprite.setPosition(sw - buttonSize * 1.2f, sh - buttonSize * 1.2f);
		
		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal("data/gfx/particle/explosion2.p"),
				Gdx.files.internal("data/gfx/particle/"));
		explosion.setPosition(player.getPosition().x, player.getPosition().y);
		explosion.reset();
		
		score_background = game.Textures.getSprite("data/gfx/menu/button.png");
		score_background.setBounds(0, 0, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/6);
	}
	
	@Override
	public void dispose() {
		explosion.dispose();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.Camera.update();
		game.Batch.setProjectionMatrix(game.Camera.combined);
		game.Batch.begin();
		level.draw(game.Batch);
		score_background.draw(game.Batch);
		game.ingame_font.draw(game.Batch, String.format("Score: %d", score), score_background.getWidth()*0.12f, score_background.getHeight()*0.2f);
		game.ingame_font.draw(game.Batch, String.format("Level: %d", Level.currentLevel), score_background.getWidth()*0.12f, score_background.getHeight()*0.6f);
		
		explosion.draw(game.Batch, delta);
		
		playSprite.draw(game.Batch);
		backSprite.draw(game.Batch);

		game.Batch.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(backSprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.ingame_font.setColor(Color.GRAY);
//			game.Sounds.stopAll();
			game.setScreen(new MainMenu(game));
			this.dispose();
		} else if(playSprite.getBoundingRectangle().contains(screenX, screenY)) {
			game.ingame_font.setColor(Color.GRAY);
			game.setScreen(new Play(game));
			this.dispose();
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
