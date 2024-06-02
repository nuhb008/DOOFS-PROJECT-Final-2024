package com.example.project_part_2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class RestaurantsManager implements Serializable {

    private ArrayList<Food> food_menu = new ArrayList<>();
    private ArrayList<Restaurant> restaurants_list = new ArrayList<>();

    public ArrayList<Restaurant> getRestaurants_list() {
        return restaurants_list;
    }

    public ArrayList<Food> getFood_menu() {
        return food_menu;
    }


    public void Add_Datas_FromFile() throws IOException {

        BufferedReader rest = new BufferedReader(new FileReader("restaurant.txt"));
        String data;
        while (true) {
            data = rest.readLine();
            if (data == null) {
                break;
            }
            String[] res_data = data.split(",", -1);
            int id = Integer.parseInt(res_data[0]);
            String name = res_data[1];
            double score = Double.parseDouble(res_data[2]);
            String price = res_data[3];
            int zip = Integer.parseInt(res_data[4]);
            ArrayList<String> catlist = new ArrayList<>();
            for (int i = 5; i < res_data.length; i++) {
                catlist.add(res_data[i]);
            }
            if (catlist.size() == 1) {
                addRestaurant(new Restaurant(id, name, score, price, zip, catlist.get(0)));
            }
            if (catlist.size() == 2) {
                addRestaurant(new Restaurant(id, name, score, price, zip, catlist.get(0), catlist.get(1)));
            }
            if (catlist.size() == 3) {
                addRestaurant(new Restaurant(id, name, score, price, zip, catlist.get(0), catlist.get(1), catlist.get(2)));
            }
        }
        rest.close();

        BufferedReader foods = new BufferedReader(new FileReader("menu.txt"));
        while (true) {
            data = foods.readLine();
            if (data == null) {
                break;
            }
            String[] food_data = data.split(",", -1);
            int id = Integer.parseInt(food_data[0]);
            String cat = food_data[1];
            String name = food_data[2];
            double price = Double.parseDouble(food_data[3]);
            addFood(new Food(id, cat, name, price));
        }

        foods.close();
    }


    // METHODS->
     /*
      ||    ADD RESTAURANT MENUS OPTION    ||
    */

    public int addRestaurant(Restaurant res) {
        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res.getName().equalsIgnoreCase(restaurants_list.get(i).getName())) {
                return 0;
            }
        }
        this.restaurants_list.add(res);


        return 1;
    }


    public int addFood(Food food_item) {
        for (int i = 0; i < food_menu.size(); i++) {
            if (food_item.getName().equalsIgnoreCase(food_menu.get(i).getName()) && food_item.getCategory().equals(food_menu.get(i).getCategory())) {
                return 0;
            }
        }
        this.food_menu.add(food_item);
        restaurant_search_by_id(food_item.getRestaurantId()).add_to_menu(food_item);


        return 1;
    }

    public void add_res_file(ArrayList<Restaurant> res) throws IOException {
        String rest;
        BufferedWriter rest_add = new BufferedWriter(new FileWriter("restaurant.txt"));

        for (int i = 0; i < res.size(); i++) {
            rest = "";
            String[] cats = res.get(i).getCategories();
            if (res.get(i).getCategories_count() == 1) {
                rest = Integer.toString(res.get(i).getId()) + "," + res.get(i).getName() + "," + Double.toString(res.get(i).getScore()) + "," + res.get(i).getPrice() + "," + Integer.toString(res.get(i).getZip_code()) + "," + cats[0];
            }
            if (res.get(i).getCategories_count() == 2) {
                rest = Integer.toString(res.get(i).getId()) + "," + res.get(i).getName() + "," + Double.toString(res.get(i).getScore()) + "," + res.get(i).getPrice() + "," + Integer.toString(res.get(i).getZip_code()) + "," + cats[0] + "," + cats[1];
            }
            if (res.get(i).getCategories_count() == 3) {
                rest = Integer.toString(res.get(i).getId()) + "," + res.get(i).getName() + "," + Double.toString(res.get(i).getScore()) + "," + res.get(i).getPrice() + "," + Integer.toString(res.get(i).getZip_code()) + "," + cats[0] + "," + cats[1] + "," + cats[2];
            }
            rest_add.write(rest);
            rest_add.newLine();
        }
        rest_add.close();
    }

    public void add_food_file(ArrayList<Food> foods) throws IOException {
        String rest;
        BufferedWriter menu_add = new BufferedWriter(new FileWriter("menu.txt"));

        for (int i = 0; i < foods.size(); i++) {
            rest = "";
            rest = Integer.toString(foods.get(i).getRestaurantId()) + "," + foods.get(i).getCategory() + "," + foods.get(i).getName() + "," + Double.toString(foods.get(i).getPrice());
            menu_add.write(rest);
            menu_add.newLine();
        }
        menu_add.close();
    }

    /*
      ||    SEARCH RESTAURANT MENUS OPTION    ||
    */

    public Restaurant restaurant_search_by_id(int res_id) {
        Restaurant res = new Restaurant("NO");
        if (restaurants_list.isEmpty()) {
            return res;
        }

        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res_id == restaurants_list.get(i).getId()) {
                res = restaurants_list.get(i);
                break;
            }
        }
        return res;
    }

    public Restaurant restaurant_search_by_name(String res_name) {
        Restaurant res = new Restaurant("NO");
        if (restaurants_list.isEmpty()) {
            return res;
        }

        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res_name.equalsIgnoreCase(restaurants_list.get(i).getName()) || restaurants_list.get(i).getName().toLowerCase().contains(res_name.toLowerCase())) {
                res = restaurants_list.get(i);
                break;
            }
        }
        return res;
    }

    public ArrayList<Restaurant> restaurant_search_by_score(double res_score1, double res_score2) {
        ArrayList<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res_score1 <= restaurants_list.get(i).getScore() && res_score2 >= restaurants_list.get(i).getScore()) {
                res.add(restaurants_list.get(i));
            }
        }
        return res;
    }

