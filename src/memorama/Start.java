package memorama;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Start extends JFrame implements ActionListener {
	JButton play, exit;
	private Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

	public Start() {
		this.setTitle("Memorama");
		this.setSize((int) screen.getHeight(), (int) screen.getHeight() - 50);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		components();
	}

	private void components() {
		Image background = Toolkit.getDefaultToolkit().createImage("src/images/Frutimory.JPG");
		Panel panel = new Panel(background);
		panel.setLayout(null);
		this.getContentPane().add(panel);

		// boton iniciar juego
		play = new JButton("Jugar!");
		play.setBounds(250, 350, 250, 40);
		play.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		play.addActionListener(this);
		panel.add(play);

		// Boton salir
		exit = new JButton("Salir");
		exit.setBounds(250, 450, 250, 40);
		exit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		exit.addActionListener(this);
		panel.add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Inicia el juego
		if (e.getSource() == play) {
			Game ventana = new Game();
			ventana.setVisible(true);
			this.setVisible(false);
		}
		// este es el evento del boton salir
		if (e.getSource() == exit)
			System.exit(0);
	}

}
