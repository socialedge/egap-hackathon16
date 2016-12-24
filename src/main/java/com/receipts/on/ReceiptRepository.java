package com.receipts.on;

import com.mongodb.*;
import com.receipts.on.model.AssignationType;
import com.receipts.on.model.DispenseType;
import com.receipts.on.model.Medication;
import com.receipts.on.model.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptRepository {

    private final DBCollection collection;
 
    public ReceiptRepository(DB db) {
        this.collection = db.getCollection("receipts");
    }

    public boolean create(Prescription prescription) {
        if (exists(prescription))
            return false;

        collection.insert(serialize(prescription));
        return true;
    }
 
    public List<Prescription> findAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            prescriptions.add(deserializePrescription(dbObject));
        }
        return prescriptions;
    }

    public Optional<Prescription> find(Long id) {
        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(String.valueOf(id)));
        DBObject dbObject = collection.findOne(query);

        if (dbObject == null)
            return Optional.empty();

        return Optional.of(deserializePrescription(dbObject));
    }

    public boolean update(Prescription prescription) {
        if (!exists(prescription))
            return false;

        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(String.valueOf(prescription.prescriptionId())));
        collection.update(query, serialize(prescription));
        return true;
    }

    public boolean delete(Long id) {
        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(String.valueOf(id)));
        DBObject dbObject = collection.findOne(query);

        if (dbObject == null)
            return false;

        collection.remove(dbObject);
        return true;
    }

    public boolean delete(Prescription prescription) {
        return delete(prescription.prescriptionId());
    }

    public boolean exists(Prescription prescription) {
        return find(prescription.prescriptionId()).isPresent();
    }

    private DBObject serialize(Prescription prescription) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("prescriptionId", prescription.prescriptionId());
        docBuilder.append("patientName", prescription.patientName());
        docBuilder.append("patientAddress", prescription.patientAddress());
        docBuilder.append("doctorName", prescription.doctorName());
        docBuilder.append("date", LocalDate.now().toString());
        docBuilder.append("dispenseType", prescription.dispenseType().toString());
        docBuilder.append("assignationType", prescription.assignationType().toString());

        List<Object> medicationList = new BasicDBList();
        prescription.medications().forEach(med -> medicationList.add(serializeMedication(med)));
        docBuilder.append("medications", medicationList);

        return docBuilder.get();
    }

    private Prescription deserializePrescription(DBObject prescriptionObject) {
        Prescription.PrescriptionBuilder prescrBuilder = Prescription.builder();

        prescrBuilder.patientName((String) prescriptionObject.get("patientName"))
                     .patientAddress((String) prescriptionObject.get("patientAddress"))
                     .doctorName((String) prescriptionObject.get("doctorName"))
                     .date(LocalDate.parse((String) prescriptionObject.get("date")))
                     .dispenseType(DispenseType.valueOf((String) prescriptionObject.get("dispenseType")))
                     .assignationType(AssignationType.valueOf((String) prescriptionObject.get("assignationType")))
                     .medications(deserializeMedications((BasicDBList) prescriptionObject.get("medications")));

        return prescrBuilder.build();
    }

    private DBObject serializeMedication(Medication medication) {
        BasicDBObjectBuilder medBuilder = BasicDBObjectBuilder.start();

        medBuilder.append("name", medication.name());
        medBuilder.append("count", medication.count());
        medBuilder.append("description", medication.description());

        return medBuilder.get();
    }

    private List<Medication> deserializeMedications(BasicDBList medicationDBList) {
        return medicationDBList.stream()
                .map(o -> (BasicDBObject) o)
                .map(this::deserializeMedication)
                .collect(Collectors.toList());
    }

    private Medication deserializeMedication(BasicDBObject medicationDBObject) {
        return Medication.of(medicationDBObject.getString("name"),
                             medicationDBObject.getInt("count"),
                             medicationDBObject.getString("description"));
    }

}