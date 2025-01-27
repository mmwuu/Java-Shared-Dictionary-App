//
// This class is responsible for handling the client's requests. It implements the Runnable interface.
// Written by Michael Yixiao Wu | StuID: 1388097
//

package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private int clientId;
    private Dictionary dictionary;

    public ClientHandler(Socket clientSocket, int clientId, Dictionary dictionary) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
        this.dictionary = dictionary;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection accepted from " + clientSocket.getInetAddress().getHostName() + " | Client Number: " + clientId);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String clientMsg;
            while ((clientMsg = in.readLine()) != null) {
                System.out.println("Request from client " + clientId + ": " + clientMsg);

                // Parse the client's message
                String[] parts = clientMsg.split(" ", 3);
                String operation = parts[0].toUpperCase();
                String word = parts.length > 1 ? parts[1].toLowerCase() : "";
                word = word.toLowerCase();
                String response = "";

                // Check if operation is valid.
                if (!Arrays.asList("ADD", "REMOVE", "QUERY", "UPDATE").contains(operation)) {
                    response = "Invalid operation";
                } else {
                    switch (operation) {
                        case "ADD":
                            if (parts.length < 2 || word.isEmpty()) {
                                response = "Error: No word provided";
                                break;
                            }
                            else if (parts.length < 3 || parts[2].isEmpty()) {
                                response = "Word not added: Ensure that the word has at least one meaning.";
                                break;
                            }
                            // Meanings should be split with ';' - will show instructions in UI.
                            String[] meanings = parts[2].split("; ");
                            response = dictionary.addWord(word, Arrays.asList(meanings));
                            break;
                        case "REMOVE":
                            if (parts.length < 2 || word.isEmpty()) {
                                response = "Error: No word provided";
                                break;
                            }
                            response = dictionary.removeWord(word);
                            break;
                        case "QUERY":
                            List<String> queriedMeanings = dictionary.queryWord(word);
                            response = String.join("; ", queriedMeanings);
                            break;
                        case "UPDATE":
                            if (parts.length < 2 || word.isEmpty()) {
                                response = "Error: No word provided";
                                break;
                            }
                            else if (parts.length < 3 || parts[2].isEmpty()) {
                                response = "Word not added: Ensure that the word has at least one meaning.";
                                break;
                            }
                            String[] updatedMeanings = parts[2].split("; ");
                            response = dictionary.updateWord(word, Arrays.asList(updatedMeanings));
                            break;
                        default:
                            response = "Invalid operation";
                            break;
                    }
                }

                // Send the response to the client.
                try {
                    out.write(response + "\n");
                    out.flush();
                } catch (IOException e) {
                    System.err.println("An error occurred while writing to the client socket: " + e.getMessage());
                }
                System.out.println("Response sent");
            }
            System.out.println("Client " + clientId + " disconnected");

        } catch (SocketException e) {
            System.out.println("Connection was closed abruptly");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
