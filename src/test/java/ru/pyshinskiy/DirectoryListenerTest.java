package ru.pyshinskiy;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.pyshinskiy.listener.DirectoryListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryListenerTest {
    private static final Logger LOGGER = Logger.getLogger(DirectoryListenerTest.class);
    private static final File directory = new File("src/test/resources/testDirectory");
    private static final DirectoryListener listener = new DirectoryListener(directory);
    private static final Thread listenerThread =
            new Thread(new DirectoryListener(directory));

    @BeforeClass
    public static void startListener() {
        listenerThread.start();
        LOGGER.info("DirectoryListener started...");
    }

    @Test
    public void removeWrongFileTest() throws InterruptedException {
        File txtFile = new File("src/test/resources/testFiles/testTxtFile.txt");
        copyPasteFile(txtFile);
        LOGGER.info("file testFile.txt was copy and move into testDirectory.");
        boolean fileExists = txtFile.exists();
        Assert.assertFalse(fileExists);
    }

    @AfterClass
    public static void stopListener() {
        listenerThread.interrupt();
        LOGGER.info("DirectoryListener stopped.");
    }

    private void copyPasteFile(File file) {

        try {
            Files.copy(file.toPath(), new File(directory.toPath().toString().concat("/").concat(file.getName())).toPath()
                    ,StandardCopyOption.REPLACE_EXISTING);
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        File copiedFile = new File("src/test/resources/testDirectory/testTxtFile.txt");
        Assert.assertNotNull(copiedFile);
    }

}
