package serverclientencryption;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private TextArea displayArea;
    private TextField messageField;
    private PrintWriter out;

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
        
        primaryStage.setTitle("TCP Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        startClient();
    }

    private void startClient() {
        new Thread(() -> {
            try (Socket socket = new Socket("localhost", 12345)) {
                displayArea.appendText("Connected to server\n");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

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
                out.println(encryptedMessage); // Send encrypted message to the server
                messageField.clear(); // Clear the text field after sending
            }
        } catch (Exception e) {
            displayArea.appendText("Error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
