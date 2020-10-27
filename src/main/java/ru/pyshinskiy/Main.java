package ru.pyshinskiy;

import ru.pyshinskiy.listener.DirectoryListener;
import util.Util;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = Util.inputFile();
        Thread listener = new Thread(new DirectoryListener(file));
        listener.start();
    }
}
