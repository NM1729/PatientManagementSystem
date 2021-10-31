package project.gui;

import java.util.*;
import project.system.*;

public class PatientPage {
    
    Scanner sc;
    Patient patient;

    PatientPage(Patient pat, Scanner scanner) {
        patient = pat;
        sc = scanner;
    }

    public void displayDetails() {

        System.out.println("Patient details");
        System.out.println("---------------");
        System.out.println();

        System.out.printf("""
        Name: %s
        Age: %d
        Sex: %s
        Phone Number: %s
        Pending Payments: %f
        Remarks: %s
        """, patient.getName(), patient.getAge(), patient.getSex(), patient.getPhoneNumber(), patient.getPendingPayments(), patient.getRemarks());
        
    }

    public AppointmentsList displayAppointments() {

        AppointmentsList apps = patient.getAppointments();
        AppointmentsListPage appsListPage = new AppointmentsListPage(apps, sc);
        System.out.println("Appointments");
        System.out.println("---------------");
        System.out.println();
        appsListPage.display(apps.getAppointments());
        System.out.println();
        return appsListPage.show();

    }

    public Patient show() {

        displayDetails();
        AppointmentsList appsList = displayAppointments();
        ArrayList<Appointment> apps = new ArrayList<Appointment>();

        for(Appointment app : appsList.getAppointments()) {
            app.setPatientName(patient.getName());
            apps.add(app);
        }

        appsList.setAppointments(apps);
        patient.setAppointments(appsList);
        return patient;

    }

}
