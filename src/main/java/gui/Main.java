package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socket.Socket;

/**
 * A GUI for Socket using FXML.
 */
public class Main extends Application {

    private Socket socket = new Socket();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<gui.MainWindow>getController().setSocket(socket); // inject the Socket instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
