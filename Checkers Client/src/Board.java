
import java.util.ArrayList;

public class Board {
	// A class to keep track of the model of the board

	private static final int rows = 8;
	private static final int columns = 8;
	private Tile[][] tiles;

	public Tile[][] getTiles() {
		return tiles;
	}

	public Board() {
		// A constructor, which set the number of rows and columns, places the pieces in
		// their starting points and assigns each player their pieces.
		tiles = new Tile[rows][columns];
		placeCheckers();
		assignPlayers();
	}

	private void placeCheckers() {
		// A method to set the starting position of each piece on the board
		boolean startsOccupied;
		boolean isOccupied;

		for (int i = 0; i < rows; i++) {

			if (i % 2 == 1) {
				startsOccupied = true;
			} else
				startsOccupied = false;

			for (int k = 0; k < columns; k++) {
				if (i > 2 && i < 5)
					isOccupied = false;
				else if (startsOccupied && k % 2 == 0)
					isOccupied = true;
				else if (!startsOccupied && k % 2 == 1)
					isOccupied = true;
				else
					isOccupied = false;

				tiles[i][k] = new Tile(i, k, isOccupied);
			}
		}

	}

	private void assignPlayers() {
		// A method to assign each piece to a player.

		for (int i = 0; i < 3; i++) {

			for (int k = 0; k < columns; k++) {
				if (tiles[i][k].isOccupied()) {
					tiles[i][k].setPlayerNum(1);
				}
			}
		}

		for (int i = 5; i < 8; i++) {

			for (int k = 0; k < columns; k++) {
				if (tiles[i][k].isOccupied()) {
					tiles[i][k].setPlayerNum(2);
				}
			}
		}

	}

