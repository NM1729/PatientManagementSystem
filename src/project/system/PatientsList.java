package project.system;

import java.util.*;

public class PatientsList {
    
    private ArrayList<Patient> patients;

    public PatientsList() {
        patients = new ArrayList<Patient>();
    }

    public void setPatients(ArrayList<Patient> Patients) {
        patients = Patients;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void sortList(String field) {

        for(int i=1; i<patients.size(); i++) {
            int j = i-1;
            Patient key = patients.get(i);
            String name1, name2;

            while(j >= 0) {

                if(field.equals("Name")) {
                    name1 = key.getName();
                    name2 = patients.get(j).getName();

                    if(name1.compareTo(name2) > 0)
                        break;
                }

                else if(field.equals("Payment")) {
                    if(key.getPendingPayments() > patients.get(j).getPendingPayments())
                        break;
                }

                patients.set(j+1, patients.get(j));
                j--;
            }

            patients.set(j+1, key);
        }

        if(field.equals("Name")) {
            for(Patient patient : patients)
                System.out.println(patient.getName());
        }

        else {
            for(Patient patient: patients)
                System.out.println(patient.getPendingPayments());
        }

    }

    public void deleteFromList(Patient patient) {

        for(Patient pat : patients) {
            if(pat.equals(patient)) {
                patients.remove(pat);
                break;
            }
        }
        
    }

    public Patient searchList(String name) {

        Patient result = null;
        
        for(Patient patient : patients) {
            String phone = patient.getPhoneNumber();
            if(phone.charAt(0) == 'P') {
                patient.setPhoneNumber("C" + phone.substring(1));
                if(patient.getName().equals(name)) {
                    result = patient;
                    break;
                }
            }
        }

        return result;

    }

}
