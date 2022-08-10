package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;
import service.AuxService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

public abstract class StudentUnit implements Auditable {
    private String id;
    private Integer number;
    private Student representative;
    private Integer year;
    private Specialization specialization;
    private TreeSet<Student> students = new TreeSet<>();

    public String getId() {
        audit(AuditOption.STUDENTUNIT_GET_ID);
        return id;
    }

    public Integer getNumber() {
        audit(AuditOption.STUDENTUNIT_GET_NUMBER);
        return number;
    }

    public Integer getYear() {
        audit(AuditOption.STUDENTUNIT_GET_YEAR);
        return year;
    }

    public Specialization getSpecialization() {
        audit(AuditOption.STUDENTUNIT_GET_SPECIALIZATION);
        return specialization;
    }

    public StudentUnit(String id, Integer number, Student representative, Integer year, Specialization specialization, TreeSet<Student> students) {
        this.id = id;
        this.number = number;
        this.representative = representative;
        this.year = year;
        this.specialization = specialization;
        this.students = students;
    }

    public TreeSet<Student> getStudents() {
        audit(AuditOption.STUDENTUNIT_GET_STUDENTS);
        return students;
    }
    public void addStudent(Student s) {
        audit(AuditOption.STUDENTUNIT_ADD_STUDENT);
        students.add(s);
    }
    public StudentUnit(String id, Integer number, Integer year, Specialization specialization) {
        this.id = id;
        this.number = number;
        this.year = year;
        this.specialization = specialization;
    }

    public Student getRepresentative() {
        audit(AuditOption.STUDENTUNIT_GET_REPRESENTATIVE);
        return representative;
    }

    public void setRepresentative(Student representative) {
        this.representative = representative;
        audit(AuditOption.STUDENTUNIT_SET_REPRESENTATIVE);
    }

    public void setRepresentative(long cnp) {
        this.representative = AuxService.getStudents().get(cnp);
        audit(AuditOption.STUDENTUNIT_SET_REPRESENTATIVE);
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(
                        AuditOption.STUDENTUNIT_GET_ID,
                        AuditOption.STUDENTUNIT_GET_NUMBER,
                        AuditOption.STUDENTUNIT_GET_REPRESENTATIVE,
                        AuditOption.STUDENTUNIT_GET_STUDENTS,
                        AuditOption.STUDENTUNIT_GET_SPECIALIZATION,
                        AuditOption.STUDENTUNIT_SET_REPRESENTATIVE,
                        AuditOption.STUDENTUNIT_GET_YEAR,
                        AuditOption.STUDENTUNIT_ADD_STUDENT
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
        StudentUnit that = (StudentUnit) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(representative, that.representative) && Objects.equals(year, that.year) && Objects.equals(specialization, that.specialization) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, representative, year, specialization, students);
    }
}
