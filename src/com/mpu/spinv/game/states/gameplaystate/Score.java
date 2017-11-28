package com.mpu.spinv.game.states.gameplaystate;

import java.awt.Color;
import java.awt.Graphics;

import com.mpu.spinv.engine.model.GameText;
import com.mpu.spinv.utils.Constants;

public class Score extends GameText {
	
	// ---------------- Constants ----------------
	
	private static final int X = 0;
	private static final int Y = 10;
	
	private static final boolean INITIAL_VISIBILITY = true;
	
	private static final String SCORE_TEXT = "SCORE: 0000";
	private static final Color TEXT_COLOR = Color.WHITE;
	
	// -------------------------------------------
	
	/**
	 * The player's score.
	 */
	private int score = 0;
	
	private boolean _firstTime = true;
	
	public Score() {
		super(X, Y, INITIAL_VISIBILITY, SCORE_TEXT, TEXT_COLOR);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		if (_firstTime) {
			setX(Constants.WINDOW_WIDTH - 4 - width - 10);
			_firstTime = false;
		}
	}
	
	// Getters and Setters
	
	public void increment(int n) {
		score += n;
		setText(SCORE_TEXT.substring(0, SCORE_TEXT.length() - Integer.toString(score).length()) + score);
	}
	
	public void decrement(int n) {
		score -= n;
		setText(SCORE_TEXT + score);
	}
	
	public int getScore() {
		return score;
	}

}
