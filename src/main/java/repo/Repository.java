package repo;

import domeniu.Entity;
import repo.RepoInterface;

import java.util.ArrayList;

public class Repository<T extends Entity> implements RepoInterface<T> {
    protected ArrayList<T> entities;

    public Repository(){
        entities = new ArrayList<>();
    } // t nu are nimic in construct asta

    public void addEntity(T entity) throws Exception {
        entities.add(entity);
    }

    public T getByID(int ID){
        for (T entity : entities){
            if (entity.getID() == ID)
                return entity;
        }
        return null;
    }

    public void deleteByID(int ID) throws Exception {
        if (getByID(ID) == null)
            return;
        entities.remove(getByID(ID));
    }

    public void modifyEntity(T newEntity) throws Exception {
        //noinspection ReassignedVariable
        int count = 0;
        for (T e : entities){
            if (e.getID() == newEntity.getID()){
                entities.set(count, newEntity);
                break;
            }
            count++;
        }
    }

    public int getCurrentSize(){
        return entities.size();
    }

    public ArrayList<T> getAll(){
        return entities;
    }
}
