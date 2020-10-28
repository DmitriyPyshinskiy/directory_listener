package ru.pyshinskiy;

import ru.pyshinskiy.bootstrap.Bootstrap;

public class Main {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap(args[0]);
        bootstrap.processDirectory();
    }
}
