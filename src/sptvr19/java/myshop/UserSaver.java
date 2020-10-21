package sptvr19.java.myshop;

import java.io.*;

public class UserSaver {
     
    public void saveUser(User user) throws IOException {
        FileWriter fw = new FileWriter("users.txt", true);
        String stringtowrite = (String)(user.getLogin() + " " + user.getPassword() + " " + user.getPers().getMoney() + "\n");
        fw.write(stringtowrite);
        fw.flush(); fw.close();
    }
}
