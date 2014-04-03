package com.trihard.game;

public class Row {

	public float X;
	public float leftWidth;
	public float rightWidth;
	public boolean active;
	
	public Row(float center, float tunnelWidth, float X, boolean active)
	{
		this.active = active;
		this.X = X;
	
		leftTot = center - tunnelWidth/2;
		rightTot = TriHard.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public void Renew(float center, float tunnelWidth, float width, boolean active)
	{
		this.active = active;
		X = X + TriHard.screen_width + width;
		
		leftTot = center - tunnelWidth/2;
		rightTot = TriHard.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public float leftTot;
	public float rightTot;
	
	public void update(float delta) {
		
		float dist = TriHard.screen_width * 1f/8f;
		float ratio = 0.5f *  ((TriHard.screen_width - X) / dist) + 0.5f;
		
		if(TriHard.screen_width*7f/8 < X) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(TriHard.screen_width*1f/8 > X) {
			leftWidth -= (TriHard.screen_height*1f * delta);
			rightWidth -= (TriHard.screen_height*1f * delta);
		}
		
	}
}