package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import javax.crypto.spec.PSource;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.crateSellerDao();

        System.out.println("\n==== TESTE 1: seller findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);


        System.out.println("\n==== TESTE 2: seller findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list1 = sellerDao.findByDepartment(department);
        list1.forEach(System.out::println);
        System.out.println(department);

        System.out.println("\n==== TESTE 3: seller findAll ====");
        List<Seller> list2 = sellerDao.findAll();
        list2.forEach(System.out::println);

        System.out.println("\n==== TESTE 4: seller Insert ====");
        Seller seller1 = new Seller(null, "Pedro testeiro", "pedro@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(seller1);
        System.out.println("Inserted, new id = " + seller1.getId());
        list1 = sellerDao.findByDepartment(department);
        list1.forEach(System.out::println);

        System.out.println("\n==== TESTE 5: seller Update ====");
        seller = sellerDao.findById(1);
        System.out.println(seller);
        seller.setName("Mario Martinelli Junior Neymar");
        sellerDao.update(seller);
        seller = sellerDao.findById(1);
        System.out.println(seller);



    }
}















