package com.mpu.spinv.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GameplayState.java
 * 
 * @author Brendon Pagano
 * @date 2017-11-09
 */
public class ControlsManager implements KeyListener {

	/**
	 * A reference to the state machine. So it can pass pressed keys.
	 */
	private StateMachine sm;
	
	public ControlsManager(StateMachine sm) {
		this.sm = sm;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		sm.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sm.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		sm.keyTyped(e);
	}

}
