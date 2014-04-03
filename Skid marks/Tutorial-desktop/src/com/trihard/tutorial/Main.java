package com.trihard.tutorial;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.trihard.game.TutorialGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Tutorial";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		
		cfg.resizable = false;
		
		new LwjglApplication(new TutorialGame(), cfg);
	}
}
