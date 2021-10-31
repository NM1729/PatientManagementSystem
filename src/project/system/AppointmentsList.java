package project.system;

import java.util.*;

public class AppointmentsList {
    
    private ArrayList<Appointment> appointments;

    public AppointmentsList() {
        appointments = new ArrayList<Appointment>();
    }

    public void sortList() {

        for(int i=1; i<appointments.size(); i++) {
            int j = i-1;
            Appointment key = appointments.get(i);
            String time1, time2;

            while(j >= 0) {
                time1 = key.getTime();
                time2 = appointments.get(j).getTime();

                if(time1.compareTo(time2) > 0)
                    break;

                appointments.set(j+1, appointments.get(j));
                j--;
            }

            appointments.set(j+1, key);
        }

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
        
        for(Appointment appointment : appointments) {
            String status = appointment.getStatus();
            if(status.charAt(0) == '0') {
                appointment.setStatus("1" + status.substring(1));
                if(appointment.getTime().equals(time)) {
                    result = appointment;
                    break;
                }
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
