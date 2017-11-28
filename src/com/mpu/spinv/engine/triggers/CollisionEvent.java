package com.mpu.spinv.engine.triggers;

import com.mpu.spinv.engine.model.Event;
import com.mpu.spinv.engine.model.GameObject;

/**
 * CollisionEvent.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-21
 */
public class CollisionEvent {
	
	/**
	 * The owner of the CollisionEvent
	 */
	private GameObject owner;

	/**
	 * The key identifier of the object to collide with, in order to execute the
	 * event.
	 */
	private String collisionTarget;

	/**
	 * The event to be run once the conditions of the triggers have been met.
	 */
	private Event<GameObject> event;

	public CollisionEvent(String collisionTarget, Event<GameObject> event) {
		this.collisionTarget = collisionTarget;
		this.event = event;
	}

	/**
	 * The {@link State} will run this method once the object has collided with
	 * another object that is detecting collision.
	 * 
	 * @param key
	 *            the key identifier of the object collided with.
	 * @param obj
	 *            a reference to the object which the trigger owner collided with.
	 */
	public void collided(String key, GameObject obj) {
		if (key.equals(collisionTarget))
			event.run(obj, 0);
	}

	// Getters and Setters

	public String getCollisionTarget() {
		return collisionTarget;
	}

	public void setCollisionTarget(String collisionTarget) {
		this.collisionTarget = collisionTarget;
	}

	public Event<GameObject> getEvent() {
		return event;
	}

	public void setEvent(Event<GameObject> event) {
		this.event = event;
	}

	public GameObject getOwner() {
		return owner;
	}

	public void setOwner(GameObject owner) {
		this.owner = owner;
	}

}
