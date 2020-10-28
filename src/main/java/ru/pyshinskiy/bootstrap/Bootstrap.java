package ru.pyshinskiy.bootstrap;

import org.apache.log4j.Logger;
import ru.pyshinskiy.handlers.factory.HandlerFactory;
import ru.pyshinskiy.listener.DirectoryListener;

import java.io.File;

public class Bootstrap {

    private static final Logger LOGGER = Logger.getLogger(Bootstrap.class);

    private final HandlerFactory handlerFactory = new HandlerFactory();

    private final String directoryName;

    private Thread listenerThread;

    public Thread getListenerThread() {
        return listenerThread;
    }

    public Bootstrap(String directoryName) {
        this.directoryName = directoryName;
    }

    public void processDirectory() {
        File directoryToWatch = validateAndGetDirectory();
        listenerThread = new Thread(new DirectoryListener(directoryToWatch, handlerFactory));
        listenerThread.start();
    }

    private File validateAndGetDirectory() {
        File directory = new File(directoryName);
        if (directory.exists() && !directory.isFile()) {
            LOGGER.info("Entered directory is valid. Start DirectoryListener...");
        } else {
            LOGGER.error("Entered directory does not exist or isn't a directory");
        }
        return directory;
    }
}