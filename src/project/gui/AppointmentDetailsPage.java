package project.gui;

import java.util.*;
import project.system.*;

public class AppointmentDetailsPage {
    
    Appointment appointment;
    Scanner sc;
    Utils utils;
    public AppointmentDetailsPage(Scanner scanner) {
        appointment = null;
        sc = scanner;
        utils = new Utils(sc);
    }

    public AppointmentDetailsPage(Appointment app, Scanner scanner) {
        appointment = app;
        sc = scanner;
        utils = new Utils(sc);
    }

    public Appointment detailsPage() {

        if(appointment == null)
            appointment = enterDetails();
        else
            appointment = enterDetails(appointment);
        return appointment;
    }

    public Appointment enterDetails() {

        System.out.println("""
        Enter appointment details line by line
        In the order: 
        Details of Discomfort
        Year
        Month
        Day
        Hour
        Minute
        AM/PM
        Amount to be paid
        Remarks(avoid new lines)

        """);
        
        String detailsOfDiscomfort = sc.nextLine();
        
        String time = utils.inputTime();

        float amount=0;
        while(true) {
            try {
                amount = sc.nextFloat();
                sc.nextLine();
                if(amount >= 0)
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount input");
                sc.next();
            }
            if(amount < 0)
                System.out.println("Invalid amount input");
        }

        String remarks = sc.nextLine();

        appointment = new Appointment("", detailsOfDiscomfort, time, amount, remarks);

        char choice;

        System.out.println("Add Prescription?(y/n)");
        choice = sc.nextLine().charAt(0);

        switch(choice) {
            case 'y':   appointment = addPrescription(appointment);                            
                        break;
            
            case 'n':   break;
            default:    System.out.println("Invalid choice");
        }

        return appointment;

    }

    public Appointment enterDetails(Appointment app) {

        System.out.println("""
        Enter new appointment details line by line
        In the order(press Enter/0 for amount to skip): 
        Details of Discomfort
        Year
        Month
        Day
        Hour
        Minute
        AM/PM
        Amount to be paid
        Remarks(avoid new lines)

        """);

        System.out.printf("Current details: %s\n", app.getDetailsOfDiscomfort());
        String detailsOfDiscomfort = sc.nextLine();
        if(!detailsOfDiscomfort.isEmpty()) {
            app.setDetailsOfDiscomfort(detailsOfDiscomfort);
        }

        System.out.printf("Current time: %s\n", app.getTime());
        String time = utils.inputTime(app.getTime());
        if(!time.isEmpty()) {
            app.setTime(time);
        }

        System.out.printf("Current amount: %f\n", app.getAmountToBePaid());
        float amount=0;
        while(true) {
            try {
                amount = sc.nextFloat();
                sc.nextLine();
                if(amount >= 0)
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid amount input");
                sc.next();
            }
            if(amount < 0)
                System.out.println("Invalid amount input");
        }
        if(amount != 0)
            app.setAmountToBePaid(amount);

        System.out.printf("Current remarks: %s\n", app.getRemarks());
        String remarks = sc.nextLine();
        if(!remarks.isEmpty())
            app.setRemarks(remarks);
    
        System.out.printf("Current status: %s\n", app.getStatus());
        String status;
        Boolean check = true;
        Boolean check2 = true;
        do {
            status = sc.nextLine();
            check = status.isEmpty();
            if(!check) {
                check2 = status.equals("COMPLETED") || status.equals("PENDING");
                if(!check2)
                    System.out.println("Invalid status input");
            }
        }while(!check2);
        if(!status.isEmpty())
            app.setStatus(status);

        return app;

    }

    public Appointment addPrescription(Appointment appointment) {
      
        Prescription prescription = appointment.getPrescription();
        
        if(!prescription.getRemarks().equals("None"));
            System.out.printf("Current prescription remarks: %s\n", prescription.getRemarks());
        System.out.println("Enter prescription remarks: ");
        String remarks = sc.nextLine();
        if(remarks != "")
            prescription.setRemarks(remarks);

        char choice;

        HashMap<String, String> items = new HashMap<String, String>();

        do {
            System.out.println("Add Item?(y/n)");
            choice = sc.nextLine().charAt(0);

            switch(choice) {
                case 'y':   System.out.println("Enter item name: ");
                            String name = sc.nextLine();
                            System.out.println("Enter item timings: ");
                            String timings = sc.nextLine();

                            items.put(name, timings);

                            break;
                
                case 'n':   break;
                default:    System.out.println("Invalid choice");
            }
        
        }while(choice != 'n');

        for(Map.Entry<String, String> entry : items.entrySet())
            prescription.addItem(entry);
        
        appointment.setPrescription(prescription);

        return appointment;

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment app) {
        appointment = app;
    }

}

