package memorama;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Score extends JFrame implements ActionListener {
	private JLabel time, label, points;
	private JButton playAgain;

	public Score() {
		this.setTitle("Record de Jugador");
		this.setSize(300, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		this.getContentPane().add(panel);

		label = new JLabel("Puntuacion");
		label.setBounds(80, 10, 200, 20);
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(label);

		// Tiempo del jugador
		label = new JLabel("Tiempo: ");
		label.setBounds(50, 60, 150, 40);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(label);

		time = new JLabel();
		time.setBounds(150, 60, 150, 40);
		time.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(time);

		// Puntaje obtenido
		label = new JLabel("Puntos: ");
		label.setBounds(50, 100, 150, 40);
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(label);

		points = new JLabel();
		points.setBounds(170, 100, 150, 40);
		points.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(points);

		// Boton para jugar de nuevo
		playAgain = new JButton("Jugar de nuevo");
		playAgain.setBounds(70, 250, 150, 40);
		playAgain.setFont(new Font("Arial", Font.PLAIN, 16));
		playAgain.addActionListener(this);
		panel.add(playAgain);
	}

	public void setTime(String time) {
		this.time.setText(time);
	}

	public void setPoints(String points) {
		this.points.setText(points);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Ir a la ventana de inicio
		if (e.getSource() == playAgain) {
			Start start = new Start();
			start.setVisible(true);
			this.setVisible(false);
		}
	}
}
