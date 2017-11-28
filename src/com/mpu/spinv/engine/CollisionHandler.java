package com.mpu.spinv.engine;

import com.mpu.spinv.engine.model.GameObject;

/**
 * CollisionHandler.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-20
 */
public class CollisionHandler {

	public static boolean hasCollided(GameObject obj0, GameObject obj1) {
		return CollisionHandler.hasCollided(obj0.getX(), obj0.getY(), obj0.getX() + obj0.getWidth(), obj0.getY() + obj0.getHeight(),
				obj1.getX(), obj1.getY(), obj1.getX() + obj1.getWidth(), obj1.getY() + obj1.getHeight());
	}
	
	public static boolean hasCollided(int x0, int y0, int xw0, int yh0, int x1, int y1, int xw1, int yh1) {
		if (x0 < xw1 &&
				xw0 > x1 &&
				yh0 > y1 &&
				y0 < yh1)
			return true;
		
		return false;
	}

}
