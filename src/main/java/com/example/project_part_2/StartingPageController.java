package com.example.project_part_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingPageController {
    @FXML
    private Button customerbtn;
    @FXML
    private Button res_own_btn;

    @FXML
    ImageView image;

    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;
    @FXML
    public void customer_view(ActionEvent e) throws IOException {

        root = FXMLLoader.load(getClass().getResource("CustomerViewPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void res_view(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RestaurantLogIn.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as Restaurant Owner...");
        stage.setScene(scene);
        stage.show();
    }

}