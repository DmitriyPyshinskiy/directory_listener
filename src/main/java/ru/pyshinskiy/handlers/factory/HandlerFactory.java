package ru.pyshinskiy.handlers.factory;

import ru.pyshinskiy.handlers.DeleteFileHandler;
import ru.pyshinskiy.handlers.JsonFileHandler;
import ru.pyshinskiy.handlers.XmlFileHandler;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerFactory {

    public static final String JSON_EXT = ".json";
    public static final String XML_EXT = ".xml";

    public Runnable getHandlerByFileExtension(File file) {

        switch(getFileExtension(file.toString())) {
            case JSON_EXT: return new JsonFileHandler(file);
            case XML_EXT: return new XmlFileHandler(file);
            default: return new DeleteFileHandler(file);
        }
    }

    private String getFileExtension(String fileName) {
        Pattern p = Pattern.compile("\\.\\w+$");
        Matcher m = p.matcher(fileName);
        return m.find() ? m.group() : "unknown";
    }
}

