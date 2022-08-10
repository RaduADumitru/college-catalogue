package model;

import enums.AuditOption;
import interfaces.Auditable;
import exceptions.InvalidGradeException;
import service.Auditer;
import service.AuxService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Grade implements Comparable<Grade>, Auditable {
    private Student student;
    private Course course;
    private Float score;

    public Grade(Student student, Course course, Float score) {
        try {
            this.student = student;
            this.course = course;
            this.score = Math.round(score * 100) / (float)100;
            AuxService.validateScore(score);
        }
        catch (InvalidGradeException e) {
            e.printStackTrace();
        }
        audit(AuditOption.GRADE_CONSTRUCTOR);
    }

    public Student getStudent() {
        audit(AuditOption.GRADE_GET_STUDENT);
        return student;
    }

    public Course getCourse() {
        audit(AuditOption.GRADE_GET_COURSE);
        return course;
    }

    public Float getScore() {
        audit(AuditOption.GRADE_GET_SCORE);
        return score;
    }

    @Override
    public String toString() {
        audit(AuditOption.GRADE_TOSTRING);
        return "Grade{" +
                "student=" + student.getFirst_name() + " " + student.getSurname() + " " + student.getCNP() +
                ", course=" + course.getCode() +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Grade o) {
        int studentComp = student.compareTo(o.getStudent());
        audit(AuditOption.GRADE_COMPARETO);
        if(studentComp!=0) {
            return studentComp;
        }
        else {
            return course.compareTo(o.getCourse());
        }
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.GRADE_COMPARETO,
                        AuditOption.GRADE_CONSTRUCTOR,
                        AuditOption.GRADE_GET_COURSE,
                        AuditOption.GRADE_GET_SCORE,
                        AuditOption.GRADE_TOSTRING,
                        AuditOption.GRADE_GET_STUDENT)
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
        Grade grade = (Grade) o;
        return Objects.equals(student, grade.student) && Objects.equals(course, grade.course) && Objects.equals(score, grade.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course, score);
    }
}
