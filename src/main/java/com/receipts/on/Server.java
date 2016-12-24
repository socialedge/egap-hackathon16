package com.receipts.on;

import com.mongodb.Mongo;

import java.net.UnknownHostException;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) throws UnknownHostException {
        setPort(8080);
        staticFileLocation("/");
        Mongo mongoClient = new Mongo("localhost");
        new ReceiptResource(new ReceiptService(mongoClient.getDB("receipts")));
    	new QrCodeResource();
    }

}