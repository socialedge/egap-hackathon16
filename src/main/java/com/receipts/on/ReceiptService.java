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

        docBuilder.append("prescriptionId", prescription.prescriptionId());
        docBuilder.append("patientName", prescription.patientName());
        docBuilder.append("patientAddress", prescription.patientAddress());
        docBuilder.append("doctorName", prescription.doctorName());
        docBuilder.append("date", LocalDate.now().toString());
        docBuilder.append("dispenseType", prescription.dispenseType().toString());
        docBuilder.append("assignationType", prescription.assignationType().toString());
        //TODO append prescription medications
        return docBuilder.get();
    }

}