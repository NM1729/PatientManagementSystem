package project.database;

import java.sql.*;
import project.system.*; 
import java.util.*;

public class Insert {
    
    Connection conn;

    public Insert(Connection connection) {
        conn = connection;
    }

    public void insertItems(int presID, HashMap<String, String> items) {

        String query1 = """
        SELECT COUNT(*) AS total FROM items;
        """;

        String query2 = """
        INSERT INTO items (item_id, item_name, item_timings) VALUES (?, ?, ?)
        """;
        
        String query3 = """
        INSERT INTO prescription_items (prescription_id, item_id) VALUES (?, ?)
        """;

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);

            int count = rs.getInt("total");

            int i = count + 1;
            for(Map.Entry<String, String> item : items.entrySet()) {
                
                PreparedStatement pst = conn.prepareStatement(query2);
                pst.setInt(1, i);
                pst.setString(2, item.getKey());
                pst.setString(3, item.getValue());

                PreparedStatement pst2 = conn.prepareStatement(query3);
                pst2.setInt(1, presID);
                pst2.setInt(2, i);

                pst.executeUpdate();
                pst2.executeUpdate();

                i++;
            }

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
        }

    }

    public void insertPrescription(Prescription prescription, int presID) {
        
        String query2 = """
        INSERT INTO prescriptions 
        (prescription_id, appointmentDetails, remarks)
        VALUES (?, ?, ?)
        """;

        try {
                
            PreparedStatement pst = conn.prepareStatement(query2);
            pst.setInt(1, presID);
            pst.setString(2, prescription.getAppointmentDetails());
            pst.setString(3, prescription.getRemarks());

            pst.executeUpdate();

            insertItems(presID, prescription.getItems());

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
        }

    }

    public void insertAppointments(AppointmentsList appointments, int patientID) {

        String query1 = """
        SELECT COUNT(*) AS total FROM appointmentlist;
        """;
        
        String query2 = """
        INSERT INTO appointmentlist 
        (appointment_id, patientName, detailsOfDiscomfort, consultingTime, amountToBePaid, remarks, prescription_id, appointmentStatus)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        String query3 = """
        SELECT COUNT(*) AS total FROM prescriptions;
        """;

        String query4 = """
        INSERT INTO appointment_patient
        (patient_id, appointment_id)
        VALUES (?, ?)
        """;

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);

            int appCount = rs.getInt("total");

            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(query3);

            int presCount = rs2.getInt("total");

            int i = appCount + 1;
            int j = presCount + 1;

            ArrayList<Appointment> apps = new ArrayList<Appointment>();
            
            for(Appointment appointment : apps) {

                PreparedStatement pst = conn.prepareStatement(query2);
                pst.setInt(1, i);
                pst.setString(2, appointment.getPatientName());
                pst.setString(3, appointment.getDetailsOfDiscomfort());
                pst.setString(4, appointment.getTime());
                pst.setFloat(5, appointment.getAmountToBePaid());
                pst.setString(6, appointment.getRemarks());
                
                int presId = 0;
                if(appointment.getPrescription().getAppointmentDetails() != "None") {
                    presId = j;
                    j++;
                }

                pst.setInt(7, presId);
                pst.setString(8, appointment.getStatus());

                pst.executeUpdate();

                insertPrescription(appointment.getPrescription(), i);

                PreparedStatement pst2 = conn.prepareStatement(query4);
                pst2.setInt(1, patientID);
                pst2.setInt(2, i);

                pst2.executeUpdate();
                
                i++;
                
            }

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
        }

    }
    
    public void insertPatient(Patient patient) {

        String query1 = """
        SELECT COUNT(*) AS total FROM patient_list
        """;

        String query2 = """
        INSERT INTO patient_list
        (patient_id, name, age, sex, phone_number, remarks, pending_payments)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            int patCount = rs.getInt("total");

            int id = patCount + 1;

            PreparedStatement pst = conn.prepareStatement(query2);
            pst.setInt(1, id);
            pst.setString(2, patient.getName());
            pst.setInt(3, patient.getAge());
            pst.setString(4, patient.getSex());
            pst.setString(5, patient.getPhoneNumber());
            pst.setString(6, patient.getRemarks());
            pst.setFloat(7, patient.getPendingPayments());

            pst.executeUpdate();

            insertAppointments(patient.getAppointments(), id);

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
        }

    }

    public void insertData(PatientsList patients) {

        ArrayList<Patient> patientsList = patients.getPatients();

        for(Patient patient : patientsList) {
            insertPatient(patient);
        }

    }


}
