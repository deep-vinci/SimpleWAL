package com.flushdb.store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

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


    static int maxFileIndexCounter(Path path) throws IOException {
        int maxFileIndex = 0;

        try (Stream<Path> files = Files.list(path)) {

            for (Path file : files.toList()) {

                String fileName = file.getFileName().toString();

                if (fileName.startsWith("sstable_") && fileName.endsWith(".dat")) {

                    String indexPart = fileName
                            .replace("sstable_", "")
                            .replace(".dat", "");

                    int currentIndex = Integer.parseInt(indexPart);
                    maxFileIndex = Math.max(maxFileIndex, currentIndex);
                }
            }
        }
        return maxFileIndex;
    }
}
