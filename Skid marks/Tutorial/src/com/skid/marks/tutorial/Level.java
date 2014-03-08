package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.skid.marks.manager.TextureManager;


public class Level {
	
	private Texture texture;
	private Sprite sprite;
	
	
	private float distanceSinceLastPoint = 0.0f;
	private float distanceBetweenPoints = 250; //half screen
	private int previousPoint = 0;
	private float tunnelWidth;
	
	private float points[];
	private Row rows[];
	private int lowestRow;
	private float levelSpeed = 100f;
	private float rowHeight;
	private int nrOfRows;
	int h;
	int w;
	
	public Level()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		tunnelWidth = 200f;
		nrOfRows = 26;
		rowHeight = h / (nrOfRows-1);
		
		//punkterna som banan ska gå längs
		points = new float[10];
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
		
		//de rader som ritas ut
		rows = new Row[nrOfRows];
		for(int i = 0; i < rows.length; i++)
		{
			rows[i] = new Row(w/2, tunnelWidth, (i-1)*rowHeight);
		}
		lowestRow = nrOfRows-1;
		
		texture = new Texture(Gdx.files.internal("data/wall.png"));
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 32, 32);
		
		//sprite = new Sprite(region);
		sprite = TextureManager.getSprite("data/wall.png");

		//System.out.println(""+w+" "+h);
	}
	
	
	public void dispose() {
		texture.dispose();
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
			float activePoint = (points[previousPoint] + (ratio * (points[(previousPoint+1)%10] - points[previousPoint])))*w;
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
		
		//System.out.println("activepoint: "+ ((points[previousPoint] + (ratio * (points[(previousPoint+1)%10] - points[previousPoint])))*w));
		
	}
	
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < nrOfRows; i++)
		{
			sprite.setBounds(0, rows[i].Y, rows[i].leftWidth, rowHeight);
			sprite.draw(batch);
			sprite.setBounds(w - rows[i].rightWidth, rows[i].Y, rows[i].rightWidth, rowHeight);
			sprite.draw(batch);
			
		}
		System.out.println(""+(w - rows[lowestRow].rightWidth));
	}
}
