package ru.pyshinskiy;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.pyshinskiy.bootstrap.Bootstrap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DirectoryListenerTest {

    private static final File directory = new File("src/test/resources/testDirectory");

    private static Bootstrap bootstrap;

    @BeforeClass
    public static void startListener() {
        bootstrap = new Bootstrap(directory.getAbsolutePath());
        bootstrap.processDirectory();
    }

    @Test
    public void removeWrongFileTest() throws IOException, InterruptedException {
        File txtFile = new File("src/test/resources/testFiles/testTxtFile.txt");
        File copiedFile = copyPasteFile(txtFile);
        Assert.assertFalse(copiedFile.exists());
    }

    @AfterClass
    public static void stopListener() throws InterruptedException {
        bootstrap.getListenerThread().interrupt();
        Thread.sleep(1000);
    }

    private File copyPasteFile(File file) throws IOException, InterruptedException {
        Thread.sleep(500);
        Files.copy(file.toPath(),
                new File(directory.toPath().toString().concat("/").concat(file.getName())).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        Thread.sleep(10000);
        File copiedFile = new File("src/test/resources/testDirectory/testTxtFile.txt");
        Assert.assertNotNull(copiedFile);
        return copiedFile;
    }

}