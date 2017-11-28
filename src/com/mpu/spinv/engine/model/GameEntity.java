package com.mpu.spinv.engine.model;

import java.awt.Color;
import java.awt.Graphics;

import com.mpu.spinv.utils.AdvList;
import com.mpu.spinv.utils.Constants;

/**
 * GameEntity.java
 * 
 * @author Brendon Pagano
 * @date 2017-08-08
 */
public class GameEntity extends GameObject {

	/**
	 * A list of the object's animations.
	 */
	private final AdvList<Animation> animations;

	/**
	 * The key identifier of the active animation.
	 */
	private String actAnimation;

	/**
	 * The active animation. Currently playing if {@link GameEntity#visible} is
	 * true.
	 */
	private Animation animation;

	/**
	 * In case there is no need for the object to have an animation, use a static
	 * sprite to draw instead.
	 */
	private Sprite staticSprite;

	/**
	 * GameObject's constructor.
	 * 
	 * @param x
	 *            The x position of the object in the screen.
	 * @param y
	 *            The y position of the object in the screen.
	 * @param visible
	 *            Flag to determine if the object will begin visible or not.
	 */
	public GameEntity(int x, int y, boolean visible) {
		super(x, y, 0, 0, visible);

		// Default params
		this.animations = new AdvList<Animation>();
		this.animation = null;
		this.actAnimation = null;
		this.staticSprite = null;
	}

	public GameEntity(int x, int y, Sprite staticSprite, boolean visible) {
		super(x, y, staticSprite.getWidth(), staticSprite.getHeight(), visible);

		this.staticSprite = staticSprite;

		// Default params
		this.animations = new AdvList<Animation>();
		this.animation = null;
		this.actAnimation = null;
	}

	public GameEntity(int x, int y, Animation animation, boolean visible) {
		super(x, y, animation.getSprite().getWidth(), animation.getSprite().getHeight(), visible);

		// Default params
		this.animations = new AdvList<Animation>();
		this.animation = null;
		this.actAnimation = null;
		this.staticSprite = null;

		// Setting the default animation
		addAnimation("default", animation);
	}

	@Override
	public boolean isGroup() {
		return false;
	}

	@Override
	public void update() {
		super.update();

		if (actAnimation != null)
			animation.update();
	}

	@Override
	public void draw(Graphics g) {
		if (visible && (animation != null || staticSprite != null)) {
			if (!drawChildrenFirst()) {
				g.drawImage((actAnimation == null ? staticSprite.getSprite() : animation.getSprite()), x, y, null);	
				if (hasChildren()) {
					getChildren().forEach(go -> go.draw(g));
				}
			} else {
				if (hasChildren()) {
					getChildren().forEach(go -> go.draw(g));
				}
				g.drawImage((actAnimation == null ? staticSprite.getSprite() : animation.getSprite()), x, y, null);
			}
			
			if (Constants.SHOW_ENTITIES_BORDERS) {
				g.setColor(Color.GREEN);
				g.drawRect(x, y, width, height);
			}
		}
	}

	/**
	 * Adds an animation into the GameObject's animations list.
	 * 
	 * @param key
	 *            the identifier of the animation to add.
	 * @param animation
	 *            the animation object to be added.
	 */
	public void addAnimation(String key, Animation animation) {
		if (!key.equals("")) {
			animations.add(key, animation);
			if (actAnimation == null) {
				this.actAnimation = key;
				this.animation = animation;
			}
		}
	}

	/**
	 * Set the active and playing animation to a new one.
	 * 
	 * @param key
	 *            the identifier of the animation to be set.
	 */
	public void setAnimation(String key) {
		if (key == null) {
			actAnimation = null;
			animation = null;
		} else if (animations.containsKey(key)) {
			actAnimation = key;
			animation = animations.get(key);
		}
	}

	/**
	 * Removes an animation from the GameObject's animation list and returns the
	 * removed animation object.
	 * 
	 * @param key
	 *            the key identifier of the animation to be removed.
	 * @return the removed animation object.
	 */
	public Animation removeAnimation(String key) {
		Animation animation = null;
		if (animations.containsKey(key)) {
			animation = animations.get(key);
			animations.remove(key);
			if (actAnimation.equals(key)) {
				this.animation = null;
				this.actAnimation = null;
			}
		}
		return animation;
	}

	/**
	 * Starts playing the active animation.
	 */
	public void startAnimation() {
		if (animation != null)
			animation.start();
	}

	/**
	 * Pauses the active animation.
	 */
	public void pauseAnimation() {
		if (animation != null)
			animation.pause();
	}

	/**
	 * Restarts the active animation.
	 */
	public void restartAnimation() {
		if (animation != null)
			animation.restart();
	}

	/**
	 * Resets the active animation.
	 */
	public void resetAnimation() {
		if (animation != null)
			animation.reset();
	}

	public void resizeSprite(int width, int height) {
		this.width = width;
		this.height = height;
		staticSprite.resizeSprite(width, height);
	}

	// Getters and Setters

	public String getAnimationKey() {
		return actAnimation;
	}

	public Animation getActiveAnimation() {
		return animation;
	}

	public Sprite getStaticSprite() {
		return staticSprite;
	}

	public void setStaticSprite(Sprite staticSprite) {
		this.staticSprite = staticSprite;
		this.width = staticSprite.getWidth();
		this.height = staticSprite.getHeight();
	}

	/**
	 * Unsafe method to set GameEntity's width.
	 * 
	 * @param width
	 *            The new width to be set.
	 */
	protected void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Unsafe method to set GameEntity's height.
	 * 
	 * @param height
	 *            The new height to be set.
	 */
	protected void setHeight(int height) {
		this.height = height;
	}

}
