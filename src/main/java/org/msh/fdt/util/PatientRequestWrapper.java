package org.msh.fdt.util;

import org.msh.fdt.dto.Patient;
import org.msh.fdt.dto.PersonAddress;
import org.msh.fdt.model.Person;

/**
 * Created by kenny on 4/28/14.
 */
public class PatientRequestWrapper {

    private Patient patient;

    private Person person;

    private PersonAddress personAddress;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonAddress getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(PersonAddress personAddress) {
        this.personAddress = personAddress;
    }
}
