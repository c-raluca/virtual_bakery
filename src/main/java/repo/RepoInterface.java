package repo;

import domeniu.Entity;

import java.util.ArrayList;

public interface RepoInterface <T extends Entity>{
    void addEntity(T entity) throws Exception;
    T getByID(int ID);

    void deleteByID(int ID) throws Exception;
    void modifyEntity(T newEntity) throws Exception;
    int getCurrentSize();
    ArrayList<T> getAll();
}
