package com.myechodb.store;

import java.io.File;
import java.util.TreeMap;

public class MemTableLimit {

    public static boolean limitReached(TreeMap<String, String> memTable) {
        if (memTable.size() > 10) {
            File folder = new File("./data/db");

            if (folder.exists() && folder.listFiles() != null) {
                File[] listOfFiles = folder.listFiles();
                for (File file : listOfFiles) {
                    System.out.println(file.getName());
                }
            }
            return true;
        }
        return false;
    }
}
