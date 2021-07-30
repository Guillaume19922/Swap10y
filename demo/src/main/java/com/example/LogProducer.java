package com.example;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogProducer{
    static Logger LOGGER = Logger.getLogger(LogProducer.class.getName());

    public static void setConfig(String path) {
        String projectPath = System.getProperty("user.dir");
        LOGGER.setLevel(Level.INFO);
        try{
            FileHandler fh = new FileHandler(projectPath + "/" + path);
            LOGGER.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Logger is not working");
        }
    }

    public static void log(Level lvl, String message){
        LOGGER.log(lvl, message);
    }
}
