package org.example;

import org.example.explorers.DirExplorer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File projectDir = new File("T:\\AllHK\\HK8\\KTPM\\BaiTap\\Exercise2");
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
        }).explore(projectDir);
//        System.out.println("llll");
    }
}