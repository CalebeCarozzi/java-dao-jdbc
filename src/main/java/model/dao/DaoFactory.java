package model.dao;

import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    //serve para instanciar os daos

    public static SellerDao crateSellerDao(){
        return new SellerDaoJDBC();
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC();
    }

}
