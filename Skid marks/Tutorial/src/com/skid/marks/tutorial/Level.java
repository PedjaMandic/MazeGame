package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.skid.marks.manager.TextureManager;


public class Level {
	
	private TutorialGame game;
	
	private Sprite[] sprites;
	private int currentSprite = 0;
	private float distanceSinceLastPoint = 0.0f;
	private float distanceBetweenPoints; //half screen
	private int previousPoint = 0;
	private float tunnelWidth;
	
	private float points[];
	private Row rows[];
	private int lowestRow;
	private float levelSpeed;
	private float rowHeight;
	private int nrOfRows;
	private int h;
	private int w;
	
	public Level(TutorialGame game)
	{
		this.game = game;
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		tunnelWidth = w*0.8f;
		nrOfRows = 1+h/40;
		rowHeight = h / (nrOfRows-1);
		distanceBetweenPoints = h/4;
		levelSpeed = h * 0.75f;
		
		sprites = new Sprite[3];
		sprites[0] = game.Textures.getSprite("data/gfx/bar.png");
		sprites[1] = game.Textures.getSprite("data/gfx/bar.png");
		sprites[2] = game.Textures.getSprite("data/gfx/bar.png");
		
		sprites[0].setColor(1, 0, 0, 1);
		sprites[1].setColor(0, 1, 0, 1);
		sprites[2].setColor(0, 0, 1, 1);
		
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
			rows[i] = new Row(w/2, tunnelWidth, (i-1)*rowHeight, currentSprite);
			currentSprite++;
			currentSprite %= sprites.length;
		}
		lowestRow = nrOfRows-1;
		
		
		//sprite = new Sprite(region);
		//sprite = TextureManager.getSprite("data/gfx/bar.png");

		//System.out.println(""+w+" "+h);
	}
	
	//förbättra på något sätt
	public boolean HasCollided(Rectangle rect)
	{
		int start = nrOfRows + lowestRow - (int)((h-rect.y)/rowHeight);
		int nrOfRowsToCheck = 1+(int)(rect.height/rowHeight);
		//System.out.println(""+lowestRow+"/"+nrOfRows+", "+(start%nrOfRows)+"/"+nrOfRows);
		for(int i = start; i < start+nrOfRowsToCheck; i++)
		{
			if(rect.x < rows[i%nrOfRows].leftWidth)
				return true;
			if(rect.x+rect.width >= w - rows[i%nrOfRows].rightWidth)
				return true;
		}
		
		return false;
	}
	
	public void dispose() {
	}
	
	public void update(float delta)
	{
		distanceSinceLastPoint+= levelSpeed*delta;
		
		if(distanceSinceLastPoint >= distanceBetweenPoints)
		{
			distanceSinceLastPoint -= distanceBetweenPoints;
			previousPoint ++;
			previousPoint %= points.length;
		}
		
		float ratio = distanceSinceLastPoint / distanceBetweenPoints;
		
		for(int i = 0; i < rows.length;i++)
		{
			rows[i].Y += levelSpeed*delta;
			
		}
		if(rows[lowestRow].Y >= h)
		{
			float activePoint = (points[previousPoint] + (ratio * (points[(previousPoint+1)%points.length] - points[previousPoint])))*w;
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight, currentSprite);
			currentSprite++;
			currentSprite %= sprites.length;
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
		
	}
	
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < nrOfRows; i++)
		{
			sprites[rows[i].sprite].setBounds(0, rows[i].Y, rows[i].leftWidth, rowHeight);
			sprites[rows[i].sprite].draw(batch);
			sprites[rows[i].sprite].setBounds(w - rows[i].rightWidth, rows[i].Y, rows[i].rightWidth, rowHeight);
			sprites[rows[i].sprite].draw(batch);
			
			
		}
	}
}
