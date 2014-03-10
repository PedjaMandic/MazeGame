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
		this.sprite = sprite;
//		leftWidth = center - holeWidth/2;
//		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		
		leftTot = leftWidth = center - holeWidth/2;
		rightTot = rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public void Renew(float center, float holeWidth, float height, int sprite)
	{
		Y = Y - Gdx.graphics.getHeight()-height;
		this.sprite = sprite;
//		leftWidth = center - holeWidth/2;
//		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		
		leftTot = leftWidth = center - holeWidth/2;
		rightTot = rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public float leftTot;
	public float rightTot;
	
	public void update(Player p, float delta) {
		float py = p.getPosition().y;
		
		float dist = Gdx.graphics.getHeight() * 1f/8f;
		float ratio = 0.5f *  (Y / dist) + 0.5f;
		
		if(py > Y) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else {
			// Some funky shit
		}
	}
}