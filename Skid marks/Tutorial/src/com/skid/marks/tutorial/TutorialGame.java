package com.skid.marks.tutorial;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.skid.marks.apptype.AndroidApp;
import com.skid.marks.apptype.BaseApp;
import com.skid.marks.apptype.GenericApp;

public class TutorialGame implements ApplicationListener {

	private ApplicationType appType;
	
	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Vector2 position;
	private Vector2 accelerometer;
	
	private TextureRegion textureRegion;
	
	private BaseApp app;
	
	@Override
	public void create() {
		appType = Gdx.app.getType();
		
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		accelerometer = Vector2.Zero;
		
		/*
		 * Bilder läggs i <Projektnamn>-android/assets/data/
		 * Är inte hundra om dom behövs läggas i data/ eller om man kan lägga dom i assets/ direkt
		 **/
		textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/test.png")));
		textureRegion.flip(false, true);
		
		/*
		 * Detta är ett sätt strukturera upp koden för olika plattformar.
		 * Den enda stora skillnaden mellan Andriod och Desktop är inputs.
		 * I detta projektet används inte BaseApp. Kolla update och draw istället.
		 **/
		switch (appType) {
			case Android:
				app = new AndroidApp(appType);
				break;
			default:
				app = new GenericApp(appType);
				break;
		}
		app.init();
	}

	@Override
	public void dispose() {
		font.dispose();
		batch.dispose();
		textureRegion.getTexture().dispose();
	}
	
	void update(float time) {	
		/*
		 * Input ges av Gdx.input... I princip allt får man genom Gdx.<namn>...
		 * Gdx.graphics,
		 * Gdx.audio,
		 * Gdx.input,
		 * Gdx.net etc.
		 **/
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		/*
		 * Som sagt så används inte BaseApp men man skulle kunna strukturera
		 * upp på det sättet. Här är ett simpelt exempel på hur man skulle
		 * kunna göra för olika inputs från olika plattformer. Man helt enkelt
		 * kollar typen som körs just nu.
		 **/
		final float speed = 150;
		if(appType == ApplicationType.Desktop) {
			if(Gdx.input.isKeyPressed(Keys.W)) {
				position.y -= speed * time;
			} else if(Gdx.input.isKeyPressed(Keys.S)) {
				position.y += speed * time;
			}
			if(Gdx.input.isKeyPressed(Keys.A)) {
				position.x -= speed * time;
			} else if(Gdx.input.isKeyPressed(Keys.D)) {
				position.x += speed * time;
			}
		} else if(appType == ApplicationType.Android) {
			float x = Gdx.input.getAccelerometerX() / 10.0f;
			float y = Gdx.input.getAccelerometerY() / 10.0f;
			accelerometer.set(x, y);
			
			position.x += speed * time * y;
			position.y += speed * time * x;
		}
	}
	
	float rot = 0;
	void draw(float time) {
		Gdx.gl.glClearColor(100/255f, 100/255f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
//		rot += time * 1000;
		rot = 0;
		
		/*
		 * Det här borde kännas igen från XNA
		 * batch.begin()
		 * batch.draw(..)
		 * batch.end()
		 **/
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, appType.name(), 20, 20);
		font.draw(batch, String.format("FPS: %s", Gdx.graphics.getFramesPerSecond()), 20, 50);
		batch.draw(textureRegion,
				position.x,
				position.y,
				textureRegion.getRegionWidth() / 2,
				textureRegion.getRegionHeight() / 2,
				textureRegion.getRegionWidth(),
				textureRegion.getRegionHeight(),
				1.0f,
				1.0f,
				rot);
		if(camera.frustum.pointInFrustum(new Vector3(position, 0.0f))) {
			font.draw(batch, "Inside frustum", 20, 80);
		}
		batch.end();
		
		if(appType == ApplicationType.Android) {
			batch.begin();
			font.draw(batch, String.format("Acc: (%s, %s)", accelerometer.x, accelerometer.y), 20, 80);
			batch.end();
		}
	} 

	@Override
	public void render() {
		float time = Gdx.graphics.getDeltaTime();
		update(time);
		draw(time);
		
		app.render(time);
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
}
