package ru.pyshinskiy.listener;

import org.apache.log4j.Logger;
import ru.pyshinskiy.handlers.factory.HandlerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class DirectoryListener implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(DirectoryListener.class);
    private final File folder;
    private final HandlerFactory handlerFactory;

    public DirectoryListener(File pointFile, HandlerFactory handlerFactory) {
        this.folder = pointFile;
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void run() {
        WatchService watchService = createAndRegisterWatchService();

        boolean poll = true;
        while (poll) {
            WatchKey key = null;
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if(key != null) {
                Path filePath = (Path) key.watchable();
                for (WatchEvent<?> event : key.pollEvents()) {
                    LOGGER.info("A new file appeared: " + event.context() + " - [time : "+LocalDateTime.now()+"]");
                    File file = filePath.resolve((Path) event.context()).toFile();
                    Runnable handlerTask = handlerFactory.getHandlerByFileExtension(file);
                    Thread handlerThread = new Thread(handlerTask);
                    handlerThread.setDaemon(true);
                    handlerThread.start();
                }
                poll = key.reset();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
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
            LOGGER.error(e.getMessage(), e);
        }
        return Objects.requireNonNull(watchService);
    }
}