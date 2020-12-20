
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
	
	// A class to handle the player data on the server side.

	private Socket playerSocket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	public Player(Socket playerSocket) { // A constructor which takes the a corresponding player socket as a parameter

		this.playerSocket = playerSocket;
		try {
			inputStream = new DataInputStream(playerSocket.getInputStream());
			outputStream = new DataOutputStream(playerSocket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Socket getPlayerSocket() {
		return playerSocket;
	}

	public void setPlayerSocket(Socket playerSocket) {
		this.playerSocket = playerSocket;
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
