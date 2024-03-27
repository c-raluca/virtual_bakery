package service;

import domeniu.Cake;
import domeniu.Order;
import repo.Repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.MonthDay.parse;


public class Service {
    private Repository<Cake> cakeRepository;
    private Repository<Order> orderRepository;

    public Service(Repository<Cake> cakeRepository, Repository<Order> orderRepository) {
        this.cakeRepository = cakeRepository;
        this.orderRepository = orderRepository;
    }

    public int getNrOrders(){
        return orderRepository.getCurrentSize();
    }

    public int getNrCakes(){
        return cakeRepository.getCurrentSize();
    }

    public ArrayList<Cake> getCakes(){
        return cakeRepository.getAll();
    }

    public ArrayList<Order> getOrders(){
        return orderRepository.getAll();
    }


    public void addCake(int ID, String type) throws Exception {
        for (Cake cake : getCakes()){
            if (cake.getID() == ID){
                throw new Exception("exista deja prajitura cu id-ul dat!");
            }
        }

        if (type.length() > 10000)
            throw new Exception("tipul prajiturii nu poate sa fie mai mare de 10000");

        Cake newCake = new Cake(ID, type);
        cakeRepository.addEntity(newCake);
    }

    public void modifyCake (int newID, String newType) throws Exception {
        Boolean found = false;
        for (Cake cake : cakeRepository.getAll()){
            if (cake.getID() == newID) {
                found = true;
                break;
            }
        }

        if (!found){
            throw new Exception("nu exista prajitura cu id-ul dat! Try again!");
        }

        if (newType.length() > 1000)
            throw new Exception("tipul prajiturii nu poate sa fie mai mare de 10000");

        Cake newCake = new Cake(newID, newType);
        cakeRepository.modifyEntity(newCake);
    }

    public void deleteCake(int ID) throws Exception {
        Boolean found = false;
        for (Cake cake : cakeRepository.getAll()){
            if (cake.getID() == ID) {
                found = true;
                break;
            }
        }

        if (!found){
            throw new Exception("nu exista praji cu id-ul dat! Try again!");
        }

        cakeRepository.deleteByID(ID);
    }

    public void dateValidator(String type) throws Exception {
        if(type.length() != 10){
            throw new Exception("data trebuie sa aiba lungimea 10!");
        }
        if(type.charAt(2) != '-' || type.charAt(5) != '-')
            throw new Exception("Data comenzii trebuie scrisa sub forma DD-MM-YYYY");
        String[] verificare = type.split("-");
        if(Integer.parseInt(verificare[0]) < 1 || Integer.parseInt(verificare[0]) > 31)
            throw new Exception("Ziua trebuie cuprinsa in [1, 31]");
        if(Integer.parseInt(verificare[1]) < 1 || Integer.parseInt(verificare[1]) > 12)
            throw new Exception("Luna trebuie cuprinsa in [1, 12]");
    }

    public void addOrder(int ID, ArrayList<Cake> cakes, String date) throws Exception {
        dateValidator(date);
        // throwing an exception if the ID is duplicated
        for (Order o : getOrders()){
            if (o.getID() == ID){
                throw new Exception("exista deja comanda cu id-ul dat! Try again!");
            }
        }

        // throw exception if nr of cakes is zero
        int nr_cakes = cakes.size();
        if (nr_cakes == 0){
            throw new Exception ("You cannot order 0 cakes!");}

        // throw exception if one or more of the cakes do not exist in the list
        for (Cake c: cakes){
            if (c == null){
                throw new Exception("EROARE, nu exista prajitura");
            }
        }

        // now add order
        Order newOrder = new Order(ID, cakes, date);
        orderRepository.addEntity(newOrder);
    }

    public void modifyOrder(int ID, ArrayList<Cake> newCakes, String newDate) throws Exception {
        dateValidator(newDate);

        Boolean found = false;
        for (Order o : getOrders()){
            if (o.getID() == ID){
                found = true;
                break;
            }
        }

        // throw exception if order id does not exist
        if (!found){
            throw new Exception("nu exista comanda cu id-ul dat");}

        // throw exception if nr of cakes is zero
        int nr_cakes = newCakes.size();
        if (nr_cakes == 0)
            throw new Exception ("nu se pot comanda 0 prajituri!");

        // throw exception if one or more of the cakes do not exist in the list
        for (Cake c: newCakes){
            if (c == null){
                throw new Exception("EROARE, nu exista prajitura");
            }
        }

        Order newOrder = new Order(ID, newCakes, newDate);
        orderRepository.modifyEntity(newOrder);
    }

