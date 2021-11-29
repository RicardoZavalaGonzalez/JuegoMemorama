package memorama;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image = null;

	public Panel(Image image) {
		this.image = image;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			int x = 0;
			int y = 0;
			g.drawImage(image, 0, 0, this);
		}
	}
}
