package com.mpu.spinv.game.states;

import java.awt.Graphics;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.State;
import com.mpu.spinv.game.states.gamemenu.Background;
import com.mpu.spinv.game.states.gamemenu.Menu;

/**
 * GameMenu.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-30
 */
public class GameMenu extends State {

	// ---------------- Constants ----------------

	private final static boolean SAVE_RESOURCES = false;

	public final String SPRITESHEET_URL = "/resources/img/spritesheet.png";

	// -------------------------------------------
	
	private State parent;
	
	private Background background;
	
	private Menu menu;

	public GameMenu(State parent) {
		super(SAVE_RESOURCES);
		this.parent = parent;
		init();
	}
	
	@Override
	public void init() {
		StateMachine.spriteSheet.setSpriteSheetImage(GameMenu.class.getResource(SPRITESHEET_URL));
		
		background = new Background();
		menu = new Menu();
	}
	
	@Override
	public void loadResources() {
		addResource("background", background);
		addResource("menu", menu);
	}
	
	@Override
	public void draw(Graphics g) {
		parent.draw(g);
		super.draw(g);
	}

}
