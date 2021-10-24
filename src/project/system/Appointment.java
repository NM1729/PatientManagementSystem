package project.system;

public class Appointment {
    
    private String patientName;
    private String detailsOfDiscomfort;
    private String time;
    private float amountToBePaid;
    private String remarks;
    private Prescription prescription;
    private String status;

    public Appointment(String name, String details, String Time, float amount, String Remarks) {
        patientName = name;
        detailsOfDiscomfort = details;
        time = Time;
        amountToBePaid = amount;
        remarks = Remarks;
        String prescriptionDetails = name + ": " + Time;
        prescription = new Prescription(prescriptionDetails, "None");
        status = "PENDING";
    }

    public void setPatientName(String name) {
        patientName = name;
    }

    public void setDetailsOfDiscomfort(String details) {
        detailsOfDiscomfort = details;
    }

    public void setTime(String Time) {
        time = Time;
    }

    public void setAmountToBePaid(float amount) {
        amountToBePaid = amount;
    }

    public void setRemarks(String Remarks) {
        remarks = Remarks;
    }

    public void setPrescription(Prescription prescribedItems) {
        prescription = prescribedItems;
    }

    public void setStatus(String Status) {
        status = Status;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDetailsOfDiscomfort() {
        return detailsOfDiscomfort;
    }

    public String getTime() {
        return time;
    }

    public float getAmountToBePaid() {
        return amountToBePaid;
    }

    public String getRemarks() {
        return remarks;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public String getStatus() {
        return status;
    }

}
