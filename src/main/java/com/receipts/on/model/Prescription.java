package com.receipts.on.model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@ToString
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
@Accessors(fluent = true)
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

        BasicDBList medicationsList = (BasicDBList) prescriptionObject.get("medications");
        medications = medicationsList.stream()
                .map(o -> (BasicDBObject) o)
                .map(o -> new Medication(o.getString("name"),
                                         o.getInt("count"),
                                         o.getString("description")))
                .collect(Collectors.toList());
    }
}
