package project.system;

import java.util.*;

public class Patient {
    
    private String name;
    private int age;
    private String sex;
    private String phoneNumber;
    private String remarks;
    private float pendingPayments;
    private AppointmentsList appointments;

    public Patient(String Name, int Age, String Sex, String phone, String Remarks, float payment) {
        name = Name;
        age = Age;
        sex = Sex;
        phoneNumber = phone;
        remarks = Remarks;
        pendingPayments = payment;
        appointments = new AppointmentsList();
    }

    public void updatePendingPayments() {
        ArrayList<Appointment> Appointments = appointments.getAppointments();

        float payment = 0;

        for(Appointment appointment : Appointments) {
            payment += appointment.getAmountToBePaid();
        }

        pendingPayments = payment;
    }

    public void setName(String Name) {
        name = Name;
    }

    public void setAge(int Age) {
        age = Age;
    }

    public void setSex(String Sex) {
        sex = Sex;
    }

    public void setPhoneNumber(String phone) {
        phoneNumber = phone;
    }

    public void setRemarks(String Remarks) {
        remarks = Remarks;
    }

    public void setPendingPayments(float payments) {
        pendingPayments = payments;
    }

    public void setAppointments(AppointmentsList Appointments) {
        appointments = Appointments;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public float getPendingPayments() {
        return pendingPayments;
    }

    public AppointmentsList getAppointments() {
        return appointments;
    }
    
}
