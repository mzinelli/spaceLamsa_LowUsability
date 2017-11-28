package com.mpu.spinv.engine.model;

/**
 * Event.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-20
 */
public interface Event<T> {
	
	/**
	 * The method to be run once the event has been called.
	 */
	public void run(T t, int info);

}
