package ru.pyshinskiy.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import org.apache.log4j.Logger;

public class RemoverHandler implements FileHandler {
    private static final Logger LOGGER = Logger.getLogger(RemoverHandler.class);
    private final File file;

    public RemoverHandler(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        LOGGER.info("RemoverHandler running, handle file : " + file.getName());
        LocalDateTime startTimeProcessing = LocalDateTime.now();
        LOGGER.info("Start time removing - [" + startTimeProcessing.toString() + "]");

        try {
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException e) {
            System.out.println("Occurred exception : " + e.getMessage() + " Cause : " + e.getCause());
        }

        if(!file.exists()) {
            LocalDateTime endTimeProcessing = LocalDateTime.now();
            Duration duration = Duration.between(endTimeProcessing, startTimeProcessing);
            LOGGER.info("Total time removing - [" + duration + "]");
        } else {
            LOGGER.warn("File not deleted");
        }
    }

}
