package com.interactive.demointro.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class CommonUtil {

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024 * 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
            System.gc();
        }
        return byteBuffer.toByteArray();
    }
}
