package project.gui;

import java.util.*;
import project.system.*;

public class PrescriptionPage {
    
    Prescription prescription;
    Scanner sc;

    PrescriptionPage(Prescription pres, Scanner scanner) {
        prescription = pres;
        sc = scanner;
    }

    public void displayDetails() {

        System.out.println("Prescription details");
        System.out.println("---------------");
        System.out.println();

        System.out.printf("""
        Appointment Details: %s
        Remarks: %s
        """, prescription.getAppointmentDetails(), prescription.getRemarks());
    
    }

    public void displayItems() {

        System.out.println("Prescription Items");
        System.out.println("------------------");
        System.out.println("Item Name | Item Timing");
        System.out.println("-----------------------");

        HashMap<String, String> items = prescription.getItems();
        int i=1;

        for(Map.Entry<String, String> item : items.entrySet()) {
            System.out.printf("%d|%s|%s\n", i, item.getKey(), item.getValue());
            i++;
        }

        System.out.println("""
        
        1. Add item
        2. Remove item
        3. Update item
        4. Back
        
        """);

        int choice;

        while(true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if(choice == 1 || choice == 2)
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid choice");
                sc.next();
            }

            System.out.println("Invalid choice");
        }

        if(choice == 1) {
            addItem();
        }

        if(choice == 2) {
            
            Map.Entry<String, String> selectedItem = selectItem(items);

            if(selectedItem != null)
                prescription.removeItem(selectedItem);

        }

        if(choice == 3) {
            
            Map.Entry<String, String> selectedItem = selectItem(items);
            updateItem(selectedItem);
            
        }

    }

    public void addItem() {
        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();
        System.out.println("Enter item timings: ");
        String itemTiming = sc.nextLine();

        HashMap<String, String> item = new HashMap<String, String>();
        item.put(itemName, itemTiming);
        for(Map.Entry<String, String> entry : item.entrySet())
            prescription.addItem(entry);
    }

    public Map.Entry<String, String> selectItem(HashMap<String, String> items) {

        System.out.println("Enter serial number of item to remove: ");
            
        int serial=1;
        while(true) {
            try {
                serial = sc.nextInt();
                if(serial > 0 && serial <= items.size())
                    break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid input");
            }
            System.out.println("Invalid input");
        }

        int j=1;
        Map.Entry<String, String> selectedItem=null;
        for(Map.Entry<String, String> entry : items.entrySet()) {
            if(j == serial) {
                selectedItem = entry;
                break;
            }
            else
                j++;
        }

        return selectedItem;
    }

    public void updateItem(Map.Entry<String, String> selectedItem) {

        prescription.removeItem(selectedItem);
            
        System.out.println("Current item name: " + selectedItem.getKey());
        System.out.println("Enter item name: ");
        String itemName = sc.nextLine();
        if(!itemName.isEmpty())
            itemName = selectedItem.getKey();
            

        System.out.println("Current item timings: " + selectedItem.getValue());
        System.out.println("Enter item timings: ");
        String itemTiming = sc.nextLine();
        if(!itemTiming.isEmpty())
            itemTiming = selectedItem.getValue();

        HashMap<String, String> updatedItems = new HashMap<String, String>();
        updatedItems.put(itemName, itemTiming);

        for(Map.Entry<String, String> item : updatedItems.entrySet())
            prescription.addItem(item);

    }

    public void show() {

        displayDetails();
        System.out.println();
        displayItems();

    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription pres) {
        prescription = pres;
    }

}
