package model;

import enums.AuditOption;
import interfaces.Auditable;
import enums.Degree;
import service.Auditer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Specialization implements Auditable {
    private final String code;
    private final String name;
    private final Integer nr_years;
    private final Degree degree;

    public Specialization(String code, String name, Integer nr_years, Degree degree) {
        this.code = code;
        this.name = name;
        this.nr_years = nr_years;
        this.degree = degree;
        audit(AuditOption.SPECIALIZATION_CONSTRUCTOR);
    }

    public String getCode() {
        audit(AuditOption.SPECIALIZATION_GET_CODE);
        return code;
    }

    public Degree getDegree() {
        audit(AuditOption.SPECIALIZATION_GET_DEGREE);
        return degree;
    }

    @Override
    public String toString() {
        audit(AuditOption.SPECIALIZATION_TOSTRING);
        return "Specialization{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", nr_years=" + nr_years +
                ", degree=" + degree +
                '}';
    }

    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.SPECIALIZATION_CONSTRUCTOR,
                        AuditOption.SPECIALIZATION_GET_CODE,
                        AuditOption.SPECIALIZATION_GET_DEGREE,
                        AuditOption.SPECIALIZATION_TOSTRING
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
        Specialization that = (Specialization) o;
        return Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(nr_years, that.nr_years) && degree == that.degree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, nr_years, degree);
    }
}
