package com.myechodb.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

class LogManager {

    public static TreeMap<String, String> memTable = new TreeMap<>();

    public static void readLog() throws IOException {
        List<String> lines = Files.readAllLines(Path.of("data/log.txt"));

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
                String kv = parts[0] + ":" + parts[1];
                long localChecksum = ChecksumCR32.calculateChecksum(kv);

                if (String.valueOf(localChecksum).equals(parts[2])) {
                    memTable.put(parts[0], parts[1]);
                } else {
                    return;
                }
            }
        }
    }

    // simple cmd prompt
    public static void main(String[] args) {
        try {
            readLog();
            m
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine();

            if (input.isEmpty()) continue;
            String[] parts = input.split(" ");

            String cmd = parts[0].toLowerCase();
            if (
                !cmd.equals("get") && !cmd.equals("set") && !cmd.equals("exit")
            ) {
                System.out.println("Unknown command");
                continue;
            }

            if (cmd.equals("get") && parts.length != 2) {
                System.out.println("Invalid GET command");
                continue;
            }

            if (cmd.equals("set") && parts.length != 3) {
                System.out.println("Invalid SET command");
                continue;
            }

            if (parts[0].toLowerCase().equals("get")) {
                String key = parts[1];
                String value = memTable.get(key);
                System.out.println(value);
                continue;
            }

            if (parts[0].toLowerCase().equals("exit")) {
                System.out.println("Exiting...");
                System.exit(0);
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
                memTable.put(key, value);
                System.out.println("Data written to log file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
