package com.example.project_part_2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Thread_handler implements Runnable {

    Socket socket;
    Thread thread;
    RestaurantsManager restaurantsManager;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    ArrayList<Food> ordered_list;
    static ArrayList<Customer> just_what_the_doctor_ordered=new ArrayList<>();


    Thread_handler(Socket socket, RestaurantsManager restaurantsManager) throws IOException {
        this.socket=socket;
        this.restaurantsManager=restaurantsManager;
        thread = new Thread(this);
        oos =new ObjectOutputStream(socket.getOutputStream());
        ois =new ObjectInputStream(socket.getInputStream());
        thread.start();
    }


    @Override
    public void run() {



        while(true)
        {
            try {
                String flag=(String) ois.readObject();
                System.out.println(flag);
                if(flag.equals("C"))
                {
                    oos.writeObject(restaurantsManager);
                }
                if(flag.equals("O"))
                {
                    Customer customer= (Customer) ois.readObject();
                    just_what_the_doctor_ordered.add(customer);
                    //System.out.println(just_what_the_doctor_ordered.get(0).getUsrID());
                }
                if(flag.equals("R"))
                {
                    int t=(Integer)ois.readObject();
                    ArrayList<Customer> eta_pathabo=new ArrayList<>();
                    if(just_what_the_doctor_ordered!=null){
                    for(Customer c:just_what_the_doctor_ordered)
                    {
                        if(c.getOrder().get(0).getRestaurantId()==t)
                        {
                            eta_pathabo.add(c);
                        }
                    }
                    oos.writeObject(eta_pathabo);
                    }
                    oos.writeObject(restaurantsManager);
                }
                if(flag.equals("AGAIN"))
                {
                    int t=(Integer)ois.readObject();
                    ArrayList<Customer> eta_pathabo=new ArrayList<>();
                    System.out.println(just_what_the_doctor_ordered);
                    if(just_what_the_doctor_ordered!=null){
                        for(Customer c:just_what_the_doctor_ordered)
                        {
                            if(c.getOrder().get(0).getRestaurantId()==t)
                            {
                                eta_pathabo.add(c);
                            }
                        }
                        System.out.println(eta_pathabo);
                        oos.writeObject(eta_pathabo);
                    }
                }
                if (flag.equals("EXIT"))
                {
                    break;
                }


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


public class TheServer {
    public static void main(String[] args) throws Exception
    {
        RestaurantsManager res_man= new RestaurantsManager();
        ServerSocket serverSocket= new ServerSocket(1269);
        res_man.Add_Datas_FromFile();


        while(true)
        {
            Socket skt=serverSocket.accept();
            System.out.println("Connected");
            new Thread_handler(skt,res_man);
        }

    }
}
