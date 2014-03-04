package com.skid.marks.tutorial;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Tutorial";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 800;
		
		new LwjglApplication(new TutorialGame(), cfg);
	}
}
