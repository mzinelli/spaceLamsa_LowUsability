package com.mpu.spinv.game.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.mpu.spinv.engine.model.Event;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.utils.Constants;

public class MenuItem extends GameEntity {

	// ---------------- Constants ----------------

	private static final int WIDTH = 300;
	private static final int HEIGHT = 40;

	// -------------------------------------------

	/**
	 * The text that the MenuItem displays.
	 */
	private String content;

	/**
	 * A flag to say if the MenuItem is selected or not, if it is not selected when
	 * the user press enter, it won't execute it's event.
	 */
	private boolean selected;

	public MenuItem(int x, int y, String content, Event<Integer> event, boolean selected) {
		super(x, y, true);

		this.content = content;
		this.selected = selected;

		setWidth(WIDTH);
		setHeight(HEIGHT);

		on(new KeyTriggerEvent(KeyEvent.VK_ENTER, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED && this.selected)
				event.run(0, 0);
		}));
	}

	public MenuItem(int x, int y, String content, Event<Integer> event) {
		this(x, y, content, event, false);
	}

	@Override
	public void draw(Graphics g) {
		// Draws container
		g.setColor(selected ? Color.GRAY : Color.WHITE);
		g.fillRect(x, y, width, height);

		// Draws text
		g.setColor(Color.BLACK);
		g.drawString(content, x + (width / 2 - g.getFontMetrics().stringWidth(content) / 2),
				y + height / 2 + g.getFontMetrics().getHeight() / 4);
		
		if (Constants.SHOW_ENTITIES_BORDERS) {
			g.setColor(Color.GREEN);
			g.drawRect(x, y, width, height);
		}
	}

	// Getters and Setters

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getContent() {
		return content;
	}

}
