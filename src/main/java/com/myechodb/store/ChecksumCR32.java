package com.myechodb.store;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ChecksumCR32 {

    public static long calculateChecksum(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        Checksum cr32 = new CRC32();
        cr32.update(bytes, 0, bytes.length);
        return cr32.getValue();
    }
}
