package com.mpu.spinv.engine.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.mpu.spinv.engine.CollisionHandler;
import com.mpu.spinv.engine.triggers.CollisionEvent;
import com.mpu.spinv.utils.Constants;

/**
 * Group.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-14
 */
public class Group extends GameObject {

	// ---------------- Constants ----------------

	/**
	 * Every element of the Group will be displayed horizontally.
	 */
	public static final int LAYOUT_HORIZONTAL = 0;

	/**
	 * Every element of the Group will be displayed vertically.
	 */
	public static final int LAYOUT_VERTICAL = 1;

	/**
	 * Every element of the Group will be displayed in a grid of N rows by N
	 * columns.
	 */
	public static final int LAYOUT_GRID = 2;

	// -------------------------------------------

	/**
	 * The list of GameEntity's that the Group contains.
	 */
	private List<GameEntity> gameEntities;

	/**
	 * The margin applied to every {@link GameEntity} of the Group.
	 */
	private int spacingHorizontal, spacingVertical;

	/**
	 * The layout to display the group.
	 */
	private int layout;

	/**
	 * The number of columns the {@link Group#LAYOUT_GRID} will display.
	 */
	private int gridCols;

	/**
	 * A flag to call {@link Group#resetSize()} if an entity from the Group has been
	 * removed from the {@link Group#gameEntities} list.
	 */
	private boolean _entityDestroyed;

	public Group(int x, int y, int layout) {
		super(x, y, 0, 0, true);

		this.layout = layout;

		// Default params
		this.gameEntities = new ArrayList<GameEntity>();
		this.spacingHorizontal = 0;
		this.spacingVertical = 0;
		this.gridCols = 0;
		this._entityDestroyed = false;
	}

	@Override
	public boolean isGroup() {
		return true;
	}

	@Override
	public void update() {
		// Checks for dead objects within the group
		for (int i = gameEntities.size() - 1; i >= 0; i--) {
			if (gameEntities.get(i).isDead()) {
				gameEntities.remove(i);
				_entityDestroyed = true;
			}
		}
		
		if (_entityDestroyed) {
			resetSize();
			_entityDestroyed = false;
		}

		int previousX = x, previousY = y;

		super.update();

		if (previousX != x || previousY != y) {
			int diffX = x - previousX;
			int diffY = y - previousY;

			gameEntities.forEach(go -> {
				go.setX(go.getX() + diffX);
				go.setY(go.getY() + diffY);
			});
		}

		gameEntities.forEach(go -> {
			go.update();
		});
	}

	@Override
	public void draw(Graphics g) {
		if (visible) {
			gameEntities.forEach(go -> {
				go.draw(g);
			});

			if (Constants.SHOW_ENTITIES_BORDERS) {
				g.setColor(Color.GREEN);
				g.drawRect(x, y, width, height);
			}
		}
	}

	public List<GameObject> returnCollided(GameObject go) {
		List<GameObject> collidedObjs = new ArrayList<GameObject>();

		gameEntities.forEach(ge -> {
			if (!ge.isListenCollision() || !ge.isVisible() || ge.isDead()) {
				return;
			}
			
			if (CollisionHandler.hasCollided(go, ge))
				collidedObjs.add(ge);
		});

		return collidedObjs;
	}
	
	@Override
	public boolean hasCollisionEvents() {
		if (collisionEvents.size() > 0)
			return true;
		
		for (int i = 0; i < gameEntities.size(); i++)
			if (gameEntities.get(i).hasCollisionEvents())
				return true;
		
		return false;
	}
	
	@Override
	public List<CollisionEvent> getCollisionEvents() {
		List<CollisionEvent> colEvents = new ArrayList<CollisionEvent>();
		
		collisionEvents.forEach(c -> colEvents.add(c));
		
		gameEntities.forEach(g -> {
			if (g.hasCollisionEvents())
				g.getCollisionEvents().forEach(c -> colEvents.add(c));
		});
		
		return colEvents;
	}

