package com.mpu.spinv.engine.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.mpu.spinv.utils.Constants;

/**
 * GameText.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-29
 */
public class GameText extends GameObject {

	/**
	 * The text content of the Object.
	 */
	protected String text;

	/**
	 * The text color to be painted. Default is {@link Color#BLACK}.
	 */
	private Color color;

	/**
	 * A flag to determine if the content has changed or not. If it has, when the
	 * {@link GameText#draw(Graphics)} is next called, the width and height of the
	 * GameObject will be altered.
	 */
	private boolean _changed;
	
	/**
	 * If the text has more than one line, each line will be stored in this array for painting.
	 */
	private String[] lines;
	
	public GameText(int x, int y, boolean visible, String text) {
		super(x, y, 0, 0, visible);
		this.text = text;

		// Default params
		_changed = true;
		color = Color.BLACK;
	}

	public GameText(int x, int y, boolean visible) {
		this(x, y, visible, "");
	}
	
	public GameText(int x, int y, boolean visible, String text, Color color) {
		this(x, y, visible, text);
		this.color = color;
	}

	@Override
	public boolean isGroup() {
		return false;
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics g) {
		FontMetrics fMet = g.getFontMetrics();
		int fontHeight = fMet.getHeight();
		
		if (_changed) {
			lines = text.split("\n");
			
			if (lines.length > 1) {
				int hWidth = 0;
				for (int i = 0; i < lines.length; i++)
					if (fMet.stringWidth(lines[i]) > hWidth)
						hWidth = fMet.stringWidth(lines[i]);
				width = hWidth;
			} else {
				width = fMet.stringWidth(text);
			}
			
			height = fMet.getHeight() * lines.length;
			_changed = false;
		}
		
		g.setColor(color);
		for (int i = 1; i <= lines.length; i++)
			g.drawString(lines[i-1], x, y + fontHeight*i);
		
		if (Constants.SHOW_ENTITIES_BORDERS) {
			g.setColor(Color.GREEN);
			g.drawRect(x, y, width, height);
		}
	}

	// Getters and Setters

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		_changed = true;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
