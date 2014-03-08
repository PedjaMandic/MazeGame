package com.skid.marks.tutorial;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Logger {
	
	static {
		init();
	}
	
	class Msg {
		
		public String text;
		public float x;
		public float y;
		public Color color;
		public float fontSize;
		
		public Msg(String text, float x, float y, Color color, float fontSize) {
			this.text = text;
			this.x = x;
			this.y = y;
			this.color = color;
			this.fontSize = fontSize;
		}
	}
	
	private static BitmapFont _font;
	private static final Color DEFAULT_COLOR = Color.WHITE;
	private static final float DEFAULT_FONT_SIZE = 1.0f;
	
	private static ArrayList<Msg> _logs = new ArrayList<Msg>();
	
	private static void init() {
		_font = new BitmapFont(true);
		_logs = new ArrayList<Msg>();
	}
	
	public static void screen(String text, float x, float y, Color color, float fontSize) {
		Logger.Msg msg = new Logger().new Msg(text, x, y, color, fontSize);
		_logs.add(msg);
	}
	public static void screen(String text, float x, float y, Color color) {
		screen(text, x, y, color, DEFAULT_FONT_SIZE);
	}
	public static void screen(String text, float x, float y) {
		screen(text, x, y, DEFAULT_COLOR, DEFAULT_FONT_SIZE);
	}
//	public static void screen(String text) {
//	}
	
	public static void render(SpriteBatch batch) {
		for (Msg msg : _logs) {
			_font.setColor(msg.color);
			_font.setScale(msg.fontSize);
			_font.draw(batch, msg.text, msg.x, msg.y);
		}
		_logs.clear();
	}
	
}
