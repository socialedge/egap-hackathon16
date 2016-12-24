package com.receipts.on.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@ToString
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(fluent = true)
public class Prescription {
    private final String prescriptionId = UUID.randomUUID().toString();
    private final String patientName;
    private final String patientAddress;
    private final String doctorName;
    private final LocalDate date;
    private final DispenseType dispenseType;
    private final AssignationType assignationType;
    private List<Medication> medications;
}
