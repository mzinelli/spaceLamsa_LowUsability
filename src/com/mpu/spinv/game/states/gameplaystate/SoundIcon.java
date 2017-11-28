package com.mpu.spinv.game.states.gameplaystate;

import java.awt.event.KeyEvent;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.model.Sprite;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.utils.Constants;

/**
 * SoundIcon.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-30
 */
public class SoundIcon extends GameEntity {

	// ---------------- Constants ----------------
	
	private static final int WIDTH = 46;
	private static final int HEIGHT = 46;
	
	private static final int X = 10;
	private static final int Y = Constants.WINDOW_HEIGHT - 30 - HEIGHT - 10;
	
	private static final boolean INITIAL_VISIBILITY = true;

	// -------------------------------------------
	
	private boolean mute = false;
	
	private Sprite onSprite;
	private Sprite offSprite;

	public SoundIcon() {
		super(X, Y, INITIAL_VISIBILITY);
		
		onSprite = new Sprite(StateMachine.spriteSheet.getSprite(597, 1057, WIDTH, HEIGHT));
		offSprite = new Sprite(StateMachine.spriteSheet.getSprite(581, 757, WIDTH, HEIGHT));
		
		setStaticSprite(onSprite);
		
		on(new KeyTriggerEvent(KeyEvent.VK_F1, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED) {
				mute = !mute;
				setStaticSprite(offSprite);
			}
		}));
		
		on(new KeyTriggerEvent(KeyEvent.VK_F2, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED) {
				mute = !mute;
				setStaticSprite(onSprite);
			}
		}));
	}

}
