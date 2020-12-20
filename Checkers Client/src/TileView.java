
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TileView extends JPanel {
	// A class to handle the view of the tiles.

	private CheckersController controllerObject;
	private JButton button;

	public TileView(CheckersController controller, Tile tile) {

		controllerObject = controller;
		button = new JButton(); // Each tile has a button
		button.setBorderPainted(false);
		button.setPreferredSize(new Dimension(50, 50)); // Sets the size of the button in each tile.
		this.add(button);
		button.addMouseListener(controllerObject);
		paint(tile);

	}

	public void paint(Tile tile) { // A method to paint a tile, illustrating whether or not it is occupied by a
									// piece, which colour the piece is and whether it is a King piece.
		
		if (tile.getPlayerNum() == 0) { // An empty tile
			button.setContentAreaFilled(false);
			button.setIcon(null);
		}

		if (tile.getPlayerNum() == 1) { // Player one is red
			if (tile.getIsKing() == true)
				button.setIcon(new ImageIcon("images/redKing.jpg"));
			else
				button.setIcon(new ImageIcon("images/red.jpg"));

		}

		if (tile.getPlayerNum() == 2) { // Player two is black
			if (tile.getIsKing() == true)
				button.setIcon(new ImageIcon("images/blackKing.jpg"));
			else
				button.setIcon(new ImageIcon("images/black.jpg"));
		}
	}

	public JButton getButton() {
		return button;
	}

}
