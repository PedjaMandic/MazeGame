package com.skid.marks.tutorial;

import com.badlogic.gdx.Gdx;

public class Row {

	public float Y;
	public float leftWidth;
	public float rightWidth;
	public int sprite;
	public boolean active;
	
	public Row(float center, float holeWidth, float Y, int sprite, boolean active)
	{
		this.active = active;
		this.Y = Y;
		this.sprite = sprite;
//		leftWidth = center - holeWidth/2;
//		rightWidth = Gdx.graphics.getWidth() - (center + holeWidth/2);
		
		leftTot = center - holeWidth/2;
		rightTot = Gdx.graphics.getHeight() - (center + holeWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public void Renew(float center, float holeWidth, float height, int sprite, boolean active)
	{
		this.active = active;
		Y = Y + Gdx.graphics.getWidth() + height;
		this.sprite = sprite;
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
		float ratio = 0.5f *  ((Gdx.graphics.getWidth() - Y) / dist) + 0.5f;
		
		if(py < Y) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(Gdx.graphics.getWidth()*3f/8 > Y) {
			leftWidth -= (300 * delta);
			rightWidth -= (300 * delta);
		}
		
		//leftWidth = leftTot;
		//rightWidth = rightTot;
	}
}