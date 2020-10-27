package util;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Util {
    private static final Logger log = Logger.getLogger(Util.class);

    public static File inputFile() {
        File file = null;
        boolean isFile = true;
        System.out.println("Hello! Please enter file name to work directory listener:");
        while (isFile) {
            try (Scanner scanner = new Scanner(System.in)) {
                String fileName = scanner.nextLine();
                file = new File(fileName);
                if (file.exists() && !file.isFile()) {
                    log.info("correct input file.");
                    System.out.println("[OK]");
                    isFile = false;
                } else {
                    System.out.println("Invalid data. Try input again.");
                    log.warn("Wrong input file");
                }
            } catch (Exception e) {
                log.error("Occurred exception" + e.getCause() + " Cause : " + e.getCause());
            }
        }
        return Objects.requireNonNull(file);
    }
}
