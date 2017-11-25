

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

	static ServerSocket server;

	public static void main(String[] args) throws IOException {
		int port = Integer.parseInt(args[0]);
		server = new ServerSocket(port, 4);
		Map<String, Socket> operators = new HashMap<>();
		Scanner scanner = new Scanner(System.in);
		Socket socket;
		String operator;
		String expression;
		String terms[];
		DataInputStream input[] = new DataInputStream[4];
		DataOutputStream output[] = new DataOutputStream[4];

		System.out.println("Starting Client");
		System.out.println("Waiting for Operators\n");
		for (int i = 0; i < 4; i++) {

			socket = server.accept();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream ou = new DataOutputStream(socket.getOutputStream());
			System.out.println(in.readUTF());
			operator = in.readUTF();
			input[i] = in;
			output[i] = ou;
			operators.put(operator, socket);
		}
		System.out.println("\nAll operators are online");
		

		while (true) {
			printMenu();
			
			while(true){
				expression = scanner.nextLine();
				if(expression.equals("exit")){
					break;
				}
				else if (expression.split(" ").length != 3){
					System.out.println("Wrong format, try again");
				} else {
					break;
				}
			}

			terms = expression.split(" ");
			if(terms[0].equals("exit")){
				DataOutputStream close = new DataOutputStream(operators.get("+").getOutputStream());
				close.writeUTF("close");
				operators.get("+").close();
				close = new DataOutputStream(operators.get("-").getOutputStream());
				close.writeUTF("close");
				operators.get("-").close();
				close = new DataOutputStream(operators.get("*").getOutputStream());
				close.writeUTF("close");
				operators.get("*").close();
				close = new DataOutputStream(operators.get("/").getOutputStream());
				close.writeUTF("close");
				operators.get("/").close();
				System.exit(0);
			}
			Socket s = operators.get(terms[1]);
			DataInputStream inp = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF(terms[0]);
			out.flush();
			out.writeUTF(terms[2]);
			out.flush();
			System.out.println(inp.readUTF());

		}

	}

	private static void printMenu() {
		System.out.println("\nFormat: number operation number");
		System.out.println("Exemple: '2 + 2' \n");
	}
}
