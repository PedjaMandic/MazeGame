package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;

public class Row {

	public float Y;
	public float leftWidth;
	public float rightWidth;
	public int sprite;
	
	public Row(float center, float holeWidth, float Y, int sprite)
	{
		this.Y = Y;
		leftWidth = center - holeWidth/2;
		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		this.sprite = sprite;
	}
	
	public void Renew(float center, float holeWidth, float height, int sprite)
	{
		Y = -height;
		leftWidth = center - holeWidth/2;
		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		this.sprite = sprite;
	}
}