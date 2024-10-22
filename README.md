### README for Encrypted Client-Server Communication System

#### Overview
This project is a simple client-server system where communication between the client and server is encrypted using basic encryption and decryption functions. The system is built using Java, with the following key components:
1. **Client**: A graphical user interface (GUI) application built using JavaFX for sending and receiving encrypted messages.
2. **Server**: A multi-threaded TCP server that receives encrypted messages from the client, decrypts them, and can send encrypted responses.
3. **Encryptor**: A utility class that handles message encryption and decryption.

#### File Structure
- `Client.java`: Contains the client-side code responsible for sending and receiving encrypted messages to/from the server.
- `Server.java`: Contains the server-side code that listens for client connections and manages encrypted communication.
- `Encryptor.java`: A utility class for encrypting and decrypting messages using simple methods.

#### How It Works
1. The client initiates a connection to the server and sends messages through a GUI.
2. Messages sent from the client are encrypted using the `Encryptor` class and then transmitted to the server.
3. The server receives the encrypted message, decrypts it, and processes it (e.g., logs it or sends a response).
4. The server can also send back an encrypted response, which the client will decrypt upon receiving.

#### Requirements
- Java Development Kit (JDK) 8 or above
- JavaFX library for the GUI on the client-side

#### Setup Instructions

1. **Clone the Repository:**
   Clone the project to your local machine using the following command:
   ```
   git clone <repository-url>
   ```

2. **Compile the Project:**
   You can compile the project using `javac`:
   ```
   javac -cp .:javafx-sdk/lib/* *.java
   ```

3. **Run the Server:**
   First, start the server by running the `Server.java` file. It will listen on port `12345` for incoming client connections.
   ```
   java Server
   ```

4. **Run the Client:**
   Once the server is up and running, you can start the client by running the `Client.java` file. It will connect to the server and allow you to send encrypted messages.
   ```
   java Client
   ```

#### Usage
- **Client**: Enter a message in the text field and click the "Send" button to send an encrypted message to the server.
- **Server**: The server will automatically receive, decrypt, and display the incoming message. It can also send a response back to the client.

#### Encryptor
The `Encryptor` class contains the following methods:
- `encrypt(String message)`: Encrypts a plain-text message.
- `decrypt(String encryptedMessage)`: Decrypts an encrypted message.

Both encryption and decryption are simple and demonstrate basic string manipulation.

#### Example
1. The client sends the message "Hello".
2. The message is encrypted and appears like "Encrypted: sd7fs7df" on the client side.
3. The server receives the encrypted message and decrypts it to display "Hello" on its console.

#### License
This project is open-source and available under the MIT License.

#### Author
Developed by [Your Name].

This README provides the basic usage instructions for setting up and running the encrypted communication system. If you need any further customization or additional details, feel free to modify this document.
