package com.skid.marks.tutorial;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Debug {
	
	static {
		init();
	}
	
	private static BitmapFont _font;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	private static ArrayList<String> _text = new ArrayList<String>();
	private static ArrayList<Color> _color = new ArrayList<Color>();
	
	private static void init() {
		_font = new BitmapFont(true);
		_text = new ArrayList<String>();
		_color = new ArrayList<Color>();
	}
	
	public static void log(String text, Color color) {
		_text.add(text);
		_color.add(color);
	}
	public static void log(String text) {
		log(text, DEFAULT_COLOR);
	}
	
	public static void render(SpriteBatch batch) {
		float oy = 50;
		for(int i = 0; i < _text.size(); i++) {
			Color color = _color.get(i);
			String text = _text.get(i);
			_font.setColor(color);
			_font.draw(batch, text, 20, oy);
			oy += 30;
		}
		
		_text.clear();
		_color.clear();
	}
	
}
