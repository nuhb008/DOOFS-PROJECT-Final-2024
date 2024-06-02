package com.example.project_part_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RestaurantOwnerViewController implements Initializable {


    int LoggedIn;
    RestaurantsManager restaurantsManager;




    @FXML
    TableView<Customer> live_table;

    @FXML
    TableColumn<Customer,Integer> customerId;

    @FXML
    TableColumn<Customer,String> order_line;



    @FXML
    TableView<Food> order_details;
    @FXML
    TableColumn<Food,String> food_name;
    @FXML
    TableColumn<Food,Double> food_price;


    @FXML
    Label userid;
    @FXML
    Label price_details;
    @FXML
    Button close;

    @FXML
    Label name;
    @FXML
    Label price;
    @FXML
    Label score;


    @FXML
    Button view;
    @FXML
    Button exit;
    @FXML
    Button view_order;

    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;


    Parent root;
    Stage stage;
    Scene scene;



    ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();
    ObservableList<Food> orderd_food= FXCollections.observableArrayList();
    ArrayList<Customer> customers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("CurRes.txt"));
            String data;
            data=br.readLine();
            LoggedIn=Integer.parseInt(data);
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            socket = new Socket("127.0.0.1", 1269);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("R");
            oos.writeObject(LoggedIn);
            customers=(ArrayList<Customer>) ois.readObject();
            restaurantsManager=(RestaurantsManager) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (Customer c: customers)
        {
            c.order_stringer();
        }
//        if(customers!=null){
//            System.out.println(customers.get(0).getOrder_line());
//        }
        Restaurant needed_restaurant=restaurantsManager.restaurant_search_by_id(LoggedIn);
        name.setText(needed_restaurant.getName());
        price.setText(needed_restaurant.getPrice());
        score.setText(Double.toString(needed_restaurant.getScore()));

        customerId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("usrID"));
        order_line.setCellValueFactory(new PropertyValueFactory<Customer,String>("order_line"));
        customerObservableList.addAll(customers);
        live_table.setItems(customerObservableList);


        food_name.setCellValueFactory(new PropertyValueFactory<Food,String>("name"));
        food_price.setCellValueFactory(new PropertyValueFactory<Food,Double>("price"));

    }

    @FXML
    public void refresh_action(ActionEvent e) throws Exception {

        for(int i=0;i<live_table.getItems().size();i++)
        {
            live_table.getItems().clear();
        }
        oos.writeObject("AGAIN");
        oos.writeObject(LoggedIn);
        ArrayList<Customer> temp= (ArrayList<Customer>) ois.readObject();
        for(Customer c:temp)
        {
            c.order_stringer();
        }
        customerObservableList.removeAll();
        customerObservableList.addAll(temp);
        //System.out.println(temp);
        live_table.setItems(customerObservableList);
    }



    @FXML
    public void view_order_details(ActionEvent e) throws IOException {
        for(int i=0;i<order_details.getItems().size();i++){
            order_details.getItems().clear();
        }
        Customer c=live_table.getSelectionModel().getSelectedItem();
        ArrayList<Food> temp_order=c.getOrder();
        orderd_food.removeAll();
        orderd_food.addAll(temp_order);
        order_details.setItems(orderd_food);

        userid.setText(Integer.toString(c.getUsrID()));
        double total=0;
        for(Food f:temp_order)
        {
            total=total+f.getPrice();
        }

        price_details.setText(String.format("%.2f",total));
    }


    @FXML
    public void exit_action(ActionEvent e) throws IOException {

//        oos.writeObject("EXIT");
//        stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
//        stage.close();
//        socket.close();

        root = FXMLLoader.load(getClass().getResource("RestaurantLogin.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        // stage.setTitle("Sign Up to DOOFS!!!");
        stage.setScene(scene);
        stage.show();
    }


}
