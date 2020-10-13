package sptvr19.java.myshop;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

class App {
    
    Thing things[] = new Thing[10];
    
    int max_count = 10;
    int max_price = 120;
    
    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    
    int money = 300;        
    Person pers = new Person(money);
    
    int ID1 = 0;
    int ID2 = 0;
    
    String names[] = {"Banana","Vodka","Sausage","Chair","Sofa","Pickaxe", "Knife"};    
      
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public void run() throws Exception {
        System.out.println(ANSI_PURPLE + " ---------------" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "----- MyShop -----" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + " ---------------" + ANSI_RESET);
        boolean work = true;
           
        FileReader fr = new FileReader("invent.txt");
        Scanner fr_scan = new Scanner(fr);
        
        String checking_array[] = new String[4];
                    
        while (fr_scan.hasNextLine()) {      
            checking_array = fr_scan.nextLine().split(" ");
            Thing this_thing = new Thing(checking_array[0],Integer.parseInt(checking_array[1]),Integer.parseInt(checking_array[2]),Integer.parseInt(checking_array[3]));
                for (int i = 0; i < things.length; i++) {
                    if (pers.getSpecialThings(i) != null && this_thing != null) { 
                        ID1 = pers.getSpecialThings(i).getID();
                        ID2 = this_thing.getID();
                        if (ID1 == ID2) {
                            Thing same = pers.getSpecialThings(i);
                            same.addCount();                                       
                            this_thing = null;
                        }
                    }
                }
                if (ID1 != ID2) {
                    pers.setThings(this_thing);
                }
            }   
        
        fr.close();
        
        reset();  
        
        FileWriter fw = new FileWriter("invent.txt");
        String StringToWrite;
        
        while (work) {
            System.out.println(ANSI_YELLOW + "\n1. Buy an Item" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "2. Check Storage" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "3. Reset Storage" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "4. Inventory/Money" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "5. Exit" + ANSI_RESET);
            
            System.out.print(ANSI_YELLOW + "\n> Choose the Option: " + ANSI_RESET);
            int function = scan.nextInt();
                         
            switch(function) {
                case 1:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.print(ANSI_YELLOW + "Choose ID of item: " + ANSI_RESET);
                    int choosed_item = scan.nextInt();                  
                    if (pers.getMoney() >= things[choosed_item-1].getPrice()) {
                        System.out.println(ANSI_GREEN + "You bought this item!" + ANSI_RESET);
                        if (things[choosed_item-1].getCount() > 1) {
                            things[choosed_item-1].setCount(things[choosed_item-1].getCount()-1);
                            pers.setMoney(pers.getMoney()- things[choosed_item-1].getPrice());
                            Thing this_thing = new Thing(things[choosed_item-1].getName(),1,things[choosed_item-1].getPrice(),things[choosed_item-1].getID());
                            for (int i = 0; i < things.length; i++) {
                                if (pers.getSpecialThings(i) != null && this_thing != null) { 
                                    ID1 = pers.getSpecialThings(i).getID();
                                    ID2 = this_thing.getID();
                                    if (ID1 == ID2) {
                                            Thing same = pers.getSpecialThings(i);
                                            same.addCount();   
                                            StringToWrite = (this_thing.getName() + " " + this_thing.getCount() + " " + this_thing.getPrice() + " " + this_thing.getID() + "\n");
                                            fw.write(StringToWrite);
                                            this_thing = null;
                                    }
                                }
                            }
                            if (ID1 != ID2) {
                                StringToWrite = (this_thing.getName() + " " + this_thing.getCount() + " " + this_thing.getPrice() + " " + this_thing.getID() + "\n");
                                fw.write(StringToWrite);
                            }
                            pers.setThings(this_thing);
                        } else {                        
                            pers.setMoney(pers.getMoney() - things[choosed_item-1].getPrice());
                            Thing this_thing = new Thing(things[choosed_item-1].getName(),1,things[choosed_item-1].getPrice(),things[choosed_item-1].getID());
                            things[choosed_item-1] = null;                            
                            for (int i = 0; i < things.length; i++) {
                                if (pers.getSpecialThings(i) != null && this_thing != null) { 
                                    int ID1 = pers.getSpecialThings(i).getID();
                                    int ID2 = this_thing.getID();
                                    if (ID1 == ID2) {
                                        Thing same = pers.getSpecialThings(i);
                                        same.addCount();
                                        StringToWrite = (this_thing.getName() + " " + this_thing.getCount() + " " + this_thing.getPrice() + " " + this_thing.getID() + "\n");
                                        fw.write(StringToWrite);
                                        this_thing = null;
                                    }
                                }
                            }
                            if (ID1 != ID2) {
                                StringToWrite = (this_thing.getName() + " " + this_thing.getCount() + " " + this_thing.getPrice() + " " + this_thing.getID() + "\n");
                                fw.write(StringToWrite);
                            }
                            pers.setThings(this_thing);
                        }
                    } else {
                        System.out.println(ANSI_RED + "You don't have enough money!" + ANSI_RESET);
                    }
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Storage: " + ANSI_RESET);
                    for (int i = 0; i < things.length; i++) {
                        if (things[i] != null) {
                            System.out.println(ANSI_CYAN + things[i].toString() + ANSI_RESET);
                        }
                    }
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    break;
                case 3:
                    reset();
                    System.out.println(ANSI_YELLOW + "\n---------------- " + ANSI_RESET);
                    System.out.println(ANSI_GREEN + "Storage Reseted " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "---------------- " + ANSI_RESET);
                    break;
                case 4:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Your money: " + pers.getMoney() + "$" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "\nYour inventory: " + ANSI_RESET);
                    for (int i = 0; i < pers.getThings().length; i++) {
                        System.out.print(ANSI_CYAN + pers.toString(i) + ANSI_RESET);
                    }
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    break;
                case 5:
                    fw.close();
                    work = false;
                    break;
                default: 
                    System.out.println(ANSI_YELLOW + "\n----------------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_RED + "There is no function like inputed! " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "----------------------------------- " + ANSI_RESET);
                    continue;
            }   

        }
    }
    
    public void reset() throws Exception {
        for (int i = 0; i < things.length-(rand.nextInt(things.length)); i++) {
            int random_name = rand.nextInt(names.length);
            int random_count = rand.nextInt(max_count) + 1;
            int random_price = rand.nextInt(max_price) + 1;
            things[i] = new Thing(names[random_name],random_count,random_price, i+1);
        }
    }
}

class Thing {
    private String name;
    private int count = 0;
    private int price;
    private int ID;
    Thing(String name, int count, int price, int ID) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.ID = ID;
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
    
    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void addCount() {
        this.count++;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    
    
    @Override
    public String toString() {
        return ID + "/ " + name + " x " + count + " (" + price + "$" + ')';
    }
}

class Person {
    private int money;
    private boolean breakthis;
    private Thing[] selfthings = new Thing[10];
    Person(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public Thing[] getThings() {
        return selfthings;
    }
    
    public Thing getSpecialThings(int i) {
        return selfthings[i];
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public void setThings(Thing thing) {
        for (int i = 0; i < this.selfthings.length; i++) {
            if (this.selfthings[i] == null) {
                this.selfthings[i] = thing;                    
                break;
            }
        }             
    }
    
    public String toString(int i) {
       if (this.selfthings[i] != null) {
            return "- " + this.selfthings[i].getName() + " (" + this.selfthings[i].getPrice() + "$) x " + this.selfthings[i].getCount() + "\n";  
       }
       return "";
    }
}