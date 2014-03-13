package com.skid.marks.tutorial;

import java.sql.Time;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Level {
	
	private TutorialGame game;
	
	private Random random;
	private Sprite[] sprites;
	private int currentSprite = 0;
	private float distanceSinceLastPoint = 0.0f;
	private float distanceBetweenPoints; //half screen
	private int previousPoint = 0;
	private float currentPoint;
	private float nextPoint;
	private float tunnelWidth;
	
	private float points[];
	private Row rows[];
	private int lowestRow;
	private float levelSpeed;
	private float rowHeight;
	private int nrOfRows;
	private int h;
	private int w;
	
	private int spacing = 1;
	
	public static boolean isRandom;
	
	private Background background;
	
	private ParticleEffect lightEffect;
	private ParticleEmitter lightTouchedEmitter;
	private ParticleEmitter lightUntouchedEmitter;
	private boolean lightParticleCollision;
	
	public Level(TutorialGame game)
	{
		this.game = game;
		this.random = new Random();
		this.background = new Background(game);
		this.reset();
	}
	
	public void reset() {
		lightEffect = new ParticleEffect();
		lightEffect.load(Gdx.files.internal("data/particle/lightParticle.p"),
					   Gdx.files.internal("data/particle/"));
		lightTouchedEmitter = lightEffect.findEmitter("touched");
		lightUntouchedEmitter = lightEffect.findEmitter("untouched");
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		tunnelWidth = h*0.4f;
		nrOfRows = 1+w/40;
		rowHeight = w / (nrOfRows-1);
		distanceBetweenPoints = w/4;
		levelSpeed = w * 0.8f;
		currentPoint = 0.5f;
		nextPoint = 0.5f;
		//isRandom = true;	-> Görs i gamemode.java
		
		currentSprite = 0;
		distanceSinceLastPoint = 0.0f;
		previousPoint = 0;
		
		sprites = new Sprite[3];
		sprites[0] = game.Textures.getSprite("data/gfx/platform_white.png");
		sprites[1] = game.Textures.getSprite("data/gfx/platform_white.png");
		sprites[2] = game.Textures.getSprite("data/gfx/platform_white.png");
		
//		sprites[0].setColor(1, 0, 0, 1);
//		sprites[1].setColor(0, 1, 0, 1);
//		sprites[2].setColor(0, 0, 1, 1);
		
		//punkterna som banan ska gå längs
		points = new float[15];
		points[0] = 0.5f;
		points[1] = 0.4f;
		points[2] = 0.7f;
		points[3] = 0.6f;
		points[4] = 0.7f;
		points[5] = 0.5f;
		points[6] = 0.3f;
		points[7] = 0.2f;
		points[8] = 0.25f;
		points[9] = 0.4f;
		points[10] = 0.5f;
		points[11] = 0.45f;
		points[12] = 0.3f;
		points[13] = 0.6f;
		points[14] = 0.65f;
		
		//de rader som ritas ut
		rows = new Row[nrOfRows];
		for(int i = 0; i < rows.length; i++)
		{
			rows[i] = new Row(h/2, tunnelWidth, (rows.length - (i-1))*rowHeight, currentSprite, (i%spacing == 0));
			currentSprite++;
			currentSprite %= sprites.length;
			
		}
		lowestRow = nrOfRows-1;
		
		//sprite = new Sprite(region);
		//sprite = TextureManager.getSprite("data/gfx/bar.png");

		//System.out.println(""+w+" "+h);
	}
	
	//förbättra på något sätt
	// Rect == player bounds
	public boolean HasCollided(Rectangle rect)
	{
		int start = (nrOfRows + lowestRow - (int)(rect.x/rowHeight)-1)%nrOfRows;
		int nrOfExtraRowsToCheck = 0;
		
		while(rows[(nrOfRows+start-nrOfExtraRowsToCheck)%nrOfRows].X+rowHeight < rect.x + rect.width)
		{
			nrOfExtraRowsToCheck++;
		}
		
		for(int i = start; i >= start-nrOfExtraRowsToCheck; i--)
		{
			if(rows[(nrOfRows+i)%nrOfRows].active)
			{
				if(rect.y < rows[(nrOfRows+i)%nrOfRows].leftWidth)
					return true;
				if((rect.y+rect.height) > (h - rows[(nrOfRows+i)%nrOfRows].rightWidth))
					return true;
			}
		}
		
		// Check particle collision
		if(lightParticleCollision == false) {
			// Kl är mycket, så det blev så här så länge
			Rectangle partRect = new Rectangle();
			partRect.x = lightUntouchedEmitter.getX();
			partRect.y = lightUntouchedEmitter.getY();
//			partRect.width = 64; // TODO: sänk värdet
//			partRect.height = 64; // TODO: sänk värdet
			partRect.width = lightUntouchedEmitter.getScale().getHighMax();
			partRect.height = lightUntouchedEmitter.getScale().getHighMax();
			
			if(rect.overlaps(partRect)) {
				lightParticleCollision = true;
				lightTouchedEmitter.reset();
				background.setColorRandom();
			}
		}
		
		return false;
	}
	
	public void dispose() {
	}
	
	public void update(Player p, float delta)
	{		
		background.update(delta);
		
		distanceSinceLastPoint+= levelSpeed*delta;
		
		if(distanceSinceLastPoint >= distanceBetweenPoints)
		{
			distanceSinceLastPoint -= distanceBetweenPoints;
			if(!isRandom){
				previousPoint ++;
				previousPoint %= points.length;
				currentPoint = points[previousPoint];
				nextPoint = points[(previousPoint+1)%points.length];
			}else {
				currentPoint = nextPoint;
				nextPoint = currentPoint-0.25f+0.5f*random.nextFloat();
				if(nextPoint < 0.05f+tunnelWidth/h/2)
					nextPoint = 0.05f+tunnelWidth/h/2;
				else if(nextPoint > 0.95f- tunnelWidth/h/2)
					nextPoint = 0.95f-tunnelWidth/h/2;
			}
		}
		
		float ratio = distanceSinceLastPoint / distanceBetweenPoints;
		
		for(int i = 0; i < rows.length;i++)
		{
			rows[i].X -= levelSpeed*delta;
			
			// Anim
			rows[i].update(p, delta);
		}
		while(rows[lowestRow].X <= -rowHeight)
		{
			float activePoint = (currentPoint + (ratio * (nextPoint - currentPoint)))*h;
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight, currentSprite, (lowestRow%spacing == 0));
			currentSprite++;
			currentSprite %= sprites.length;
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
		
		if(lightParticleCollision) {
			lightTouchedEmitter.setPosition(p.getPosition().x, p.getPosition().y);
			lightTouchedEmitter.update(delta);
			if(lightTouchedEmitter.isComplete()) {
				lightParticleCollision = false;
			}
		} else {
			lightUntouchedEmitter.setPosition(rows[0].X + rowHeight / 2, rows[0].leftWidth + 20);
			lightUntouchedEmitter.update(delta);
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		background.draw(batch);
		
		if(lightParticleCollision) {
			lightTouchedEmitter.draw(batch);
		} else {
			lightUntouchedEmitter.draw(batch);
		}
		
		for(int i = 0; i < nrOfRows; i++)
		{
			if(rows[i].active)
			{
				sprites[rows[i].sprite].flip(false, true);
				sprites[rows[i].sprite].setBounds(rows[i].X, 0, rowHeight, rows[i].leftWidth);
				sprites[rows[i].sprite].draw(batch);
				sprites[rows[i].sprite].flip(false, true);
				sprites[rows[i].sprite].setBounds(rows[i].X, h - rows[i].rightWidth, rowHeight, rows[i].rightWidth);
				sprites[rows[i].sprite].draw(batch);
			}
		}
	}
}
