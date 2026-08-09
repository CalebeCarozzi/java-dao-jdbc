package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import javax.crypto.spec.PSource;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.crateSellerDao();

        System.out.println("==== TESTE 1: seller findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);


        System.out.println("==== TESTE 2: seller findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list1 = sellerDao.findByDepartment(department);
        list1.forEach(System.out::println);
        System.out.println(department);

        System.out.println("==== TESTE 3: seller findAll ====");
        List<Seller> list2 = sellerDao.findAll();
        list2.forEach(System.out::println);
    }
}















