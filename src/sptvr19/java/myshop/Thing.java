package sptvr19.java.myshop;

public class Thing {
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