	/**
	 * Return true if any of the GameEntities has key triggers attached to it.
	 */
	@Override
	public boolean hasKeyTriggers() {
		boolean groupKeyTriggers = super.hasKeyTriggers();
		if (groupKeyTriggers)
			return true;

		for (int i = 0; i < gameEntities.size(); i++)
			if (gameEntities.get(i).hasKeyTriggers())
				return true;

		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		gameEntities.forEach(g -> {
			if (g.hasKeyTriggers())
				g.keyPressed(e);
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		gameEntities.forEach(g -> {
			if (g.hasKeyTriggers())
				g.keyReleased(e);
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		gameEntities.forEach(g -> {
			if (g.hasKeyTriggers())
				g.keyTyped(e);
		});
	}

	/**
	 * Adds a GameEntity into the list.
	 * 
	 * @param go
	 *            the GameEntity to be added.
	 */
	public void add(GameEntity go) {
		int listSize = gameEntities.size();
		GameEntity lastElem = listSize > 0 ? gameEntities.get(listSize - 1) : null;
		
		if (layout == Group.LAYOUT_HORIZONTAL) {
			go.setX(lastElem != null ? lastElem.getX() + lastElem.getWidth() + spacingHorizontal : x);
			go.setY(y);
		} else if (layout == Group.LAYOUT_VERTICAL) {
			go.setX(x);
			go.setY(lastElem != null ? lastElem.getY() + lastElem.getHeight() + spacingVertical : y);
		} else if (layout == Group.LAYOUT_GRID) {
			int maxY = getMaxY();
			if (listSize % gridCols == 0) {
				go.setX(x);
				go.setY(maxY + spacingVertical);
			} else {
				go.setX(lastElem != null ? lastElem.getX() + lastElem.getWidth() + spacingHorizontal : x);
				go.setY(lastElem != null ? lastElem.getY() : y);
			}
		}
		
		gameEntities.add(go);
		resetSize();
	}

	/**
	 * Removes an element from the list, given an index number.
	 * 
	 * @param i
	 *            the index number to extract the GameEntity
	 * @return the removed GameEntity, or null in case of an error.
	 */
	public GameEntity removeAt(int i) {
		if (i < 0 || i > gameEntities.size() - 1)
			return null;
		else
			return gameEntities.remove(i);
	}

	/**
	 * Removes all the elements from the group.
	 */
	public void clear() {
		gameEntities.clear();
	}

	/**
	 * Set the size of the Group's grid. Only executes if layout is set to
	 * {@link Group#LAYOUT_GRID}.
	 * 
	 * @param rows
	 *            how many rows should the grid have.
	 * @param cols
	 *            how many columns should the grid have.
	 */
	public void setGridSize(int cols) {
		if (layout == Group.LAYOUT_GRID && cols > 1) {
			gridCols = cols;
			resetCoordinates();
		}
	}

	/**
	 * Center the Group on screen horizontally.
	 */
	public void centerHorizontalAxis() {
		x = Constants.WINDOW_WIDTH / 2 - width / 2 - 4;
		resetCoordinates();
	}

	/**
	 * Center the Group on screen vertically.
	 */
	public void centerVerticalAxis() {
		y = Constants.WINDOW_HEIGHT / 2 - height / 2 - 30;
		resetCoordinates();
	}

	/**
	 * Center the Group both horizontally and vertically on screen.
	 */
	public void centerBothAxis() {
		x = Constants.WINDOW_WIDTH / 2 - width / 2 - 4;
		y = Constants.WINDOW_HEIGHT / 2 - height / 2 - 30;
		resetCoordinates();
	}

	private void resetSize() {
		if (gameEntities.size() == 0)
			return;

		int minX = 0, maxX = 0, minY = 0, maxY = 0;

		for (int i = 0; i < gameEntities.size(); i++) {
			GameEntity g = gameEntities.get(i);
			if (i == 0) {
				minX = g.getX();
				minY = g.getY();
				maxX = g.getX() + g.getWidth();
				maxY = g.getY() + g.getHeight();
			} else {
				if (g.getX() < minX)
					minX = g.getX();
				if (g.getY() < minY)
					minY = g.getY();
				if (g.getX() + g.getWidth() > maxX)
					maxX = g.getX() + g.getWidth();
				if (g.getY() + g.getHeight() > maxY)
					maxY = g.getY() + g.getHeight();
			}
		}

		// Resets position and size
		x = minX;
		y = minY;
		width = maxX - minX;
		height = maxY - minY;
	}

	protected void resetCoordinates() {
		if (gameEntities.size() == 0)
			return;
		
		int maxY = y;
		
		for (int i = 0; i < gameEntities.size(); i++) {
			GameEntity elem = gameEntities.get(i);
			GameEntity lastElem = i > 0 ? gameEntities.get(i-1) : null;
			
			if (lastElem != null && lastElem.getY() + lastElem.getHeight() > maxY)
				maxY = lastElem.getY() + lastElem.getHeight();
			
			if (layout == Group.LAYOUT_HORIZONTAL) {
				elem.setX(lastElem != null ? lastElem.getX() + lastElem.getWidth() + spacingHorizontal : x);
				elem.setY(y);
			} else if (layout == Group.LAYOUT_VERTICAL) {
				elem.setX(x);
				elem.setY(lastElem != null ? lastElem.getY() + lastElem.getHeight() + spacingVertical : y);
			} else if (layout == Group.LAYOUT_GRID) {
				if (i > 0 && i % gridCols == 0) {
					elem.setX(x);
					elem.setY(maxY + spacingVertical);
				} else {
					elem.setX(lastElem != null ? lastElem.getX() + lastElem.getWidth() + spacingHorizontal : x);
					elem.setY(lastElem != null ? lastElem.getY() : y);
				}
			}
		}
		
		resetSize();
	}
	
	private int getMaxY() {
		int maxY = y;
		
		for (int i = 0; i < gameEntities.size(); i++) {
			GameEntity g = gameEntities.get(i);
			if (g.getY() + g.getHeight() > maxY)
				maxY = g.getY() + g.getHeight();
		}
		
		return maxY;
	}

	// Getters and Setters

	public void setX(int x) {
		this.x = x;
		resetCoordinates();
	}

	public void setY(int y) {
		this.y = y;
		resetCoordinates();
	}

	/**
	 * A shorthand for setting up all the elements spacing.
	 * 
	 * @param horizontal
	 *            The horizontal spacing between each element of the group.
	 * @param vertical
	 *            The vertical spacing between each element of the group.
	 */
	public void setSpacing(int horizontal, int vertical) {
		this.spacingHorizontal = horizontal;
		this.spacingVertical = vertical;

		resetCoordinates();
	}

	public int getSpacingHorizontal() {
		return spacingHorizontal;
	}

	public void setSpacingHorizontal(int spacingHorizontal) {
		this.spacingHorizontal = spacingHorizontal;
		resetCoordinates();
	}

	public int getSpacingVertical() {
		return spacingVertical;
	}

	public void setSpacingVertical(int spacingVertical) {
		this.spacingVertical = spacingVertical;
		resetCoordinates();
	}

	/**
	 * Retrieves the list of gameEntities.
	 * 
	 * @return the list of all the added GameEntity's of the group.
	 */
	public List<GameEntity> getGameEntities() {
		return gameEntities;
	}
	
	public GameEntity get(int i) {
		if (i < 0 || i >= gameEntities.size())
			return null;
		return gameEntities.get(i);
	}

	public int getGridCols() {
		return gridCols;
	}

}
