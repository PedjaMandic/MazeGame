package com.skid.marks.apptype;

import com.badlogic.gdx.Application.ApplicationType;

public class AndroidApp extends BaseApp {

	public AndroidApp(ApplicationType type) {
		super(type);
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void render(float time) {
		this.update(time);
		this.draw(time);
	}
	
	private void update(float time) {
	}

	private void draw(float time) {
	}

}