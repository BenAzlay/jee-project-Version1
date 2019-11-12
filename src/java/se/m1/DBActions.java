package se.m1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static se.m1.Constants.*;

public class DBActions {
    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<Employee> listEmployees;
    ArrayList<User> listUsers;

    public DBActions(String dbUrl, String dbUser, String dbPwd) {
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public Statement getStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return stmt;

    }

    public ResultSet getResultSet(String query) {
        stmt = getStatement();
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;

    }
    
       
    public User getUser(String login, String pwd) {
        User userBean = new User();
        userBean.setLogin(login);
        userBean.setPwd(pwd);

        return userBean;
    }
    
    public ArrayList<User> getUsers() {
        listUsers = new ArrayList<>();
        rs = getResultSet(QUERY_SEL_CREDENTIALS);
        try {
            while (rs.next()) {
                User userBean = new User();
                userBean.setLogin(rs.getString("LOGIN"));
                userBean.setPwd(rs.getString("PWD"));

                listUsers.add(userBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listUsers;
    }

    public ArrayList<Employee> getEmployees() {
        listEmployees = new ArrayList<>();
        rs = getResultSet(QUERY_SEL_EMPLOYEES);
        try {
            while (rs.next()) {
                Employee employeeBean = new Employee();
                employeeBean.setId(rs.getInt("ID"));
                employeeBean.setFirstname(rs.getString("FIRSTNAME"));
                employeeBean.setLastname(rs.getString("NAME"));
                employeeBean.setAddress(rs.getString("ADRESS"));
                employeeBean.setTelHome(rs.getString("TELHOME"));
                employeeBean.setTelMob(rs.getString("TELMOB"));
                employeeBean.setTelPro(rs.getString("TELPRO"));
                employeeBean.setAddress(rs.getString("ADRESS"));
                employeeBean.setPostalcode(rs.getString("POSTALCODE"));
                employeeBean.setCity(rs.getString("CITY"));
                employeeBean.setEmail(rs.getString("EMAIL"));

                listEmployees.add(employeeBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listEmployees;
    }
    
    public Employee getEmployeeById(String sid) {
        int id = Integer.parseInt(sid); 
        Employee employeeBean = new Employee();
        rs = getResultSet(QUERY_SEL_EMPLOYEES);
        try {
            while (rs.next()) {
                if(rs.getInt("ID") == id){
                    employeeBean.setId(rs.getInt("ID"));
                    employeeBean.setFirstname(rs.getString("FIRSTNAME"));
                    employeeBean.setLastname(rs.getString("NAME"));
                    employeeBean.setAddress(rs.getString("ADRESS"));
                    employeeBean.setTelHome(rs.getString("TELHOME"));
                    employeeBean.setTelMob(rs.getString("TELMOB"));
                    employeeBean.setTelPro(rs.getString("TELPRO"));
                    employeeBean.setAddress(rs.getString("ADRESS"));
                    employeeBean.setPostalcode(rs.getString("POSTALCODE"));
                    employeeBean.setCity(rs.getString("CITY"));
                    employeeBean.setEmail(rs.getString("EMAIL"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeBean;
    }
    
    public void updateEmployee(String sid,
               String name,
               String firstname,
               String telHome,
               String telMob,
               String telPro,
               String address,
               String postalcode,
               String city,
               String email) throws SQLException {
        int id = Integer.parseInt(sid); 
        stmt = getStatement();
        int result = stmt.executeUpdate("UPDATE JEEPRJ SET NAME = '" + name + "',"
                + "FIRSTNAME = '" + firstname + "',"
                + "TELHOME = '" + telHome + "',"
                + "TELMOB = '" + telMob + "',"
                + "TELPRO = '" + telPro + "',"
                + "ADRESS = '" + address + "',"
                + "POSTALCODE = '" + postalcode + "',"
                + "CITY = '" + city + "',"
                + "EMAIL = '" + email + "'"
                + " WHERE ID = " + id);
        if (result == 1) {
                System.out.println("Employee Updated Successfully");
        }
    }
    public void deleteEmployee(String sid) throws SQLException {
        int id = Integer.parseInt(sid); 
        stmt = getStatement();
        int result = stmt.executeUpdate("DELETE FROM JEEPRJ WHERE ID = " + id);
        if (result == 1) {
                System.out.println("Employee Updated Successfully");
        }
    }
       
    public void addEmployee(String name,
               String firstname,
               String telHome,
               String telMob,
               String telPro,
               String address,
               String postalcode,
               String city,
               String email) throws SQLException {
        stmt = getStatement();
        int id = 0;
        rs = getResultSet(QUERY_SEL_EMPLOYEES);
        try {
            while (rs.next()) {
                id++; //The id of the new employee needs to equal id of previous + 1
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int result = stmt.executeUpdate("INSERT INTO JEEPRJ (NAME, FIRSTNAME, TELHOME, TELMOB, TELPRO, ADRESS, POSTALCODE, CITY, EMAIL)"
                + "VALUES ('"+name+"',"
                + "'"+firstname+"',"
                + "'"+telHome+"',"
                + "'"+telMob+"',"
                + "'"+telPro+"',"
                + "'"+address+"',"
                + "'"+postalcode+"',"
                + "'"+city+"',"
                + "'"+email+"')");
        if (result == 1) {
                System.out.println("Employee Added Successfully");
        }
    }

    public boolean checkCredentials(User userInput) {
        boolean test = false;
        listUsers = getUsers();

        for (User userBase : listUsers) {
            if (userBase.getLogin().equals(userInput.getLogin())
                    && userBase.getPwd().equals(userInput.getPwd())) {
                test = true;
            }
        }
        return test;
    }  
}