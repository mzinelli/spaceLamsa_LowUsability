package com.mpu.spinv.engine.model;

import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

/**
 * Sprite.java
 * 
 * @author Brendon Pagano
 * @date 2017-08-08
 */
public class Sprite {

	/**
	 * The sprite image do be drawn.
	 */
	private BufferedImage sprite;

	/**
	 * Width and height information about the sprite image.
	 */
	private int width, height;

	/**
	 * Sprite's class constructor.
	 * 
	 * @param sprite
	 *            An image of the sprite to be drawn on screen.
	 */
	public Sprite(BufferedImage sprite) {
		setSprite(sprite);
	}

	/**
	 * Sprite's class constructor with resize.
	 * 
	 * @param sprite
	 *            An image of the sprite to be drawn on screen.
	 * @param width
	 *            The width of the sprite.
	 * @param height
	 *            The height of the sprite.
	 */
	public Sprite(BufferedImage sprite, int width, int height) {
		this.sprite = sprite;
		resizeSprite(width, height);
		setSprite(this.sprite);
	}

	/**
	 * Resizes the sprite to a given width and height.
	 * 
	 * @param width
	 *            width that the sprite must be resized to.
	 * @param height
	 *            height that the sprite must be resized to.
	 */
	public void resizeSprite(int width, int height) {
		BufferedImage resized = Scalr.resize(sprite, width, height);
		sprite = resized;
	}

	// Getters and Setters

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
