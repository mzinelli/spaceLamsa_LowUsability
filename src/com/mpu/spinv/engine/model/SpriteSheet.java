package com.mpu.spinv.engine.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * SpriteSheet.java
 * 
 * @author Brendon Pagano
 * @date 2017-08-08
 */
public class SpriteSheet {

	private BufferedImage spriteSheet;
	
	/**
	 * Null constructor.
	 */
	public SpriteSheet() {
		spriteSheet = null;
	}

	/**
	 * The SpriteSheet's constructor.
	 * 
	 * @param path
	 *            Path to the sprite sheet image file.
	 */
	public SpriteSheet(URL path) {
		spriteSheet = null;
		setSpriteSheetImage(path);
	}

	/**
	 * Sets a new sprite sheet image to the object.
	 * 
	 * @param path
	 *            Path to the new sprite sheet image file.
	 */
	public void setSpriteSheetImage(URL path) {
		try {
			spriteSheet = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves a sprite from the sprite sheet.
	 * 
	 * @param x
	 *            How many pixels, starting from the left, should it start
	 *            cropping the sprite.
	 * @param y
	 *            How many pixels, starting from the top, should it start
	 *            cropping the sprite.
	 * @param width
	 *            Width of the sprite.
	 * @param height
	 *            Height of the sprite.
	 * @return A Sprite object to be used for drawing.
	 */
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
	}

}
