import javax.swing.ImageIcon;

public class Tile {
	// A class to handle the model of the tiles.

	private int colNum;
	private int rowNum;
	private boolean isKing;
	private boolean occupied;
	private int playerNum;
	private Tile jumped;

	public Tile(int rowNum, int colNum, boolean occupied) {
		// When a tile is made, it has a row, column and knows whether it starts with a
		// piece.
		this.colNum = colNum;
		this.rowNum = rowNum;
		this.occupied = occupied;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}

	public Tile getJumped() {
		return jumped;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public boolean isOpponentsTile(Tile tile) {
		// The method takes one Tile as a parameter and checks whether this tile has a
		// piece of the opposite colour.
		if (playerNum == 0 || tile.getPlayerNum() == 0)
			return false;
		if (this.playerNum == tile.playerNum)
			return false;
		else
			return true;
	}

	public boolean getIsKing() {
		return isKing;
	}

	public int getColNum() {
		return colNum;
	}

	public int getRowNum() {
		return rowNum;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void clear() {
		// A method to clear a tile of a piece.
		occupied = false;
		playerNum = 0;
	}

	public void replace(Tile newPiece) {
		// A method which takes a Tile as a parameter and moves the piece in that tile
		// to this one.
		occupied = newPiece.occupied;
		playerNum = newPiece.playerNum;
		setKing(newPiece.isKing);
	}

	public boolean equals(Tile tile) {
		// An equals methods to compare to tiles.
		if (tile.colNum == this.colNum && tile.rowNum == this.rowNum) {
			return true;
		} else
			return false;
	}

	public void setJumped(Tile jumped) {
		this.jumped = jumped;
	}

}
