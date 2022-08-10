package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;

import java.util.*;

public class Professor extends Person implements Auditable {
    private LinkedHashSet<Subject> subjectsTaught = new LinkedHashSet<>();
    private LinkedHashSet<Course> coursesTaught = new LinkedHashSet<>();
    public Professor(String first_name, String surname, Date date_of_birth, Long CNP) {
        super(first_name, surname, date_of_birth, CNP);
        audit(AuditOption.PROFESSOR_CONSTRUCTOR);
    }

    //    Folosim LinkedHashSet pentru a memora ordinea inserarii;
//    traversarea sa, desi mai putin eficienta in memorie, nu va crea deloc probleme,
//    deoarece lungimea va fi foarte mica in principiu (maxim circa 5-10 materii predate de un profesor)
    public void addCourseTaught(Course c) {
        coursesTaught.add(c);
        subjectsTaught.add(c.getSubject());
        c.setProfessor(this);
        audit(AuditOption.PROFESSOR_ADD_COURSE);
    }
    @Override
    public String toString() {
        StringBuilder subjects = new StringBuilder();
        subjects.append("{");
        for(Subject s : subjectsTaught) subjects.append(s.getCode()).append(" ");
        subjects.setLength(subjects.length()-1);
        subjects.append("}");
        StringBuilder courses = new StringBuilder();
        courses.append("{");
        for(Course c : coursesTaught) {
            subjects.append(c.getCode()).append(" ");
        }
        subjects.setLength(subjects.length()-1);
        subjects.append("}");
        audit(AuditOption.PROFESSOR_TOSTRING);
        return "Professor{" +
                "first_name='" + first_name + '\'' +
                ", surname='" + surname + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", CNP=" + CNP +
                ", subjectsTaught=" + subjectsTaught +
                ", coursesTaught=" + coursesTaught +
                '}';
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.PROFESSOR_CONSTRUCTOR,
                        AuditOption.PROFESSOR_ADD_COURSE,
                        AuditOption.PROFESSOR_TOSTRING,
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Professor professor = (Professor) o;
        return Objects.equals(subjectsTaught, professor.subjectsTaught)
                && Objects.equals(coursesTaught, professor.coursesTaught);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subjectsTaught, coursesTaught);
    }
}
