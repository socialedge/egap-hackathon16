package com.receipts.on;

import com.google.gson.Gson;
import com.receipts.on.model.Prescription;
import com.receipts.on.util.Notifications;
import com.receipts.on.util.QrCodes;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static spark.Spark.get;
import static spark.Spark.post;

public class ReceiptResource {

    private static final String API_CONTEXT = "/api/v1";

    private static final Gson GSON = new Gson();
 
    private final ReceiptRepository receiptRepository;
 
    public ReceiptResource(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
        post(API_CONTEXT + "/prescriptions", "application/json", (request, response) -> {
            Prescription prescription = GSON.fromJson(request.body(), Prescription.class);

            if (receiptRepository.create(prescription))
                response.status(HttpServletResponse.SC_CREATED);
            else
                response.status(HttpServletResponse.SC_CONFLICT);

            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/prescriptions/:id", "application/json", (request, response) -> {
            Optional<Prescription> prescriptionOpt = receiptRepository.find(request.params(":id"));

            if (!prescriptionOpt.isPresent()) {
                response.status(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            return prescriptionOpt.get();
        }, new JsonTransformer());

        get(API_CONTEXT + "/prescriptions/:id/qrcodes/identifier", (request, response) -> {
                    Optional<Prescription> prescriptionOpt = receiptRepository.find(request.params(":id"));

                    if (!prescriptionOpt.isPresent()) {
                        response.status(HttpServletResponse.SC_NOT_FOUND);
                        return null;
                    }

                    byte[] qrCode = QrCodes.generateCode(prescriptionOpt.get().prescriptionId());

                    response.raw().setContentType("image/jpeg");
                    response.raw().getOutputStream().write(qrCode);

                    return null;
        });

        // TODO: add /prescriptions/:id/qrcodes/url endpoint
 
        get(API_CONTEXT + "/prescriptions", "application/json",
                (request, response) -> receiptRepository.findAll(), new JsonTransformer());

        post(API_CONTEXT + "/prescriptions/:id/notifications/emails", "application/json", (request, response) -> {
            Optional<Prescription> prescriptionOpt = receiptRepository.find(request.params(":id"));

            if (!prescriptionOpt.isPresent()) {
                response.status(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            Notifications.sendEmail(prescriptionOpt.get());

            response.status(HttpServletResponse.SC_OK);

            return response;
        }, new JsonTransformer());
    }
}