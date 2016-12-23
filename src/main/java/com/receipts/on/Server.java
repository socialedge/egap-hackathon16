package com.receipts.on;

import com.google.gson.Gson;
import com.receipts.on.model.Receipt;

import static spark.Spark.*;

public class Server {
    public static void main(String[] args) {
        get("/receipt", (req, res) -> generateReceipt());
    }

    public static String generateReceipt() {
    	Receipt receipt = new Receipt();
    	receipt.setPatientName("Jogn");
    	receipt.setAge(25);
    	return new Gson().toJson(receipt);
    }
}