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

    public Optional<Prescription> find(String id) {
        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(id));
        DBObject dbObject = collection.findOne(query);

        if (dbObject == null)
            return Optional.empty();

        return Optional.of(deserializePrescription(dbObject));
    }

    public boolean update(Prescription prescription) {
        if (!exists(prescription))
            return false;

        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(String.valueOf(prescription.getPrescriptionId())));
        collection.update(query, serialize(prescription));
        return true;
    }

    public boolean delete(String id) {
        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(id));
        DBObject dbObject = collection.findOne(query);

        if (dbObject == null)
            return false;

        collection.remove(dbObject);
        return true;
    }

    public boolean delete(Prescription prescription) {
        return delete(prescription.getPrescriptionId());
    }

    public boolean exists(Prescription prescription) {
        return find(prescription.getPrescriptionId()).isPresent();
    }

    private DBObject serialize(Prescription prescription) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("prescriptionId", prescription.getPrescriptionId());
        docBuilder.append("patientName", prescription.getPatientName());
        docBuilder.append("patientAddress", prescription.getPatientAddress());
        docBuilder.append("doctorName", prescription.getDoctorName());
        docBuilder.append("date", LocalDate.now().toString());
        docBuilder.append("dispenseType", prescription.getDispenseType().toString());
        docBuilder.append("assignationType", prescription.getAssignationType().toString());

        List<Object> medicationList = new BasicDBList();
        prescription.getMedications().forEach(med -> medicationList.add(serializeMedication(med)));
        docBuilder.append("medications", medicationList);

        return docBuilder.get();
    }

    private Prescription deserializePrescription(DBObject prescriptionObject) {
        return new Prescription(
                AssignationType.valueOf((String) prescriptionObject.get("assignationType")),
                (String) prescriptionObject.get("patientName"),
                (String) prescriptionObject.get("patientAddress"),
                (String) prescriptionObject.get("doctorName"),
                LocalDate.parse((String) prescriptionObject.get("date")),
                DispenseType.valueOf((String) prescriptionObject.get("dispenseType"))
        );
    }

    private DBObject serializeMedication(Medication medication) {
        BasicDBObjectBuilder medBuilder = BasicDBObjectBuilder.start();

        medBuilder.append("name", medication.getName());
        medBuilder.append("count", medication.getCount());
        medBuilder.append("description", medication.getDescription());

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