    public void deleteOrder(int ID) throws Exception{
        Boolean found = false;
        for (Order o : getOrders()){
            if (o.getID() == ID){
                found = true;
                break;
            }
        }

        // throw exception if order id does not exist
        if (!found){
            throw new Exception("comanda nu exista cu id-ul dat");
        }

        this.orderRepository.deleteByID(ID);
    }

    public Cake getCakeById(int ID){
        for (Cake cake : cakeRepository.getAll()){
            if (cake.getID() == ID) {
                return cake;
            }
        }
        return null;
    }

    private ArrayList<Integer> generateNrs(){
        ArrayList<Integer> randomNrs = new ArrayList<Integer>();
        for (int i=0; i< 100; i++) randomNrs.add(i);
        Collections.shuffle(randomNrs);
        return randomNrs;
    }

    public void generateCakes() throws Exception {
        //to clear all existing entities from memory
//        for (Cake c : getCakes())
//            cakeRepository.deleteByID(c.getID());

        ArrayList<Integer> randomNrs = this.generateNrs();
        String[] types = new String[]{"Chocolate Cake", "Vanilla Cake", "Strawberry Shortcake", "Red Velvet Cake", "Carrot Cake", "Lemon Pound Cake", "Coconut Cake", "Black Forest Cake", "Cheesecake", "Angel Food Cake", "Marble Cake", "Pineapple Upside-Down Cake", "Coffee Cake", "Funfetti Cake", "German Chocolate Cake", "Tiramisu", "Fruitcake", "Pumpkin Spice Cake", "Blueberry Muffin Cake", "Hazelnut Torte", "Almond Joy Cake", "Banana Nut Cake", "Caramel Apple Cake", "Cherry Almond Cake", "Peanut Butter Chocolate Cake", "Raspberry Swirl Cake", "Key Lime Pie Cake", "Oreo Cake", "Mint Chocolate Chip Cake", "Pistachio Cake", "S'mores Cake", "Mocha Cake", "Coconut Lime Cake", "Neapolitan Cake", "Mango Tango Cake", "Pina Colada Cake", "White Chocolate Raspberry Cake", "Irish Cream Cake", "Almond Poppy Seed Cake", "Butter Pecan Cake", "Chai Spice Cake", "Salted Caramel Chocolate Cake", "Lavender Honey Cake", "Earl Grey Tea Cake", "Maple Bacon Cake", "Snickerdoodle Cake", "Pineapple Coconut Cake", "Chocolate Cherry Cake", "Lemon Blueberry Cake", "Cinnamon Roll Cake", "Pistachio Rose Cake", "Gingerbread Cake", "Peppermint Bark Cake", "Cappuccino Cake", "Passion Fruit Cake", "Cookies and Cream Cake", "Pumpkin Cheesecake", "Cranberry Orange Cake", "Espresso Walnut Cake", "Chocoflan (Flan Cake)", "Matcha Green Tea Cake", "Caramel Macchiato Cake", "Almond Joy Cheesecake", "Triple Chocolate Mousse Cake", "Raspberry Lemonade Cake", "Red Wine Chocolate Cake", "Eggnog Cake", "Blackberry Cobbler Cake", "Rocky Road Cake", "Butterscotch Toffee Cake", "Caramelized Banana Cake", "Rosewater Pistachio Cake", "Chocolate Hazelnut Torte", "Apricot Almond Cake", "Guinness Chocolate Cake", "Hibiscus Elderflower Cake", "Lemon Lavender Cake", "Apple Cider Donut Cake", "Churro Cake", "Pomegranate Chocolate Cake", "Butterbeer Cake", "Peaches and Cream Cake", "Caramel Banana Upside-Down Cake", "Fig and Honey Cake", "White Chocolate Peppermint Cake", "Chocolate Guinness Stout Cake", "Blueberry Lemon Bundt Cake", "Pumpkin Roll Cake", "Mango Habanero Cake", "Cinnamon Apple Crumb Cake", "Caramelized Pear and Walnut Cake", "Rosemary Olive Oil Cake", "Pistachio Cardamom Cake", "Almond Cherry Blossom Cake", "Blood Orange Olive Oil Cake", "Chocolate Avocado Cake", "Honey Sesame Cake", "Coconut Mango Sticky Rice Cake", "Cardamom Rose Cake", "Lavender Earl Grey Cake"};

        for (int i = 0; i < 100; i++) {
            int id = randomNrs.get(i);
            this.addCake(id, types[i]);
        }
    }

