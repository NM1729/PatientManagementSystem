package project.database;

import java.sql.*;
import project.system.*; 
import java.util.*;

public class Fetch {
    
    Connection conn;

    public Fetch(Connection connection) {
        conn = connection;
    }

    public HashMap<String, String> fetchItems(int presID) {

        HashMap<String, String> items = new HashMap<String, String>();

        String query = """
        SELECT i.item_name, i.item_timings
        FROM items i, prescription_items pi, prescriptions p
        WHERE p.prescription_id = ? AND i.item_id=pi.item_id AND pi.prescription_id=p.prescription_id
        """;

        try {

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, presID);

            ResultSet rs = pst.executeQuery(query);

            while(rs.next()) {
                String item_name = rs.getString(1);
                String item_timings = rs.getString(2);

                items.put(item_name, item_timings);
            }

            return items;
        } catch (SQLException e) {
            System.out.println("Invalid SQL");
            return null;
        }

    }

    public Prescription fetchPrescription(int presID) {

        String query = """
        SELECT p.appointmentDetails, p.remarks
        FROM prescriptions p
        WHERE p.prescription_id = ?
        """;

        try {

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, presID);

            ResultSet rs = pst.executeQuery(query);

            String details="", remarks="";
            HashMap<String, String> items = null;

            while(rs.next()) {
                details = rs.getString(1);
                remarks = rs.getString(2);

                items = fetchItems(presID);
            }

            Prescription prescription = new Prescription(details, remarks);
            prescription.setItems(items);

            return prescription;
        } catch (SQLException e) {
            System.out.println("Invalid SQL");
            return null;
        }

    }

    public Appointment fetchAppointment(int appID) {

        String query = """
        SELECT a.patientName, a.detailsOfDiscomfort, a.consultingTime, a.amountToBePaid, a.remarks, a.prescription_id, a.appointmentStatus
        FROM appointmentlist a
        WHERE a.appointment_id = ?
        """;

        try {

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, appID);

            ResultSet rs = pst.executeQuery(query);

            String name="", details="", time="", remarks="", status="";
            float amount = 0;
            int prescription_id = 0;
            Prescription prescription = null;

            while(rs.next()) {
                name = rs.getString(1);
                details = rs.getString(2);
                time = rs.getString(3);
                amount = rs.getFloat(4);
                remarks = rs.getString(5);
                prescription_id = rs.getInt(6);
                status = rs.getString(7);

                prescription = fetchPrescription(prescription_id);
            }

            Appointment appointment = new Appointment(name, details, time, amount, remarks);
            appointment.setStatus(status);
            appointment.setPrescription(prescription);

            return appointment;

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
            return null;
        }

    }
    
    public Patient fetchPatient(int id) {

        String query = """
        SELECT p.name, p.age, p.sex, p.phone_number, p.remarks, p.pending_payments
        FROM patient_list p
        WHERE p.patient_id=?
        """;

        try {

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery(query);

            String name="", sex="", phone="", remarks="";
            float amount = 0;
            int age = 0;
            ArrayList<Appointment> appointments = new ArrayList<Appointment>();

            while(rs.next()) {
                name = rs.getString(1);
                age = rs.getInt(2);
                sex = rs.getString(3);
                phone = rs.getString(4);
                remarks = rs.getString(5);
                amount = rs.getFloat(6);
            }

            Patient patient = new Patient(name, age, sex, phone, remarks, amount);
            AppointmentsList appsList = new AppointmentsList();

            String query2 = """
            SELECT a.appointment_id
            FROM appointmentlist a, appointment_patient ap, patient_list p
            WHERE a.appointment_id=ap.appointment_id AND p.patient_id=ap.patient_id AND p.patient_id=?
            """;

            PreparedStatement pst2 = conn.prepareStatement(query2);
            pst2.setInt(1, id);

            ResultSet rs2 = pst2.executeQuery(query2);

            int appId;
            while(rs2.next()) {
                appId = rs2.getInt(1);

                Appointment appointment = fetchAppointment(appId);
                appointments.add(appointment);
            }

            appsList.setAppointments(appointments);
            patient.setAppointments(appsList);

            return patient;

        } catch (SQLException e) {
            System.out.println("Invalid SQL");
            return null;
        }

    }

    public PatientsList fetchData() {

        String query = """
        SELECT p.patient_id
        FROM patient_list p
        """;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ArrayList<Integer> ids = new ArrayList<Integer>();

            while(rs.next()) {
                ids.add(rs.getInt(1));
            }

            ArrayList<Patient> patients = new ArrayList<Patient>();
            
            for(int id : ids) {
                Patient patient = fetchPatient(id);
                patients.add(patient);
            }

            PatientsList patientsList = new PatientsList();
            patientsList.setPatients(patients);

            return patientsList;

        } catch(SQLException e) {
            System.out.println("Invalid sql");
            return null;
        }

    }


}
