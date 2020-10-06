package sptvr19.java.myshop;

import java.util.Scanner;
import java.util.Random;

class App {
    
    Thing things[] = new Thing[10];
    
    int max_count = 10;
    int max_price = 120;
    
    String names[] = {"Banana","Vodka","Sausage","Chair","Sofa"};    
       
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public void run() {
        System.out.println(ANSI_PURPLE + " --- MyShop --- " + ANSI_RESET);
        
        for (int i = 0; i < things.length-1; i++) {
            Random rand = new Random();
            int random_name = rand.nextInt(names.length);
            int random_count = rand.nextInt(max_count) + 1;
            int random_price = rand.nextInt(max_price);
            things[i] = new Thing(names[random_name],random_count,random_price);
            System.out.println(things[i].toString());
        }             
    }
}

class Thing {
    private String name;
    private int count;
    private int price;
    Thing(String name, int count, int price) {
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Thing{" + "name=" + name + ", count=" + count + ", price=" + price + '}';
    }
    
}