package ru.pyshinskiy.listener;

import org.apache.log4j.Logger;
import ru.pyshinskiy.handlers.JsonFileHandler;
import ru.pyshinskiy.handlers.RemoverHandler;
import ru.pyshinskiy.handlers.XmlFileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;


public class DirectoryListener implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(DirectoryListener.class);
    private final File folder;

    public DirectoryListener(File pointFile) {
        this.folder = pointFile;
    }


    public void run() {
        WatchService watchService = createAndRegisterWatchService();

        boolean poll = true;
        while (poll) {
            WatchKey key = null;
            try {
                key = Objects.requireNonNull(watchService).take();
            } catch (InterruptedException e) {
                LOGGER.error("Occurred exception : " + e.getMessage() + " Cause : " + e.getCause());
            }
            Path filePath = (Path) Objects.requireNonNull(key).watchable();
            for (WatchEvent<?> event : key.pollEvents()) {
                LOGGER.info("Added new file : " + event.context() + " - [time : "+LocalDateTime.now()+"]");
                Thread handler = selectHandler(filePath.resolve((Path) event.context()).toFile());
                handler.start();
            }
            poll = key.reset();
        }
    }

    private WatchService createAndRegisterWatchService() {
        WatchService watchService = null;
        Path path;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path = Paths.get(folder.getPath());
            path.register(watchService, ENTRY_CREATE);
        } catch (IOException e) {
            LOGGER.error("Occurred exception" + e.getMessage() + " Cause : " + e.getCause());
        }
        return Objects.requireNonNull(watchService);
    }

    private String getFileExtension(String file) {
        Pattern p = Pattern.compile("\\.\\w+$");
        Matcher m = p.matcher(file);
        m.find();
        return m.group();
    }

    private Thread selectHandler(File file) {
        if(".json".equals(getFileExtension(file.toString()))) {
            return new Thread(new JsonFileHandler(file));

        } else if(".xml".equals(getFileExtension(file.toString()))) {
            return new Thread(new XmlFileHandler(file));

        } else {
            return new Thread(new RemoverHandler(file));
        }
    }
}
