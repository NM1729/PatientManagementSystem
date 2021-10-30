package project.gui;

import java.util.*;
import project.system.*;

public class AppointmentPage {
    
    Scanner sc;
    Appointment appointment;

    AppointmentPage(Appointment app, Scanner scanner) {
        appointment = app;
        sc = scanner;
    }

    public void displayDetails() {

        System.out.println("Appointment details");
        System.out.println("---------------");
        System.out.println();

        System.out.printf("""
        PatientName: %s
        Details of Discomfort: %s
        Time: %s
        Amount: %f
        Remarks: %s
        Status: %s
        """, appointment.getPatientName(), appointment.getDetailsOfDiscomfort(), appointment.getTime(), appointment.getAmountToBePaid(), appointment.getRemarks(), appointment.getStatus());
        
    }

    public Prescription displayPrescription() {

        Prescription prescription = appointment.getPrescription();
        PrescriptionPage prescriptionPage = new PrescriptionPage(prescription, sc);
        prescriptionPage.show();
        return prescriptionPage.getPrescription();

    }

    public Appointment show() {
        displayDetails();
        System.out.println("Show prescription?(y/n)");

        String choice;

        while(true) {
            choice = sc.nextLine();
            if(choice.equals("y") || choice.equals("n"))
                break;
        }

        if(choice.equals("y")) {
            Prescription pres = displayPrescription();
            appointment.setPrescription(pres);
        }

        return appointment;
    }

}
