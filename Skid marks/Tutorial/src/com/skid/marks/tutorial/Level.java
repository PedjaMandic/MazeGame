package com.skid.marks.tutorial;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skid.marks.manager.TextureManager;


public class Level {
	
	private Sprite sprite;
	
	
	private float distanceSinceLastPoint = 0.0f;
	private float distanceBetweenPoints; //half screen
	private int previousPoint = 0;
	private float tunnelWidth;
	
	private float points[];
	private Row rows[];
	private Color[] colors;
	private int lowestRow;
	private float levelSpeed;
	private float rowHeight;
	private int nrOfRows;
	int h;
	int w;
	
	public Level()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		tunnelWidth = w*0.8f;
		nrOfRows = 1+h/20;
		rowHeight = h / (nrOfRows-1);
		distanceBetweenPoints = h/4;
		levelSpeed = h * 0.75f;
		
		// slumpa lite färger
		Random rand = new Random();
		colors = new Color[nrOfRows];
		float r = 0, b = 0, g = 0;
		for(int i = 0; i < nrOfRows; i++) {
			r = rand.nextFloat();
			g = rand.nextFloat();
			b = rand.nextFloat();
			colors[i] = new Color(r, g, b, 1.0f);
		}
		
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
			rows[i] = new Row(w/2, tunnelWidth, (i-1)*rowHeight);
		}
		lowestRow = nrOfRows-1;
		
		
		//sprite = new Sprite(region);
		sprite = TextureManager.getSprite("data/gfx/bar.png");

		//System.out.println(""+w+" "+h);
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
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowHeight);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nrOfRows-1;
		}
		
	}
	
	public void draw(SpriteBatch batch)
	{
		for(int i = 0; i < nrOfRows; i++)
		{
			sprite.setColor(colors[i]);
			sprite.setBounds(0, rows[i].Y, rows[i].leftWidth, rowHeight);
			sprite.draw(batch);
			sprite.setBounds(w - rows[i].rightWidth, rows[i].Y, rows[i].rightWidth, rowHeight);
			sprite.draw(batch);
			
		}
	}
}
