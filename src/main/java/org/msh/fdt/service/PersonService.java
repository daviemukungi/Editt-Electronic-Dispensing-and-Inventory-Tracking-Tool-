package org.msh.fdt.service;

import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.*;

import java.util.List;

/**
 * Created by kenny on 4/2/14.
 */
public interface PersonService {

    public Integer addPerson(Person person);

    public List<PersonInfo> getAllPerson();

    public Integer addPatient(Patient patient);

    public Integer savePersonAddress(PersonAddress address);

    public PersonInfo savePerson(Person person, Patient patient, PersonAddress address, String service, PatientIdentifier patientIdentifier, List<PatientServiceType> patientServiceTypes);

    public Integer savePatientTransactionItem(PatientTransactionItem transactionItem);

    public void saveTransactions(Visit visit, List<PatientTransactionItem> patientTransactionItems, Transaction transaction, List<TransactionItem> transactionItems, Integer accountId);

    public Integer saveVisit(Visit visit);

    public Patient getPatient(Integer personId);

    public Person getPerson(Integer personId);

    public PersonAddress getPersonAddress(Integer personId);

    public PatientIdentifier getPatientIdentifier(Integer patientId);
    public List<PatientIdentifier> getPatientIdentifiers(Integer patientId);

    public PersonInfo updatePerson(Person person, Patient patient, PersonAddress personAddress, String service, PatientIdentifier patientIdentifier, List<PatientServiceType> patientServiceTypeList);

    public Visit getLastVisit(Integer patientId);
    public List loadLastDispense(Integer patientId);

    public Integer getPersonAge(Integer patientId);

    public List listVisit(Integer patientId, Integer account);

    public void voidDispense(Integer visitId,Integer patientTransactionItemId, Integer transactionId, Integer transactionItemID);

    public List getPatientServiceType(Integer patientId);

    public Integer loadExpectedPatients(String date, String servicearea);
    public Integer loadSeenPatients(String service_area);

    public void updatePEPPatients();

    public void updatePMCTPatients();

    public void updateLostToFollowupPatients();
    public List getPatientPrescription(Integer patientId);
    public Patient getPatientById(Integer patientId);


}
