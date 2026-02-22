package com.myechodb.store;

import java.io.IOException;
import java.io.IOException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

class LogManager {

    static HashMap<String, String> map = new HashMap<>();

    public static void readLog() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("data/log.txt"));

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
                map.put(parts[0], parts[1]);
            }
        }
        System.out.println(map);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Hello World!");
            readLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("> ");
            String input = sc.nextLine();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ");
            if (parts[0].equals("get")) {
                String key = parts[1];
                String value = map.get(key);
                System.out.println(value);
                continue;
            }

            String key = parts[1];
            String value = parts[2];
            long checksum = ChecksumCR32.calculateChecksum(key + ":" + value);
            String finalLine = key + ":" + value + ":" + checksum + "\n";

            Path logFile = Paths.get("data", "log.txt");
            try {
                Files.write(
                    logFile,
                    finalLine.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.SYNC
                );
                map.put(key, value);
                System.out.println("Data written to log file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
