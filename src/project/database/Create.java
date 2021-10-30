package project.database;

import java.sql.*;

public class Create {
    
    Connection conn;

    public Create(Connection connection) {
        conn = connection;
    }

    public void addPatient(String name, int age, String sex, String phone, String remarks) {

        try {    
            
            float pendingPayments = 0;
            
            String query = "INSERT INTO patient_list " + 
            "(name, age, sex, phone_number, remarks, pending_payments) " +
            "VALUE (?, ?, ?, ?, ?, ?)";
        
            PreparedStatement p = conn.prepareStatement(query);

            p.setString(1, name);
            p.setInt(2, age);
            p.setString(3, sex);
            p.setString(4, phone);
            p.setString(5, remarks);
            p.setFloat(6, pendingPayments);

            p.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Unable to access sql");
        }

    }

}
