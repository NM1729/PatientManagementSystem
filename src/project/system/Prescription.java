package project.system;

import java.util.*;

public class Prescription {
    
    private String appointmentDetails;
    private HashMap<String, String> items;
    private String remarks;

    Prescription(String details, String Remarks) {
        items = new HashMap<String, String>();
        appointmentDetails = details;
        remarks = Remarks;
    }

    public void addItem(Map.Entry<String, String> item) {
        items.put(item.getKey(), item.getValue());
    }

    public void removeItem(Map.Entry<String, String> item) {
        items.remove(item.getKey());
    }

    public void setAppointmentDetails(String details) {
        appointmentDetails = details;
    }

    public void setItems(HashMap<String, String> Items) {
        items = Items;
    }

    public void setRemarks(String Remarks) {
        remarks = Remarks;
    }

    public String getAppointmentDetails() {
        return appointmentDetails;
    }

    public HashMap<String, String> getItems() {
        return items;
    }

    public String getRemarks() {
        return remarks;
    }

}
