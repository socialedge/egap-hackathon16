package com.receipts.on.model;

import com.mongodb.DBObject;

import java.time.LocalDate;
import java.util.List;

public class Prescription {
    private final long prescriptionId = System.nanoTime();
    private final String patientName;
    private final String patientAddress;
    private final String doctorName;
    private final LocalDate date;
    private final DispenseType dispenseType;
    private final AssignationType assignationType;
    private List<Medication> medications;

    public Prescription(DBObject prescriptionObject) {
        patientName = (String) prescriptionObject.get("patientName");
        patientAddress = (String) prescriptionObject.get("patientAddress");
        doctorName = (String) prescriptionObject.get("doctorName");
        date = LocalDate.parse((String) prescriptionObject.get("date"));
        dispenseType = DispenseType.valueOf((String) prescriptionObject.get("dispenseType"));
        assignationType = AssignationType.valueOf((String) prescriptionObject.get("assignationType"));
        //TODO initialize medications from DBObject
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public DispenseType getDispenseType() {
        return dispenseType;
    }

    public AssignationType getAssignationType() {
        return assignationType;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public List<Medication> getMedications() {
        return medications;
    }
}