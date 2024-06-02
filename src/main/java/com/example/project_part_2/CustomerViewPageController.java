package com.example.project_part_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;




public class CustomerViewPageController implements Initializable {


    ArrayList<Customer> customers=new ArrayList<>();
    @FXML
    TextField user;
    @FXML
    PasswordField password;
    @FXML
    Button loginbtn;
    @FXML
    Button BACK;
    @FXML
    Button signup;
    @FXML
    Button backto;
    @FXML
    Button go_back1;
    @FXML
    Button go_back2;
    @FXML
    TextField signupuser;
    @FXML
    PasswordField signuppass;
    @FXML
    Button signup2;
//    @FXML
//    Button refresh;
    @FXML
    Parent root;
    @FXML
    Stage stage;
    @FXML
    Scene scene;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            add_customers_from_file();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }









    public void add_customers_from_file() throws IOException {

        BufferedReader cus= new BufferedReader(new FileReader("customers.txt"));
        String data;
        while(true)
        {
            data=cus.readLine();
            if(data==null)
            {
                break;
            }
            String[] str=data.split(",");
            int id=Integer.parseInt(str[0]);
            String pass=str[1];
            customers.add(new Customer(id,pass));
        }
    }






    @FXML
    public void login_button_action(ActionEvent e) throws IOException {

        int num=Integer.parseInt(user.getText());
        String pass=password.getText();
        int cas=0;
        Customer found=new Customer(0,"0");
        for(Customer c:customers)
        {
            if(num==c.getUsrID())
            {
                found=c;
                cas=1;
                break;
            }
        }

        if(cas==0)
        {
            root= FXMLLoader.load(getClass().getResource("nouserfound.fxml"));
            stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            scene =new Scene(root);
            stage.setScene(scene);
            stage.setTitle(":( Invalid Login");
            stage.show();
            return;
        }
        if(found.getPass().equals(pass))
        {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("CurrentLoggedIn.txt"));
            String text=user.getText()+","+password.getText();
            bufferedWriter.write(text);
            bufferedWriter.close();

            root= FXMLLoader.load(getClass().getResource("Restaurants.fxml"));
            stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            scene =new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome to DOOFS!!!! Order As You Like! :)");
            stage.show();

        }
        else {
            root= FXMLLoader.load(getClass().getResource("nouserfound.fxml"));
            stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            scene =new Scene(root);
            stage.setScene(scene);
            stage.setTitle(":( Invalid Login");

            stage.show();
        }

    }




    @FXML
    public void signup_button_action(ActionEvent e) throws IOException {

        int id=Integer.parseInt(signupuser.getText());
        String pass=signuppass.getText();
        int cas=0;
        for(Customer c:customers)
        {
            if(c.getUsrID()==id)
            {
                cas=1;
            }
        }

        if (cas==1)
        {
            root= FXMLLoader.load(getClass().getResource("user_already_exists.fxml"));
            stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
            scene =new Scene(root);
            stage.setScene(scene);
            stage.setTitle("USER ALREADY EXISTS :'( ....");
            stage.show();
            return;
        }

        String text=signupuser.getText()+","+signuppass.getText();
        BufferedWriter bw=new BufferedWriter(new FileWriter("customers.txt"));
        bw.write(text);
        bw.newLine();
        String temp;
        for(Customer c:customers)
        {
            temp=Integer.toString(c.getUsrID())+","+c.getPass();
            bw.write(temp);
            bw.newLine();
        }
        bw.close();
        customers.add(new Customer(id,pass));
        root= FXMLLoader.load(getClass().getResource("SignUpCompleted.fxml"));
        stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DONE SIGNING UP!");
        stage.show();
    }







    @FXML
    public void go_back1(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CustomerViewPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void backto(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StartingPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        //stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    public void BACK(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("CustomerViewPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Login as customer...");
        stage.setScene(scene);
        stage.show();


    }





    @FXML
    public void got_to_signup(ActionEvent e) throws IOException {
        root= FXMLLoader.load(getClass().getResource("SignUp Page.fxml"));
        stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
        scene =new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up to DOOFS!!!");
        stage.show();
    }




    @FXML
    public void go_back2(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SignUp Page.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Sign Up to DOOFS!!!");
        stage.setScene(scene);
        stage.show();

    }







//    @FXML
//    public void refresh_action(ActionEvent e) throws IOException {
//        if(customers.isEmpty())
//        {
//            add_customers_from_file();
//        }
//    }


}
