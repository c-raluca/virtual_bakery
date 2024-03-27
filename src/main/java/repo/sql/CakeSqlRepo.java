package repo.sql;
import domeniu.Cake;
import org.sqlite.SQLiteDataSource;

import repo.Repository;

import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CakeSqlRepo extends Repository<Cake> implements AutoCloseable{
    private String JDBC_URL = "jdbc:sqlite:cake.db";

    private Connection connection;

    public CakeSqlRepo(){
        openConnection();
        createTable();
        //initiateTable();
    }
    public void openConnection(){
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()){
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    public void closeConnection(){
//        if (connection != null){
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void createTable(){
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS cakes(id int, type varchar(400));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initiateTable(){
        ArrayList<Cake> cakes = new ArrayList<>();
        cakes.add(new Cake(1,"cheesecake"));
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO cakes VALUES (?,?);")){
            for (Cake c : cakes){
                stmt.setInt(1, c.getID());
                stmt.setString(2, c.getType());
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEntity(Cake c){
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO cakes VALUES (?,?);")){
            stmt.setInt(1, c.getID());
            stmt.setString(2, c.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifyEntity(Cake c){
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE Cakes SET id=?, type=? WHERE id=?;")){
            stmt.setInt(1, c.getID());
            stmt.setString(2, c.getType());
            stmt.setInt(3, c.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteByID(int id) {
        try {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM cakes WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Cake> getAll() {
        ArrayList<Cake> cakes = new ArrayList<>();

        try {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * from cakes"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Cake c = new Cake(rs.getInt("id"), rs.getString("type"));
                    cakes.add(c);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cakes;
}


    @Override
    public void close() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
