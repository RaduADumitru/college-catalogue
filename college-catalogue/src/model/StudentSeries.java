package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

public class StudentSeries extends StudentUnit implements Comparable<StudentSeries>, Auditable {
    private TreeSet<StudentGroup> studentGroups = new TreeSet<>();

    @Override
    public int compareTo(StudentSeries o) {
        int number_comp = this.getNumber().compareTo(o.getNumber());
        audit(AuditOption.STUDENTSERIES_COMPARETO);
        if(number_comp != 0) {
            return number_comp;
        }
        else {
            return this.getYear().compareTo(o.getYear());
        }
    }

    public StudentSeries(String id, Integer number, Integer year, Specialization specialization) {
        super(id, number, year, specialization);
        audit(AuditOption.STUDENTSERIES_CONSTRUCTOR);
    }
    public void addGroup(StudentGroup group) {
        studentGroups.add(group);
        audit(AuditOption.STUDENTSERIES_ADD_GROUP);
    }

    @Override
    public String toString() {
        audit(AuditOption.STUDENTSERIES_TOSTRING);
        return "StudentSeries{" + super.toString() +
                " studentGroups=" + studentGroups +
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
                        AuditOption.STUDENTSERIES_COMPARETO,
                        AuditOption.STUDENTSERIES_ADD_GROUP,
                        AuditOption.STUDENTSERIES_CONSTRUCTOR,
                        AuditOption.STUDENTSERIES_TOSTRING
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
        StudentSeries that = (StudentSeries) o;
        return Objects.equals(studentGroups, that.studentGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studentGroups);
    }
}
