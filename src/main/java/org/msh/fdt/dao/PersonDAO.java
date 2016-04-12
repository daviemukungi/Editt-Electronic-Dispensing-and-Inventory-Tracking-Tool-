package org.msh.fdt.dao;

import org.hibernate.Session;
import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.*;

import java.util.List;

/**
 * Created by kenny on 4/2/14.
 */
public interface PersonDAO {

    public Integer addPerson(Person person);

    public List<PersonInfo> getAllPerson();

    public Integer addPatient(Patient patient);

    public Integer savePatientIdentifier(PatientIdentifier identifier);

    public Integer savePersonAddress(PersonAddress address);

    public Integer saveVisit(Visit visit);

    public Session getSession();

    public Integer savePatientTransactionItem(PatientTransactionItem transactionItem);

    public Patient getPatient(Integer personId);

    public Person getPerson(Integer personId);

    public PersonAddress getPersonAddress(Integer personId);

    public PatientIdentifier getPatientIdentifier(Integer patientId);
    public List<PatientIdentifier> getPatientIdentifiers(Integer patientId);

    public Integer updatePerson(Person person);

    public void updatePersonAddress(PersonAddress personAddress);

    public Integer updatePatient(Patient patient);

    public Integer updatePatientIdentifier(PatientIdentifier identifier);

    public Visit getLastVisit(Integer patientId);

    public List loadLastDispense(Integer patientId);

    public void voidDispense(Integer visitId,Integer patientTransactionItemId, Integer transactionId, Integer transactionItemID);

    public Patient getPatientById(Integer patientId);

    public List<Visit> listVisit(Integer patientId);

    public Object savePatientServiceType(PatientServiceType patientServiceType);

    public void updatePatientServiceType(PatientServiceType patientServiceType);

    public List getPatientServiceType(Integer patientId);
    public List getPatientPrescription(Integer patientId);


}
