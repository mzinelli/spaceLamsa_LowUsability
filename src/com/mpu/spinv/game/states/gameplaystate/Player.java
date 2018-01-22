package com.mpu.spinv.game.states.gameplaystate;

import java.awt.event.KeyEvent;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.Animation;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.model.GameObject;
import com.mpu.spinv.engine.model.Sprite;
import com.mpu.spinv.engine.triggers.CollisionEvent;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.utils.Constants;

import jplay.Sound;

/**
 * Player.java
 * 
 * @author Brendon Pagano
 * @date 2017-18-08
 */
public class Player extends GameEntity {

	// ---------------- Constants ----------------

	private static final int WIDTH = 70;
	private static final int HEIGHT = 53;

	private static final int INITIAL_X = Constants.WINDOW_WIDTH / 2 - WIDTH / 2;
	// = Constants.WINDOW_WIDTH / 2 - WIDTH / 2
	// - WIDTH / 2
	private static final int INITIAL_Y = 618;

	private final int VELOCITY = 5;

	private static final boolean INITIAL_VISIBILITY = true;

	// -------------------- Sounds Player --------------------------

		private Sound soundLevel;
		private Sound soundShoot;
		private Sound soundThird;

		private boolean soundsOn = true;

		// -------------------------------------------
	
	
	/**
	 * The player sprite.
	 */
	private Sprite sprite;

	/**
	 * A reference to the score class.
	 */
	private Score score;

	/**
	 * A reference to the boss game entity.
	 */
	private Boss boss;

	public Player(Score score, Boss boss) {
		super(INITIAL_X, INITIAL_Y, INITIAL_VISIBILITY);

		this.score = score;
		this.boss = boss;

		sprite = new Sprite(StateMachine.spriteSheet.getSprite(224, 928, 99, 75));

		// Setting the player image.
		setStaticSprite(sprite);
		resizeSprite(WIDTH, HEIGHT);

		// Setting up some options
		setScreenBound(true);
		setVelocity(VELOCITY, VELOCITY);
		drawChildrenFirst(true);

		soundLevel = new Sound("C://Users//Miguel//Documents//space-Something//src//resources//song//2.wav");
		soundShoot = new Sound("C://Users//Miguel//Documents//space-Something//src//resources//song//shoot.wav");
		soundThird = new Sound("C://Users//Miguel//Documents//space-Something//src//resources//song//1.wav");

		soundLevel.play();
		soundLevel.setRepeat(true);
		soundThird.play();
		soundThird.decreaseVolume(16.0f);

		/**
		 * Setting the player movements triggers.
		 */

		// Moving up
		on(new KeyTriggerEvent(KeyEvent.VK_UP, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_PRESSED)
				moveUp(false);
			else if (t == KeyTriggerEvent.KEY_RELEASED)
				moveUp(false);
		}));

		// Moving down
		on(new KeyTriggerEvent(KeyEvent.VK_DOWN, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_PRESSED)
				moveDown(false);
			else if (t == KeyTriggerEvent.KEY_RELEASED)
				moveDown(false);
		}));

		// Moving right
		on(new KeyTriggerEvent(KeyEvent.VK_L, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_PRESSED)
				moveRight(true);
			else if (t == KeyTriggerEvent.KEY_RELEASED)
				moveRight(false);
		}));

		// Moving left
		on(new KeyTriggerEvent(KeyEvent.VK_J, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_PRESSED)
				moveLeft(true);
			else if (t == KeyTriggerEvent.KEY_RELEASED)
				moveLeft(false);
		}));

		// Shoot
		on(new KeyTriggerEvent(KeyEvent.VK_Q, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED) {
				Shot shot = new Shot(x + getWidth() / 2, y);
				addChild(shot);
				if (soundsOn == true) {
					shootSongPlay();

				} else {
					// shootSongStop();

				}
			}

		}));
		
		on(new KeyTriggerEvent(KeyEvent.VK_F1, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED) {
				soundThird.stop();
				soundLevel.stop();
				shootSongStop();
			}

		}));
		
		on(new KeyTriggerEvent(KeyEvent.VK_F2, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED && soundsOn == false) {
				soundThird.play();
				soundLevel.play();
				soundsOn = true;
			}

		}));
	}
	

	@Override
	public void update() {
		super.update();

		for (int i = getChildren().size() - 1; i >= 0; i--) {
			GameObject shot = getChild(i);

			if (shot.getY() + shot.getHeight() < 0) {
				removeChild(i);
				continue;
			} else if (shot.isDead()) {
				removeChild(i);
				continue;
			}
		}
	}

	public void shootSongPlay() {
		soundShoot = new Sound("C://Users//Miguel//Documents//space-Something//src//resources//song//shoot.wav");
		soundShoot.play();
		soundShoot.decreaseVolume(19.0f);
	}

	public void shootSongStop() {
		soundShoot.stop();
		soundsOn = false;
	}
	
	private class Shot extends GameEntity {

		// ---------------- Constants ----------------

		private static final int SHOT_VELOCITY = 9;
		private static final boolean INITIAL_VISIBILITY = true;

		private static final int SHOT_WIDTH = 9;
		private static final int SHOT_HEIGHT = 54;

		// -------------------------------------------

		/**
		 * The shot's sprite.
		 */
		private Sprite sprite;

		private Animation bossHitAnimation;

		public Shot(int x, int y) {
			super(x - SHOT_WIDTH / 2, y, INITIAL_VISIBILITY);

			// Collision with boss animation
			bossHitAnimation = new Animation(
					new Sprite[] { new Sprite(StateMachine.spriteSheet.getSprite(0, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(96, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(192, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(288, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(384, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(480, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(576, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(672, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(768, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(864, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(960, 0, 96, 96), 50, 50),
							new Sprite(StateMachine.spriteSheet.getSprite(1056, 0, 96, 96), 50, 50), },
					3, Animation.NO_LOOP, true);
			addAnimation("boss-hit", bossHitAnimation);
			setAnimation(null);
			
			sprite = new Sprite(StateMachine.spriteSheet.getSprite(858, 326, SHOT_WIDTH, SHOT_HEIGHT));
			setStaticSprite(sprite);

			setVelocityY(SHOT_VELOCITY);
			moveUp(true);

			on(new CollisionEvent("alien-group", (go, i) -> {
				if (!go.isDead()) {
					go.die();
					die();

					score.increment(Constants.ALIEN_SCORE);
				}
			}));

			on(new CollisionEvent("boss", (go, i) -> {
				if (!go.isDead() && listenCollision) {
					boss.decrementLife();
					moveUp(false);
					die(true);
				}
			}));
		}
		
		@Override
		public void update() {
			super.update();
			
			if (!listenCollision && getActiveAnimation().hasEnded())
				die();
		}
		
		private void die(boolean isBoss) {
			if (isBoss) {
				setListenCollision(false);
				setAnimation("boss-hit");
				startAnimation();
			}
		}

	}

}
