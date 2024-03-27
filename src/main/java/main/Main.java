package main;

import domeniu.Cake;
import domeniu.Order;
import domeniu.Settings;
import repo.BinaryFileRepository;
import repo.CakeFileRepository;
import repo.OrderFileRepository;
import repo.Repository;
import repo.sql.CakeSqlRepo;
import repo.sql.OrdersSqlRepo;
import service.Service;
import ui.UI;

public class Main {
    public static void main(String[] args) throws Exception {
        String repositoryType = Settings.getRepositoryType();
        String cakeRepoString = Settings.getRepoFileCakes();
        String orderRepoString = Settings.getRepoFileOrders();
        Repository<Cake> cakes_repo = null;
        Repository<Order> order_repo = null;


        if ("binary".equals(repositoryType)){
            cakes_repo = new BinaryFileRepository<>(cakeRepoString);
            order_repo = new BinaryFileRepository<>(orderRepoString);
        }
        else if ("text".equals(repositoryType)){
            cakes_repo = new CakeFileRepository(cakeRepoString);
            order_repo = new OrderFileRepository(orderRepoString);
        }
        else if ("generic".equals(repositoryType)){
            cakes_repo = new Repository<>();
            order_repo = new Repository<>();
        } else if ("bd".equals(repositoryType)) {
            cakes_repo = new CakeSqlRepo();
            order_repo = new OrdersSqlRepo();
        }

        Service service = new Service(cakes_repo, order_repo);
        UI ui = new UI(service);

        ui.start();
    }
}