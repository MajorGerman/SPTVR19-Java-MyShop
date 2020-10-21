package sptvr19.java.myshop;

import java.io.*;
import java.util.Scanner;
import static sptvr19.java.myshop.App.ANSI_GREEN;
import static sptvr19.java.myshop.App.ANSI_RED;
import static sptvr19.java.myshop.App.ANSI_RESET;
import static sptvr19.java.myshop.App.ANSI_YELLOW;

class SecureManager {
    
    Scanner sec_scan = new Scanner(System.in);
    
    public Person checkTask(int money) throws IOException, Exception {
        
        System.out.println(ANSI_YELLOW + "\n1. Sign Up" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "2. Log In" + ANSI_RESET);   
        System.out.println(ANSI_YELLOW + "3. Exit" + ANSI_RESET);
        int option;
        
        String login;
        String password;    
        Person pers = new Person(money);
        User user = null;
       
        UserManager userManager = new UserManager();
        
        while (true) {
            try {
                System.out.print(ANSI_YELLOW + "\n> Choose the Option: " + ANSI_RESET);
                option = sec_scan.nextInt();
            } catch (Exception e) {
                System.out.println(ANSI_RED + "\nThere is no function like inputed! " + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "-----------------------------------" + ANSI_RESET);
                continue;
            }
         
            switch(option) {
                case 1:        
                    System.out.println(ANSI_YELLOW + "-------------------------------" + ANSI_RESET);
                    System.out.print(ANSI_YELLOW + "\nInput your login: " + ANSI_RESET);
                    sec_scan.nextLine();
                    login = sec_scan.nextLine();
                    System.out.print(ANSI_YELLOW + "Input your password: " + ANSI_RESET);
                    password = sec_scan.nextLine();
                    user = userManager.regUser(login, password, pers);
                    pers = checkSave(pers, login, password, user);
                    System.out.println(ANSI_GREEN + "User was successfully created!" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "-------------------------------" + ANSI_RESET);
                    pers.setLogin(login);
                    return pers;
                case 2:
                    System.out.println(ANSI_YELLOW + "-------------------------------" + ANSI_RESET);
                    System.out.print(ANSI_YELLOW + "\nInput your login: " + ANSI_RESET);
                    sec_scan.nextLine();
                    login = sec_scan.nextLine();
                    System.out.print(ANSI_YELLOW + "Input your password: " + ANSI_RESET);
                    password = sec_scan.nextLine();
                    if (userManager.checkUser(login, password)) {
                        System.out.println(ANSI_GREEN + "Welcome back, " + login + "!" + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "-------------------------------" + ANSI_RESET);
                        pers = checkSave(pers, login, password, user);
                        pers.setLogin(login);
                        return pers;
                    }
                    System.out.println(ANSI_RED + "\nThis user doesn't exist! " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "-----------------------------------" + ANSI_RESET);
                    continue;
                case 3:
                    System.exit(0);
                    break;
                default: 
                    break;
                }
        return pers;
        }
    }

    public Person checkSave(Person pers, String login, String password, User user) throws Exception {      
        FileReader fr = new FileReader("users.txt");  // Создаем поток и сканнер для чтения файла с сохранением 
        Scanner fr_scan = new Scanner(fr);             
        
        String checking_array[]; // Создаем массив 
                    
        while (fr_scan.hasNextLine()) {    
            checking_array = fr_scan.nextLine().split(" ");   // Превращаем сохраненную строку в массив
                    
            if (login.equals(checking_array[0]) && password.equals(checking_array[1])) {
                pers.setMoney(Integer.parseInt(checking_array[2]));
            }
        }

    fr.close();
    return pers;
    }  
    
    public void saveMoney(int money, String login) throws IOException {
        FileReader fr = new FileReader("users.txt");  // Создаем поток и сканнер для чтения файла с сохранением 
        Scanner fr_scan = new Scanner(fr); 
        FileWriter fw = new FileWriter("users_temp.txt");
        
        String checking_array[];
        String stringtowrite;
              
        while (fr_scan.hasNextLine()) {
            checking_array = fr_scan.nextLine().split(" ");
            if (login.equals(checking_array[0])) {
                System.out.println("asdjasjadsjdasdas");
                stringtowrite = (String)(checking_array[0] + " " + checking_array[1] + " " + money + " \n");
                fw.write(stringtowrite);
            } else {
                stringtowrite = (String)(checking_array[0] + " " + checking_array[1] + " " + checking_array[2] + " \n");
                fw.write(stringtowrite);
            }
        }
        
        fr.close();          
        fw.flush(); fw.close();  
        
        FileReader fr2 = new FileReader("users_temp.txt");  // Создаем поток и сканнер для чтения файла с сохранением 
        Scanner fr2_scan = new Scanner(fr2); 
        
        FileWriter fw2 = new FileWriter("users.txt");
        
        while (fr2_scan.hasNextLine()) {
            stringtowrite = (String)(fr2_scan.nextLine() + " \n");
            fw2.write(stringtowrite);           
        }
        
        fr2.close();          
        fw2.flush(); fw2.close();  
    } 
}
