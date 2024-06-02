package com.example.project_part_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DOOFS extends Application {

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DOOFS.class.getResource("StartingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("DOOFS!");
        stage.setScene(scene);
        stage.show();

        // Establish connection to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            // Replace "localhost" with the server's IP address
            Socket socket = new Socket("192.168.156.242", 1269);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            // You can now use oos and ois to communicate with the server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
