
import java.io.*;
import java.net.*;

public class Server{
	// A class with a main method for the server
	private static int PORT = 0451;

	public static void main(String[] args) throws IOException {
		// Makes a server object
		ServerSocket listener = new ServerSocket(PORT);
		// Waits for a connection and creates the clients
		Socket playerOne = listener.accept();
		Socket playerTwo = listener.accept();
		new GameLoop(playerOne, playerTwo);
		
		
	}

	
}