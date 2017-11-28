package com.mpu.spinv.engine.model;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.mpu.spinv.engine.ControlsManager;
import com.mpu.spinv.engine.triggers.CollisionEvent;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.utils.Constants;

/**
 * GameObject.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-14
 */
public abstract class GameObject {

	/**
	 * The x and y position of the object in the screen.
	 */
	protected int x, y;

	/**
	 * The direction to update x and y axis.
	 */
	private int dx, dy;

	/**
	 * The velocity the object moves on both axis.
	 */
	private int velocityX, velocityY;

	/**
	 * The width and height of the game object.
	 */
	protected int width, height;

	/**
	 * Attribute used for checking. Mainly for drawing or not the object accordingly
	 * to it's visibility status.
	 */
	protected boolean visible;

	/**
	 * If true, the object cannot be moved out of the screen.
	 */
	protected boolean screenBound;

	/**
	 * If the object should listen to collision calls.
	 */
	protected boolean listenCollision;

	/**
	 * Declares if the object is dead or not. If this is set to true, the object
	 * will be removed to any list that it is present.
	 */
	protected boolean dead;

	/**
	 * A list of the key triggers of the object.
	 */
	protected List<KeyTriggerEvent> keyTriggers;

	/**
	 * A list mapping events to keys. So the state can compare the id of the
	 * collided objects.
	 */
	protected List<CollisionEvent> collisionEvents;

	/**
	 * The object's children, if it has any.
	 */
	protected List<GameObject> children;

	/**
	 * If this flag is set to true, the GameObject's children will be drawn first.
	 */
	private boolean _drawChildrenFirst;

	/**
	 * If this flag is set to true, the GameObject will only update it's coordinates
	 * if he's visible.
	 */
	private boolean _moveOnlyIfVisible;

	public abstract void draw(Graphics g);

	public GameObject(int x, int y, int width, int height, boolean visible) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = visible;

		// Default params
		this.keyTriggers = new ArrayList<KeyTriggerEvent>();
		this.collisionEvents = new ArrayList<CollisionEvent>();
		this.children = new ArrayList<GameObject>();
		this.dx = 0;
		this.dy = 0;
		this.velocityX = 0;
		this.velocityY = 0;
		this.screenBound = false;
		this.listenCollision = true;
		this._drawChildrenFirst = false;
		this._moveOnlyIfVisible = false;
	}

	public void update() {
		if (!_moveOnlyIfVisible || visible) {
			x += dx;
			y += dy;
	
			if (screenBound) {
				if (x < 0)
					x = 0;
				else if (x + width > Constants.WINDOW_WIDTH - 4)
					x = Constants.WINDOW_WIDTH - width - 4;
	
				if (y < 0)
					y = 0;
				else if (y + height > Constants.WINDOW_HEIGHT - 30)
					y = Constants.WINDOW_HEIGHT - height - 30;
			}
		}

		if (hasChildren()) {
			children.forEach(go -> go.update());
		}

	}

	public void die() {
		visible = false;
		dead = true;
	}

	// Movement management

	/**
	 * Makes the GameObject move up, the velocity will be determined by
	 * {@link GameObject#velocityY}
	 */
	public void moveUp(boolean should) {
		dy = should ? -velocityY : 0;
	}

	/**
	 * Makes the GameObject move down, the velocity will be determined by
	 * {@link GameObject#velocityY}
	 */
	public void moveDown(boolean should) {
		dy = should ? velocityY : 0;
	}

	/**
	 * Makes the GameObject move right, the velocity will be determined by
	 * {@link GameObject#velocityX}
	 */
	public void moveRight(boolean should) {
		dx = should ? velocityX : 0;
	}

	/**
	 * Makes the GameObject move left, the velocity will be determined by
	 * {@link GameObject#velocityX}
	 */
	public void moveLeft(boolean should) {
		dx = should ? -velocityX : 0;
	}

	// Children management

	public boolean hasChildren() {
		return children.size() > 0;
	}

	public List<GameObject> getChildren() {
		return children;
	}

	public GameObject getChild(int i) {
		if (children.size() == 0 || i < 0 || i > children.size() - 1)
			return null;
		return children.get(i);
	}

	public void addChild(GameObject obj) {
		children.add(obj);
	}

	public void removeChild(int i) {
		children.remove(i);
	}

	// Collision Manager methods

	public List<CollisionEvent> getCollisionEvents() {
		List<CollisionEvent> colEvents = new ArrayList<CollisionEvent>();

		collisionEvents.forEach(c -> colEvents.add(c));

		children.forEach(ch -> {
			if (ch.hasCollisionEvents())
				ch.getCollisionEvents().forEach(c -> colEvents.add(c));
		});

		return colEvents;
	}

	public boolean hasCollisionEvents() {
		if (collisionEvents.size() > 0)
			return true;

		if (hasChildren())
			for (int i = 0; i < children.size(); i++)
				if (children.get(i).hasCollisionEvents())
					return true;

		return false;
	}

	/**
	 * Adds a new {@link CollisionEvent} to the Object's collision events list.
	 * 
	 * @param collisionEvent
	 *            The new collision event to add.
	 */
	public void on(CollisionEvent collisionEvent) {
		collisionEvent.setOwner(this);
		collisionEvents.add(collisionEvent);
	}

	/**
	 * The {@link State} will run this method once the object has collided with
	 * another object that is detecting collision.
	 * 
	 * @param key
	 *            the key identifier of the object collided with.
	 * @param obj
	 *            a reference to the object collided with.
	 */
	public void collided(String key, GameObject obj) {
		collisionEvents.forEach(c -> c.collided(key, obj));
	}

	// Controls Manager methods

	/**
	 * Binds a key press, key released or key typed to an event.
	 * 
	 * Then, when this key is activated by the {@link ControlsManager} class, the
	 * event is fired.
	 * 
	 * @param trigger
	 *            The key and the resulting event to be fired.
	 */
	public void on(KeyTriggerEvent trigger) {
		keyTriggers.add(trigger);
	}

	public boolean hasKeyTriggers() {
		return keyTriggers.size() > 0;
	}

	public void keyPressed(KeyEvent e) {
		if (keyTriggers.size() > 0)
			keyTriggers.forEach(kt -> {
				kt.update(e, KeyTriggerEvent.KEY_PRESSED);
			});
	}

	public void keyReleased(KeyEvent e) {
		if (keyTriggers.size() > 0)
			keyTriggers.forEach(kt -> {
				kt.update(e, KeyTriggerEvent.KEY_RELEASED);
			});
	}

	public void keyTyped(KeyEvent e) {
		if (keyTriggers.size() > 0)
			keyTriggers.forEach(kt -> {
				kt.update(e, KeyTriggerEvent.KEY_TYPED);
			});
	}

	// Getters and Setters

	public abstract boolean isGroup();
	
	public void moveOnlyIfVisible(boolean opt) {
		_moveOnlyIfVisible = opt;
	}
	
	public boolean moveOnlyIfVisible() {
		return _moveOnlyIfVisible;
	}

	public void drawChildrenFirst(boolean draw) {
		_drawChildrenFirst = draw;
	}

	public boolean drawChildrenFirst() {
		return _drawChildrenFirst;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isScreenBound() {
		return screenBound;
	}

	public void setScreenBound(boolean screenBound) {
		this.screenBound = screenBound;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isDead() {
		return dead;
	}

	public boolean isListenCollision() {
		return listenCollision;
	}

	public void setListenCollision(boolean listenCollision) {
		this.listenCollision = listenCollision;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public void setVelocity(int velocityX, int velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}

}
