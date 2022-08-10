package service;

import enums.AuditOption;
import interfaces.Auditable;
import enums.ObjectRead;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

public class CSV_Reader implements Auditable {
    private static final String separator = ",";
    private static CSV_Reader instance;
    private CSV_Reader() {
    }
    public static CSV_Reader getInstance() {
        if(instance == null) instance = new CSV_Reader();
        return instance;
    }


    public void readObjects() throws IOException, ParseException {
        String line;
        BufferedReader reader;
        String[] aux;
        for(ObjectRead objectRead : ObjectRead.values()) {
            reader = new BufferedReader(new FileReader(objectRead.getFilepath()));
            reader.readLine(); // skip header
            while (true) {
                line = reader.readLine();
                if (line == null)
                    break;
                aux = line.split(separator);
                switch (objectRead) {
                    // Specializations: code, name, nr_years, degree
                    case SPECIALIZATIONS -> AuxService.addSpecialization(aux[0], aux[1], aux[2], aux[3]);
                    // Series: id, number, year, specialization
                    case SERIES -> AuxService.addSeries(aux[0], aux[1], aux[2], aux[3]);
                    // Groups: id, number, year, specialization, series
                    case GROUPS -> AuxService.addGroup(aux[0], aux[1], aux[2], aux[3], aux[4]);
                    // Students: first_name, surname, date_of_birth, CNP, specialization_code, group_id
                    case STUDENTS -> AuxService.addStudent(aux[0], aux[1], aux[2], aux[3], aux[4]);
                    // Professors: first_name, surname, date_of_birth, CNP
                    case PROFESSORS -> AuxService.addProfessor(aux[0], aux[1], aux[2], aux[3]);
                    // Subjects: code, name, credits, participateInTotalGrade, specialization
                    case SUBJECTS -> AuxService.addSubject(aux[0], aux[1], aux[2], aux[3]);
                    // Courses: code, name, credits, participateInTotalGrade, year, collegeYear, semester
                    case COURSES -> AuxService.addCourse(aux[0], aux[1], aux[2], aux[3], aux[4], aux[5], aux[6]);
                    // Grades: student, subject, professor
                    case GRADES -> AuxService.addGrade(aux[0], aux[1], aux[2]);
                }
            }
            reader.close();
        }
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                List.of(
                        AuditOption.CSV_READER_READOBJECTS
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