	public String checkWinner() {
		// A method which checks if the game is over by looping over all the pieces and
		// checking if a player has no pieces left. It returns a String which is
		// directly passed to the GUI.
		// This method was also meant to check if a player loses via having no viable
		// moves left via the checkAvailableMoves method, but this wasn't achieved.
		int p1count = 0;
		int p2count = 0;
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				if (tiles[i][k].getPlayerNum() == 1) {
					p1count++;
				}
				if (tiles[i][k].getPlayerNum() == 2) {
					p2count++;
				}
			}
		}
		if (p1count == 0) {
			return "Player 2 Wins";
		}
		if (p2count == 0) {
			return "Player 1 Wins";
		}

		else
			return null;
	}

	public boolean attemptMove(Tile from, Tile to) {
		// A method which takes two Tile objects as parameters and tries to move a piece
		// from one Tile to the other.

		if (from != to) {

			ArrayList<Tile> availableMoves = checkAvailableMoves(from);
			Tile chosenTile;

			for (int i = 0; i < availableMoves.size(); i++) {
				if (availableMoves.get(i).equals(to)) {
					chosenTile = availableMoves.get(i);
					to.replace(from);
					from.clear();

					if (chosenTile.getJumped() != null) {
						chosenTile.getJumped().clear();
						chosenTile.setJumped(null);
					}
					for (int k = 0; k < availableMoves.size(); k++) {
						availableMoves.get(k).setJumped(null);
					}

					return true;

				}
			}

			for (int k = 0; k < availableMoves.size(); k++) {
				availableMoves.get(k).setJumped(null);
			}

		}

		return false;

	}

	public ArrayList<Tile> checkAvailableMoves(Tile tile) {
		// A method which takes a Tile object as a parameter and returns a list of Tile
		// objects corresponding to the moves available to the given Tile. The method
		// checks both simple moves and jump moves.

		int frontRow;
		int nextFrontRow;
		ArrayList<Tile> availableMoves = new ArrayList<Tile>();
		Tile diagonalRight;
		Tile diagonalRightJump;
		Tile diagonalLeft;
		Tile diagonalLeftJump;

		if (tile.getPlayerNum() == 1) {
			frontRow = tile.getRowNum() + 1;
			nextFrontRow = frontRow + 1;

		} else {
			frontRow = tile.getRowNum() - 1;
			nextFrontRow = frontRow - 1;
		}

		if (frontRow >= 0 && frontRow < 8) {
			// checks if the piece can move right
			if (tile.getColNum() >= 0 && tile.getColNum() < 7) {
				diagonalRight = tiles[frontRow][tile.getColNum() + 1];
				if (diagonalRight.isOccupied() == false) {
					availableMoves.add(diagonalRight);
				}
				// checks if the piece can jump right
				if (nextFrontRow >= 0 && nextFrontRow < 8) {
					if (tile.getColNum() >= 0 && tile.getColNum() < 6 && tile.isOpponentsTile(diagonalRight) == true) {
						diagonalRightJump = tiles[nextFrontRow][tile.getColNum() + 2];
						if (diagonalRightJump.isOccupied() == false) {
							diagonalRightJump.setJumped(diagonalRight);
							availableMoves.add(diagonalRightJump);
						}
					}
				}
			}

			// checks if the piece can move left
			if (tile.getColNum() > 0 && tile.getColNum() <= 8) {

				diagonalLeft = tiles[frontRow][tile.getColNum() - 1];
				if (diagonalLeft.isOccupied() == false) {
					availableMoves.add(diagonalLeft);
				}
				// checks if the piece can jump left
				if (nextFrontRow >= 0 && nextFrontRow < 8) {
					if (tile.getColNum() > 1 && tile.getColNum() <= 7 && tile.isOpponentsTile(diagonalLeft) == true) {
						diagonalLeftJump = tiles[nextFrontRow][tile.getColNum() - 2];
						if (diagonalLeftJump.isOccupied() == false) {
							diagonalLeftJump.setJumped(diagonalLeft);
							availableMoves.add(diagonalLeftJump);
						}
					}
				}
			}
		}

		// checks all of the above, but including King moves (moving backward)
		if (tile.getIsKing()) {
			if (tile.getPlayerNum() == 1) {
				frontRow = tile.getRowNum() - 1;
				nextFrontRow = frontRow - 1;

			} else {
				frontRow = tile.getRowNum() + 1;
				nextFrontRow = frontRow + 1;
			}

			if (frontRow >= 0 && frontRow < 8) {
				// check right moves
				if (tile.getColNum() >= 0 && tile.getColNum() < 7) {

					diagonalRight = tiles[frontRow][tile.getColNum() + 1];
					if (diagonalRight.isOccupied() == false) {
						availableMoves.add(diagonalRight);
					}

					if (nextFrontRow >= 0 && nextFrontRow < 8) {
						if (tile.getColNum() >= 0 && tile.getColNum() < 6
								&& tile.isOpponentsTile(diagonalRight) == true) {
							diagonalRightJump = tiles[nextFrontRow][tile.getColNum() + 2];
							if (diagonalRightJump.isOccupied() == false) {
								diagonalRightJump.setJumped(diagonalRight);
								availableMoves.add(diagonalRightJump);
							}
						}
					}
				}

				// check left moves
				if (tile.getColNum() > 0 && tile.getColNum() <= 8) {

					diagonalLeft = tiles[frontRow][tile.getColNum() - 1];
					if (diagonalLeft.isOccupied() == false) {
						availableMoves.add(diagonalLeft);
					}

					if (nextFrontRow >= 0 && nextFrontRow < 8) {
						if (tile.getColNum() > 1 && tile.getColNum() <= 7
								&& tile.isOpponentsTile(diagonalLeft) == true) {
							diagonalLeftJump = tiles[nextFrontRow][tile.getColNum() - 2];
							if (diagonalLeftJump.isOccupied() == false) {
								diagonalLeftJump.setJumped(diagonalLeft);
								availableMoves.add(diagonalLeftJump);
							}
						}
					}
				}
			}
		}
		return availableMoves;
	}

}
