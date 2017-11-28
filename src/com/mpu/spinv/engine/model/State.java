package com.mpu.spinv.engine.model;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import com.mpu.spinv.engine.CollisionHandler;
import com.mpu.spinv.engine.triggers.CollisionEvent;
import com.mpu.spinv.utils.AdvList;

public abstract class State {

	private final AdvList<GameObject> gameObjects;

	private String spriteSheetUrl;

	private boolean saveResources;

	public State(boolean saveResources) {
		gameObjects = new AdvList<GameObject>();
		this.saveResources = saveResources;
	}

	/**
	 * Main update method.
	 */
	public void update() {
		gameObjects.forEach((k, v) -> v.update());
		collisionCheckup();
	}

	/**
	 * Draws every game object, if visible, on screen.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		gameObjects.forEach((k, v) -> {
			v.draw(g);
		});
	}

	/**
	 * Iterates through all the state elements and check the collisions.
	 */
	protected void collisionCheckup() {
		gameObjects.forEach((k, v) -> {
			if (v.hasCollisionEvents()) {
				List<CollisionEvent> collisionEvents = v.getCollisionEvents();
				
				collisionEvents.forEach(ce -> {
					GameObject owner = ce.getOwner();
					GameObject target = gameObjects.get(ce.getCollisionTarget());
					
					if (!target.isVisible() || !target.isListenCollision())
						return;
					
					
					if (CollisionHandler.hasCollided(owner, target)) {
						if (target.isGroup()) {
							Group gTarget = (Group) target;
							List<GameObject> collidedObjs = gTarget.returnCollided(owner);
							collidedObjs.forEach(co -> {
								owner.collided(ce.getCollisionTarget(), co);
							});
						} else
							owner.collided(ce.getCollisionTarget(), gameObjects.get(ce.getCollisionTarget()));
					}
				});
			}
		});
	}

	/**
	 * This method should initiate all the GameObject's from the State. This should
	 * be invoked in the State constructor and it will be called by the StateMachine
	 * every time the state with {@link State#saveResources} is set to false.
	 */
	public abstract void init();

	/**
	 * This method will be called just before playing the actual state on screen. It
	 * will load all resources needed by the state.
	 * 
	 * Must be extended in each state in order to function correctly.
	 */
	public abstract void loadResources();

	/**
	 * Kills the resources loaded by {@link State#loadResources()}, if allowed.
	 */
	public void killResources() {
		if (!saveResources)
			gameObjects.clear();
	}

	// Controls Handling Methods.

	public void keyPressed(KeyEvent e) {
		gameObjects.forEach((k, v) -> {
			if (v.hasKeyTriggers())
				v.keyPressed(e);
		});
	}

	public void keyReleased(KeyEvent e) {
		gameObjects.forEach((k, v) -> {
			if (v.hasKeyTriggers())
				v.keyReleased(e);
		});
	}

	public void keyTyped(KeyEvent e) {
		gameObjects.forEach((k, v) -> {
			if (v.hasKeyTriggers())
				v.keyTyped(e);
		});
	}

	/**
	 * Adds a GameObject into the {@link State#gameEntities}.
	 * 
	 * @param obj
	 *            A {@link GameEntity} to be added.
	 */
	protected void addResource(String key, GameObject obj) {
		gameObjects.add(key, obj);
	}

	/**
	 * Removes a {@link GameEntity} from the state resources list.
	 * 
	 * @param obj
	 *            the reference of the object to remove.
	 */
	protected void removeResource(String key) {
		gameObjects.remove(key);
	}

	// Getters and Setters

	public boolean isSaveResources() {
		return saveResources;
	}

	public void setSaveResources(boolean saveResources) {
		this.saveResources = saveResources;
	}

	public String getSpriteSheetUrl() {
		return spriteSheetUrl;
	}

	public void setSpriteSheetUrl(String spriteSheetUrl) {
		this.spriteSheetUrl = spriteSheetUrl;
	}

}
