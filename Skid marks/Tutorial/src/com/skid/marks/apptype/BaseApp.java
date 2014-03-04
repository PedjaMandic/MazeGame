package com.skid.marks.apptype;

import com.badlogic.gdx.Application.ApplicationType;

public abstract class BaseApp {
	
	protected ApplicationType applicationType;
	
	public BaseApp(ApplicationType type) {
		this.applicationType = type;
	}
	
	public void dispose() {}

	public void init() {}
	
	public void render(float time) {}
	
	public ApplicationType getType() {
		return this.applicationType;
	}

}