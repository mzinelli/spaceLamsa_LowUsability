package com.mpu.spinv.game.states.gamemenu;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mpu.spinv.Core;
import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.engine.model.Group;
import com.mpu.spinv.engine.triggers.KeyTriggerEvent;
import com.mpu.spinv.game.menu.MenuItem;
import com.mpu.spinv.utils.Constants;

import javafx.scene.layout.Border;
import jplay.Animation;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Window;

/**
 * Menu.java
 * 
 * @author Brendon Pagano
 * @date 2017-09-30
 */
public class Menu extends Group {


	// ---------------- Constants ----------------

	private static final int X = 0;
	private static final int Y = 0;

	private static final int LAYOUT = Group.LAYOUT_VERTICAL;

	// -------------------------------------------
	private BorderLayout layout;
	private int i = 0;

	public Menu() {
		super(X, Y, LAYOUT);

		setSpacingVertical(10);

		add(new MenuItem(0, 0, "CONTINUAR", (i, j) -> {
			StateMachine.setActiveState("gameplay");
		}, true));

		add(new MenuItem(0, 0, "AJUDA", (i, j) -> {
			 
			JOptionPane.showMessageDialog(null,
			 " Para jogar utilize:\n" + "\nSetas Direcionais: \n <- Esquerda \n-> Direita \n" +"Barra de Espaço - Atirar",
			 "Ajuda",
			 JOptionPane.INFORMATION_MESSAGE);


		}));

		add(new MenuItem(0, 0, "SAIR", (i, j) -> {
			int confirm = JOptionPane.showConfirmDialog(null, "Você deseja sair do jogo? =(", null,
					JOptionPane.OK_CANCEL_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				// verifica se o usuário clicou no botão YES
				System.exit(0);
			} else {
				StateMachine.setActiveState("gameplay");
			}
			// System.exit(0);
		}));

		centerBothAxis();

		on(new KeyTriggerEvent(KeyEvent.VK_UP, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED && i > 0) {
				MenuItem sel = (MenuItem) get(i);
				sel.setSelected(false);
				i--;
				sel = (MenuItem) get(i);
				sel.setSelected(true);
			}
		}));

		on(new KeyTriggerEvent(KeyEvent.VK_DOWN, (k, t) -> {
			if (t == KeyTriggerEvent.KEY_RELEASED && i < getGameEntities().size() - 1) {
				MenuItem sel = (MenuItem) get(i);
				sel.setSelected(false);
				i++;
				sel = (MenuItem) get(i);
				sel.setSelected(true);
			}
		}));
	}

}
