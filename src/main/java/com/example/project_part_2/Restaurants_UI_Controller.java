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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Restaurants_UI_Controller implements Initializable, Serializable {

    @FXML
    TableView<Restaurant> res_table;
    @FXML
    TableColumn<Restaurant,String> res_name;
    @FXML
    TableColumn<Restaurant,String> res_price;
    @FXML
    TableColumn<Restaurant,Double> res_score;
    @FXML
    TableColumn<Restaurant,Integer> res_cats;



    @FXML
    Label total;

    @FXML
    TableView<Food> food_table;
    @FXML
    TableColumn<Food,String> food_name;
    @FXML
    TableColumn<Food,Double> food_price;
    @FXML
    TableColumn<Food,String> food_cat;




    @FXML
    TableView<Food> cart;
    @FXML
    TableColumn<Food,String> cart_name;
    @FXML
    TableColumn<Food,Double> cart_price;



    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ArrayList<Food> foods=new ArrayList<>();
    ArrayList<Restaurant> restaurants=new ArrayList<>();

    ArrayList<Food> menu=new ArrayList<>();
    RestaurantsManager restaurantsManager=new RestaurantsManager();

    //RestaurantsManager restaurantsManager;





    @FXML
    TextField search_bar;

    //@FXML
    //Button connect_server

    @FXML
    Button exit;

    @FXML
    Button search;

//    @FXML
//    Button show_details_res;
//
//    @FXML
//    Button show_details_food;

    @FXML
    Button add_to_cart;

    @FXML
    Button go_back;


    @FXML
    Button rmv;

    @FXML
    ChoiceBox<String> dropdown;

    @FXML
    Button show_menu;
    @FXML
    Button place_order;



    @FXML
    Label confirmed;
    @FXML
    Label re1;
    @FXML
    Label re2;



    String[] search_ways={"By Name","By Zip","By Price",};




    ObservableList<Restaurant> restaurantObservableList= FXCollections.observableArrayList();
    ObservableList<Food> foodObservableList=FXCollections.observableArrayList();
    ObservableList<Food> to_cart=FXCollections.observableArrayList();

    Customer customer;
    ArrayList<Food> to_cart_helper =new ArrayList<>();

//    HashMap<Customer,ArrayList<Food>> order=new HashMap<>();

    Parent root;
    Stage stage;
    Scene scene;

//    @FXML
//    public void connect_server_action(ActionEvent e) throws Exception {
//        Socket socket=new Socket("127.0.0.1",1269);
//        ois=new ObjectInputStream(socket.getInputStream());
//        oos=new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject("C");
//        restaurantsManager=(RestaurantsManager) ois.readObject();
//        restaurantsManager.Add_Datas_FromFile();
//        restaurants=restaurantsManager.getRestaurants_list();
//        foods=restaurantsManager.getFood_menu();
//        ObservableList<Restaurant> restaurantObservableList= FXCollections.observableArrayList();
//        restaurantObservableList.addAll(restaurants);
//        res_table.setItems(restaurantObservableList);
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        confirmed.setVisible(false);
        re1.setVisible(false);
        re2.setVisible(false);
        try {
            socket = new Socket("192.168.156.242", 1269);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("C");
            restaurantsManager = (RestaurantsManager) ois.readObject();
            System.out.println("read");
            System.out.println(restaurantsManager.getRestaurants_list());
        }
        catch(Exception e)
        {e.printStackTrace();}


        String text;
        try {
            BufferedReader br = new BufferedReader(new FileReader("CurrentLoggedIn.txt"));
            text=br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] splited=text.split(",",-1);
        customer=new Customer(Integer.parseInt(splited[0]),splited[1]);

        res_name.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("Name"));
        res_price.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("Price"));
        res_score.setCellValueFactory(new PropertyValueFactory<Restaurant,Double>("Score"));
        res_cats.setCellValueFactory(new PropertyValueFactory<Restaurant,Integer>("zip_code"));

        food_name.setCellValueFactory(new PropertyValueFactory<Food,String>("name"));
        food_price.setCellValueFactory(new PropertyValueFactory<Food,Double>("price"));
        food_cat.setCellValueFactory(new PropertyValueFactory<Food,String>("category"));


        cart_name.setCellValueFactory(new PropertyValueFactory<Food,String>("name"));
        cart_price.setCellValueFactory(new PropertyValueFactory<Food,Double>("price"));


//        try {
//            restaurantsManager.Add_Datas_FromFile();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        restaurants=restaurantsManager.getRestaurants_list();
        foods=restaurantsManager.getFood_menu();
//        ObservableList<Restaurant> restaurantObservableList= FXCollections.observableArrayList();
        restaurantObservableList.addAll(restaurants);
        res_table.setItems(restaurantObservableList);
