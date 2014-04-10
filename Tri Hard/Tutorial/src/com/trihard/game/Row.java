package com.trihard.game;

public class Row {

	public float X; //it's X coordinate
	public float leftWidth; //the current height of the top row (drawn and collided against)
	public float rightWidth; //the current height of the bottom row (drawn and collided against)

	public float leftTot; //the maximum height of the top row
	public float rightTot; //the maximum height of the bottom row
	
	public boolean active; //if the row is collidable/drawn
	
	//constructor
	public Row(float center, float tunnelWidth, float X, boolean active)
	{
		this.active = active;
		this.X = X;
	
		leftTot = center - tunnelWidth/2;
		rightTot = TriHard.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	//when a row needs new information, this is called (usually when lowestRow needs to be moved)
	public void Renew(float center, float tunnelWidth, float width, boolean active)
	{
		this.active = active;
		X = X + TriHard.screen_width + width;
		
		leftTot = center - tunnelWidth/2;
		rightTot = TriHard.screen_height - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	//update function (all it does is create the "entering/leaving" effect on the rows. Actual movement is handled in Level)
	public void update(float delta) {
		
		float dist = TriHard.screen_width * 1f/8f; //the distance for when a row is supposed to be fully entered in the level
		float ratio = 0.5f *  ((TriHard.screen_width - X) / dist) + 0.5f; //starting amount for the incoming rows
		
		if(TriHard.screen_width-dist < X) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(dist > X) {
			leftWidth -= (TriHard.screen_height*1f * delta);
			rightWidth -= (TriHard.screen_height*1f * delta);
		}
		
	}
}