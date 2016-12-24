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
        System.out.println("findAll");
        List<Receipt> receipts = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            receipts.add(new Receipt());
        }
        return receipts;
    }
 
    public void createNew(String body) {
        System.out.println("createNew");
        Receipt receipt = new Gson().fromJson(body, Receipt.class);
        DBObject doc = createDBObject(receipt);
        collection.insert(doc);
    }

    public Receipt find(String id) {
        System.out.println("find" + id);
        DBObject query = new BasicDBObject();
        query.put("_id", id);
        return new Receipt(collection.findOne(query));
    }

    private DBObject createDBObject(Receipt receipt) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("name", receipt.getPatientName());
        docBuilder.append("age", receipt.getAge());
        return docBuilder.get();
    }

}