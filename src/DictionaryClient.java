//
// This class implements a simple client that connects to a server to perform dictionary operations.
// Written by Michael Yixiao Wu | StuID: 1388097
//

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class DictionaryClient {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private static String serverAddress = "localhost";
    private static int port = 4444;

    public DictionaryClient(String serverAddress, int serverPort) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // Send a request to the server.
    public void sendRequest(String request) throws IOException {
        out.write(request + "\n");
        out.flush();
    }

    // Receive a response from the server.
    public String receiveResponse() throws IOException {
        return in.readLine();
    }

    // Close the connection.
    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        // Take command line arguments
        try {
            if (Integer.parseInt(args[1]) <= 1024 || Integer.parseInt(args[1]) >= 49151) {
                System.out.println("Error: Please enter a port number between 1024 and 49151");
                System.exit(-1);
            }
            port = Integer.parseInt(args[1]);
            serverAddress = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Arguments should be of the form \"java - jar DictionaryClient.jar <server-address> <server-port>\"");
        } catch (NumberFormatException e) {
            System.out.println("Invalid Port Number");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DictionaryClient client = new DictionaryClient(serverAddress, port);

            // Start GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ClientGUI(client);
                }
            });

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Prompt the user to enter an operation if using terminal.
                System.out.println("Enter operation:");
                String request = scanner.nextLine();

                if ("EXIT".equalsIgnoreCase(request)) {
                    break;
                }

                client.sendRequest(request);
                String response = client.receiveResponse();
                System.out.println("Response from server: " + response);
            }

            client.close();
            scanner.close();
        } catch (IOException e) {
            System.err.println("An error occurred while communicating with the server: " + e.getMessage());
        }
    }

}