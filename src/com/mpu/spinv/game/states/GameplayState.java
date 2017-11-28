package com.mpu.spinv.game.states;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.State;
import com.mpu.spinv.game.states.gameplaystate.AlienGroup;
import com.mpu.spinv.game.states.gameplaystate.Background;
import com.mpu.spinv.game.states.gameplaystate.HelpText;
import com.mpu.spinv.game.states.gameplaystate.LifeBar;
import com.mpu.spinv.game.states.gameplaystate.Player;
import com.mpu.spinv.game.states.gameplaystate.Score;
import com.mpu.spinv.game.states.gameplaystate.SoundIcon;

/**
 * GameplayState.java
 * 
 * @author Brendon Pagano
 * @date 2017-18-08
 */
public class GameplayState extends State {

	// ---------------- Constants ----------------

	private final static boolean SAVE_RESOURCES = true;

	public final String SPRITESHEET_URL = "/resources/img/spritesheet.png";

	// -------------------------------------------

	private Background background;

	private LifeBar lifebar;
	private Score score;
	private HelpText helpText;
	private SoundIcon soundIcon;

	private Player player;
	private AlienGroup alienGroup;

	public GameplayState() {
		super(SAVE_RESOURCES);
		init();
	}

	@Override
	public void init() {
		StateMachine.spriteSheet.setSpriteSheetImage(GameplayState.class.getResource(SPRITESHEET_URL));
		
		background = new Background();
		lifebar = new LifeBar();
		score = new Score();
		player = new Player(score);
		alienGroup = new AlienGroup(lifebar);
		helpText = new HelpText();
		soundIcon = new SoundIcon();
	}

	@Override
	public void loadResources() {
		addResource("background", background);
		addResource("player", player);
		addResource("alien-group", alienGroup);
		addResource("lifebar", lifebar);
		addResource("score", score);
		addResource("help-text", helpText);
		addResource("sound-icon", soundIcon);
	}

}
