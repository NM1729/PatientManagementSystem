package project.gui;

import java.util.*;
import project.system.*;

public class AppointmentsListPage {
    
    private AppointmentsList appointments;
    private Scanner sc;
    private PatientsList patients;
    
    public AppointmentsListPage(AppointmentsList appointmentsList, Scanner scanner) {
        appointments = appointmentsList;
        sc = scanner;
        patients = null;
    }

    public AppointmentsListPage(AppointmentsList appointmentsList, Scanner scanner, PatientsList pats) {
        appointments = appointmentsList;
        sc = scanner;
        patients = pats;
    }

    public void display(ArrayList<Appointment> appointments) {
        int i=1;
        System.out.println("S.No.|Patient Name|Discomfort|Time|Amount|");
        System.out.println("------------------------------------------");
        for(Appointment appointment : appointments) {
            System.out.printf("""
            %d|%s|%s|%s|%f
            --------------
            """, i, appointment.getPatientName(), appointment.getDetailsOfDiscomfort(), appointment.getTime(), appointment.getAmountToBePaid());
            i+=1;
        }
    }

    public int selectMenuOption() {

        System.out.println("""
        Select option:
        1. View appointments list
        2. View appointment
        3. Add appointment
        4. Sort list by time
        5. Search appointments by time
        6. Update appointment
        7. Delete appointment
        8. Delete all completed appointments
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

    public void sortList() {
        appointments.sortList();
    }

    public ArrayList<Appointment> searchList(String time) {

        ArrayList<Appointment> appointmentsArray = appointments.getAppointments();
        ArrayList<Appointment> newArray = new ArrayList<Appointment>();

        for(int i=0; i<appointmentsArray.size(); i++) {

            Appointment appointment = appointmentsArray.get(i);
            appointment.setStatus("0" + appointment.getStatus());
            appointmentsArray.set(i, appointment);

        }

        appointments.setAppointments(appointmentsArray);

        for(int i=0; i<appointmentsArray.size(); i++) {
            Appointment searchedAppointment = appointments.searchList(time);
            if(searchedAppointment != null)
                newArray.add(searchedAppointment);
        }

        for(int i=0; i<appointmentsArray.size(); i++) {

            Appointment appointment = appointmentsArray.get(i);
            appointment.setStatus(appointment.getStatus().substring(1));
            appointmentsArray.set(i, appointment);

        }

        for(int i=0; i<newArray.size(); i++) {

            Appointment appointment = newArray.get(i);
            appointment.setStatus(appointment.getStatus().substring(1));
            newArray.set(i, appointment);

        }

        appointments.setAppointments(appointmentsArray);

        return newArray;
    }

    public void deleteAppointment(Appointment appointment) {
        appointments.deleteFromList(appointment);
    }

    public void deleteAppointment() {
        appointments.deleteFromList();
    }

    public int selectAppointment() {

        System.out.println(appointments.getAppointments().isEmpty());
        if(appointments.getAppointments().isEmpty()) {
            System.out.println("No appointments.");
            return -1;
        }

        else {
            System.out.println("Enter serial no of appointment to select: ");
            int number;
            while(true) {
                try {
                    number = sc.nextInt();
                    sc.nextLine();
                    if(number < 1 || number > appointments.getAppointments().size())
                        System.out.println("Invalid input");
                    else
                        break;
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input");
                }
            }

            return number-1;
        }

    }

    public void addAppointment() {

        if(patients == null) {
            AppointmentDetailsPage appointmentDetails = new AppointmentDetailsPage(sc);
            Appointment appointment = appointmentDetails.detailsPage();
            updateList(-1, appointment);
        }

        if(patients != null) {
            AppointmentDetailsPage appointmentDetails = new AppointmentDetailsPage(sc);
            Appointment appointment = appointmentDetails.detailsPage();
            updateList(-1, appointment);

            PatientsListPage patientsListPage = new PatientsListPage(patients, sc);
            int index = patientsListPage.selectPatient();

            if(index != -1) {
                Patient patient = patients.getPatients().get(index);
                appointment.setPatientName(patient.getName());
                AppointmentsList appsList = patient.getAppointments();
                ArrayList<Appointment> apps = appsList.getAppointments();
                apps.add(appointment);
                appsList.setAppointments(apps);
                patient.setAppointments(appsList);
                ArrayList<Patient> pats = patients.getPatients();
                pats.set(index, patient);
                patients.setPatients(pats);

            }
        }
    }

    public void viewAppointment() {

        int index = selectAppointment();
        if(index != -1) {
            Appointment selectedAppointment = appointments.getAppointments().get(index);
            AppointmentPage appointmentPage = new AppointmentPage(selectedAppointment, sc);
            selectedAppointment = appointmentPage.show();
            updateList(index, selectedAppointment);
        }

    }

    public Appointment updateAppointment(Appointment appointment) {

        AppointmentDetailsPage appointmentDetails = new AppointmentDetailsPage(appointment, sc);
        appointmentDetails.detailsPage();
        return appointmentDetails.getAppointment();

    };

    public void updateList(int index, Appointment appointment) {

        ArrayList<Appointment> appointmentsList = appointments.getAppointments();
        if(index != -1)
            appointmentsList.set(index, appointment);
        else
            appointmentsList.add(appointment);
        appointments.setAppointments(appointmentsList);

    }

    public AppointmentsList show() {

        int menuOption=0;

        while(menuOption != 9) {
            menuOption = selectMenuOption();
            Appointment appointment;
            int index;

            switch(menuOption) {
                
                case 1: display(appointments.getAppointments());
                        break;
                case 2: viewAppointment();
                        break;
                case 3: addAppointment();
                        break;
                case 4: sortList();
                        break;
                case 5: Utils utils = new Utils(sc);
                        String time = utils.inputTime();
                        ArrayList<Appointment> searchedAppointments = searchList(time);
                        if(searchedAppointments.isEmpty())
                            System.out.println("No appointments found");
                        else
                            display(searchedAppointments);
                        break;
                case 6: index = selectAppointment();
                        if(index != -1) {
                            appointment = updateAppointment(appointments.getAppointments().get(index));
                            updateList(index, appointment);
                        }
                        break;
                case 7: index = selectAppointment();
                        if(index != -1) {
                            appointment = appointments.getAppointments().get(index);
                            deleteAppointment(appointment);
                        }
                        break;
                case 8: deleteAppointment();
                        break;
                case 9: break;
                default: System.out.println("Invalid input");

            }
        }

        return appointments;

    }

    public void setAppointments(AppointmentsList apps) {
        appointments = apps;
    }

    public void setPatients(PatientsList pats) {
        patients = pats;
    }

    public AppointmentsList getAppointments() {
        return appointments;
    }

    public PatientsList getPatients() {
        return patients;
    }

}

