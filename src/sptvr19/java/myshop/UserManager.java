package sptvr19.java.myshop;

import java.io.*;
import java.io.IOException;
import java.util.Scanner;

public class UserManager {
    UserSaver usersaver = new UserSaver();
    public UserManager() {
    }
    
    public User regUser(String login, String password, Person pers) throws IOException {
        User user = new User(login, password, pers);
        usersaver.saveUser(user);
        return user;
    }

    public boolean checkUser(String login, String password) throws FileNotFoundException {
        FileReader fr = new FileReader("users.txt");  // Создаем поток и сканнер для чтения файла с сохранением 
        Scanner fr_scan = new Scanner(fr);             
        
        String checking_array[]; // Создаем массив 
                    
        while (fr_scan.hasNextLine()) {      
            checking_array = fr_scan.nextLine().split(" ");   // Превращаем сохраненную строку в массив
            if (checking_array[0].equals(login) && checking_array[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
}