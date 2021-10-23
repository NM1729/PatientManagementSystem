package project.system;

import java.util.*;

public class PatientsList {
    
    private ArrayList<Patient> patients;

    PatientsList() {
        patients = new ArrayList<Patient>();
    }

    public void setPatients(ArrayList<Patient> Patients) {
        patients = Patients;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void sortList(String field) {

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
        
        for(Patient pat : patients) {
            String phone = pat.getPhoneNumber();
            if(phone.charAt(0) == 'C') 
                continue;
            else {
                pat.setPhoneNumber("C" + phone.substring(1));
                if(pat.getName().equals(name)) {
                    result = pat;
                    break;
                }
            }
        }

        return result;

    }

}
