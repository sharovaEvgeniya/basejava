package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File(".gitignore");
        System.out.println(file.getCanonicalPath());

        File dir = new File("./src");
        recurse(dir, "");
    }

    public static void recurse(File file, String whitespace) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File directory : files) {
                if (directory.isFile()) {
                    System.out.println(whitespace + "File : " + directory.getName());
                } else if (directory.isDirectory()) {
                    System.out.println(whitespace + "Directory : " + directory.getName());
                    recurse(directory, whitespace + " ");
                }
            }
        }
    }
}
