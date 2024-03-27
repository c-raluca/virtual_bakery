package repo;

import domeniu.Cake;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class CakeFileRepository extends Repository<Cake> {
    private String fileName;

    public CakeFileRepository(String fileName) throws Exception {
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
            int id = Integer.parseInt(line.split(",")[0]);
            String type = line.split(",")[1];

            Cake cake = new Cake(id, type);
            addEntity(cake);
        }
        scanner.close();
    }

    private void saveToFile() throws Exception {
        FileWriter w = new FileWriter(fileName);

        for (Cake c : getAll()){
            w.write(c.getID() + "," + c.getType()+ "\n");
        }

        w.close();
    }


    @Override
    public void addEntity(Cake entity) throws Exception {
        super.addEntity(entity);
        saveToFile();
    }

    @Override
    public void deleteByID(int ID) throws Exception {
        super.deleteByID(ID);
        saveToFile();
    }

    @Override
    public void modifyEntity(Cake newEntity) throws Exception {
        super.modifyEntity(newEntity);
        saveToFile();
    }
}