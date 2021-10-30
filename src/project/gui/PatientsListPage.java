package project.gui;

import java.util.*;
import project.system.*;

public class PatientsListPage {
    
    private PatientsList patients;
    private Scanner sc;
    
    public PatientsListPage(PatientsList patientsList, Scanner scanner) {
        patients = patientsList;
        sc = scanner;
    }

    public void display(ArrayList<Patient> patients) {
        int i=1;
        System.out.println("S.No.|Name|Age|Sex|Pending Payments|");
        System.out.println("------------------------------------");
        for(Patient patient : patients) {
            System.out.printf("""
            %d|%s|%d|%s|%f
            --------------
            """, i, patient.getName(), patient.getAge(), patient.getSex(), patient.getPendingPayments());
            i+=1;
        }
    }

    public int selectMenuOption() {

        System.out.println("""
        Select option:
        1. View patients list
        2. View patient
        3. Add patient
        4. Sort list by name
        5. Sort list by pending payments
        6. Search patients by name
        7. Update patient
        8. Delete patient
        9. Back
        """);

        int choice;

        while(true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if(choice < 0 || choice > 9) 
                    System.out.println("Invalid choice");
                else
                    break;

            } catch(InputMismatchException e) {
                System.out.println("Invalid input");
                sc.next();
            }
        }

        return choice;
    }

    public void sortList(String field) {
        patients.sortList(field);
    }

    public ArrayList<Patient> searchList(String name) {
        ArrayList<Patient> originalArray = patients.getPatients();
        ArrayList<Patient> patientsArray = patients.getPatients();
        ArrayList<Patient> newArray = new ArrayList<Patient>();

        for(int i=0; i<patientsArray.size(); i++) {

            Patient patient = patientsArray.get(i);
            patient.setPhoneNumber("P" + patient.getPhoneNumber());
            patientsArray.set(i, patient);

        }

        patients.setPatients(patientsArray);

        for(int i=0; i<patientsArray.size(); i++) {
            Patient searchedPatient = patients.searchList(name);
            if(searchedPatient != null)
                newArray.add(searchedPatient);
        }

        patients.setPatients(originalArray);

        return newArray;
    }

    public void deletePatient(Patient patient) {
        patients.deleteFromList(patient);
    }

    public int selectPatient() {

        System.out.println("Enter serial no of patient to select: ");
        int number;
        while(true) {
            try {
                number = sc.nextInt();
                sc.nextLine();
                if(number < 1 || number > patients.getPatients().size())
                    System.out.println("Invalid input");
                else
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }

        return number-1;

    }

    public Patient addPatient() {

        PatientDetailsPage patientDetails = new PatientDetailsPage(sc);
        patientDetails.detailsPage();
        return patientDetails.getPatient();

    }

    public void viewPatient() {

        int index = selectPatient();
        Patient selectedPatient = patients.getPatients().get(index);
        PatientPage patientPage = new PatientPage(selectedPatient, sc);
        selectedPatient = patientPage.show();
        updateList(index, selectedPatient);

    }

    public Patient updatePatient(Patient appointment) {

        PatientDetailsPage appointmentDetails = new PatientDetailsPage(appointment, sc);
        appointmentDetails.detailsPage();
        return appointmentDetails.getPatient();

    };

    public void updateList(int index, Patient patient) {

        ArrayList<Patient> patientsList = patients.getPatients();
        if(index != -1)
            patientsList.set(index, patient);
        else
            patientsList.add(patient);
        patients.setPatients(patientsList);

    }

    public PatientsList show() {

        int menuOption = selectMenuOption();
        Patient patient;
        int index;

        switch(menuOption) {
            
            case 1: display(patients.getPatients());
                    break;
            case 2: viewPatient();
                    break;
            case 3: patient = addPatient();
                    updateList(-1, patient);
                    break;
            case 4: sortList("Name");
                    break;
            case 5: sortList("Payment");
                    break;
            case 6: System.out.println("Enter name to search: ");
                    String name = sc.nextLine();
                    ArrayList<Patient> searchedPatients = searchList(name);
                    if(searchedPatients.isEmpty())
                        System.out.println("No patients found");
                    else
                        display(searchedPatients);
                    break;
            case 7: index = selectPatient();
                    patient = updatePatient(patients.getPatients().get(index));
                    updateList(index, patient);
                    break;
            case 8: index = selectPatient();
                    patient = patients.getPatients().get(index);
                    deletePatient(patient);
                    break;
            case 9: break;
            default: System.out.println("Invalid input");

        }

        return patients;

    }

}
