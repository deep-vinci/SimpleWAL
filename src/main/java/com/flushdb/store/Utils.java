package com.flushdb.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Utils {
    static void writeToLog(Path path, String data) {
        Path fileName = Paths.get(path.toUri());
        try {
            Files.writeString(
                    fileName,
                    data,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.SYNC
            );
            System.out.println("Data written to log file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
