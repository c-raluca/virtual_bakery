package domeniu;

import domeniu.Cake;
import domeniu.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Order extends Entity implements Serializable {
    private ArrayList<Cake> cakes;
    private String date;

    private static final long serialVersionUID = 5943442517620890570L;


    public Order(int ID, ArrayList<Cake> cakes, String date) {
        super(ID);
        this.cakes = cakes;
        this.date = date;
    }

    public ArrayList<Cake> getCakes() {
        return cakes;
    }

    public String getDate() {
        return date;
    }

    public void setCakes(ArrayList<Cake> cakes) {
        this.cakes = cakes;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        // cakes_string will save the data from the list of cakes
        StringBuilder cakes_string = new StringBuilder();
        for (Cake cake : cakes) {
            cakes_string.append(cake.toString()).append(' ');
        }


        return "Order {ID: " + ID + " " + cakes_string +
                " Date: " + date + "} ";

    }
}
