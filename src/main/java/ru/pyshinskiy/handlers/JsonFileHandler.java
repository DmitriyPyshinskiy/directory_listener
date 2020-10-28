package ru.pyshinskiy.handlers;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.Duration;
import java.time.LocalDateTime;

public class JsonFileHandler extends FileHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(JsonFileHandler.class);

    public JsonFileHandler(File file) {
        super(file);
    }

    @Override
    public void run() {
        LOGGER.info("JsonFileHandler is started, handle file : " + file.getName());
        LocalDateTime startTimeProcess = LocalDateTime.now();
        LOGGER.info("Handler start time - [" + startTimeProcess.toString() + "]");

        int countLine = 0;
        try {
            FileReader reader = new FileReader(file);
            LineNumberReader lineNumberReader = new LineNumberReader(reader);
            while(lineNumberReader.readLine() != null) {
                countLine++;
            }
            LOGGER.info("Count rows in file = " + countLine);
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcess);
            LOGGER.info("Handler total work time - [" + duration + "]");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}