package com.mpu.spinv.game.states.gameplaystate;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.model.Group;
import com.mpu.spinv.engine.model.Sprite;

/**
 * LifeBar.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-14
 */
public class LifeBar extends Group {

	// ---------------- Constants ----------------

	private static final int MAX_LIFES = 3;

	private static final int X = 10;
	private static final int Y = 10;

	// -------------------------------------------

	/**
	 * How many lives the player still has.
	 */
	private int life;

	public LifeBar() {
		super(X, Y, Group.LAYOUT_HORIZONTAL);

		life = MAX_LIFES;
		
		setSpacingHorizontal(5);
		
		initializeLifeIcons();
		
		
	}

	/**
	 * Initializes the {@link Group} of {@link LifeBar.LifeIcon}.
	 */
	private void initializeLifeIcons() {
		for (int i = 0; i < MAX_LIFES; i++) {
		
			add(new LifeIcon());
		}
	}
	
	/**
	 * Decreases the player life by one.
	 */
	public void decreaseLife() {
		if (life > 0) {
			life--;
			getGameEntities().remove(getGameEntities().size()-1);
		}
		if(life <=0){
			System.exit(0);
		}
	}

	private class LifeIcon extends GameEntity {

		// ---------------- Constants ----------------

		private static final boolean INITIAL_VISIBILITY = true;

		// -------------------------------------------
		
		private Sprite sprite;

		public LifeIcon() {
			// Pass 0 and 0 to x and y params because the {@link Group} will reset it anyway
			super(0, 0, INITIAL_VISIBILITY);
			
			sprite = new Sprite(StateMachine.spriteSheet.getSprite(775, 397, 32, 25));
			
			//setStaticSprite(sprite);
			setScreenBound(true);

		}

	}

	// Getters and Setters

	public int getLife() {
		return life;
	}

}
