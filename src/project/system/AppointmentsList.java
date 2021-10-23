package project.system;

import java.util.*;

public class AppointmentsList {
    
    private ArrayList<Appointment> appointments;

    AppointmentsList() {
        appointments = new ArrayList<Appointment>();
    }

    public void sortList() {

    }

    public void deleteFromList(Appointment appointment) {

        for(Appointment Appointment : appointments) {
            if(Appointment.equals(appointment)) {
                appointments.remove(appointment);
            }
        }
    }

    public void deleteFromList() {

        for(Appointment Appointment : appointments) {
            if(Appointment.getStatus().equals("COMPLETED")) {
                appointments.remove(Appointment);
            }
        }
    }

    public Appointment searchList(String time) {

        Appointment result = null;
        
        for(Appointment Appointment : appointments) {
            if(Appointment.getTime().equals(time)) {
                result = Appointment;
            }
        }

        return result;

    }
    
    public void setAppointments(ArrayList<Appointment> Appointments) {
        appointments = Appointments;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

}
