package com.receipts.on;

import static spark.Spark.get;
import static spark.Spark.post;

public class ReceiptResource {

    private static final String API_CONTEXT = "/api/v1";
 
    private final ReceiptService receiptService;
 
    public ReceiptResource(ReceiptService receiptService) {
        this.receiptService = receiptService;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
        post(API_CONTEXT + "/receipts", "application/json", (request, response) -> {
            receiptService.createNew(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());
 
        get(API_CONTEXT + "/receipts/:id", "application/json", (request, response)
 
                -> receiptService.find(request.params(":id")), new JsonTransformer());
 
        get(API_CONTEXT + "/receipts", "application/json", (request, response)
 
                -> receiptService.findAll(), new JsonTransformer());
 
    }
}