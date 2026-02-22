package com.myechodb.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class LogManager {

    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("> ");
            String rawKV = sc.nextLine();

            long checksum = ChecksumCR32.calculateChecksum(rawKV);
            String finalLine = rawKV + ":" + checksum + "\n";

            Path logFile = Paths.get("data", "log.txt");
            try {
                Files.write(
                    logFile,
                    finalLine.getBytes(StandardCharsets.UTF_8),
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
}
