package com.skid.marks.tutorial;

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
		rightTot = TutorialGame.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public void Renew(float center, float tunnelWidth, float width, boolean active)
	{
		this.active = active;
		X = X + TutorialGame.screen_width + width;
		
		leftTot = center - tunnelWidth/2;
		rightTot = TutorialGame.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	public float leftTot;
	public float rightTot;
	
	public void update(float delta) {
		
		float dist = TutorialGame.screen_width * 1f/8f;
		float ratio = 0.5f *  ((TutorialGame.screen_width - X) / dist) + 0.5f;
		
		if(TutorialGame.screen_width*7f/8 < X) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(TutorialGame.screen_width*1f/8 > X) {
			leftWidth -= (TutorialGame.screen_height*1f * delta);
			rightWidth -= (TutorialGame.screen_height*1f * delta);
		}
		
	}
}