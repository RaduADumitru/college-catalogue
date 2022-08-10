package model;

import enums.AuditOption;
import interfaces.Auditable;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Subject implements Comparable<Subject>, Auditable {
    private String code;
    private String name;
    private Integer credits;
    private Boolean participatesInTotalGrade;

    public Subject(String code, String name, Integer credits, Boolean participatesInTotalGrade) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.participatesInTotalGrade = participatesInTotalGrade;
        audit(AuditOption.SUBJECT_CONSTRUCTOR);
    }

    public String getCode() {
        audit(AuditOption.SUBJECT_GET_CODE);
        return code;
    }

    public String getName() {
        audit(AuditOption.SUBJECT_GET_NAME);
        return name;
    }

    public Integer getCredits() {
        audit(AuditOption.SUBJECT_GET_CREDITS);
        return credits;
    }

    public Boolean getParticipatesInTotalGrade() {
        audit(AuditOption.SUBJECT_GET_PARTICIPATESINTOTALGRADE);
        return participatesInTotalGrade;
    }

    @Override
    public String toString() {
        audit(AuditOption.SUBJECT_TOSTRING);
        return "Subject{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", participatesInTotalGrade=" + participatesInTotalGrade +
                '}';
    }

    @Override
    public int compareTo(Subject o) {
        audit(AuditOption.SUBJECT_COMPARETO);
        return name.compareTo(o.getName());
    }

    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(
                        AuditOption.SUBJECT_COMPARETO,
                        AuditOption.SUBJECT_CONSTRUCTOR,
                        AuditOption.SUBJECT_GET_CODE,
                        AuditOption.SUBJECT_GET_CREDITS,
                        AuditOption.SUBJECT_GET_NAME,
                        AuditOption.SUBJECT_GET_PARTICIPATESINTOTALGRADE,
                        AuditOption.SUBJECT_GET_SPECIALIZATION,
                        AuditOption.SUBJECT_TOSTRING
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
        Subject subject = (Subject) o;
        return Objects.equals(code, subject.code) && Objects.equals(name, subject.name) && Objects.equals(credits, subject.credits) && Objects.equals(participatesInTotalGrade, subject.participatesInTotalGrade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, credits, participatesInTotalGrade);
    }
}
