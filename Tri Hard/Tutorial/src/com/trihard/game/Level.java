package com.trihard.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Level {
	
	private TriHard game;
	
	private Random random; //for randomisation
	private Sprite sprite; //sprite for rows
	private float distanceSinceLastPoint;
	private float distanceBetweenPoints;
	private float currentPoint; //the Y coordinate for the current point (0.0f is top, 1.0 is bottom)
	private float nextPoint; //the Y coordinate for the next point (0.0f is top, 1.0 is bottom)
	private float tunnelWidth; //the width of the tunnel (measured in pixel amounts)
	
	private Row rows[]; //the rows of the level
	private int lowestRow; //the position of the leftmost row
	private float levelSpeed; //the speed the level travels, measured in pixels per second
	private float rowWidth; //the width of an individual row, measured in pixels
	private int nrOfRows; //the total amount of rows for easy reference
	
	private float timeUntilLevelStarts; //the amount of time left for the pauses
	private float totalPauseTime; //the total amount of time for pauses
	private float timeUntilLevelEnds; //the amount of time left for the actual level
	private float timeBetweenLevels; //the total amount of time for the level
	private float maximumPointDifference; //determines the maximum difference a between points when randomly determining the next point (measured in percentages)
	
	public static int currentLevel = 0; //the current level (0 being the first intermission, and 1, 2, 3 and so forth being actual levels)
	
	private Background background; //the moving background
	
	public static boolean isBetweenLevels; //boolean for determining if there is currently an intermission between levels
	private Sprite pauseSprite; //sprite that holds the "New Level" text that flies by during intermission

	//constructor
	public Level(TriHard game)
	{
		this.game = game;
		this.random = new Random();
		this.background = new Background(game);
		this.reset();
	}
	
	//resets all values to their default. Is called when the player starts/restarts the game
	public void reset() {
		TriHard.screen_width = Gdx.graphics.getWidth();
		TriHard.screen_height = Gdx.graphics.getHeight();
		tunnelWidth = TriHard.screen_height*0.5f; //default tunnelwidth is half a screen wide.
		nrOfRows = 1+(int)TriHard.screen_width/40; //on a 1280x720 resolution, this results in 32 rows. The higher resolution, the more rows. The extra +1 is for smoothness
		rowWidth = TriHard.screen_width / (nrOfRows-1);
		distanceBetweenPoints = TriHard.screen_width/4; //nextpoint is determined after a fourth of a screen
		levelSpeed = TriHard.screen_width * 0.8f;
		currentPoint = 0.5f; //percentage of the games height where the current point is
		nextPoint = 0.5f; //to avoid problems, the next point has the same percentage
		isBetweenLevels = true; //the game starts in intermission
		currentLevel = 0;
		timeBetweenLevels = 20f; //the first level is 20 seconds long, higher levels are longer
		timeUntilLevelEnds = timeBetweenLevels;
		totalPauseTime = 3f; //the intermission time is always three seconds
		timeUntilLevelStarts = totalPauseTime;
		distanceSinceLastPoint = 0.0f;
		maximumPointDifference = 0.4f; //in order to not have too much of a difficulty in the game, maximum change interval between points is 40%. This is never changed
		
		sprite = game.Textures.getSprite("data/gfx/rows_2.1.png"); //all the rows look the same, and thus share the same sprite
		pauseSprite = game.Textures.getSprite("data/gfx/newlevel2.png"); //the pausetext needs it's own sprite
		pauseSprite.setColor(Color.WHITE); 
		
		rows = new Row[nrOfRows]; //the array that holds the rows
		for(int i = 0; i < rows.length; i++)
		{
			 //all the rows are set as a straight line as default. Not that it matters since it'll be part of the intermission
			rows[i] = new Row(TriHard.screen_height/2, tunnelWidth, (rows.length - (i-1))*rowWidth, !isBetweenLevels);
		}
		lowestRow = nrOfRows-1; //the lowest row is last place in the array
		
	}
	
	//collision-detection, that checks only against the rows that "contain" the same Y coordinates as the player
	//rect is (usually) the player rectangle
	public boolean HasCollided(Rectangle rect)
	{
		//this part determines which part of the row-array "contains" the player
		int start = (nrOfRows + lowestRow - (int)(rect.x/rowWidth)-1)%nrOfRows;
		int nrOfExtraRowsToCheck = 0;
		
		//while this checks for additional rows to check due to the players size
		while(rows[(nrOfRows+start-nrOfExtraRowsToCheck)%nrOfRows].X+rowWidth < rect.x + rect.width)
		{
			nrOfExtraRowsToCheck++;
		}
		
		//no point in checking if the rows aren't visible or collidable
		if(rows[start].active)
		{
			if(rect.y < rows[start].leftWidth)
				return true;
			if(rect.y+rect.height > (TriHard.screen_height - rows[start].rightWidth))
				return true;
		}
		
		//uses modulus to determine which part of the array to check
		for(int i = start-1; i >= start-nrOfExtraRowsToCheck; i--)
		{
			if(rows[(nrOfRows+i)%nrOfRows].active)
			{
				if(rect.y + rect.height/2f < rows[(nrOfRows+i)%nrOfRows].leftWidth)
					return true;
				if((rect.y+rect.height/2f) > (TriHard.screen_height - rows[(nrOfRows+i)%nrOfRows].rightWidth))
					return true;
			}
		}
		
		//if nothing returned true, then obviously there was no collision
		return false;
	}
	
	public void dispose() {
	}
	
	//triggers once per intermission, when it ends
	private void endPauseTrigger()
	{
		//increase in difficulty
		if(currentLevel < 9){
			currentLevel++; //a higher level
			tunnelWidth *= 0.95f; //tighter tunnel
			levelSpeed *= 1.05f; //faster level
			timeBetweenLevels *= 1.05f; //longer level
			Player.MOVE_SPEED *= 1.05f; //the player can move faster
		}
		
		//special background colors for certain levels
		if(currentLevel == 8)
			background.setColor(Color.WHITE);
		else if (currentLevel == 9)
			background.setColor(Color.BLACK);
		else
			background.setColorRandom(); //does not set a random color (but it used to)
		
		timeUntilLevelStarts+= totalPauseTime; //reset pause timer for the next intermission
		isBetweenLevels = false; //end of intermission
	}
	
	//triggers once per intermission, when it starts
	private void startPauseTrigger()
	{
		timeUntilLevelEnds += timeBetweenLevels; //reset the level timer for the next level
		isBetweenLevels = true; //start of intermission
	}
	
	//the main update function
	//delta is the amount of time since the last update
	public void update(float delta)
	{		
		background.update(delta); //background update (movement and such)
		
		//TIMERS
		if(isBetweenLevels) //if there's an intermission...
		{			
			timeUntilLevelStarts -= delta;
			if(timeUntilLevelStarts <= 0)
			{
				endPauseTrigger(); //intermission end trigger
			}
		}
		else {
			timeUntilLevelEnds -= delta;
			if(timeUntilLevelEnds <= 0 && currentLevel < 9)
			{
				startPauseTrigger(); //intermission start trigger (there are no intermissions on level 9
			}
		}
		
		//LEVEL CALCULATIONS
		distanceSinceLastPoint+= levelSpeed*delta;
		
		//determines what Y-percentage the next point will have
		if(distanceSinceLastPoint >= distanceBetweenPoints)
		{
			distanceSinceLastPoint -= distanceBetweenPoints; //reset distance

			float convertedHalfTW = (tunnelWidth/TriHard.screen_height)/2; //conversion for half the current tunnelwidth in a percentage for easy reference and less calculations
			
			currentPoint = nextPoint;
			float deltaValue = -maximumPointDifference + random.nextFloat()*(maximumPointDifference*2); //how much the points will differ
			
			nextPoint = currentPoint +deltaValue;

			//correctional calculations (0.05f is a buffer from the edges of the game screen). This prevents the next point from ending up outside the screen
			if(nextPoint < convertedHalfTW+0.05f || nextPoint > (0.95f - convertedHalfTW))
				nextPoint = currentPoint - deltaValue;
			
			//if the last one doesn't do it, this one will
			if(nextPoint < convertedHalfTW+0.05f)
				nextPoint = convertedHalfTW+0.05f;
			if(nextPoint > (0.95f - convertedHalfTW))
				nextPoint = 0.95f - convertedHalfTW;
		}

		//INDIVIDUAL ROW CALCULATIONS
		
		//update for every row
		for(int i = 0; i < rows.length;i++)
		{
			rows[i].X -= levelSpeed*delta;
			rows[i].update(delta);
		}
		
		//moves rows that have fully gone from right to left back to the start. LowestRow determines which row in the array is farthest to the left.
		while(rows[lowestRow].X <= -rowWidth)
		{
			float ratio = distanceSinceLastPoint / distanceBetweenPoints;
			float activePoint = (currentPoint + (ratio * (nextPoint - currentPoint)))*TriHard.screen_height;
			
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowWidth, !isBetweenLevels);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
	}
	
	//draw-function
	//batch allows the drawing of sprites
	public void draw(SpriteBatch batch)
	{
		background.draw(batch); //draws the background
		
		game.Particles.render(batch, Gdx.graphics.getDeltaTime()); //draws particles
		
		//determines the position of and draws the pausesprite
		if(isBetweenLevels && currentLevel >= 1)
		{
			float xPos = (float)Math.pow(totalPauseTime/2 - timeUntilLevelStarts, 4f)*TriHard.screen_width/2; //results in a form of swooshing effect
			
			if(timeUntilLevelStarts > totalPauseTime/2)
				xPos = TriHard.screen_width/2 + xPos;
			else
				xPos = TriHard.screen_width/2 - xPos;
			pauseSprite.setBounds(xPos, TriHard.screen_height/4, TriHard.screen_width/4, TriHard.screen_height/8);
			pauseSprite.draw(batch);
		}
		
		//drawing the rows
		for(int i = 0; i < nrOfRows; i++)
		{
			if(rows[i].active) //doesn't draw the rows if they aren't collidable (during an intermission, for example)
			{
				//flips the sprite twice, once for each side of the tunnel
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, -TriHard.screen_height + rows[i].leftWidth, rowWidth, TriHard.screen_height);
				sprite.draw(batch);
				sprite.flip(false, true);
				sprite.setBounds(rows[i].X, TriHard.screen_height - rows[i].rightWidth, rowWidth, TriHard.screen_height);
				sprite.draw(batch);
			}
		}
	}
}
