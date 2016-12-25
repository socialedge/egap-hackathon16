package com.receipts.on.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

//@Builder
//@ToString
//@Getter @Setter
//@EqualsAndHashCode
//@AllArgsConstructor
//@Accessors(fluent = true)
public class Prescription {
    private final String prescriptionId = UUID.randomUUID().toString();
    private final String patientName;
    private final String patientAddress;
    private final String doctorName;
    private final LocalDate date;
    private final DispenseType dispenseType;
    private final AssignationType assignationType;
    private List<Medication> medications;

    public Prescription(AssignationType assignationType, String patientName, String patientAddress, String doctorName, LocalDate date, DispenseType dispenseType) {
        this.assignationType = assignationType;
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.doctorName = doctorName;
        this.date = date;
        this.dispenseType = dispenseType;
    }



    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
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

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}
