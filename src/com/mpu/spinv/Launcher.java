package com.mpu.spinv;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mpu.spinv.engine.StateMachine;
import com.mpu.spinv.utils.Constants;

import jplay.Animation;
import jplay.GameImage;
import jplay.Mouse;
import jplay.Window;
import sun.font.CreatedFontTracker;

public class Launcher {


	public static void main(String[] args) {
		new Launcher();
	}

	public Launcher() {

		JFrame f = new JFrame("Space Lamsa LU");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		f.setResizable(false);
		f.setLocationRelativeTo(null);

		f.add(new Core());
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				StateMachine.setActiveState("game-menu");
//				int confirm = JOptionPane.showConfirmDialog(null, "Voc� deseja sair do jogo? =(", "Aten��o",
//						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
//
//				if (confirm == JOptionPane.YES_OPTION) {
//					// verifica se o usu�rio clicou no bot�o YES
					System.exit(0);

//				} else {
//					StateMachine.setActiveState("gameplay");
//				}
//				// System.exit(0);
//
			}
		});
	}


}