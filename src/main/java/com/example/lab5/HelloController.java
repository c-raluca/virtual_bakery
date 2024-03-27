package com.example.lab5;

import domeniu.Cake;
import domeniu.Entity;
import domeniu.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.events.MouseEvent;
import service.Service;

import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public ListView<String> cakeListView;

    @FXML
    public ListView<String> orderListView;

//    @FXML
//    private Button onAddCakeButton;
//
//    @FXML
//    private Button deleteCakeButton;

    @FXML
    private TextField idTextField;
    @FXML
    private TextField idOrderTextField;

//    @FXML
//    private Button modifyCakeButton;

    @FXML
    private TextField typeTextField;
    @FXML
    private TextField idCakesOrderTextField;
    @FXML
    private TextField dateTextField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    Service service;

    public HelloController(Service service) {
        this.service = service;
    }

    protected void printCakesFromSQL(){
        // print cakes in ascending order of id
        ObservableList<String> cakes = FXCollections.observableArrayList();

        List<Cake> cakeList = service.getCakes().stream()
                .sorted(Comparator.comparing(Entity::getID)).toList();

        for (Cake c : cakeList) {
            cakes.add("ID: " + c.getID() + ", Type: " + c.getType());
        }
        cakeListView.setItems(cakes);
    }

    @FXML
    private void addCakeButton() throws Exception {
        System.out.println("Add cake button clicked!");

        try{
            int id = Integer.parseInt(idTextField.getText());
            String type = typeTextField.getText();
            service.addCake(id, type);
        }catch (Exception e){
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printCakesFromSQL();
    }

    @FXML
    private void addOrderButton(){
        System.out.println("Add order button clicked!");

        try {
            int id = Integer.parseInt(idOrderTextField.getText());
            String date = dateTextField.getText();

            String[] idCakes = idCakesOrderTextField.getText().split(" ");

            ArrayList<Cake> cakeList = new ArrayList<>();
            for (int i = 0; i < idCakes.length; i++){
                int idCake = Integer.parseInt(idCakes[i]);
                Cake c = service.getCakeById(idCake);
                cakeList.add(c);
            }
            service.addOrder(id, cakeList, date);
        } catch (Exception e) {
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printOrdersFromSQL();
    }

    @FXML
    private void modifyOrderButton(){
        System.out.println("Modify order button clicked!");

        try {
            int id = Integer.parseInt(idOrderTextField.getText());
            String date = dateTextField.getText();

            String[] idCakes = idCakesOrderTextField.getText().split(" ");

            ArrayList<Cake> cakeList = new ArrayList<>();
            for (int i = 0; i < idCakes.length; i++){
                int idCake = Integer.parseInt(idCakes[i]);
                Cake c = service.getCakeById(idCake);
                cakeList.add(c);
            }
            service.modifyOrder(id, cakeList, date);
        } catch (Exception e) {
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printOrdersFromSQL();
    }

    @FXML
    private void modifyCakeButton() {
        System.out.println("Modify cake button clicked!");

        try {
            int id = Integer.parseInt(idTextField.getText());
            String type = typeTextField.getText();
            service.modifyCake(id, type);
        } catch (Exception e) {
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printCakesFromSQL();
    }

    @FXML
    private void deleteCakeButton() {
        System.out.println("Delete cake button clicked!");
        try {
            int id = Integer.parseInt(idTextField.getText());
            service.deleteCake(id);
        } catch (Exception e) {
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printCakesFromSQL();
    }

    @FXML
    private void deleteOrderButton() {
        System.out.println("Delete order button clicked!");
        try {
            int id = Integer.parseInt(idOrderTextField.getText());
            service.deleteOrder(id);
        } catch (Exception e) {
            Alert alertPopUp = new Alert(Alert.AlertType.ERROR);
            alertPopUp.setTitle("Error");
            alertPopUp.setContentText(e.getMessage());
            alertPopUp.show();
        }
        printOrdersFromSQL();
    }


    protected void printOrdersFromSQL(){
        // print orders in ascending order of id

        ObservableList<String> orders = FXCollections.observableArrayList();

        List<Order> orderList = service.getOrders().stream().sorted(Comparator.comparing(Entity::getID)).toList();

        StringBuilder cakes_string = new StringBuilder();

        for (Order o : orderList){
            for (Cake cake : o.getCakes()) {
                cakes_string.append(cake.toString()).append(' ');
            }
            orders.add("ID: " + o.getID() + ", Cakes: " + cakes_string +
                    " Date: " + o.getDate());
            cakes_string.setLength(0);
        }

        orderListView.setItems(orders);
    }

    @FXML
    public ListView<String> streamListView;

    @FXML
    protected void showCakesByDayButton(){
        ObservableList<String> cakesFilteredByDay = FXCollections.observableArrayList();

        Stream<Map.Entry<String, Long>> raport = service.showCakesByDay();
        raport.forEach(entry -> cakesFilteredByDay.add(entry.getKey() + ": " + entry.getValue() + " torturi"));
        streamListView.setItems(cakesFilteredByDay);
    }

    @FXML
    protected void mostOrderedCakesButton(){
        ObservableList<String> mostOrderedCakes = FXCollections.observableArrayList();

        Stream<Map.Entry<String, Long>> raport = service.mostOrderedCakes();
        raport.forEach(entry -> mostOrderedCakes.add(entry.getKey() + "; nr: " + entry.getValue()));
        streamListView.setItems(mostOrderedCakes);
    }

    @FXML
    protected void showCakesByMonthButton(){
        ObservableList<String> cakesFilteredByMonth = FXCollections.observableArrayList();

        Stream<Map.Entry<String, Long>> raport = service.cakesByMonth();
        raport.forEach(entry -> cakesFilteredByMonth.add(entry.getKey() + "; " + entry.getValue() + " torturi"));
        streamListView.setItems(cakesFilteredByMonth);
        //System.out.printf("ASDFGHJK");
    }

//    public void initialize(){
////        ObservableList<Cake> cakes = FXCollections.observableList(service.getCakes());
////        this.listView = new ListView<String>();
//
//}
}