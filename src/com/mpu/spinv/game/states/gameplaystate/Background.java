package com.mpu.spinv.game.states.gameplaystate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.utils.Constants;

public class Background extends GameEntity {
	
	// ---------------- Constants ----------------
	
	private static final int INITIAL_X = 0;
	private static final int INITIAL_Y = 0;

	private static final boolean INITIAL_VISIBILITY = true;
	
	private static final String IMAGE_NAME = "background_purple.png";
	
	// -------------------------------------------
	
	private TexturePaint pattern;
	private BufferedImage sprite;
	
	public Background() {
		super(INITIAL_X, INITIAL_Y, INITIAL_VISIBILITY);

		try {
			sprite = ImageIO.read(Background.class.getResource("/resources/img/" + IMAGE_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pattern = new TexturePaint(sprite, new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight()));
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(pattern);
		g2d.fill(new Rectangle(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
	}

}
