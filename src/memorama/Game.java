package memorama;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;

public class Game extends JFrame implements ActionListener {

	private Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private String images[] = { "guess", "apple", "banana", "cherry", "lemon", "orange", "pineapple" };
	private JButton start, exit, reset;
	private JPanel panel;
	private JLabel imagesMatrix[][], title, timer, numPoints;
	private int locationsCards[][] = new int[3][4], matrix[][] = new int[3][4];
	private Random random;
	private int counter, flag, flag2, secondCard, secondCardPosX, secondCardPosY, firstCard, firstCardPosX,
			firstCardPoxY;
	private Timer waitting, waitting2, time;
	private int counterWaitting, seg, min, points;

	public Game() {
		this.setTitle("Memorama");
		this.setSize((int) screen.getHeight(), (int) screen.getHeight() - 50);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		// se coloca un panel en la ventana
		panel = new JPanel();
		this.getContentPane().add(panel);
		panel.setLayout(null);

		// matrix es para que las cartas aparezcan volteadas
		random = new Random();
		randomCards();

		/* matriz de im�genes */
		imagesMatrix = new JLabel[3][4];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				imagesMatrix[i][j] = new JLabel();
				imagesMatrix[i][j].setSize(imagesMatrix[i][j].getWidth(), imagesMatrix[i][j].getHeight());
				// espacio entre las im�genes
				imagesMatrix[i][j].setBounds(30 + (j * 130), 80 + (i * 140), 130, 130);
				// Dibujando imagen de "pregunta" lleva el nombre "0"
				imagesMatrix[i][j].setIcon(new ImageIcon("src/images/" + images[0] + ".JPEG"));
				imagesMatrix[i][j].setVisible(true);
				// Agregando la imagen "pregunta"
				panel.add(imagesMatrix[i][j], 0);
			}
		}

		seg = 60;
		min = 0;
		// Countdown del juego, se termina en 2 minutos
		time = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seg--;
				if (seg == 0) {
					min--;
					seg = 60;
				}
				/*
				 * Si el tiempo se acaba entonces se detiene el juego y pasa a la ventana de
				 * puntuaciones
				 */
				if (min < 0) {
					JOptionPane.showMessageDialog(panel, "Se acabo el tiempo!");
					Score score = new Score();
					score.setVisible(true);
					time.stop();
					score.setTime("00:00");
					score.setPoints(""+points);
					min = seg = 0;
				}
				timer.setText(min + ":" + seg);
			}
		});
		time.start();
		// timer para esperar y voltear la tarjeta
		counterWaitting = 0;
		waitting = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				counterWaitting++;
			}
		});
		waitting.start();
		waitting.stop();
		counterWaitting = 0;

		flag = 0;
		flag2 = 0;

		// evento de clic sobre las cartas
		counter = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				imagesMatrix[i][j].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						for (int k = 0; k < 3; k++) {
							for (int l = 0; l < 4; l++) {
								if (e.getSource() == imagesMatrix[k][l]) {
									// cuando se hace click sobre la carta esta se voltea
									if (matrix[k][l] == 0 && counter != 2) {
										matrix[k][l] = locationsCards[k][l];
										// Dibujando la imagen con el numero en la posicion k,l
										imagesMatrix[k][l]
												.setIcon(new ImageIcon("src/images/" + images[matrix[k][l]] + ".JPG"));
										counter++;
										firstCard = locationsCards[k][l];
										firstCardPosX = k;
										firstCardPoxY = l;
										if (counter == 1) {
											secondCard = locationsCards[k][l];
											secondCardPosX = k;
											secondCardPosY = l;
										}

										// tiempo que se tarda en dar vuelta
										waitting2 = new Timer(500, new ActionListener() {
											public void actionPerformed(ActionEvent e) {

												if (counter == 2 && flag2 == 0) {
													waitting.restart();
													flag2 = 1;
												}
												if (counter == 2 && counterWaitting == 2) {
													waitting.stop();
													counterWaitting = 0;
													/*
													 * Si las cartas son iguales se quitan de la matriz dejando a las
													 * demas
													 */
													if (matrix[firstCardPosX][firstCardPoxY] == matrix[secondCardPosX][secondCardPosY]) {
														matrix[firstCardPosX][firstCardPoxY] = -1;
														matrix[secondCardPosX][secondCardPosY] = -1;
														imagesMatrix[firstCardPosX][firstCardPoxY]
																.setIcon(new ImageIcon("src/images/"
																		+ matrix[firstCardPosX][firstCardPoxY]
																		+ ".JPG"));
														imagesMatrix[secondCardPosX][secondCardPosY]
																.setIcon(new ImageIcon("src/images/"
																		+ matrix[secondCardPosX][secondCardPosY]
																		+ ".JPG"));
														counter = 0;
														points += 20;
														numPoints.setText("" + points);
														seg += 10;
														if (seg > 60) {
															min++;
															seg = seg - 60;
														}
														// Si toda la matriz es igual a -1 el juego termina
														int founded = 0;
														for (int m = 0; m < 3; m++) {
															for (int n = 0; n < 4; n++) {
																if (matrix[m][n] == -1)
																	founded++;
															}
														}
														// se muestra un mensaje de "Ganaste y pasa a la ventana de
														// Score"
														if (founded == 12) {
															JOptionPane.showMessageDialog(panel,
																	"FELICIDADES GANASTE!");
															Score score = new Score();
															score.setVisible(true);
															time.stop();
															score.setTime(min + ":" + seg);
															score.setPoints(""+points);
														}
													} else {
														points -= 5;
														numPoints.setText("" + points);
														if (points < 0) {
															numPoints.setText("0");
															points = 0;
														}
													}
													for (int m = 0; m < 3; m++) {
														for (int n = 0; n < 4; n++) {
															// se coloca el valor -1 a las cartas pares
															if (matrix[m][n] != 0 && matrix[m][n] != -1) {
																matrix[m][n] = 0;
																imagesMatrix[m][n].setIcon(new ImageIcon("src/images/"
																		+ images[matrix[m][n]] + ".JPEG"));
																counter = 0;
															}
														}
													}
													waitting2.stop();
													flag2 = 0;
												}
											}
										});
										if (flag == 0)
											waitting2.start();
										flag = 1;
										if (counter == 2)
											waitting2.restart();
									}
								}
							}
						}
					}
				});
			}
		}
		// Inicializando componentes
		componentes();
	}

	/*
	 * Obteniendo aleatoriamente los numeros para que las cartas aparezcan en
	 * diferentes lugar cada vez que inicia el juego.
	 */
	private void randomCards() {
		int counter;
		// Ubicaciones de las cartas en el tablero
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				locationsCards[i][j] = random.nextInt(6) + 1;
				do {
					counter = 0;
					for (int k = 0; k < 3; k++) {
						for (int l = 0; l < 4; l++) {
							if (locationsCards[i][j] == locationsCards[k][l])
								counter += 1;
						}
					}
					// Ubicacion de la carta dos veces en el tablero
					if (counter == 3)
						locationsCards[i][j] = random.nextInt(6) + 1;
				} while (counter == 3);
			}
		}
	}

	// estos son los componentes del programa
	private void componentes() {
		// Titulo del juego
		title = new JLabel("Memorama");
		title.setBounds(280, 10, 260, 40);
		title.setFont(new Font("Arial", Font.PLAIN, 45));
		panel.add(title);

		// Boton para reiniciar el juego
		reset = new JButton("Reiniciar");
		reset.setBounds(210, 520, 160, 35);
		reset.setFont(new Font("Arial", Font.PLAIN, 15));
		reset.addActionListener(this);
		panel.add(reset);

		// Boton salir
		exit = new JButton("Salir");
		exit.setBounds(390, 520, 160, 35);
		exit.setFont(new Font("Arial", Font.PLAIN, 15));
		exit.addActionListener(this);
		panel.add(exit);

		// Label para mostrar el tiempo restante del jugador
		title = new JLabel("Tiempo: ");
		title.setBounds(570, 80, 150, 40);
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(title);

		timer = new JLabel();
		timer.setBounds(670, 80, 150, 40);
		timer.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(timer);

		// Label para mostrar los puntos del jugador
		title = new JLabel("Puntos: ");
		title.setBounds(570, 120, 150, 40);
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(title);

		numPoints = new JLabel("0");
		numPoints.setBounds(690, 120, 150, 40);
		numPoints.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(numPoints);

	}

	// estos son los eventos de accion
	@Override
	public void actionPerformed(ActionEvent e) {

		// Boton para salir del juego y terminar el programa
		if (e.getSource() == exit)
			System.exit(0);

		// Boton para reiniciar el juego
		if (e.getSource() == reset) {
			if (JOptionPane.showConfirmDialog(rootPane, "Quieres reiniciar el juego?", "Reiniciar",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION) {
				Game newGame = new Game();
				newGame.setVisible(true);
				this.setVisible(false);
			} else {
				setDefaultCloseOperation(0);
			}
		}
	}
}
