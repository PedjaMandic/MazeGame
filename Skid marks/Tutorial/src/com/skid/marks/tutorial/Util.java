package com.skid.marks.tutorial;

public class Util {
	
	public static float distance(float p1y, float p2y) {
		return Math.abs(p1y - p2y);
	}
	
	
	public static float lerp(float v1, float v2, float t) {
		return v1 * (1 - t) + (v2 * t);
	}
}
