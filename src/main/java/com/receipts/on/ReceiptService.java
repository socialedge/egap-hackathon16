package com.receipts.on;

import com.google.gson.Gson;
import com.mongodb.*;
import com.receipts.on.model.Receipt;
 
import java.util.ArrayList;
import java.util.List;
 
public class ReceiptService {

    private final DBCollection collection;
 
    public ReceiptService(DB db) {
        this.collection = db.getCollection("receipts");
    }
 
    public List<Receipt> findAll() {
        List<Receipt> receipts = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            receipts.add(new Receipt(dbObject));
        }
        return receipts;
    }
 
    public void createNew(String body) {
        Receipt receipt = new Gson().fromJson(body, Receipt.class);
        DBObject doc = createDBObject(receipt);
        collection.insert(doc);
    }

    public Receipt find(String id) {
        DBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObject = collection.findOne(query);
        return new Receipt(dbObject);
    }

    private DBObject createDBObject(Receipt receipt) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("name", receipt.getPatientName());
        docBuilder.append("age", receipt.getAge());
        return docBuilder.get();
    }

}