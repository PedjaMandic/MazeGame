package com.trihard.game;

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
	private float currentPoint;
	private float nextPoint;
	private float tunnelWidth;
	
	private Row rows[];
	private int lowestRow;
	private float levelSpeed;
	private float rowHeight;
	private int nrOfRows;
	
	private float timeUntilLevelStarts;
	private float totalPauseTime;
	private float timeUntilLevelEnds;
	private float timeBetweenLevels;
	private float maximumPointDifference;
	
	public static int currentLevel = 0;
	
	private Background background;
	
	//för pausskärm
	public static boolean isBetweenLevels;
	private Sprite pauseSprite;

	public Level(TutorialGame game)
	{
		this.game = game;
		this.random = new Random();
		this.background = new Background(game);
		this.reset();
	}
	
	public void reset() {
		TutorialGame.screen_width = Gdx.graphics.getWidth();
		TutorialGame.screen_height = Gdx.graphics.getHeight();
		tunnelWidth = TutorialGame.screen_height*0.5f;
		nrOfRows = 1+(int)TutorialGame.screen_width/40;
		rowHeight = TutorialGame.screen_width / (nrOfRows-1);
		distanceBetweenPoints = TutorialGame.screen_width/4;
		levelSpeed = TutorialGame.screen_width * 0.8f;
		currentPoint = 0.5f;
		nextPoint = 0.5f;
		isBetweenLevels = true;
		currentLevel = 0;
		timeBetweenLevels = 20f;
		timeUntilLevelEnds = timeBetweenLevels;
		totalPauseTime = 3f;
		timeUntilLevelStarts = totalPauseTime;
		distanceSinceLastPoint = 0.0f;
		maximumPointDifference = 0.4f;
		
		sprite = game.Textures.getSprite("data/gfx/rows_2.1.png");
		pauseSprite = game.Textures.getSprite("data/gfx/newlevel2.png");
		pauseSprite.setColor(Color.WHITE);
		


		
		//de rader som ritas ut
		rows = new Row[nrOfRows];
		for(int i = 0; i < rows.length; i++)
		{
			rows[i] = new Row(TutorialGame.screen_height/2, tunnelWidth, (rows.length - (i-1))*rowHeight, !isBetweenLevels);

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
		
		if(rows[start].active)
		{
			if(rect.y < rows[start].leftWidth)
				return true;
			if(rect.y+rect.height > (TutorialGame.screen_height - rows[start].rightWidth))
				return true;
		}
		
		for(int i = start-1; i >= start-nrOfExtraRowsToCheck; i--)
		{
			if(rows[(nrOfRows+i)%nrOfRows].active)
			{
				if(rect.y + rect.height/2f < rows[(nrOfRows+i)%nrOfRows].leftWidth)
					return true;
				if((rect.y+rect.height/2f) > (TutorialGame.screen_height - rows[(nrOfRows+i)%nrOfRows].rightWidth))
					return true;
			}
		}
		
		return false;
	}
	
	public void dispose() {
	}
	
	
	private void endPauseTrigger()
	{
		if(currentLevel < 9){
			currentLevel++;
			tunnelWidth *= 0.95f;
			levelSpeed *= 1.05f;
			timeBetweenLevels *= 1.05f;
			Player.MOVE_SPEED *= 1.05f;
		}
		
		if(currentLevel == 8)
			background.setColor(Color.WHITE);
		else if (currentLevel == 9)
			background.setColor(Color.BLACK);
		else
			background.setColorRandom();
		
		timeUntilLevelStarts+= totalPauseTime;
		isBetweenLevels = false;
	}
	
	private void startPauseTrigger()
	{
		timeUntilLevelEnds += timeBetweenLevels;
		isBetweenLevels = true;
	}
	
	
	public void update(float delta)
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
			if(timeUntilLevelEnds <= 0 && currentLevel < 9)
			{
				startPauseTrigger();
			}
		}
		
		distanceSinceLastPoint+= levelSpeed*delta;
		
		if(distanceSinceLastPoint >= distanceBetweenPoints)
		{
			distanceSinceLastPoint -= distanceBetweenPoints;

			float convertedHalfTW = (tunnelWidth/TutorialGame.screen_height)/2;
			currentPoint = nextPoint;
			float deltaValue = -maximumPointDifference + random.nextFloat()*(maximumPointDifference*2);
			nextPoint = currentPoint +deltaValue;

			if(nextPoint < convertedHalfTW+0.05f || nextPoint > (0.95f - convertedHalfTW))
				nextPoint = currentPoint - deltaValue;


			if(nextPoint < convertedHalfTW+0.05f)
				nextPoint = convertedHalfTW+0.05f;
			if(nextPoint > (0.95f - convertedHalfTW))
				nextPoint = 0.95f - convertedHalfTW;
		}

		float ratio = distanceSinceLastPoint / distanceBetweenPoints;
		
		for(int i = 0; i < rows.length;i++)
		{
			rows[i].X -= levelSpeed*delta;
			
			// Anim
			rows[i].update(delta);
		}
		while(rows[lowestRow].X <= -rowHeight)
		{
			float activePoint = (currentPoint + (ratio * (nextPoint - currentPoint)))*TutorialGame.screen_height;
			
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight, !isBetweenLevels);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		background.draw(batch);
		
		game.Particles.render(batch, Gdx.graphics.getDeltaTime());
		
		if(isBetweenLevels && currentLevel >= 1)
		{
			float xPos = (float)Math.pow(totalPauseTime/2 - timeUntilLevelStarts, 4f)*TutorialGame.screen_width/2;
			
			if(timeUntilLevelStarts > totalPauseTime/2)
				xPos = TutorialGame.screen_width/2 + xPos;
			else
				xPos = TutorialGame.screen_width/2 - xPos;
			pauseSprite.setBounds(xPos, TutorialGame.screen_height/4, TutorialGame.screen_width/4, TutorialGame.screen_height/8);
			pauseSprite.draw(batch);
		}
		
		for(int i = 0; i < nrOfRows; i++)
		{
			if(rows[i].active)
			{
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, -TutorialGame.screen_height + rows[i].leftWidth, rowHeight, TutorialGame.screen_height);
				sprite.draw(batch);
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, TutorialGame.screen_height - rows[i].rightWidth, rowHeight, TutorialGame.screen_height);
				sprite.draw(batch);
			}
		}
	}
}
