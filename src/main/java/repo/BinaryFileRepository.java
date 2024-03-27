package repo;

import domeniu.Entity;

import java.io.*;
import java.util.ArrayList;

public class BinaryFileRepository<T extends Entity> extends Repository<T> {
    String fileName;
    public BinaryFileRepository(String FileName) throws IOException, ClassNotFoundException {
        this.fileName=FileName;
        loadFromFile();
    }

    public void loadFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            this.entities = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Repo starting a new file");
        } catch (EOFException e){
            System.out.println("No previous entries in binary file");
        }
        finally {
            if (ois != null)
                ois.close();
        }

    }

    public void saveInFile() throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(entities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null)
                oos.close();
        }
    }

    @Override
    public void addEntity(T elem) throws Exception {
        super.addEntity(elem);
        saveInFile();
    }

    @Override
    public void deleteByID(int ID) throws Exception {
        super.deleteByID(ID);
        saveInFile();
    }

    @Override
    public void modifyEntity(T newEntity) throws Exception {
        super.modifyEntity(newEntity);
        saveInFile();
    }
}