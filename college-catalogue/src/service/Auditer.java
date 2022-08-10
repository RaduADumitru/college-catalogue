package service;

import java.io.IOException;
import java.sql.Timestamp;

public class Auditer {
    public static void log(String action) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CSV_Writer writer = CSV_Writer.getInstance();
        try {
            writer.writeLine(action + "," + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
