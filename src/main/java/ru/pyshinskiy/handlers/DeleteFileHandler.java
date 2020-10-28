package ru.pyshinskiy.handlers;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

public class DeleteFileHandler extends FileHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(DeleteFileHandler.class);

    public DeleteFileHandler(File file) {
        super(file);
    }

    @Override
    public void run() {
        LOGGER.info("DeleteFileHandler is started, delete file: " + file.getName());
        LocalDateTime startTimeProcessing = LocalDateTime.now();
        LOGGER.info("Handler start time - [" + startTimeProcessing.toString() + "]");

        try {
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        if(!file.exists()) {
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcessing);
            LOGGER.info("Handler total work time - [" + duration + "]");
        } else {
            LOGGER.warn("Not able to delete file: " + file.getName());
        }
    }
}