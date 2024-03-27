package ui;

import domeniu.Cake;
import domeniu.Order;
import domeniu.Settings;
import service.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class UI {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    private void printMenu(){
        System.out.println("1. afiseaza prajiturile");
        System.out.println("2. adauga prajitura");
        System.out.println("3. modifica prajitura");
        System.out.println("4. sterge prajitura");
        System.out.println("5. afiseaza comenzi");
        System.out.println("6. adauga comanda");
        System.out.println("7. modifica comanda");
        System.out.println("8. sterge comanda");
        System.out.println("9. nr. de prajituri din fiecare zi");
        System.out.println("10. show cakes by day");
        System.out.println("11. cele mai comandate prajituri");
        System.out.println("0. Exit");
    }

    private void printCakes(){
        for (Cake cake : service.getCakes()){
            System.out.println(cake.toString());
        }
    }

    private void addCake() throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("domeniu.Cake ID: ");
        int ID = in.nextInt();  // exception here

        System.out.println("Type: ");
        String type = in.next();

        service.addCake(ID, type);
    }

    private void deleteCake() throws Exception {
        System.out.println("id ce trebuie sters: ");
        Scanner in = new Scanner(System.in);
        int ID = in.nextInt();

        service.deleteCake(ID);

    }

    private void modifyCake() throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("id ce trebuie modificat: ");
        int ID = in.nextInt();

        System.out.println("new cake type: ");
        String newType = in.next();

        service.modifyCake(ID, newType);
    }

    private void printOrders(){
        for (Order order : service.getOrders()){
            System.out.println(order.toString());
        }
    }
    private void addOrder() throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("id: ");
        int ID = in.nextInt();

        System.out.println("cate prajituri vrei sa comanzi?");
        int nr = in.nextInt();

        ArrayList<Cake> cake_list = new ArrayList<>();

        System.out.println("poti alege din lista asta: ");
        this.printCakes();

        System.out.println("adauga id-ul prajiturilor pe care vrei sa le adaugi: ");

        for (int i = 0; i < nr; i++){
            int ID_cake = in.nextInt();
            Cake cake = service.getCakeById(ID_cake);
            cake_list.add(cake);
        }

        System.out.println("data (YYYY-MM-DD): ");
        String date = in.next();

        service.addOrder(ID, cake_list, date);
    }

    private void modifyOrder() throws Exception{
        Scanner in = new Scanner(System.in);

        System.out.println("id de modificat: ");
        int ID = in.nextInt();

        System.out.println("cate prajituri vrei sa adaugi");
        int nr = in.nextInt();

        ArrayList<Cake> new_cake_list = new ArrayList<>();

        System.out.println("poti alege din: ");
        this.printCakes();

        System.out.println("scrie id-urile prajiturilor pe care vrei sa le adaugi: ");

        for (int i = 0; i < nr; i++){
            int ID_cake = in.nextInt();
            Cake cake = service.getCakeById(ID_cake);
            new_cake_list.add(cake);
        }

        System.out.println("data noua(YYYY MM DD): ");
        String new_date = in.next();

        service.modifyOrder(ID, new_cake_list, new_date);
    }

    private void deleteOrder() throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("id de sters: ");
        int id = in.nextInt();
        this.service.deleteOrder(id);
    }

    private void createCakeList() throws Exception {
        this.service.addCake(1, "fruit cake");
        this.service.addCake(2, "carrot cake");
        this.service.addCake(3, "red velvet");
        this.service.addCake(4, "chocolate");
        this.service.addCake(5, "cheesecake");
    }

    private void createOrderList() throws Exception {
        ArrayList<Cake> cakeList1 = new ArrayList<>();
        cakeList1.add(new Cake(1, "fruit cake"));
        cakeList1.add(new Cake(2, "carrot cake"));

        ArrayList<Cake> cakeList2 = new ArrayList<>();
        cakeList2.add(new Cake(2, "carrot cake"));

        ArrayList<Cake> cakeList3 = new ArrayList<>();
        cakeList3.add(new Cake(2, "carrot cake"));
        cakeList3.add(new Cake(4, "chocolate"));
        cakeList3.add(new Cake(3, "red velvet"));

        ArrayList<Cake> cakeList4 = new ArrayList<>();
        cakeList4.add(new Cake(4, "cheesecake"));

        ArrayList<Cake> cakeList5 = new ArrayList<>();
        cakeList5.add(new Cake(5, "cheesecake"));

        service.addOrder(1,cakeList1, "2023-7-23");
        service.addOrder(2,cakeList2,"2023-6-12");
        service.addOrder(3,cakeList3,"2023-8-14" );
        service.addOrder(4,cakeList4, "2023-9-2");
        service.addOrder(5,cakeList5, "2023-8-18");

    }

    private void generate_random() throws Exception {
        String repositoryType = Settings.getRepositoryType();
        if ("bd".equals(repositoryType)){
            service.generateCakes();
        }
    }

    private void generate_random_order() throws Exception {
        String repositoryType = Settings.getRepositoryType();
        if ("bd".equals(repositoryType)){
            service.generateOrder();
        }
    }

    private void generate_list() throws Exception {
        String repositoryType = Settings.getRepositoryType();
        if ("generic".equals(repositoryType)){
            this.createCakeList();
            this.createOrderList();
        }
    }

    private void showCakesByDay(){
        Stream<Map.Entry<String, Long>> raport = service.showCakesByDay();
        raport.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()+" torturi"));
    }

    private void mostOrderedCakes(){
        Stream<Map.Entry<String, Long>> raport = service.mostOrderedCakes();
        raport.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()+" comenzi"));
    }

    private void showCakesByMonth(){
        Stream<Map.Entry<String, Long>> raport = service.cakesByMonth();
        raport.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()+" torturi"));
    }


    public void start() throws Exception{
        try{
            generate_list();
        }
        catch (Exception i){
        }
        try{
            generate_random();
        }
        catch (Exception i){
        }
        try{
            generate_random_order();
        }
        catch (Exception i){
            System.out.printf(i.toString());
        }

        int s;
        while (true){
            try{
                this.printMenu();

                System.out.println("Choose an option: ");
                Scanner in = new Scanner(System.in);
                s = Integer.parseInt(in.nextLine());

                if (s == 1){
                    this.printCakes();
                } else if(s==9){
                    showCakesByDay();
                }
                else if(s==10){
                    showCakesByMonth();
                }
                else if(s==11){
                    mostOrderedCakes();
                }
                else if (s == 2) {
                    this.addCake();
                }
                else if (s == 3){
                    this.modifyCake();
                }
                else if (s == 4){
                    this.deleteCake();
                }
                else if (s == 5){
                    this.printOrders();
                }
                else if (s == 6){
                    this.addOrder();
                }
                else if (s == 7){
                    this.modifyOrder();
                }
                else if (s == 8){
                    this.deleteOrder();
                }
                else if (s == 0){
                    return;
                }
                else{
                    System.out.println("nu-i ok, reincearca");
                }
            }catch (Exception e){
                System.out.println(e);
            }

        }

    }
}