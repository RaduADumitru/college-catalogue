package model;

import enums.AuditOption;
import interfaces.Auditable;
import enums.Semester;
import enums.Year;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Course implements Comparable<Course>, Auditable {
    private String code;
    private Subject subject;
    private Professor professor;
    private Integer year;
    private Year collegeYear;
    private Semester semester;
    private StudentSeries series;

    public Course(String code, Subject subject, Professor professor, Integer year, Year collegeYear,
                  Semester semester, StudentSeries series){
        this.code = code;
        this.subject = subject;
        this.professor = professor;
        this.year = year;
        this.collegeYear = collegeYear;
        this.semester = semester;
        this.series = series;
        audit(AuditOption.COURSE_CONSTRUCTOR);
    }

    public Integer getYear() {
        audit(AuditOption.COURSE_GET_YEAR);
        return year;
    }

    public Year getCollegeYear() {
        audit(AuditOption.COURSE_GET_COLLEGE_YEAR);
        return collegeYear;
    }

    public Semester getSemester() {
        audit(AuditOption.COURSE_GET_SEMESTER);
        return semester;
    }

    public Subject getSubject() {
        audit(AuditOption.COURSE_GET_SUBJECT);
        return subject;
    }

    public String getCode() {
        audit(AuditOption.COURSE_GET_CODE);
        return code;
    }

    public void setProfessor(Professor professor) {
        audit(AuditOption.COURSE_SET_PROFESSOR);
        this.professor = professor;
    }

    @Override
    public String toString() {
        audit(AuditOption.COURSE_TOSTRING);
        return "Course{" +
                "code='" + code + '\'' +
                ", subject=" + subject +
                ", professor=" + professor +
                ", year=" + year +
                ", collegeYear=" + collegeYear +
                ", semester=" + semester +
                ", series=" + series +
                '}';
    }

    @Override
    public int compareTo(Course o) { //year, college_year, semester, subject
        int yearComp;
        yearComp = year.compareTo(o.getYear());
        if(yearComp != 0) return yearComp;
        else {
            int collegeYearComp;
            collegeYearComp = collegeYear.compareTo(o.getCollegeYear());

            if(collegeYearComp != 0) return collegeYearComp;
            else {
                int semesterComp;
                semesterComp = semester.compareTo(o.getSemester());
                if(semesterComp != 0) return semesterComp;
                else {
                    return subject.compareTo(o.getSubject());
                }
            }
        }
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.COURSE_COMPARETO,
                        AuditOption.COURSE_CONSTRUCTOR,
                        AuditOption.COURSE_GET_CODE,
                        AuditOption.COURSE_GET_SEMESTER,
                        AuditOption.COURSE_GET_COLLEGE_YEAR,
                        AuditOption.COURSE_GET_SUBJECT,
                        AuditOption.COURSE_GET_YEAR,
                        AuditOption.COURSE_SET_PROFESSOR,
                        AuditOption.COURSE_TOSTRING
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
        Course course = (Course) o;
        return Objects.equals(code, course.code) && Objects.equals(subject, course.subject) && Objects.equals(professor, course.professor) && Objects.equals(year, course.year) && collegeYear == course.collegeYear && semester == course.semester && Objects.equals(series, course.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, subject, professor, year, collegeYear, semester, series);
    }
}
