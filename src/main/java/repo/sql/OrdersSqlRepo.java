package repo.sql;

import domeniu.Cake;
import domeniu.Order;
import org.sqlite.SQLiteDataSource;
import repo.Repository;

import java.sql.*;
import java.util.ArrayList;

public class OrdersSqlRepo extends Repository<Order> implements AutoCloseable{
    private String JDBC_URL = "jdbc:sqlite:orders.db";

    private Connection connection;

    public OrdersSqlRepo(){
        openConnection();
        createTable();
        // initTable();
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

    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable(){
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS orders(id int, cakes varchar(400), date varchar(400));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEntity(Order o){
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?);")){
            stmt.setInt(1, o.getID());

            // convert list of cakes to String
            StringBuilder sb = new StringBuilder();
            for (Cake c : o.getCakes()){
                sb.append(c.getID() + "," + c.getType() + ";");
            }
            sb.deleteCharAt(sb.length() - 1);

            stmt.setString(2, String.valueOf(sb));
            stmt.setString(3, o.getDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> getAll(){
        ArrayList<Order> orders = new ArrayList<>();

        try {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * from orders"); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String[] cakeString = rs.getString("cakes").split(";");
                    String date = rs.getString("date");

                    ArrayList<Cake> cakeList = new ArrayList<>();
                    // "1,choco;2,banana;3,pula"
                    for (int i = 0; i < cakeString.length; i++){
                        int idc = Integer.parseInt(cakeString[i].split(",")[0]);
                        String typec = cakeString[i].split(",")[1];
                        Cake cake = new Cake(idc, typec);
                        cakeList.add(cake);
                    }
                    Order order = new Order(id, cakeList, date);
                    orders.add(order);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public void deleteByID(int id) {
        try {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyEntity(Order o){
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET id=?, cakes=?, date=? WHERE id=?;")){
            stmt.setInt(1, o.getID());

            // convert list of cakes to String
            StringBuilder sb = new StringBuilder();
            for (Cake c : o.getCakes()){
                sb.append(c.getID() + "," + c.getType() + ";");
            }
            sb.deleteCharAt(sb.length() - 1);

            stmt.setString(2, String.valueOf(sb));
            stmt.setString(3, o.getDate());
            stmt.setInt(4, o.getID());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
