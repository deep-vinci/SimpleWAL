package com.myechodb.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class LogManager {

    public static void main(String[] args) {
        String data = "val: 10\n";
        Path logFile = Paths.get("data", "log.txt");
        try {
            Files.write(
                logFile,
                data.getBytes(StandardCharsets.UTF_8),
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
