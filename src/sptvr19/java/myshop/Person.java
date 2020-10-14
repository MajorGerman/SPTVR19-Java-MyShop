package sptvr19.java.myshop;

public class Person {
    
    private int money;
    private boolean breakthis;
    private Thing[] selfthings = new Thing[20];
    private int count;
    
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
    
    public int getItemsCount() {
        for (int i = 0; i < this.selfthings.length; i++) {
            if (selfthings[i] != null) {
                count++;
            }
        }
        return count;
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