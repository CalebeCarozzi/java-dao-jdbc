package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {

            st = conn.prepareStatement("select s.*, d.name as DepName " +
                    "from seller s inner join department d " +
                    "on s.departmentId = d.Id " +
                    "where s.id = ?; ");

            st.setInt(1, id);
            rs = st.executeQuery();

            //testar se veio algum resultado, caso não tenha vindo, vai retornar falso, dai a gente retorna nulo
            if (rs.next()) {

                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, dep);

                return obj;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(dep);

        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }


    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {

            st = conn.prepareStatement(
                    "select s.*, d.name as DepName " +
                    "from seller s inner join department d " +
                    "on s.DepartmentId = d.id " +
                    "where s.DepartmentId = ? " +
                    "order by Name ");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            //como pode ter mais valores tem que ser um while, que dai ele percorre o resultset enquanto tem mais valores
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            //usa map pra não repetir a criação de departamentos
            // mas é meio redundante, já que já estmaos passando um objeto department instanciado, então não teria
            // que instanciar outro igual, e além desse não tem mais pra instanciar
            //pois a busca por departamento vai ser só essa
            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }


}
