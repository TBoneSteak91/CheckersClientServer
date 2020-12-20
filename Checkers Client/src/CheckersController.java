
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class CheckersController implements Runnable, MouseListener {
	// A controller class to handle client side input.

	private Board boardModel;
	private Player playerModel;
	private BoardView boardView;
	private MouseEvent entered;
	private Socket socket;
	private int colSelected;
	private int rowSelected;
	private boolean holdingPiece;

	public CheckersController(Board boardModel, Socket socket) throws IOException {
		// The controller takes a board object and a server socket as an input.
		this.boardModel = boardModel;
		this.socket = socket;

	}

	public void setView(BoardView boardView) {
		this.boardView = boardView;
	}

	public void run() {
		// A thread to handle the game loop on the client side.

		DataInputStream is;
		try {
			is = new DataInputStream(socket.getInputStream());
			playerModel = new Player(is.readInt()); // The first int read from the server relates to the players ID
													// (either player 1 or 2)
			System.out.println("I am player " + playerModel.getPlayerID());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (playerModel.getPlayerID() == 1) // Player 1 goes first.
			playerModel.setMyTurn(true);

		recieveBoard();
	}

	public void recieveBoard() {
		// Calls a method to read the board information passed from the server. The
		// client reads four int values relating to the other players move; two for the
		// previous position of the piece (x1, y1) and two for the new position (x2,
		// y2). The information is then used to update the model and view.
		while (true) {
			DataInputStream is;
			try {
				is = new DataInputStream(socket.getInputStream());
				int x1 = is.readInt();
				int y1 = is.readInt();
				int x2 = is.readInt();
				int y2 = is.readInt();
				// Reading the coordinates.

				boardModel.attemptMove(boardModel.getTiles()[x1][y1], boardModel.getTiles()[x2][y2]);
				// Updating the model.

				if (boardModel.getTiles()[x2][y2].getRowNum() == 0 && playerModel.getPlayerID() == 1) {
					boardModel.getTiles()[x2][y2].setKing(true);
				}
				if (boardModel.getTiles()[x2][y2].getRowNum() == 7 && playerModel.getPlayerID() == 2) {
					boardModel.getTiles()[x2][y2].setKing(true);
				}
				// Checking if a King was made.

				boardView.repaintAllTiles();// Updating the view.
				playerModel.setMyTurn(true);// Changing the turn back.

				String winner = boardModel.checkWinner(); // Checking for a winner.
				if (winner != null) {
					JOptionPane.showMessageDialog(null, winner);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// A method to handle mouse input. This method picks up the piece and the next
		// method handles dropping it somewhere else.

		if (!playerModel.isMyTurn()) {
			JOptionPane.showMessageDialog(null, "It is not your turn!", "", JOptionPane.ERROR_MESSAGE, null);
			// Checking to see if its the players turn.

		} else {
			for (int i = 0; i < 8; i++) {
				for (int k = 0; k < 8; k++) {
					if (e.getSource() == boardView.getPanels()[i][k].getButton()) {
						if (boardModel.getTiles()[i][k].getPlayerNum() == playerModel.getPlayerID()) {
							// Checking which tile was clicked.
							rowSelected = i;
							colSelected = k;
							holdingPiece = true;
							boardView.changeCursor(boardView.getPanels()[i][k].getButton().getIcon().toString());
							// Changing the mouse cursor to the piece to simulate lifting it.
						} else
							JOptionPane.showMessageDialog(null, "That's not your piece!", "", JOptionPane.ERROR_MESSAGE,
									null);

					}
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// A method to handle what happens when a piece is dropped.

		if (holdingPiece = true) {

			for (int i = 0; i < 8; i++) {
				for (int k = 0; k < 8; k++) {

					if (entered.getSource() == boardView.getPanels()[i][k].getButton()) {
						boolean moved = (boardModel.attemptMove(boardModel.getTiles()[rowSelected][colSelected],
								boardModel.getTiles()[i][k]));// Checking to see if the piece can be moved and moving
																// it.

						boardView.repaintAllTiles();// Updating the view.
						boardView.changeCursorback();// Changing the cursor back to normal.

						if (moved) {
							if (boardModel.getTiles()[i][k].getRowNum() == 0 && playerModel.getPlayerID() == 2) {
								boardModel.getTiles()[i][k].setKing(true);
							}
							if (boardModel.getTiles()[i][k].getRowNum() == 7 && playerModel.getPlayerID() == 1) {
								boardModel.getTiles()[i][k].setKing(true);
							} // Checking if a new King was made.

							boardView.repaintAllTiles(); // Updating the view.
							sendBoard(rowSelected, colSelected, i, k); // Sending the move to the server.
							playerModel.setMyTurn(false); // Changing whose turn it is.

						}

					}
				}
			}
		}

	}

	public void sendBoard(int x1, int y1, int x2, int y2) {
		// A method to handle sending data to the server. Takes four ints as parameters
		// relating to the coordinates of before and after the move.

		DataOutputStream is;
		try {
			is = new DataOutputStream(socket.getOutputStream());
			is.writeInt(x1);
			is.writeInt(y1);
			is.writeInt(x2);
			is.writeInt(y2);
			System.out.println("sent board client" + x1 + y1 + " to " + +x2 + y2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String winner = boardModel.checkWinner(); // Checks if a win has occured.
		if (winner != null) {
			JOptionPane.showMessageDialog(null, winner);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		entered = e;

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}