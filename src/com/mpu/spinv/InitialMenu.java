package com.mpu.spinv;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mpu.spinv.engine.StateMachine;

public class InitialMenu {

	public static void main(String[] args) {
		new InitialMenu();
	}

	public InitialMenu() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				try {
					JLabel label = new JLabel(new ImageIcon(getClass().getResource("/resources/img/spaceLamsa.png")));

					JFrame frame = new JFrame("Space Lamsa");
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.setContentPane(label);
					// frame.setLayout(new BorderLayout());
					// JLabel text = new JLabel("Hello from the foreground");
					// text.setForeground(Color.WHITE);
					// text.setHorizontalAlignment(JLabel.CENTER);
					// frame.add(text);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.setResizable(false);
					
					
					JButton button = new JButton("Iniciar");
					
					button.setOpaque(true);
					button.setForeground(Color.YELLOW);
					button.setBackground(Color.YELLOW);
					button.setFont(new Font("Microsoft Sans Serif", Font.HANGING_BASELINE, 30));

					label.setLayout(null);
					button.setBounds(800, 600, 200, 60);
					button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub

							System.out.println("Clicou no bot�o Iniciar.");
							frame.setVisible(false);
							Launcher l = new Launcher();
						}
					});

					JButton buttonExit = new JButton("Sair");
					label.setLayout(null);
					buttonExit.setBounds(200, 200, 300, 60);
					buttonExit.setBackground(Color.YELLOW);
					buttonExit.setForeground(Color.YELLOW);
					buttonExit.setFont(new Font("Microsoft Sans Serif", Font.HANGING_BASELINE, 30));
					buttonExit.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							System.out.println("Sair do jogo.");
							System.exit(0);
						}
					});

					label.add(button);
					label.add(buttonExit);

					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {

//							int confirm = JOptionPane.showConfirmDialog(null, "Voc� deseja sair do jogo? =(", "Aten��o",
//									JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
//
//							if (confirm == JOptionPane.YES_OPTION) {
//								// verifica se o usu�rio clicou no bot�o YES
								System.exit(0);
//
//							} else {
//								StateMachine.setActiveState("gameplay");
//							}
							// System.exit(0);

						}
					});

				} catch (HeadlessException exp) {
					exp.printStackTrace();
				}
			}
		});
	}

}
