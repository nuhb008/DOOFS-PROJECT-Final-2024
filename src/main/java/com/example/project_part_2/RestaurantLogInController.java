package com.example.project_part_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RestaurantLogInController implements Initializable {

    @FXML
    TextField res_user;
    @FXML
    Button res_log;

    @FXML
    Button go_back;

    @FXML
    Button b2b;

    Parent root;
    Stage stage;
    Scene scene;
    ArrayList<Integer> res_id=new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("restaurant.txt"));
            String data;
            while (true) {
                data = br.readLine();
                if (data == null) {
                    break;
                }
                String[] str = data.split(",");
                int id = Integer.parseInt(str[0]);
                res_id.add(id);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void res_log_action(ActionEvent e) throws IOException {
        int id=Integer.parseInt(res_user.getText());
        int cas=0;
        for(int i=0;i<res_id.size();i++)
        {
            if(id==res_id.get(i))
            {
                cas=1;
                break;
            }
        }
        if(cas==1)
        {

            BufferedWriter bw=new BufferedWriter(new FileWriter("CurRes.txt"));
            bw.write(Integer.toString(id));
            bw.close();
            root = FXMLLoader.load(getClass().getResource("RestaurantOwnerView.fxml"));
            stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setTitle("Welcome to DOOFS!!!!");
            stage.setScene(scene);
            stage.show();
        }

        else {

            root = FXMLLoader.load(getClass().getResource("no_res_found.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Invalid Login :( ...");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void go_back_action(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RestaurantLogIn.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();
    }

    public void b2b(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StartingPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();
    }



}



