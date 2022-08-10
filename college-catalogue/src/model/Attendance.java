package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Attendance implements Comparable<Attendance>, Auditable {
    private Student student;
    private Course course;

    public Attendance(Student student, Course course){
        this.student = student;
        this.course = course;
        audit(AuditOption.ATTENDANCE_CONSTRUCTOR);
    }

    public Attendance() {
    }

    public Student getStudent() {
        audit(AuditOption.ATTENDANCE_GET_STUDENT);
        return student;
    }

    public Course getCourse() {
        audit(AuditOption.ATTENDANCE_GET_COURSE);
        return course;
    }

    @Override
    public String toString() {
        audit(AuditOption.ATTENDANCE_TOSTRING);
        return "Attendance{" +
                    "student=" + student.getFirst_name() + " " + student.getSurname() + student.getCNP() +
                    ", course=" + course.getCode() +
                    '}';

    }

    @Override
    public int compareTo(Attendance o) {
        int compareStudent;
        compareStudent = student.compareTo(o.getStudent());
        audit(AuditOption.ATTENDANCE_COMPARETO);
        if(compareStudent != 0){
            return compareStudent;
        }
        else {
            return course.compareTo(o.getCourse());
            }
        }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.ATTENDANCE_COMPARETO,
                        AuditOption.ATTENDANCE_CONSTRUCTOR,
                        AuditOption.ATTENDANCE_GET_COURSE,
                        AuditOption.ATTENDANCE_TOSTRING,
                        AuditOption.ATTENDANCE_GET_STUDENT)
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
        Attendance that = (Attendance) o;
        return Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}
