package com.mpu.spinv.game.states.gamemenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.utils.Constants;

/**
 * Background.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-30
 */
public class Background extends GameEntity {

	// ---------------- Constants ----------------

	private static final int INITIAL_X = 0;
	private static final int INITIAL_Y = 0;

	private static final boolean INITIAL_VISIBILITY = true;

	// -------------------------------------------
	
	private Color color;
	
	public Background() {
		super(INITIAL_X, INITIAL_Y, INITIAL_VISIBILITY);
		
		color = new Color(0, 0, 0, 210);
		
		on(new KeyTriggerEvent(KeyEvent.VK_ESCAPE, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED)
				StateMachine.setActiveState("gameplay");
		}));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	}

}
