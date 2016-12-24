package com.receipts.on.model;

import com.mongodb.DBObject;

import java.time.LocalDate;

public class Prescription {
    private final long prescriptionId = System.nanoTime();
    private final String patientName;
    private final String patientAddress;
    private final long doctorId;
    private final LocalDate date;
    private final DispenseType dispenseType;
    private final AssignationType assignationType;

    public Prescription(DBObject prescriptionObject) {
        patientName = (String) prescriptionObject.get("patientName");
        patientAddress = (String) prescriptionObject.get("patientAddress");
        doctorId = (long) prescriptionObject.get("doctorId");
        date = LocalDate.parse((String) prescriptionObject.get("date"));
        dispenseType = DispenseType.valueOf((String) prescriptionObject.get("dispenseType"));
        assignationType = AssignationType.valueOf((String) prescriptionObject.get("assignationType"));
    }

    public long getPrescriptionId() {
        return prescriptionId;
    }

    public long getDoctorId() {
        return doctorId;
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
}
