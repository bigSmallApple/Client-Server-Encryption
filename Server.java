package serverclientencryption;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    private TextArea displayArea;
    private TextField messageField;
    private PrintWriter out;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    @Override
    public void start(Stage primaryStage) {
        displayArea = new TextArea();
        displayArea.setEditable(false);  // Display area for communication and processes
        
        messageField = new TextField();
        messageField.setPromptText("Enter message...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        VBox layout = new VBox(10, messageField, sendButton, displayArea);
        Scene scene = new Scene(layout, 400, 400);
        
        primaryStage.setTitle("TCP Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle window close event
        primaryStage.setOnCloseRequest((WindowEvent we) -> shutdown());

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12345); // Start server socket on port 12345
                displayArea.appendText("Server started on port 12345\n");

                clientSocket = serverSocket.accept(); // Accept client connection
                displayArea.appendText("Client connected\n");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String incomingMessage;
                while ((incomingMessage = in.readLine()) != null) {
                    String decryptedMessage = Encryptor.decrypt(incomingMessage);  // Decrypt received message
                    displayArea.appendText("Received (Decrypted): '" + decryptedMessage + "'\n");
                }
            } catch (Exception e) {
                displayArea.appendText("Error: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        try {
            String message = messageField.getText();
            if (message != null && !message.isEmpty()) {
                String encryptedMessage = Encryptor.encrypt(message);  // Encrypt message
                displayArea.appendText("Sent (Encrypted): '" + encryptedMessage + "'\n");
                out.println(encryptedMessage); // Send encrypted message to the client
                messageField.clear(); // Clear the text field after sending
            }
        } catch (Exception e) {
            displayArea.appendText("Error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void shutdown() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close(); // Close client socket
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Close server socket
            }
            displayArea.appendText("Server shut down\n");
        } catch (Exception e) {
            displayArea.appendText("Error while shutting down: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        System.exit(0); // Exit the application
    }

    public static void main(String[] args) {
        launch(args);
    }
}
