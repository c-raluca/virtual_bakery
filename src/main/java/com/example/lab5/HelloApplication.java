package com.example.lab5;

import domeniu.Cake;
import domeniu.Order;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repo.sql.CakeSqlRepo;
import repo.sql.CakeSqlRepo;
import repo.RepoInterface;
import repo.Repository;
import repo.sql.OrdersSqlRepo;
import service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    private Repository<Cake> cakeRepository = new CakeSqlRepo();
    private Repository<Order> orderRepository = new OrdersSqlRepo();
    private Service service = new Service(cakeRepository, orderRepository);

    @Override
    public void start(Stage stage) throws Exception {
        HelloController hc = new HelloController(service);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        fxmlLoader.setController(hc);

        Scene scene = new Scene(fxmlLoader.load(), 640, 320);

        hc.printCakesFromSQL();
        hc.printOrdersFromSQL();
        stage.setTitle("Cakes!");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
}
}