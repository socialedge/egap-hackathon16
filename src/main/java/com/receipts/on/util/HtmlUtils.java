package com.receipts.on.util;

import com.receipts.on.model.Medication;
import com.receipts.on.model.Prescription;

public class HtmlUtils {

    public static String prescriptionTable(Prescription prescription) {
        String rows = prescription.medications().stream().map(HtmlUtils::generateRow).reduce("", (result, s) -> result += s);
        return "<table>" + rows + "</table>";
    }

    private static String generateRow(Medication medication) {
        String result = "<tr>";
        result += "<td>" + medication.name() + "</td>";
        result += "<td>" + medication.count() + "</td>";
        result += "<td>" + medication.description() + "</td>";
        result += "</tr>";
        return result;
    }
}
