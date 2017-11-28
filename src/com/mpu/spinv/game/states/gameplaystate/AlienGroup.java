package com.mpu.spinv.game.states.gameplaystate;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.Animation;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.model.Group;
import com.mpu.spinv.engine.model.Sprite;
import com.mpu.spinv.engine.triggers.CollisionEvent;
import com.mpu.spinv.utils.Constants;

/**
 * AlienGroup.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-17
 */
public class AlienGroup extends Group {

	// ---------------- Constants ----------------

	private static final int X = 50;
	private static final int Y = 50;

	private static final int TOTAL_ALIENS = 40;

	private static final int VELOCITY = 1;

	// -------------------------------------------

	private LifeBar playerLifebar;
	private AlienShot shot;

	private int ticks = 0;

	public AlienGroup(LifeBar playerLifebar) {
		super(X, Y, Group.LAYOUT_GRID);

		this.playerLifebar = playerLifebar;
		this.shot = new AlienShot(0, 0);

		setGridSize(10);
		setSpacing(10, 10);

		for (int i = 0; i < TOTAL_ALIENS; i++)
			add(new Alien());

		setVelocity(VELOCITY, VELOCITY);
		moveRight(true);
	}

	@Override
	public void update() {
		boolean _entityDestroyed = false;

		List<GameEntity> als = getGameEntities();
		for (int i = 0; i < als.size(); i++)
			if (als.get(i).isDead()) {
				_entityDestroyed = true;
				break;
			}

		super.update();

		if (_entityDestroyed)
			resetCoordinates();

		ticks++;

		if (ticks >= 30 && getGameEntities().size() > 0) {
			ticks = 0;

			Random random = new Random();
			List<GameEntity> aliens = getAbleToShootAliens();

			if (aliens.size() > 0 && !shot.isVisible()) {
				shot.setListenCollision(true);

				Alien alien = (Alien) aliens.get(random.nextInt(aliens.size()));
				shot.setX(alien.getX() + alien.getWidth() / 2 - shot.getWidth() / 2);
				shot.setY(alien.getY() + 14);
				shot.setVisible(true);
			}
		}

		if (x + width > Constants.WINDOW_WIDTH - 4 - 10)
			moveLeft(true);
		else if (x < 10)
			moveRight(true);

		shot.update();

		if (shot.getY() > Constants.WINDOW_HEIGHT - 30) {
			shot.setVisible(false);
		}
	}

	@Override
	public void draw(Graphics g) {
		shot.draw(g);
		super.draw(g);
	}

	@Override
	public List<CollisionEvent> getCollisionEvents() {
		List<CollisionEvent> colEvents = super.getCollisionEvents();
		colEvents.add(shot.getCollisionEvents().get(0));

		return colEvents;
	}

	@Override
	public boolean hasCollisionEvents() {
		return shot.isListenCollision();
	}

	private List<GameEntity> getAbleToShootAliens() {
		List<GameEntity> entitiesAble = new ArrayList<GameEntity>();
		List<GameEntity> als = getGameEntities();
		int counter = 0;

		for (int i = als.size() - 1; i >= 0; i--) {
			if (als.get(i).isListenCollision()) { // If the alien is not playing death animation.
				entitiesAble.add(als.get(i));
				counter++;
			}

			if (counter == getGridCols())
				break;
		}

		return entitiesAble;
	}

	private class Alien extends GameEntity {

		// ---------------- Constants ----------------

		private static final int WIDTH = 60;
		private static final int HEIGHT = 54;

		private static final boolean INITIAL_VISIBILITY = true;

		// -------------------------------------------

		/**
		 * The alien's sprite.
		 */
		private Sprite sprite;
		private Animation destroyAnimation;

		public Alien() {
			// Pass 0 and 0 to x and y params because the {@link Group} will reset it anyway
			super(0, 0, INITIAL_VISIBILITY);

			destroyAnimation = new Animation(
					new Sprite[] { new Sprite(StateMachine.spriteSheet.getSprite(0, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(96, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(192, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(288, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(384, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(480, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(576, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(672, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(768, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(864, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(960, 0, 96, 96)),
							new Sprite(StateMachine.spriteSheet.getSprite(1056, 0, 96, 96)), },
					3, Animation.NO_LOOP, true);
			addAnimation("death", destroyAnimation);
			setAnimation(null);

			sprite = new Sprite(StateMachine.spriteSheet.getSprite(423, 825, 93, 83));
			setStaticSprite(sprite);
			resizeSprite(WIDTH, HEIGHT);
		}

		@Override
		public void update() {
			super.update();

			if (!listenCollision && getActiveAnimation().hasEnded()) {
				super.die();
			}
		}

		@Override
		public void die() {
			setListenCollision(false);
			setAnimation("death");
			startAnimation();
		}

	}

	private class AlienShot extends GameEntity {

		// ---------------- Constants ----------------

		private static final int SHOT_VELOCITY = 9;
		private static final boolean INITIAL_VISIBILITY = false;

		private static final int SHOT_WIDTH = 9;
		private static final int SHOT_HEIGHT = 54;

		// -------------------------------------------

		/**
		 * The shot's sprite.
		 */
		private Sprite sprite;

		private Animation hitAnimation;

		public AlienShot(int x, int y) {
			super(x, y, INITIAL_VISIBILITY);

			hitAnimation = new Animation(
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
			addAnimation("hit", hitAnimation);
			setAnimation(null);

			sprite = new Sprite(StateMachine.spriteSheet.getSprite(856, 517, SHOT_WIDTH, SHOT_HEIGHT));
			setStaticSprite(sprite);

			setVelocityY(SHOT_VELOCITY);
			moveOnlyIfVisible(true);

			moveDown(true);

			on(new CollisionEvent("player", (go, i) -> {
				setY(getY() + getHeight() - 20);
				
				setListenCollision(false);
				setAnimation("hit");
				startAnimation();
				
				setX(getX() - getActiveAnimation().getSprite().getWidth() / 2 + 5);
				
				playerLifebar.decreaseLife();

				moveDown(false);
			}));
		}

		@Override
		public void update() {
			super.update();

			if (!isListenCollision() && isVisible() && getActiveAnimation().hasEnded()) {
				getActiveAnimation().reset();
				setAnimation(null);
				setStaticSprite(sprite);

				setVisible(false);
				moveDown(true);
			}
		}

	}

}
