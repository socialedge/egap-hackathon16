package com.receipts.on;

import com.receipts.on.util.QrCodes;

import static spark.Spark.get;
import static spark.Spark.post;

public class QrCodeResource {

    private static final String API_CONTEXT = "/api/v1";

    {
        System.out.println("sdada");
        get(API_CONTEXT + "/codes", ((request, response) -> {
            String qrCodeContent = request.queryParams("data");
            byte[] qrCode = QrCodes.generateCode(qrCodeContent);

            response.raw().setContentType("image/jpeg");
            response.raw().getOutputStream().write(qrCode);

            return null;
        }));
    }
}