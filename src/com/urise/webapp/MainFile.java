package com.urise.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File(".gitignore");
        System.out.println(file.getCanonicalPath());

        File dir = new File("./src/com/urise/webapp");
        recurse(dir);
        printDirectoryDeeply(dir);
    }

    public static void recurse(File file) {
        if (file.isDirectory()) {
            for (File name : Objects.requireNonNull(file.listFiles())) {
                System.out.println(name.getName());
                recurse(name);
            }
        }
    }

    public static void printDirectoryDeeply(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File directory : files) {
                if (directory.isFile()) {
                    System.out.println("File : " + directory.getName());
                }
                if (directory.isDirectory()) {
                    System.out.println("Directory : " + directory.getName());
                    printDirectoryDeeply(directory);
                }
            }
        }
    }
}
