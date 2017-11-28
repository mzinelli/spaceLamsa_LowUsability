package com.mpu.spinv.engine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.mpu.spinv.engine.model.SpriteSheet;
import com.mpu.spinv.engine.model.State;
import com.mpu.spinv.game.states.GameMenu;
import com.mpu.spinv.game.states.GameplayState;
import com.mpu.spinv.utils.AdvList;

public class StateMachine {

	private static AdvList<State> states;
	public static State activeState;
	private static String actStateId;

	public static SpriteSheet spriteSheet;
	
	private GameplayState gameplayState;

	public StateMachine() {
		states = new AdvList<State>();
		actStateId = "";
		activeState = null;

		spriteSheet = new SpriteSheet();
		
		gameplayState = new GameplayState();

		// addState("splash", new Splash());
		addState("gameplay", gameplayState);
		addState("game-menu", new GameMenu(gameplayState));
	}

	public void update() {
		if (activeState != null)
			activeState.update();
	}

	public void draw(Graphics g) {
		if (activeState != null)
			activeState.draw(g);
	}

	/**
	 * Adds a {@link State} object into the {@link StateMachine#states} list.
	 * 
	 * @param key
	 *            The key identifier of the {@link State} to be added.
	 * @param state
	 *            The {@link State} to be added.
	 */
	public void addState(String key, State state) {
		if (!states.containsKey(key)) {
			states.add(key, state);

			// If no state has been added yet, make it active.
			if (actStateId.equals("")) {
				setActiveState(key);
			}
		}
	}

	/**
	 * Removes a {@link State} from the list.
	 * 
	 * @param key
	 *            The key identifier of the {@link State} to be removed.
	 */
	public State removeState(String key) {
		State state = null;
		if (states.containsKey(key)) {
			state = states.get(key);
			states.remove(key);
			if (actStateId.equals(key)) {
				activeState = null;
				StateMachine.actStateId = "";
			}
		}
		return state;
	}

	/**
	 * Sets the active {@link State} of the game.
	 * 
	 * @param key
	 *            The state's key identifier.
	 */
	public static void setActiveState(String key) {
		if (states.containsKey(key)) {
			if (activeState != null && !activeState.isSaveResources()) {
				activeState.killResources();
			}
			
			actStateId = key;
			activeState = states.get(key);

			if (!activeState.isSaveResources())
				activeState.init();
			
			activeState.loadResources();
		}
	}

	/**
	 * Return the active {@link State}.
	 * 
	 * @return The active {@link State} object.
	 */
	public State getActiveState() {
		if (!actStateId.equalsIgnoreCase(""))
			return activeState;
		else
			return null;
	}

	/**
	 * Retrieves a given {@link State} by his key identifier.
	 * 
	 * @param key
	 *            The key identifier of the {@link State} to be retrieved.
	 * @return A {@link State} if the passed key exists, null otherwise.
	 */
	public State getState(String key) {
		if (states.containsKey(key))
			return states.get(key);
		else
			return null;
	}

	// Controls Handling Methods

	public void keyPressed(KeyEvent e) {
		if (activeState != null)
			activeState.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if (activeState != null)
			activeState.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		if (activeState != null)
			activeState.keyTyped(e);
	}

}
