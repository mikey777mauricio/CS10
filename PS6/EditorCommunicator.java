import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;        // to server
	private BufferedReader in;        // from server
	protected Editor editor;        // handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4243);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		} catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			String line; // initialize line
			// while message
			while ((line = in.readLine()) != null) {
				// new message
				Message msg = new Message(line);
				// print received line
				System.out.println("Received " + line);
				// handle message
				msg.handle(editor.getSketch());
				editor.repaint(); // repaint


			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			System.out.println("server hung up");
		}
	}

	// Send editor requests to the server

	/**
	 * add
	 * method sends message to add shape to sketch
	 * @param shape shape to add
	 */
	public void add(Shape shape){
		send("add " + shape.toString());
	}

	/**
	 * move
	 * method sends message to move shape
	 * @param index index of shape
	 * @param dx change x
	 * @param dy change y
	 */
	public void move(int index, int dx, int dy){
		send("move " + index + " " + dx + " " + dy);
	}

	/**
	 * delete
	 * method sends message to delete shape from sketch
	 * @param index of shape to delete
	 */
	public void delete(int index){
		send("delete " + index);
	}

	/**
	 * recolor
	 * method sends a message to recolor shape
	 * @param index index of shape
	 * @param color color
	 */
	public void recolor(int index, int color){
		send("recolor " + index + " " + color);
	}


}


