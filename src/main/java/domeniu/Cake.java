package domeniu;

import java.io.Serializable;

public class Cake extends Entity implements Serializable {
    private String type;

    private static final long serialVersionUID = 5943442517620890570L;

    public Cake(int ID, String type) {
        super(ID);
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setType(String type1){
        this.type = type1;
    }

    @Override
    public String toString() {
        return "Cake {" + "type='" + type + "', ID=" + ID + '}';
    }
}
