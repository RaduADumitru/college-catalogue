package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class StudentGroup extends StudentUnit implements Comparable<StudentGroup>, Auditable {
    private StudentSeries series;

    public StudentGroup(String id, Integer number, Integer year, Specialization specialization, StudentSeries series) {
        super(id, number, year, specialization);
        this.series = series;
        audit(AuditOption.STUDENTGROUP_CONSTRUCTOR);
    }

    public StudentSeries getSeries() {
        audit(AuditOption.STUDENTGROUP_GET_SERIES);
        return series;
    }

    @Override
    public void addStudent(Student s) {
        super.addStudent(s);
        series.addStudent(s);
        audit(AuditOption.STUDENTGROUP_ADD_STUDENT);
    }
    @Override
    public int compareTo(StudentGroup o) {
        int number_comp = this.getNumber().compareTo(o.getNumber());
        audit(AuditOption.STUDENTGROUP_COMPARETO);
        if(number_comp != 0) {
            return number_comp;
        }
        else {
            return this.getYear().compareTo(o.getYear());
        }
    }

    public void setSeries(StudentSeries series) {
        this.series = series;
        audit(AuditOption.STUDENTGROUP_GET_SERIES);
    }

    @Override
    public String toString() {
        audit(AuditOption.STUDENTGROUP_TOSTRING);
        return "StudentGroup{" + super.toString() +
                "series=" + series.getId() +
                '}';
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.STUDENTUNIT_ADD_STUDENT,
                        AuditOption.STUDENTUNIT_GET_ID,
                        AuditOption.STUDENTUNIT_GET_NUMBER,
                        AuditOption.STUDENTUNIT_GET_REPRESENTATIVE,
                        AuditOption.STUDENTUNIT_GET_STUDENTS,
                        AuditOption.STUDENTUNIT_GET_SPECIALIZATION,
                        AuditOption.STUDENTUNIT_SET_REPRESENTATIVE,
                        AuditOption.STUDENTUNIT_GET_YEAR,
                        AuditOption.STUDENTGROUP_ADD_STUDENT,
                        AuditOption.STUDENTGROUP_COMPARETO,
                        AuditOption.STUDENTGROUP_CONSTRUCTOR,
                        AuditOption.STUDENTGROUP_GET_SERIES,
                        AuditOption.STUDENTGROUP_TOSTRING
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
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