//        ObservableList<Food> foodObservableList=FXCollections.observableArrayList();
        foodObservableList.addAll(foods);
        food_table.setItems(foodObservableList);
        dropdown.getItems().addAll(search_ways);

    }



    @FXML
    public void search_button_action(ActionEvent e)
    {

        String text=search_bar.getText();
        String option= dropdown.getValue();
        ArrayList<Restaurant> searched=new ArrayList<>();

        if(option.equals("By Name"))
        {
            Restaurant res=restaurantsManager.restaurant_search_by_name(text);
            if(res.getName().equals("NO")){

                for(int i=0;i<res_table.getItems().size();i++)
                {
                    res_table.getItems().clear();
                }
                restaurantObservableList.removeAll();
                //restaurantObservableList.addAll(searched);
                res_table.setItems(restaurantObservableList);
                return;
            }
            searched.add(res);
        }
//        if(option.equalsIgnoreCase("By Score"))
//        {
//            String[] splited=text.split(",",-1);
//            double score1=Double.parseDouble(splited[0]);
//            double score2=Double.parseDouble(splited[1]);
//            searched=restaurantsManager.restaurant_search_by_score(score1,score2);
//        }
        if(option.equalsIgnoreCase("By Zip"))
        {
            int zip=Integer.parseInt(text);
            searched=restaurantsManager.restaurant_search_by_zip(zip);
        }
        if (option.equalsIgnoreCase("By Price"))
        {
            searched=restaurantsManager.restaurant_search_by_price(text);
        }
//        if (option.equalsIgnoreCase("By Category"))
//        {
//            searched=restaurantsManager.restaurant_search_by_category(text);
//        }


        for(int i=0;i<res_table.getItems().size();i++)
        {
            res_table.getItems().clear();
        }

        restaurantObservableList.removeAll();
        restaurantObservableList.addAll(searched);
        res_table.setItems(restaurantObservableList);
    }

    @FXML
    public void go_back_action(ActionEvent e)
    {
        for(int i=0;i<res_table.getItems().size();i++)
        {
            res_table.getItems().clear();
        }
        for (int i = 0; i < food_table.getItems().size(); i++) {
            food_table.getItems().clear();
        }

        foodObservableList.removeAll();
        foodObservableList.addAll(foods);
        food_table.setItems(foodObservableList);

        restaurantObservableList.removeAll();
        restaurantObservableList.addAll(restaurants);
        res_table.setItems(restaurantObservableList);
    }


//    @FXML
//    public void show_details_food(ActionEvent e) throws IOException {
//        Food food=food_table.getSelectionModel().getSelectedItem();
//        System.out.println(food.getName());
//        Parent root2= FXMLLoader.load(getClass().getResource("food_details_viewer.fxml"));
//        Stage stage2 = new Stage();
//        Scene scene2=new Scene(root2);
//        stage2.setScene(scene2);
//        stage2.setTitle(food.getName());
//        stage2.show();
//    }

    @FXML
    public void add_cart(ActionEvent e)
    {
        Food food=food_table.getSelectionModel().getSelectedItem();
        if(to_cart_helper.isEmpty())
        {
            to_cart_helper.add(food);
            to_cart.addAll(to_cart_helper);
            cart.setItems(to_cart);
            total.setText(Double.toString(food.getPrice()));
            return;
        }

        if (to_cart.get(to_cart.size() - 1).getRestaurantId() == food.getRestaurantId()) {
            for (int i = 0; i < food_table.getItems().size(); i++) {
                cart.getItems().clear();
            }
            to_cart_helper.add(food);
            to_cart.removeAll();
            to_cart.addAll(to_cart_helper);
            cart.setItems(to_cart);
            double total_price=0;
            for (Food f:to_cart_helper)
            {
                total_price=total_price+f.getPrice();
            }
            total.setText(String.format("%.2f",total_price));
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You Can't Order From Different Restaurants Simultaneously!!!");
        alert.show();
    }

    @FXML
    public void rmv_action(ActionEvent e)
    {
        Food food=cart.getSelectionModel().getSelectedItem();
        for(int i=0;i<food_table.getItems().size();i++)
        {
            cart.getItems().clear();
        }
        to_cart_helper.remove(food);
        to_cart.removeAll();
        to_cart.addAll(to_cart_helper);
        cart.setItems(to_cart);
        double total_price=0;
        for (Food f:to_cart_helper)
        {
            total_price=total_price+f.getPrice();
        }
        total.setText(String.format("%.2f",total_price));
    }

    @FXML
    public void show_menu_action(ActionEvent e)
    {
        Restaurant restaurant=res_table.getSelectionModel().getSelectedItem();
        System.out.println(restaurant.getMenu().get(0).getName());
        for(int i=0;i<food_table.getItems().size();i++)
        {
            food_table.getItems().clear();
        }
        ArrayList<Food> menu=restaurant.getMenu();
        foodObservableList.removeAll();
        foodObservableList.addAll(menu);
        food_table.setItems(foodObservableList);

    }


    @FXML
    public void exit_button(ActionEvent e) throws IOException {
//        oos.writeObject("EXIT");
//        stage=(Stage) ((Node)e.getSource()).getScene().getWindow();
//        stage.close();
//        socket.close();
        root = FXMLLoader.load(getClass().getResource("CustomerViewPage.fxml"));
        stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
       // stage.setTitle("Sign Up to DOOFS!!!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void place_order_action(ActionEvent e) throws IOException {
        if(!to_cart_helper.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure Want to Place Order?");
            alert.showAndWait();
            oos.writeObject("O");
            customer.setOrder(to_cart_helper);
            oos.writeObject(customer);
            to_cart.removeAll();
            to_cart_helper.clear();
            for (int i = 0; i < cart.getItems().size(); i++) {
                cart.getItems().clear();
            }
            total.setText("0.00");
            confirmed.setVisible(true);
            re1.setVisible(true);
            re2.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Your Cart is Empty!");
        alert.showAndWait();
    }
}
