package com.receipts.on.util;

import com.receipts.on.model.Medication;
import com.receipts.on.model.Prescription;

public class HtmlUtils {

    private static final String thElement = "<th style=\"padding:10px; text-align:center;\">";
    private static final String tdElement = "<td style=\"padding:10px; text-align:center;\">";

    public static String prescriptionTable(Prescription prescription, String url, String imgCid) {
        String rows = prescription.getMedications().stream().map(HtmlUtils::generateRow).reduce("", (result, s) -> result += s);

        return "<p><a href = \""+url+"\">View on web site</a></p>" + "<table border=1 style=\"border: 1px solid #1464dc; border-collapse:collapse\">\n" +
                "    <thead>\n" +
                "        <tr>\n" +
                thElement + "            Назва</th>\n" +
                thElement + "            Кількість</th>\n" +
                thElement + "            Опис</th>\n" +
                "        </tr>\n" +
                "    </thead>\n" +
                "    <tbody>" + rows + "</table>\n" +
                "  <img src=\"cid:" + imgCid + "\" />";
    }

    private static String generateRow(Medication medication) {
        String result = "<tr>";
        result += tdElement + medication.getName() + "</td>";
        result += tdElement + medication.getCount() + "</td>";
        result += tdElement + medication.getDescription() + "</td>";
        result += "</tr>";
        return result;
    }
}
