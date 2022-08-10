package model;

import enums.AuditOption;
import interfaces.Auditable;
import exceptions.CNP_Exception;
import service.Auditer;
import service.AuxService;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public abstract class Person implements Auditable {
    protected String first_name;
    protected String surname;
    protected Date date_of_birth;
    protected long CNP;

    public Person(String first_name, String surname, Date date_of_birth, long CNP) {
        try {
            this.first_name = first_name;
            this.surname = surname;
            this.date_of_birth = date_of_birth;
            this.CNP = CNP;
            AuxService.validateCNP(CNP);
        }
        catch (CNP_Exception exception) {
            exception.printStackTrace();
        }
    }


    public String getFirst_name() {
        audit(AuditOption.PERSON_GET_FIRSTNAME);
        return first_name;
    }

    public String getSurname() {
        audit(AuditOption.PERSON_GET_SURNAME);
        return surname;
    }
    public String getFullName() {
        audit(AuditOption.PERSON_GET_FULL_NAME);
        return surname + " " + first_name;
    }
    public Long getCNP() {
        audit(AuditOption.PERSON_GET_CNP);
        return CNP;
    }
    @Override
    public void audit(AuditOption option) {
        final HashSet<AuditOption> auditOptions = new HashSet<>(
                Arrays.asList(AuditOption.PERSON_GET_CNP,
                        AuditOption.PERSON_GET_DATEOFBIRTH,
                        AuditOption.PERSON_GET_FIRSTNAME,
                        AuditOption.PERSON_GET_SURNAME,
                        AuditOption.PERSON_GET_FULL_NAME
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
        Person person = (Person) o;
        return Objects.equals(first_name, person.first_name) && Objects.equals(surname, person.surname) && Objects.equals(date_of_birth, person.date_of_birth) && Objects.equals(CNP, person.CNP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, surname, date_of_birth, CNP);
    }
}
