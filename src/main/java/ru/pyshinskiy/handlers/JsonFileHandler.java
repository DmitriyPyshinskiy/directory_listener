package ru.pyshinskiy.handlers;


import org.apache.log4j.Logger;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class JsonFileHandler implements FileHandler {
    private static final Logger LOGGER = Logger.getLogger(JsonFileHandler.class);
    private final File file;

    public JsonFileHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        LOGGER.info("JsonFileHandler running, handle file : " + file.getName());
        LocalDateTime startTimeProcess = LocalDateTime.now();
        LOGGER.info("Start time process - [" + startTimeProcess.toString() + "]");

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
            LOGGER.info("Total time process - [" + duration + "]");
        } catch (IOException e) {
            LOGGER.error("Occurred exception : " +e.getMessage() + " Cause : " + e.getCause());
        }
    }
}
