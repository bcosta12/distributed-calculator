import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Operator extends Thread {

	private static String ipServer;
	private static int port;
	
	static Double a;
	static Double b;
	static Double c;
	private static String testClose;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Operations operation;
		String ipServer = args[0];
		int port = Integer.parseInt(args[1]);
		String op = args[2];
		operation = Operations.valueOf(op);

		Socket socket = new Socket(ipServer, port);
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		DataInputStream input = new DataInputStream(socket.getInputStream());
		output.writeUTF("Operator '" + operation.getOperation() + "' is online");
		output.flush();
		output.writeUTF(operation.getOperation());
		output.flush();

		while (true) {

			testClose = input.readUTF();
			if(testClose.equals("close")){
				socket.close();
				System.out.println("close request");
				System.exit(0);
			} else{
				a = Double.valueOf(testClose);
			}
			b = Double.valueOf(input.readUTF());

			System.out.println("Request " + a + " '" + operation.getOperation() + "' " + b);

			if(null == operation.operation(a, b)){
				output.writeUTF("NaN");
				output.flush();
				System.out.println("Result NaN");
				
			} else {
				c = operation.operation(a, b);
				System.out.println("Result " + c);
				output.writeUTF(c.toString());
				output.flush();
			}
			
		}
	}
}