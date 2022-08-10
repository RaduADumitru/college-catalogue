package service;

import enums.AuditOption;
import interfaces.Auditable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class CSV_Writer implements Auditable {
    private static final String audit_filepath = "data/audit.csv";
    private static BufferedWriter writer;
    private static final String header = "name_of_action,timestamp";
    private static CSV_Writer instance;
    private CSV_Writer(BufferedWriter writer) {
        CSV_Writer.writer = writer;
    }
    public static CSV_Writer getInstance() {
        if(instance == null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(audit_filepath, true));
                instance = new CSV_Writer(writer);
                File auditFile = new File(audit_filepath);
                if(auditFile.length() == 0) // adauga header daca nu exista (fisierul csv de audit este gol)
                {
                    writer.write(header);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
    public void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
    public static void close() throws IOException {
        writer.flush();
        writer.close();
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                List.of(
                        AuditOption.CSV_WRITER_INITIALIZE
                )
        );
        if(auditOptions.contains(option)) {
            Auditer.log(getClass().getSimpleName() + ":" + option.getAction());
        }
        else{
            Auditer.log(getClass().getSimpleName() + "Undefined action(" + option.name() + ")");
        }
    }
}
