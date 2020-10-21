package sptvr19.java.myshop;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

class App {
    
    Thing things[] = new Thing[10]; // Создаем массив для предметов в магазине
    
    int max_count = 10;  // Устанавливаем максимальное количество предметов
    int max_price = 80; // Устанавливаем максимальную цену за 1 предмет
    
    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    
    int money = 400;  // Устанавливаем базовое количество денег
    
    int ID1 = 0; // Создаем переменные для сравнения ID предметов
    int ID2 = 0; 
    
    String names[] = {"Banana","Vodka","Sausage","Sofa","Pickaxe", "Knife"};  // Создаем массив со всеми предметами, которые могут быть в магазине
      
    public static final String ANSI_RESET = "\u001B[0m"; // Добавляем цвета
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public void run() throws Exception {
        System.out.println(ANSI_PURPLE + "><><><><><><><><><><><><" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "><>      MyShop      <><" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "><>  by Georg Laabe  <><" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "><><><><><><><><><><><><" + ANSI_RESET);
        
        boolean work = true;   
        
        SecureManager secureManager = new SecureManager();   //  Добавляем менеджер защиты     
        
        Person pers = secureManager.checkTask(money);        // Выполняем авторизацию/регистрацию и создаем нового человека 
                     
        checkSave(pers); // Проверяем сохраненные предметы и, при их наличии, добавляем их в инвентарь
        reset();     // Делаем первоначальный перезапуск, чтобы в магазине появились предметы
        
        FileWriter fw = new FileWriter("invent.txt", true); // Запускаем поток записи в файл
        String StringToWrite;                               // Создаем переменную для её записи в файл
        
        while (work) {
            System.out.println(ANSI_YELLOW + "\n1. Buy an Item" + ANSI_RESET);   // Выводим меню действий
            System.out.println(ANSI_YELLOW + "2. Check Storage" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "3. Reset Storage" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "4. Inventory/Money" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "5. Logout" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "6. Exit" + ANSI_RESET);
            
            System.out.print(ANSI_YELLOW + "\n> Choose the Option: " + ANSI_RESET);
            int function = scan.nextInt();  // Считываем введенный номер действия
                         
            switch(function) {
                case 1:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.print(ANSI_YELLOW + "Choose ID of item: " + ANSI_RESET);
                    int choosed_item = scan.nextInt();      // Считываем выбранный ID предмета                
                    try {   // В случае, если не ошибки (вроде отсутсвия выбранного ID)                     
                        if (pers.getMoney() >= things[choosed_item-1].getPrice()) {   // Если нам хватает денег
                            System.out.println(ANSI_GREEN + "\nYou bought this item!" + ANSI_RESET);
                            if (things[choosed_item-1].getCount() > 1) {    // Если выбранных предметов на складе больше одного
                                things[choosed_item-1].setCount(things[choosed_item-1].getCount()-1);                              
                                pers.setMoney(pers.getMoney()- things[choosed_item-1].getPrice());
                                Thing this_thing = new Thing(things[choosed_item-1].getName(), 
                                                            1,
                                                            things[choosed_item-1].getPrice(),
                                                            things[choosed_item-1].getID());  // Создаем предмет, как выбранный
                                this_thing.setPers(pers);
                                for (int i = 0; i < things.length; i++) {   
                                    if (pers.getSpecialThings(i) != null && this_thing != null) { 
                                        ID1 = pers.getSpecialThings(i).getID();     // Получаем ID i-го предмета
                                        ID2 = this_thing.getID();                   // Получаем ID выбранного предмета
                                        if (ID1 == ID2) {                           // Сравниваем ID
                                                Thing same = pers.getSpecialThings(i);
                                                same.addCount();  // Увеличиваем количество первого похожего предмета на 1  
                                                StringToWrite = (String)(this_thing.getName() + " "
                                                                      + this_thing.getCount() + " "
                                                                      + this_thing.getPrice() + " "
                                                                      + (-1*this_thing.getID()-pers.getItemsCount()*rand.nextInt(1000000)) + " "
                                                                      + this_thing.getPers().getLogin() + "\n");
                                                fw.write(StringToWrite); fw.flush(); // Сохраняем в файл купленный предмет
                                                this_thing.setPers(pers);
                                                this_thing = null; // Обнуляем похожий второй предмет
                                        }
                                    }
                                }
                                if (ID1 != ID2) {  // Если ID не совпали
                                    StringToWrite = (String)(this_thing.getName() + " "
                                                          + this_thing.getCount() + " "
                                                          + this_thing.getPrice() + " "
                                                          + (-1*this_thing.getID()-pers.getItemsCount()*rand.nextInt(1000000)) + " "
                                                                      + this_thing.getPers().getLogin() + "\n");
                                    fw.write(StringToWrite); fw.flush(); // Сохраняем в файл купленный предмет
                                    this_thing.setPers(pers);
                                }
                                pers.setThings(this_thing); // Добавляем купленный предмет (или null) в инвентарь
                                
                            } else {    // Если выбранный предмет остался один               
                                pers.setMoney(pers.getMoney() - things[choosed_item-1].getPrice());
                                Thing this_thing = new Thing(things[choosed_item-1].getName(),1,things[choosed_item-1].getPrice(),things[choosed_item-1].getID());
                                things[choosed_item-1] = null;  // Обнуляем предмет, чтобы он пропал со склада
                                this_thing.setPers(pers);
                                for (int i = 0; i < things.length; i++) {
                                    if (pers.getSpecialThings(i) != null && this_thing != null) { 
                                        int ID1 = pers.getSpecialThings(i).getID(); // Получаем ID i-го предмета
                                        int ID2 = this_thing.getID();               // Получаем ID выбранного предмета
                                        if (ID1 == ID2) {                           // Сравниваем ID
                                            Thing same = pers.getSpecialThings(i);
                                            same.addCount();                        // Увеличиваем количество первого похожего предмета на 1  
                                            StringToWrite = (String)(this_thing.getName() + " "
                                                                  + this_thing.getCount() + " "
                                                                  + this_thing.getPrice() + " "
                                                                  + (-1*this_thing.getID()-pers.getItemsCount()*rand.nextInt(1000000)) + " "
                                                                      + this_thing.getPers().getLogin() + "\n");
                                            fw.write(StringToWrite); fw.flush();   // Сохраняем в файл купленный предмет
                                            this_thing.setPers(pers);
                                            this_thing = null;                     // Обнуляем похожий второй предмет
                                        }
                                    }
                                }
                                if (ID1 != ID2) {   // Если ID не совпали
                                    StringToWrite = (String)(this_thing.getName() + " "
                                                          + this_thing.getCount() + " "
                                                          + this_thing.getPrice() + " "
                                                          + (-1*this_thing.getID()-pers.getItemsCount()*rand.nextInt(1000000)) + " "
                                                          + this_thing.getPers().getLogin() + "\n");
                                    fw.write(StringToWrite); fw.flush();    // Сохраняем в файл купленный предмет
                                    this_thing.setPers(pers);
                                }
                                pers.setThings(this_thing);  // Добавляем купленный предмет (или null) в инвентарь
                            }
                            
                        } else {   // Если не хватает денег
                            System.out.println(ANSI_RED + "You don't have enough money!" + ANSI_RESET);
                        }
                        System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                        
                    } catch (IOException e) {  // В случае, если есть ошибки (вроде отсутсвия выбранного ID)    
                        System.out.println(ANSI_RED + "\nThere is no ID like inputed! " + ANSI_RESET);
                        System.out.println(ANSI_YELLOW + "----------------------------------- " + ANSI_RESET);
                    }
                    break;
                case 2:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Storage: " + ANSI_RESET);
                    for (int i = 0; i < things.length; i++) {   // Пробегаемся по длине массива с предметами    
                        if (things[i] != null) {
                            System.out.println(ANSI_CYAN + things[i].toString() + ANSI_RESET);   // Построчно печатаем каждый предмет
                        }
                    }
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    break;
                case 3:
                    reset();     // Делаем перезапуск, который поменяет предметы на складе
                    System.out.println(ANSI_YELLOW + "\n---------------- " + ANSI_RESET);
                    System.out.println(ANSI_GREEN + "Storage Reseted " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "---------------- " + ANSI_RESET);
                    break;
                case 4:
                    System.out.println(ANSI_YELLOW + "\n------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "Your money: " + pers.getMoney() + "$" + ANSI_RESET); // Получаем и выводим количество денег
                    System.out.println(ANSI_YELLOW + "\nYour inventory: " + ANSI_RESET);
                    for (int i = 0; i < pers.getThings().length; i++) {   // Пробегаемся по длине массива с инвентарем
                        System.out.print(ANSI_CYAN + pers.toString(i) + ANSI_RESET);   // Печатаем построчно каждый предмет
                    }
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    break;
                case 5:
                    System.out.println(ANSI_YELLOW + "------------------------- " + ANSI_RESET);
                    secureManager.saveMoney(pers.getMoney(), pers.getLogin());
                    pers = secureManager.checkTask(pers.getMoney());
                    checkSave(pers);
                    break;
                case 6:
                    fw.close();    // Закрываем поток записи файлов
                    work = false;  // Останавливаем основной цикл программы
                    break;
                default: 
                    System.out.println(ANSI_YELLOW + "\n----------------------------------- " + ANSI_RESET);
                    System.out.println(ANSI_RED + "There is no function like inputed! " + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + "----------------------------------- " + ANSI_RESET);
                    break;
            }   

        }
    }
    
    public void checkSave(Person pers) throws Exception {      
        FileReader fr = new FileReader("invent.txt");  // Создаем поток и сканнер для чтения файла с сохранением 
        Scanner fr_scan = new Scanner(fr);             
        
        String checking_array[]; // Создаем массив 
        
        pers.setThings(new Thing("Telephone", 1, 230, 0));
                    
        while (fr_scan.hasNextLine()) {      
            checking_array = fr_scan.nextLine().split(" ");   // Превращаем сохраненную строку в массив
            Thing this_thing = new Thing(checking_array[0],
                        Integer.parseInt(checking_array[1]),
                        Integer.parseInt(checking_array[2]),
                        Integer.parseInt(checking_array[3])); // Создаем предмет из сохранения
            this_thing.setPers(pers);
                for (int i = 0; i < things.length; i++) {     
                    if (pers.getSpecialThings(i) != null && this_thing != null) { 
                        ID1 = pers.getSpecialThings(i).getID();    // Получаем ID i-го элемента из инвентаря
                        ID2 = this_thing.getID();                  // Получаем ID предмета из сохранения
                        if (ID1 == ID2) {                          // Сравниваем ID
                                Thing same = pers.getSpecialThings(i);
                                same.addCount();                // Увеличиваем количество первого похожего предмета на 1
                                this_thing = null;              // Обнуляем второй похожий предмет
                        }
                    }
                }
            if (pers.getLogin().equals(checking_array[4])) {
                pers.setThings(this_thing);   // Добавляем в инвентарь предмет
            }
        fr.close();
        }  
    }
    
    public void reset() throws Exception {
        for (int i = 0; i < things.length-(rand.nextInt(things.length)); i++) {     // Делаем случайное количество итераций
            int random_name = rand.nextInt(names.length);           // Устанавливаем случайное имя
            int random_count = rand.nextInt(max_count) + 1;         // Устанавливаем случайное количество
            int random_price = rand.nextInt(max_price) + 1;         // Устанавливаем случайную цену
            things[i] = new Thing(names[random_name],random_count,random_price, i+1);
        }
    }
}