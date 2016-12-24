package com.receipts.on;

import com.google.gson.Gson;
import com.mongodb.*;
import com.receipts.on.model.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
public class ReceiptService {

    private final DBCollection collection;
 
    public ReceiptService(DB db) {
        this.collection = db.getCollection("receipts");
    }
 
    public List<Prescription> findAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            prescriptions.add(new Prescription(dbObject));
        }
        return prescriptions;
    }
 
    public void createNew(String body) {
        Prescription prescription = new Gson().fromJson(body, Prescription.class);
        DBObject doc = createDBObject(prescription);
        collection.insert(doc);
    }

    public Prescription find(String id) {
        DBObject query = new BasicDBObject();
        query.put("prescriptionId", new ObjectId(id));
        DBObject dbObject = collection.findOne(query);
        return new Prescription(dbObject);
    }

    private DBObject createDBObject(Prescription prescription) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("prescriptionId", prescription.getPrescriptionId());
        docBuilder.append("patientName", prescription.getPatientName());
        docBuilder.append("patientAddress", prescription.getPatientAddress());
        docBuilder.append("doctorId", prescription.getDoctorId());
        docBuilder.append("date", LocalDate.now().toString());
        docBuilder.append("dispenseType", prescription.getDispenseType().toString());
        docBuilder.append("assignationType", prescription.getAssignationType().toString());
        return docBuilder.get();
    }

}