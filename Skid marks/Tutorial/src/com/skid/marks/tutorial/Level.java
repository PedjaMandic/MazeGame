package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Level {
	
	private TutorialGame game;
	
	private Random random;
	private Sprite sprite;
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
	
	private float timeUntilLevelStarts;
	private float totalPauseTime;
	private float timeUntilLevelEnds;
	private float timeBetweenLevels;
	
	private int currentLevel = 0;
	
	public static boolean isRandom;
	
	private Background background;
	
//	private ParticleEffect lightEffect;
//	private ParticleEmitter lightTouchedEmitter;
//	private ParticleEmitter lightUntouchedEmitter;
//	private boolean lightParticleCollision;
	
	
	//för pausskärm
	private boolean isBetweenLevels;
	private Sprite pauseSprite;
	
	
	
	public Level(TutorialGame game)
	{
		this.game = game;
		this.random = new Random();
		this.background = new Background(game);
		this.reset();
	}
	
	public void reset() {
//		lightEffect = new ParticleEffect();
//		lightEffect.load(Gdx.files.internal("data/particle/lightParticle.p"),
//					   Gdx.files.internal("data/particle/"));
//		lightTouchedEmitter = lightEffect.findEmitter("touched");
//		lightUntouchedEmitter = lightEffect.findEmitter("untouched");
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		tunnelWidth = h*0.5f;
		nrOfRows = 1+w/40;
		rowHeight = w / (nrOfRows-1);
		distanceBetweenPoints = w/4;
		levelSpeed = w * 0.8f;
		currentPoint = 0.5f;
		nextPoint = 0.5f;
		isBetweenLevels = true;
		
		currentLevel = 0;
		timeBetweenLevels = 20f;
		timeUntilLevelEnds = timeBetweenLevels;
		totalPauseTime = 3f;
		timeUntilLevelStarts = totalPauseTime;
		distanceSinceLastPoint = 0.0f;
		previousPoint = 0;
		
		sprite = game.Textures.getSprite("data/gfx/platform_white.png");
		pauseSprite = game.Textures.getSprite("data/gfx/newlevel2.png");
		pauseSprite.setColor(Color.WHITE);
		
		//punkterna som banan ska gå längs
		points = new float[31];
		points[0] = 0.5f;
		points[1] = 0.4f;
		points[2] = 0.7f;
		points[3] = 0.3f;
		points[4] = 0.7f;
		points[5] = 0.5f;
		points[6] = 0.3f;
		points[7] = 0.2f;
		points[8] = 0.25f;
		points[9] = 0.4f;
		points[10] = 0.7f;
		points[11] = 0.45f;
		points[12] = 0.2f;
		points[13] = 0.6f;
		points[14] = 0.65f;
		points[15] = 0.4f;
		points[16] = 0.5f;
		points[17] = 0.45f;
		points[18] = 0.3f;
		points[19] = 0.6f;
		points[20] = 0.65f;
		points[21] = 0.3f;
		points[22] = 0.2f;
		points[23] = 0.1f;
		points[24] = 0.5f;
		points[25] = 0.7f;
		points[26] = 0.8f;
		points[27] = 0.55f;
		points[28] = 0.9f;
		points[29] = 0.6f;
		points[30] = 0.65f;

		
		//de rader som ritas ut
		rows = new Row[nrOfRows];
		for(int i = 0; i < rows.length; i++)
		{
			rows[i] = new Row(h/2, tunnelWidth, (rows.length - (i-1))*rowHeight, !isBetweenLevels);

		}
		lowestRow = nrOfRows-1;
		
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
		
		return false;
	}
	
	public void dispose() {
	}
	
	
	private void endPauseTrigger()
	{
		if(currentLevel < 7)
		{
			currentLevel++;
			tunnelWidth *= 0.85f;
		}
		background.setColorRandom();
		timeUntilLevelStarts+= totalPauseTime;
		isBetweenLevels = false;
	}
	
	private void startPauseTrigger()
	{
		timeUntilLevelEnds += timeBetweenLevels;
		isBetweenLevels = true;
	}
	
	public void update(Player p, float delta)
	{		
		background.update(delta);
		
		if(isBetweenLevels)
		{			
			timeUntilLevelStarts -= delta;
			if(timeUntilLevelStarts <= 0)
			{
				endPauseTrigger();
			}
		}
		else {
			timeUntilLevelEnds -= delta;
			if(timeUntilLevelEnds <= 0)
			{
				startPauseTrigger();
			}
		}
		
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
			
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight, !isBetweenLevels);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
		
//		// Check particle collision
//		if(lightParticleCollision == false) {
//			Rectangle partRect = new Rectangle();
//			partRect.x = lightUntouchedEmitter.getX();
//			partRect.y = lightUntouchedEmitter.getY();
//			partRect.width = lightUntouchedEmitter.getScale().getHighMax();
//			partRect.height = lightUntouchedEmitter.getScale().getHighMax();
//			
//			if(p.getBounds().overlaps(partRect)) {
//				lightParticleCollision = true;
//				lightTouchedEmitter.start();
//			//	background.setColorRandom();							<- Ändras just nu i StartPause()
//			}
//		}
//		if(lightParticleCollision) {
//			lightTouchedEmitter.setPosition(p.getPosition().x, p.getPosition().y);
//			lightTouchedEmitter.update(delta);
//			
//			lightUntouchedEmitter.setPosition(rows[0].X + rowHeight / 2, rows[0].leftWidth + 20);
//			lightUntouchedEmitter.update(delta);
//			
//			if(lightTouchedEmitter.isComplete()) {
//				lightParticleCollision = false;
//			}
//		} else {
//			lightUntouchedEmitter.setPosition(rows[0].X + rowHeight / 2, rows[0].leftWidth + 20);
//			lightUntouchedEmitter.update(delta);
//		}
		
	}
	
	public void draw(SpriteBatch batch)
	{
		background.draw(batch);
		
		game.Particles.render(batch, Gdx.graphics.getDeltaTime());
		
//		if(lightParticleCollision) {
//			lightTouchedEmitter.draw(batch);
//		} else {
//			lightUntouchedEmitter.draw(batch);
//		}
		
		if(isBetweenLevels && currentLevel >= 1)
		{
			pauseSprite.setBounds(-w/4 + (w*1.25f)*(timeUntilLevelStarts/totalPauseTime), h/4, w/4, h/8);
			pauseSprite.draw(batch);
		}
		
		for(int i = 0; i < nrOfRows; i++)
		{
			if(rows[i].active)
			{
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, -h + rows[i].leftWidth, rowHeight, h);
				sprite.draw(batch);
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, h - rows[i].rightWidth, rowHeight, h);
				sprite.draw(batch);
			}
		}
	}
}