    public Stream<Map.Entry<String, Long>> showCakesByDay(){ //Numărul de torturi comandate în fiecare zi
        Map<String, Long> raport = getOrders().stream()
                .collect(Collectors.groupingBy(Order::getDate, Collectors.summingLong(c -> c.getCakes().size())));

        // Sortare descrescătoare și afișare
        return raport.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed());
    }

    public Stream<Map.Entry<String, Long>> mostOrderedCakes(){
        //cele mai comandate torturi

        Map<String, Long> raport = getOrders().stream()
                .flatMap(comanda -> comanda.getCakes().stream())
                .collect(Collectors.groupingBy(
                        Cake::toString,
                        Collectors.counting()
                ));

        // Sortare descrescătoare și afișare
        return raport.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed());
    }

    public Stream<Map.Entry<String, Long>> cakesByMonth(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Map<String, Long> raport = this.getOrders().stream()
                .collect(Collectors.groupingBy(
                        comanda -> (parse(comanda.getDate(), formatter)).getMonth().toString(),
                        Collectors.summingLong(comanda -> comanda.getCakes().size())
                ));

        // Sortare descrescătoare și afișare
        return raport.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed());
}

    public void generateOrder() throws Exception {
//        to clear all existing entities from memory
//        for(Order o : getOrders())
//            orderRepository.deleteByID(o.getID());

        //generate random id, unique
        ArrayList<Integer> randomNrs = this.generateNrs();

        //random dates
        String[] dates = new String[] {"24-08-2020", "25-12-1981", "20-01-1912", "09-08-2023", "17-11-1943", "24-02-1959", "07-04-1915", "24-01-1918", "23-12-2001", "11-02-1957", "14-04-2015", "02-08-2023", "02-12-1972", "14-08-1968", "11-09-1969", "15-08-2080", "28-03-1918", "13-12-1927", "05-10-1937", "25-02-1944", "25-12-1999", "20-04-1933", "06-11-2016", "25-12-1905", "15-07-2023", "23-07-1931", "06-12-1969", "16-05-2020", "29-08-1973", "13-05-2020", "10-08-2020", "14-08-1929", "26-01-2015", "18-08-1909", "28-09-2015", "08-11-2012", "11-02-2014", "01-08-2021", "13-09-1981", "19-03-1966", "27-12-1961", "01-01-1999", "25-01-1982", "11-02-2013", "20-08-1904", "24-11-2005", "04-05-2004", "20-04-2008", "11-08-2009", "29-10-2008", "03-11-1900", "22-05-1967", "06-06-2005", "15-02-1950", "21-10-2007", "05-04-2000", "11-01-1933", "10-10-2019", "03-02-1961", "08-05-1934", "07-12-1907", "17-11-1945", "17-10-1964", "11-03-1934", "18-09-2013", "19-02-2007", "13-11-1930", "23-12-2004", "21-12-2007", "27-05-1999", "10-11-2008", "02-11-1956", "27-04-1908", "09-01-1904", "30-09-2006", "12-10-2004", "19-12-2017", "07-11-2007", "18-12-1980", "17-12-1972", "21-04-2014", "24-10-1968", "08-03-2000", "26-12-1901", "02-08-1924", "27-04-2018", "21-11-1966", "22-08-1917", "26-05-1916", "10-01-1911", "09-02-2006", "06-06-1958", "15-12-2004", "30-01-1934", "05-03-2011", "07-11-2002", "07-10-1956", "11-04-2013", "10-02-1989","13-05-1961"};

        //generate random nr of cakes
        for(int i = 0; i < 100; i++){
            int id = randomNrs.get(i); //random id

            ArrayList<Cake> cakeListRandom = new ArrayList<>();
            int nrCakes = (int) (Math.random() *5+1);

            for(int index = 0; index < nrCakes; index++){
                //another random number for cake id, to put into this shit
                int randomNr = (int) (Math.random() *100);
                cakeListRandom.add(getCakes().get(randomNr));
            }
            this.addOrder(id, cakeListRandom, dates[id]);
        }
    }
}