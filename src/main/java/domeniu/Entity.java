package domeniu;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected int ID;
    private static final long serialVersionUID = 5943442517620890570L;

    public Entity(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int newID) {this.ID = newID;}
}