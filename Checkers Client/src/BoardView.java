
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardView extends JFrame {
	// A class to handle the view.

	private static final int rows = 8;
	private static final int columns = 8;
	private static final Color colGreen = new Color(0, 150, 0);
	private static final Color colYellow = new Color(255, 255, 0);
	private CheckersController controllerObject;

	Board boardModel;

	TileView[][] panels = new TileView[rows][columns];

	public TileView[][] getPanels() {
		return panels;
	}

	public BoardView(CheckersController controller, Board boardModel) {

		controllerObject = controller;
		this.boardModel = boardModel;

		this.setSize(512, 512); // sets the size of the window.
		this.setTitle("Checkers");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container pane = this.getContentPane();
		pane.setLayout(new GridLayout(rows, columns)); // Makes an 8 by 8 grid.
		Color colour;

		// Sets the checkered colour pattern of the grid.
		for (int i = 0; i < rows; i++) {
			if (i % 2 == 0) {
				colour = colGreen;
			} else {
				colour = colYellow;
			}
			for (int k = 0; k < columns; k++) {
				panels[i][k] = new TileView(controllerObject, boardModel.getTiles()[i][k]);
				panels[i][k].setBackground(colour);
				if (colour.equals(colGreen)) {
					colour = colYellow;
				} else {
					colour = colGreen;
				}
				pane.add(panels[i][k]);

			}

		}

	}

	public void repaintAllTiles() {
		// A method which loops over the board and repaints all the tiles after a move.
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				panels[i][k].paint(boardModel.getTiles()[i][k]);
			}

		}
	}

	public void changeCursor(String image) {
		// A method to change the cursor to a checkers piece, to simulate picking a
		// piece up.

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image tileImage = toolkit.getImage(image);
		Cursor cursor = toolkit.createCustomCursor(tileImage,
				new Point(this.getContentPane().getX(), this.getContentPane().getY()), "");
		this.setCursor(cursor);

	}

	public void changeCursorback() {
		// Changing the cursor back when a piece is dropped. 
		this.setCursor(Cursor.getDefaultCursor());
	}
}