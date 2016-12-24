package com.receipts.on.util;

import net.glxn.qrgen.javase.QRCode;

public class QrCodes {

    private static final int CODE_WIDTH = 256;
    private static final int CODE_HEIGHT = 256;

    private QrCodes() {
        throw new AssertionError("No QrCodes instances for you!");
    }

    public static byte[] generateCode(String str) {
        return QRCode.from(str)
                     .withSize(CODE_WIDTH, CODE_HEIGHT)
                     .stream()
                     .toByteArray();
    }
}