//    public ArrayList<Restaurant> restaurant_search_by_category(String res_cat) {
//        ArrayList<Restaurant> res = new ArrayList<Restaurant>();
//        String[] cats;
//        for (int i = 0; i < restaurants_list.size(); i++) {
//
//            cats = restaurants_list.get(i).getCategories();
//            for (int j = 0; j < restaurants_list.get(i).getCategories_count(); j++) {
//                if (res_cat.equalsIgnoreCase(cats[j]) || cats[j].toLowerCase().contains(res_cat)) {
//                    res.add(restaurants_list.get(i));
//                    break;
//                }
//            }
//        }
//        return res;
//    }

    public ArrayList<Restaurant> restaurant_search_by_price(String res_price) {
        ArrayList<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res_price.equalsIgnoreCase(restaurants_list.get(i).getPrice())) {
                res.add(restaurants_list.get(i));
            }
        }
        return res;
    }

    public ArrayList<Restaurant> restaurant_search_by_zip(int res_zip) {
        ArrayList<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurants_list.size(); i++) {
            if (res_zip == restaurants_list.get(i).getZip_code()) {
                res.add(restaurants_list.get(i));
            }
        }
        return res;
    }

    /*
            CATEGORY BASED RESTAURANT PART->
            CODE IT LATER

     */
