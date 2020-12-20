
public class Player {
	// A class to handle player info on the client side.

	private int playerID;
	private boolean isMyTurn;

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	private boolean hasWon;

	public boolean isMyTurn() {
		return isMyTurn;
	}

	public Player(int playerID) {

		this.playerID = playerID;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
}
