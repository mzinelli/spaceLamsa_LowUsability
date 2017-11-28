package com.mpu.spinv.engine.model;

import java.awt.image.BufferedImage;

/**
 * Animation.java
 * 
 * @author Brendon Pagano
 * @date 2017-08-09
 */
public class Animation {

	// ---------------- Constants ----------------

	/**
	 * The animation should start from the beginning when it ends.
	 */
	public static final int NORMAL_LOOP = 0;

	/**
	 * The animation should reverse it's play direction once it reaches an
	 * endpoint.
	 */
	public static final int ALTERNATIVE_LOOP = 1;

	/**
	 * The animation should not loop.
	 */
	public static final int NO_LOOP = 2;

	// -------------------------------------------

	/**
	 * A vector containing all the sprites for the animation.
	 */
	private Sprite[] frames;

	/**
	 * The amount of ticks required to update the animation frame.
	 */
	private int updateRate;

	/**
	 * Ticks passed since the last frame update. Used for comparison between
	 * this and {@link Animation#updateRate} to update the animation frame.
	 */
	private int ticksPassed;

	/**
	 * The current animation frame (aka the index used to obtain the image from
	 * {@link Animation#frames vector}
	 */
	private int currentFrame;

	/**
	 * Used to update the {@link Animation#currentFrame}. Can be positive or
	 * negative. If direction is negative, the animation will play reversed.
	 */
	private int direction;

	/**
	 * A flag to determine the type of loop the animation will be using.
	 */
	private int loopType;

	/**
	 * Flag to mark if the animation has stopped/finished.
	 */
	private boolean stopped;

	/**
	 * Animation's constructor.
	 * 
	 * @param frames
	 *            A vector of sprites, to serve as the animation frames.
	 * @param updateRate
	 *            A number corresponding to the number of times the game should
	 *            update/tick before the animation sprite image is
	 *            updated/changed.
	 * @param loop
	 *            {@link Animation#NORMAL_LOOP} for playing the animation from
	 *            beginning when it ends.
	 * 
	 *            {@link Animation#ALTERNATIVE_LOOP} for playing the animation
	 *            forward or backwards every time it reaches an endpoint.
	 * 
	 *            {@link Animation#NO_LOOP} for no looping at all.
	 * @param forward
	 *            True if the animation should play normally, false if reversed.
	 */
	public Animation(Sprite[] frames, int updateRate, int loopType, boolean forward) {
		this.frames = frames;
		this.updateRate = updateRate;
		this.loopType = loopType;
		this.ticksPassed = 0;
		this.direction = forward ? 1 : -1;
		this.currentFrame = forward ? 0 : frames.length - 1;
		this.stopped = true;
	}

	/**
	 * Plays the animation. From start of from where it was left of (if you used
	 * pause).
	 */
	public void start() {
		if (frames.length > 0)
			stopped = false;
	}

	/**
	 * Pauses the animation. For playing it again, just call
	 * {@link Animation#start()}.
	 */
	public void pause() {
		if (frames.length > 0)
			stopped = true;
	}

	/**
	 * Resets and stops the animation.
	 */
	public void reset() {
		if (frames.length > 0) {
			stopped = true;
			currentFrame = direction > 0 ? 0 : frames.length - 1;
		}
	}

	/**
	 * Restart the animation, but doesn't stop it from playing.
	 */
	public void restart() {
		if (frames.length > 0)
			currentFrame = direction > 0 ? 0 : frames.length - 1;
	}
	
	public boolean hasEnded() {
		if (loopType == Animation.NO_LOOP && currentFrame >= frames.length)
			return true;
		return false;
	}

	/**
	 * Updates the animation.
	 */
	public void update() {
		if (!stopped) {
			ticksPassed++;
			if (ticksPassed > updateRate) {
				ticksPassed = 0;
				currentFrame += direction;

				// Check if the animation has ended.
				if (currentFrame >= frames.length || currentFrame < 0) {
					if (loopType == Animation.NORMAL_LOOP)
						restart();
					else if (loopType == Animation.ALTERNATIVE_LOOP) {
						direction = -direction;
						currentFrame += direction * 2;
					} else if (loopType == Animation.NO_LOOP) {
						stopped = true;
					}
				}
			}
		}
	}

	// Getters and Setters

	public int getUpdateRate() {
		return updateRate;
	}

	public void setUpdateRate(int updateRate) {
		this.updateRate = updateRate;
	}

	public void setLoopType(int loopType) {
		if (loopType != Animation.NORMAL_LOOP || loopType != Animation.ALTERNATIVE_LOOP
				|| loopType != Animation.NO_LOOP) {
			System.out.println("Erro: Tipo de loop não suportado. Use as constantes da classe Animation.");
			System.exit(0);
		}
		this.loopType = loopType;
	}

	public BufferedImage getSprite() {
		return frames[currentFrame].getSprite();
	}

}
