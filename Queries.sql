DROP TABLE IF EXISTS patient_list;
DROP TABLE IF EXISTS appointment_patient;
DROP TABLE IF EXISTS appointmentlist;
DROP TABLE IF EXISTS prescription_items;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS items;
CREATE TABLE patient_list(
	patient_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	age INT,
	sex VARCHAR(10),
	phone_number VARCHAR(20),
	remarks VARCHAR(1000),
	pending_payments FLOAT 
);
CREATE TABLE prescriptions(
	prescription_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    appointmentDetails VARCHAR(50) NOT NULL,
    remarks VARCHAR(1000)
);
CREATE TABLE appointmentlist(
	appointment_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    patientName VARCHAR(50) NOT NULL,
    detailsOfDiscomfort VARCHAR(50),
    consultingTime VARCHAR(20),
    amountToBePaid FLOAT,
    remarks VARCHAR(1000),
    prescription_id INT,
    appointmentStatus VARCHAR(15),
    foreign key(prescription_id) references prescriptions(prescription_id)
);
CREATE TABLE appointment_patient(
	appointment_id INT NOT NULL,
    patient_id INT NOT NULL,
    foreign key (appointment_id) references appointmentlist(appointment_id),
    foreign key (patient_id) references patient_list(patient_id)
);
CREATE TABLE items(
	item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(50),
    item_timings VARCHAR(100)
);
CREATE TABLE prescription_items(
	prescription_id INT NOT NULL,
    item_id INT NOT NULL,
    foreign key(prescription_id) references prescriptions(prescription_id),
    foreign key(item_id) references items(item_id)
);