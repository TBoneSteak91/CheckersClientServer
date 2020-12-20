
import java.io.*;
import java.net.*;

public class Client {
	// A class with a main method for the client.
	private static int PORT = 0451;
	private static String server = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		// Instantiating the three main objects: model, controller and view.

		Board boardModel = new Board();
		Socket socket = new Socket(server, PORT);
		CheckersController controller = new CheckersController(boardModel, socket);
		BoardView boardView = new BoardView(controller, boardModel);
		controller.setView(boardView);
		boardView.setVisible(true); // Making the window visible.
		new Thread(controller).start();

	}

}