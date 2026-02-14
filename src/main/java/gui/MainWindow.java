package gui;

import javafx.application.Platform;
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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image socketImage = new Image(this.getClass().getResourceAsStream("/images/Socket.png"));
    // Image credits: NUS Students' Computing Club, retrieved: https://www.facebook.com/photo.php?fbid=952757871459097

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

        //@@author song-yiyang-reused
        // Reused from https://github.com/NUS-CS2103-AY2526-S2/forum/issues/157

        // autoscroll downwards after the new message bubbles have been added
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
        //@@author
    }
}
