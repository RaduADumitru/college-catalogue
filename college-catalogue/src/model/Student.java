package model;

import enums.AuditOption;
import interfaces.Auditable;
import enums.Year;
import service.Auditer;

import java.util.*;

public class Student extends Person implements Auditable,Comparable<Student> {
    LinkedHashSet<Specialization> specializations = new LinkedHashSet<>();
    TreeMap<Attendance, Grade> grades = new TreeMap<>();
    StudentGroup group;
    StudentSeries series;

    public Student(String first_name, String surname, Date date_of_birth, Long CNP, StudentGroup group) {
        super(first_name, surname, date_of_birth, CNP);
        this.specializations.add(group.getSpecialization());
        this.group = group;
        this.series = group.getSeries();
        audit(AuditOption.STUDENT_CONSTRUCTOR);
    }

    public StudentGroup getGroup() {
        audit(AuditOption.STUDENT_GET_GROUP);
        return group;
    }

    public void addGrade(Grade grade) {
        grades.put(new Attendance(grade.getStudent(), grade.getCourse()), grade);
        audit(AuditOption.STUDENT_ADD_GRADE);
    }

    @Override
    public String toString() {
        StringBuilder specialization_codes = new StringBuilder();
        specialization_codes.append("{");
        for(Specialization s : specializations) specialization_codes.append(s.getCode()).append(" ");
        specialization_codes.setLength(specialization_codes.length() - 1);
        specialization_codes.append("}");
        StringBuilder grades_list = new StringBuilder();
        for(Grade g : grades.values()) {
            grades_list.append("\t\t").append(g.getCourse().getCode())
                    .append(" ").append(g.getCourse().getSubject().getName()).append(" ")
                    .append(g.getScore()).append("\n");
        }
        audit(AuditOption.STUDENT_TOSTRING);
        return "\nStudent:\n" +
                "\tfirst_name='" + first_name + '\'' +
                "\n\tsurname='" + surname + '\'' +
                "\n\tdate_of_birth=" + date_of_birth +
                "\n\tCNP=" + CNP +
                "\n\tspecializations=" + specialization_codes +
                "\n\tgrades=\n" + grades_list +
                "\tgroup=" + group.getId() +
                "\n\tseries=" + series.getId() + "\n";
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.STUDENT_CONSTRUCTOR,
                        AuditOption.STUDENT_ADD_GRADE,
                        AuditOption.STUDENT_CALCULATEAVERAGE_GLOBAL,
                        AuditOption.STUDENT_COMPARETO,
                        AuditOption.STUDENT_TOSTRING,
                        AuditOption.STUDENT_CALCULATEAVERAGE_YEAR,
                        AuditOption.STUDENT_GET_GROUP,
                        AuditOption.STUDENT_ADDSPECIALIZATION,
                        AuditOption.PERSON_GET_CNP,
                        AuditOption.PERSON_GET_DATEOFBIRTH,
                        AuditOption.PERSON_GET_FIRSTNAME,
                        AuditOption.PERSON_GET_FULL_NAME,
                        AuditOption.PERSON_GET_SURNAME
                )
        );
        if(auditOptions.contains(option)) {
            Auditer.log(getClass().getSimpleName() + ":" + option.getAction());
        }
        else{
            Auditer.log(getClass().getSimpleName() + "Undefined action(" + option.name() + ")");
        }
    }

    @Override
    public int compareTo(Student o) {
        int groupComp = group.compareTo(o.getGroup());
        if(groupComp != 0) {
            audit(AuditOption.STUDENT_COMPARETO);
            return groupComp;
        }
        else {
            int surnameComp = surname.compareTo(o.getSurname());
            if (surnameComp!=0) return surnameComp;
            else {
                audit(AuditOption.STUDENT_COMPARETO);
                return first_name.compareTo(o.getFirst_name());
            }
        }

    }
    public float calculateAverage(Year year) {
        int totalCredits = 0;
        float totalScore = 0;
        int courseCredits;
        // Medie ponderata in functie de numarul de credite, daca materia participa la nota finala
        for(Grade grade: grades.values()) {
            if(year == grade.getCourse().getCollegeYear()) {
                if(grade.getCourse().getSubject().getParticipatesInTotalGrade()) {
                    courseCredits = grade.getCourse().getSubject().getCredits();
                    totalCredits += courseCredits;
                    totalScore += grade.getScore() * courseCredits;
                }
            }
        }
        audit(AuditOption.STUDENT_CALCULATEAVERAGE_YEAR);
        if(totalCredits == 0) {
            //            System.out.println(this.getFullName() + " has no grades in year " + year.name());
            return -1;
        }
        else {
            return Math.round(totalScore / (float)totalCredits * 100) / (float)100;
        }
    }
    public float calculateAverage() {
        int totalCredits = 0;
        float totalScore = 0;
        int courseCredits;
        // Medie ponderata in functie de numarul de credite, daca materia participa la nota finala
        for(Grade grade: grades.values()) {
                if(grade.getCourse().getSubject().getParticipatesInTotalGrade()) {
                    courseCredits = grade.getCourse().getSubject().getCredits();
                    totalCredits += courseCredits;
                    totalScore += grade.getScore() * courseCredits;
                }
        }
        audit(AuditOption.STUDENT_CALCULATEAVERAGE_GLOBAL);
        if(totalCredits == 0) {
//            System.out.println(this.getFullName() + " has no grades!");
            return -1;
        }
        else {
            return Math.round(totalScore / (float)totalCredits * 100) / (float)100; //rotunjeste la 2 zecimale
        }
    }
    public void addSpecialization(Specialization specialization) {
        audit(AuditOption.STUDENT_ADDSPECIALIZATION);
        specializations.add(specialization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
