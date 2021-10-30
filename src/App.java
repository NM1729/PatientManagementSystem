//Importing necessary modules
import project.gui.*;
import project.system.*;
import java.util.*;

//Main class
public class App {

    //Main method
    public static void main(String[] args) {
        
        //Input variables
        int choice=0;
        Scanner sc = new Scanner(System.in);

        //Patient and appointment list to be displayed and edited
        PatientsList patientsList = new PatientsList();
        AppointmentsList appointmentsList = new AppointmentsList();

        //While option isnt 3
        do {

            System.out.println("""
            1. Patients List
            2. Appointments List
            3. Exit
            """);

            choice = sc.nextInt();
            sc.nextLine();

            ArrayList<Appointment> apps = new ArrayList<Appointment>();

            for(Patient patient : patientsList.getPatients()) {
                for(Appointment appointment : patient.getAppointments().getAppointments()) {
                    apps.add(appointment);
                }
            }

            appointmentsList.setAppointments(apps);

            switch(choice) {

                case 1: PatientsListPage patientsListPage = new PatientsListPage(patientsList, sc);
                        patientsList = patientsListPage.show();
                        break;
                
                case 2: AppointmentsListPage appointmentsListPage = new AppointmentsListPage(appointmentsList, sc, patientsList);
                        appointmentsList = appointmentsListPage.show();
                        patientsList = appointmentsListPage.getPatients();
                        break;
            }

        }while(choice != 3);

        sc.close();

    }

}