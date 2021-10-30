//Declaring package
package project.gui;

//Importing necessary modules
import java.util.*;
import project.system.*;

//Main class
public class PatientDetailsPage {
    
    //Attributes
    Patient patient;
    Scanner sc;

    //Constructor with one parameter
    public PatientDetailsPage(Scanner scanner) {
        patient = null;
        sc = scanner;
    }

    //Constructor with two parameters
    public PatientDetailsPage(Patient pat, Scanner scanner) {
        patient = pat;
        sc = scanner;
    }

    //Selects update patient or add patient details page
    public void detailsPage() {

        if(patient == null)
            patient = enterDetails();
        else
            patient = enterDetails(patient);
    }

    //Page to enter details for adding a new patient
    public Patient enterDetails() {

        //Introduction message
        System.out.println("""
        Enter patient details line by line
        In the order: 
        Name
        Age
        Sex(Male/Female/Others)
        Phone Number(Optional country code(with +) followed by 10 or more numbers)
        Remarks(avoid new lines)

        """);

        //Enter name
        //String name = sc.nextLine();
        String name = "Nidhin";
        
        //Enter valid age(natural number)
        // int age=1;
        // while(true) {
        //     try {
        //         age = sc.nextInt();
        //         sc.nextLine();
        //         if(age > 0)
        //             break;
        //     } catch (InputMismatchException e) {
        //         System.out.println("Invalid age input");
        //         sc.next();
        //     }
        //     if(age <= 0)
        //         System.out.println("Invalid age input");
        // }
        int age = 21;

        //Enter valid sex(Male, Female or Others)
        String sex = "Male";
        // Boolean check = false;
        // do {
        //     sex = sc.nextLine();
        //     check = sex.equals("Male") || sex.equals("Female") || sex.equals("Others");
        //     if(!check)
        //         System.out.println("Invalid sex");
        // }while(!check);

        //Enter valid phone number(Optional + followed by a set of numbers)
        String phone = "7668019719";
        // do {
        //     phone = sc.nextLine();
        //     check = phone.matches("^\\+?\\d+");
        //     if(!check)
        //         System.out.println("Invalid phone number");
        // }while(!check);

        //Enter remarks
        //String remarks = sc.nextLine();
        String remarks = "None";

        //Creating the new patient
        Patient newPatient = new Patient(name, age, sex, phone, remarks, 0);

        //Adding appointments for the new patient
        newPatient = addAppointment(newPatient);

        //Obtaining list of appointments for the new patient
        ArrayList<Appointment> appointments = newPatient.getAppointments().getAppointments();
        
        //Setting prescription details and patient name for the appointment
        for(int i=0; i<appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            appointment.setPatientName(newPatient.getName());
            Prescription prescription = appointment.getPrescription();
            prescription.setAppointmentDetails(appointment.getPatientName() + ": " + appointment.getTime());
            appointment.setPrescription(prescription);            
            appointments.set(i, appointment);        
        }

        //Updating the appointment
        AppointmentsList apps = newPatient.getAppointments();
        apps.setAppointments(appointments);
        newPatient.setAppointments(apps); 

        //Return the new patient
        return newPatient;

    }

    //Page to enter details for updating a patient's details
    public Patient enterDetails(Patient pat) {

        //Introduction message
        System.out.println("""
        Enter new patient details line by line
        In the order(Press enter/0 for age to skip): 
        Name
        Age
        Sex(Male/Female/Others)
        Phone Number(Optional country code(with +) followed by 10 or more numbers)
        Remarks(avoid new lines)

        """);

        //Prints the current name and optional updation
        System.out.printf("Current name: %s\n", pat.getName());
        String name = sc.nextLine();
        if(!name.isEmpty())
            pat.setName(name);
        
        //Prints current age and optional updation
        System.out.printf("Current age: %d\n", pat.getAge());
        int age=0;
        while(true) {
            try {
                age = sc.nextInt();
                sc.nextLine();
                if(age >= 0)
                    break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid age input");
                sc.next();
            }
            if(age < 0)
                System.out.println("Invalid age input");
        }
        if(age != 0)
            pat.setAge(age);
        
        //Prints current sex and optional updation
        System.out.printf("Current sex: %s\n", pat.getSex());
        String sex;
        Boolean check = true;
        Boolean check2 = true;
        do {
            sex = sc.nextLine();
            check = sex.isEmpty();
            if(!check) {
                check2 = sex.equals("Male") || sex.equals("Female") || sex.equals("Others");
                if(!check2)
                    System.out.println("Invalid sex input");
            }
        }while(!check2);
        if(!sex.isEmpty())
            pat.setSex(sex);

        //Prints current phone number and optional updation
        System.out.printf("Current phone number: %s\n", pat.getPhoneNumber());
        String phone;
        do {
            phone = sc.nextLine();
            check = phone.matches("^\\+?\\d+|");
            if(!check)
                System.out.println("Invalid phone number");
        }while(!check);
        if(!phone.isEmpty())
            pat.setPhoneNumber(phone);

        //Prints current remarks and optional updation
        System.out.printf("Current remarks: %s\n", pat.getRemarks());
        String remarks = sc.nextLine();
        if(!remarks.isEmpty())
            pat.setRemarks(remarks);

        //Updating patient name of appointments and appointment details of prescription
        ArrayList<Appointment> appointments = pat.getAppointments().getAppointments();
        
        for(int i=0; i<appointments.size(); i++) {

            Appointment appointment = appointments.get(i);
            appointment.setPatientName(pat.getName());
            Prescription prescription = appointment.getPrescription();
            prescription.setAppointmentDetails(appointment.getPatientName() + ": " + appointment.getTime());
            appointment.setPrescription(prescription);            
            appointments.set(i, appointment);

        }

        AppointmentsList apps = pat.getAppointments();
        apps.setAppointments(appointments);
        pat.setAppointments(apps);

        return pat;

    }

    public Patient addAppointment(Patient newPatient) {

        //Create new appointmentslist
        AppointmentsList appointments = new AppointmentsList();

        char choice;

        //Choose whether or not you want to add new appointments
        do {
            System.out.println("Add Appointment?(y/n)");
            choice = sc.nextLine().charAt(0);

            switch(choice) {
                case 'y':   AppointmentDetailsPage appointmentDetails = new AppointmentDetailsPage(sc);
                            appointmentDetails.detailsPage();
                            Appointment appointment = appointmentDetails.getAppointment();

                            ArrayList<Appointment> apps = appointments.getAppointments();
                            apps.add(appointment);
                            appointments.setAppointments(apps);
                            
                            break;
                
                case 'n':   break;
                default:    System.out.println("Invalid choice");
            }
        
        }while(choice != 'n');

        newPatient.setAppointments(appointments);

        newPatient.updatePendingPayments();
        
        return newPatient;

    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient pat) {
        patient = pat;
    }

}
