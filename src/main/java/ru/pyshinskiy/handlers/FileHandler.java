package ru.pyshinskiy.handlers;

import java.io.File;

public abstract class  FileHandler {
    protected File file;

    protected FileHandler(File file) {
        this.file = file;
    }

}
