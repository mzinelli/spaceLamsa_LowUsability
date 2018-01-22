package com.mpu.spinv.game.states.gameplaystate;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.GameEntity;
import com.mpu.spinv.engine.model.Sprite;
import com.mpu.spinv.utils.Constants;

/**
 * Boss.java
 * 
 * @author Brendon Pagano
 * @date 2017-12-15
 */
public class Boss extends GameEntity {

	// ---------------- Constants ----------------
	boolean matou = false;
	// ---------------------------------------------
	private static final int WIDTH = 183;
	private static final int HEIGHT = 162;

	private static final int INITIAL_X = Constants.WINDOW_WIDTH / 2 - WIDTH / 2;
	private static final int INITIAL_Y = 10;

	private final int VELOCITY = 5;

	private static final boolean INITIAL_VISIBILITY = false;

	// Entity features

	private final int SCORE_VALUE = 50;

	private int life = 30;

	/**
	 * A reference to the score object.
	 */
	private Score score;

	public Boss(Score score) {
		super(INITIAL_X, INITIAL_Y, INITIAL_VISIBILITY);

		this.score = score;

		// Setting up the boss sprite
		BufferedImage image = null;
		try {
			image = ImageIO.read(Boss.class.getResource("/resources/img/ugly.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setStaticSprite(new Sprite(image));

		// Boss setup
		setScreenBound(true);
		setVelocity(VELOCITY, VELOCITY);
		moveOnlyIfVisible(true);
		setX(900);
		setY(20);
		setVelocityX(4);

	}

	@Override
	public void update() {

		if (x + width > 900) {
			moveRight(false);
			moveLeft(true);
		} else if (x < 200) {
			moveLeft(false);
			moveRight(true);
		}
		super.update();
	}

	@Override
	public void die() {
 		super.die();
		score.increment(SCORE_VALUE);
		System.exit(0);
		
//		frame.setVisible(false);
		
	}

	public void decrementLife() {
		if (life > 1)
			life--;
		else {
			die();
		}
	}

}
