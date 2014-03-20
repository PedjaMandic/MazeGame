package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;

public class Row {

	public float X;
	public float leftWidth;
	public float rightWidth;
	public boolean active;
	
	public Row(float center, float holeWidth, float Y, boolean active)
	{
		this.active = active;
		this.X = Y;
//		leftWidth = center - holeWidth/2;
//		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		
		leftTot = center - holeWidth/2;
		rightTot = Gdx.graphics.getHeight() - (center + holeWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public void Renew(float center, float holeWidth, float height, boolean active)
	{
		this.active = active;
		X = X + Gdx.graphics.getWidth() + height;
//		leftWidth = center - holeWidth/2;
//		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		
		leftTot = center - holeWidth/2;
		rightTot = Gdx.graphics.getHeight() - (center + holeWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public float leftTot;
	public float rightTot;
	
	public void update(Player p, float delta) {
		float py = p.getPosition().x;
		
		float dist = Gdx.graphics.getWidth() * 1f/8f;
		float ratio = 0.5f *  ((Gdx.graphics.getWidth() - X) / dist) + 0.5f;
		
		if(py < X) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(Gdx.graphics.getWidth()*3f/8 > X) {
			leftWidth -= (Gdx.graphics.getHeight()*1f * delta);
			rightWidth -= (Gdx.graphics.getHeight()*1f * delta);
		}
		
		//leftWidth = leftTot;
		//rightWidth = rightTot;
	}
}