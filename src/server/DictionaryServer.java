//
// This is the main dictionary server class.
// Written by Michael Yixiao Wu | StuID: 1388097
//

package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DictionaryServer {

    private static int clientId = 0;
    public static int port = 4444;
    public static String filename = "";

    public static void main(String[] args) {

        // Take command line arguments
        try {
            if (Integer.parseInt(args[0]) <= 1024 || Integer.parseInt(args[0]) >= 49151) {
                System.out.println("Port number should be between 1024 and 49151. Exiting...");
                System.exit(-1);
            }
            port = Integer.parseInt(args[0]);
            filename = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Arguments should be of the form \"java - jar DictionaryServer.jar <port> <dictionary-file>\"");
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Port Number. Exiting...");
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Get dictionary.
        DictionaryManager dictionaryManager = new DictionaryManager();
        Dictionary dictionary = dictionaryManager.getDictionary();

        // Create thread pool.
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Start the server.
        try (ServerSocket listeningSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port + " for a connection");

            while (true) {
                Socket clientSocket = listeningSocket.accept();

                synchronized (DictionaryServer.class) {
                    clientId++;
                }

                // Submit the new task to the thread pool.
                executorService.submit(new ClientHandler(clientSocket, clientId, dictionary));

                System.out.println("Client connection number " + clientId + " accepted:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shut down the executor service
        executorService.shutdown();
    }
}