//    public HashMap<String, ArrayList<String>> list_restaurant_by_category() {
//        HashMap<String, ArrayList<String>> list = new HashMap<>();
//        ArrayList<String> catlist = new ArrayList<>();
//        Restaurant res;
//        String[] cats;
//        int cas;
//        for (int i = 0; i < restaurants_list.size(); i++) {
//            res = restaurants_list.get(i);
//            cats = res.getCategories();
//            for (int j = 0; j < restaurants_list.get(i).getCategories_count(); j++) {
//                cas = 1;
//                for (int k = 0; k < catlist.size(); k++) {
//                    if (cats[j].equals(catlist.get(k))) {
//                        cas = 0;
//                        break;
//                    }
//                }
//                if (cas == 1) {
//                    catlist.add(cats[j]);
//                }
//            }
//        }
//
//        for (int i = 0; i < catlist.size(); i++) {
//            ArrayList<String> res_names = new ArrayList<>();
//            for (int j = 0; j < restaurants_list.size(); j++) {
//                res = restaurants_list.get(j);
//                cats = res.getCategories();
//                cas = 1;
//                for (int k = 0; k < res.getCategories_count(); k++) {
//                    if (cats[k].equalsIgnoreCase(catlist.get(i))) {
//                        cas = 0;
//                        break;
//                    }
//                }
//                if (cas == 0) {
//                    res_names.add(res.getName());
//                }
//            }
//            list.put(catlist.get(i), res_names);
//        }
//
//        return list;
//    }
//





    /*
      ||    SEARCH FOOD MENUS OPTION    ||
    */


    public ArrayList<Food> food_search_by_name(String food_name) {
        ArrayList<Food> foods = new ArrayList<>();

        for (int i = 0; i < food_menu.size(); i++) {
            if (food_name.equalsIgnoreCase(food_menu.get(i).getName()) || food_menu.get(i).getName().toLowerCase().contains(food_name.toLowerCase())) {
                foods.add(food_menu.get(i));
            }
        }
        return foods;

    }

    public ArrayList<Food> food_search_by_name_in_a_restaurant(String food_name, String res_name) {
        ArrayList<Food> foods;
        ArrayList<Food> foods2 = new ArrayList<>();

        Restaurant res = restaurant_search_by_name(res_name);
        foods = food_search_by_name(food_name);

        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getRestaurantId() == res.getId()) {
                foods2.add(foods.get(i));
            }
        }
        return foods2;
    }


    public ArrayList<Food> food_search_by_category(String cat) {
        ArrayList<Food> foods = new ArrayList<>();
        for (int i = 0; i < food_menu.size(); i++) {
            if (cat.equalsIgnoreCase(food_menu.get(i).getCategory()) || food_menu.get(i).getCategory().toLowerCase().contains(cat)) {
                foods.add(food_menu.get(i));
            }
        }
        return foods;
    }


    public ArrayList<Food> food_search_by_category_in_a_restaurant(String cat, String res_name) {
        ArrayList<Food> foods;
        ArrayList<Food> foods2 = new ArrayList<>();


        Restaurant res = restaurant_search_by_name(res_name);
        foods = food_search_by_category(cat);

        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getRestaurantId() == res.getId()) {
                foods2.add(foods.get(i));
            }
        }
        return foods2;
    }

    public ArrayList<Food> food_search_by_price_range(double pr1, double pr2) {
        ArrayList<Food> foods = new ArrayList<>();

        for (int i = 0; i < food_menu.size(); i++) {
            if (pr1 <= food_menu.get(i).getPrice() && pr2 >= food_menu.get(i).getPrice()) {
                foods.add(food_menu.get(i));
            }
        }
        return foods;

    }

    public ArrayList<Food> food_search_by_price_range_in_a_given_restaurant(double pr1, double pr2, String res_name) {
        ArrayList<Food> foods = new ArrayList<>();
        ArrayList<Food> foods2 = new ArrayList<>();

        Restaurant res = restaurant_search_by_name(res_name);
        foods = food_search_by_price_range(pr1, pr2);
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getRestaurantId() == res.getId()) {
                foods2.add(foods.get(i));
            }
        }
        return foods2;

    }


    public ArrayList<Food> costliest_foods_in_a_restaurant(String res_name) {
        ArrayList<Food> foods = new ArrayList<>();
        ArrayList<Food> foods2 = new ArrayList<>();
        Restaurant res = restaurant_search_by_name(res_name);


        for (int i = 0; i < food_menu.size(); i++) {
            if (food_menu.get(i).getRestaurantId() == res.getId()) {
                foods.add(food_menu.get(i));
            }
        }
        double highest = foods.get(0).getPrice();
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getPrice() > highest) {
                highest = foods.get(i).getPrice();
            }

        }
        for (int i = 0; i < foods.size(); i++) {
            if (highest == foods.get(i).getPrice()) {
                foods2.add(foods.get(i));
            }
        }
        return foods2;
    }


    public HashMap<String, Integer> list_of_restaurant_with_total_foods() {
        HashMap<String, Integer> list = new HashMap<>();
        int count = 0;
        Restaurant res;
        for (int i = 0; i < restaurants_list.size(); i++) {
            res = restaurants_list.get(i);
            count = 0;
            for (int j = 0; j < food_menu.size(); j++) {
                if (food_menu.get(j).getRestaurantId() == res.getId()) {
                    count++;
                }
            }
            list.put(res.getName(), count);

        }

        return list;
    }









}
