package repo;

import domeniu.Cake;
import domeniu.Order;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderFileRepository extends Repository<Order> {
    private String fileName;

    public OrderFileRepository(String fileName) throws Exception {
        this.fileName = fileName;
        readFromFile();
    }

    private void readFromFile() throws Exception {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        this.entities.clear();

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            int id = Integer.parseInt(line.split(";")[0]);


            String second = line.split(";")[1];  // contains the nr of cakes and the list of cakes in the order
            ArrayList<Cake> cakezz = new ArrayList<>();
            int nr_cakes = Integer.parseInt(second.split(":")[0]);


            String cake_list = second.split(":")[1]; // the list of cakes

            int id_cake;
            String type;
            for (int i = 0; i < 2 * nr_cakes; i++){

                id_cake = Integer.parseInt(cake_list.split(",")[i]);

                type = cake_list.split(",")[i+1];

                Cake c = new Cake(id_cake, type);
                cakezz.add(c);

                i +=  1;
            }


            String date = line.split(";")[2];

            Order newOrder = new Order (id, cakezz, date);
            addEntity(newOrder);
        }
        scanner.close();
    }

    private void saveToFile() throws Exception {
        FileWriter w = new FileWriter(fileName);


        for (Order o : getAll()){
            // deal with cake list separately
            StringBuilder cakes_string = new StringBuilder();

            for (Cake cake : o.getCakes()) {
                cakes_string.append(cake.getID() + "," + cake.getType() + ",");
            }
            cakes_string.deleteCharAt(cakes_string.length() - 1);


            w.write(o.getID() + ";" + o.getCakes().size() + ":" + cakes_string + ";" + o.getDate() + "\n");
        }

        w.close();
    }


    @Override
    public void addEntity(Order entity) throws Exception {
        super.addEntity(entity);
        saveToFile();
    }

    @Override
    public void deleteByID(int ID) throws Exception {
        super.deleteByID(ID);
        saveToFile();
    }

    @Override
    public void modifyEntity(Order newEntity) throws Exception {
        super.modifyEntity(newEntity);
        saveToFile();
    }
}