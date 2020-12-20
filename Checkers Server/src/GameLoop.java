
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GameLoop {
	// A class to handle the game logic on the server side.

	Player playerOne;
	Player playerTwo;
	private int x1;
	private int y1;
	private int x2;
	private int y2;

	public GameLoop(Socket player1, Socket player2) throws IOException {
		// The constructor takes the two clients sockets as inputs.

		this.playerOne = new Player(player1);
		this.playerTwo = new Player(player2);

		DataOutputStream os1 = new DataOutputStream(playerOne.getOutputStream());
		os1.writeInt(1); // Telling the player which number they are
		os1.flush();
		DataOutputStream os2 = new DataOutputStream(playerTwo.getOutputStream());
		os2.writeInt(2);
		os2.flush();
		DataInputStream is1;
		DataInputStream is2;
		is1 = new DataInputStream(playerOne.getInputStream());
		is2 = new DataInputStream(playerTwo.getInputStream());

		while (true) { // The main game loop, reading a set of coordinates from one player, passing
						// them to the other, and the repeating the process until the game ends.
			readFromClient(is1); // Player one goes first.
			sendToClient(os2);
			readFromClient(is2);
			sendToClient(os1);
		}
	}

	public void sendToClient(DataOutputStream os) throws IOException {
		// A method to write the coordinates of a move to a player, via two sets of x
		// and y positions corresponding to the old postion of the piece (x1, y1) and
		// the new position (x2, y2)
		os.writeInt(x1);
		os.writeInt(y1);
		os.writeInt(x2);
		os.writeInt(y2);
		os.flush();
	}

	public void readFromClient(DataInputStream is) throws IOException {
		// A method to read the coordinates from a client
		x1 = is.readInt();
		y1 = is.readInt();
		x2 = is.readInt();
		y2 = is.readInt();
	}
}
