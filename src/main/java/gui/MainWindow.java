package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import socket.Command;
import socket.Socket;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Socket socket;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image socketImage = new Image(this.getClass().getResourceAsStream("/images/DaSocket.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Socket instance */
    public void setSocket(Socket s) {
        socket = s;
        String response = socket.getWelcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getSocketDialog(response, socketImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Socket's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().strip();
        if (input.isEmpty()) {
            return;
        }

        String response = socket.getResponse(input);
        Command commandType = socket.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSocketDialog(response, socketImage, commandType)
        );
        userInput.clear();
    }
}
