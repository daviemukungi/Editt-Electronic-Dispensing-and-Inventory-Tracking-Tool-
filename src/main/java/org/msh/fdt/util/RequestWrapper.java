package org.msh.fdt.util;

import org.msh.fdt.model.*;

import java.util.List;

/**
 * Created by kenny on 3/30/14.
 */
public class RequestWrapper {

    // Person object
    private Person person;

    private List<Person> personList;

    // Patient object
    private Patient patient;
    //Patient address
    private PersonAddress personAddress;

    private PatientIdentifier patientIdentifier;

    private List<PatientTransactionItem> patientTransactionItems;

    private List<Transaction> transactions;

    private List<TransactionItem> transactionItems;

    private List<PatientIdentifier> patientIdentifiers;

    private Visit visit;

    private List<Visit> visits;

    private Transaction transaction;

    private String service;

    private List<PatientServiceType> patientServiceTypeList;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionItem> getTransactionItems() {
        return transactionItems;
    }

    public void setTransactionItems(List<TransactionItem> transactionItems) {
        this.transactionItems = transactionItems;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPersonAddress(PersonAddress personAddress) {
        this.personAddress = personAddress;
    }

    public PersonAddress getPersonAddress() {
        return personAddress;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public List<PatientTransactionItem> getPatientTransactionItems() {
        return patientTransactionItems;
    }

    public void setPatientTransactionItems(List<PatientTransactionItem> patientTransactionItems) {
        this.patientTransactionItems = patientTransactionItems;
    }


    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public PatientIdentifier getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(PatientIdentifier patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public List<PatientServiceType> getPatientServiceTypeList() {
        return patientServiceTypeList;
    }

    public void setPatientServiceTypeList(List<PatientServiceType> patientServiceTypeList) {
        this.patientServiceTypeList = patientServiceTypeList;
    }

    public List<PatientIdentifier> getPatientIdentifiers() {
        return patientIdentifiers;
    }

    public void setPatientIdentifiers(List<PatientIdentifier> patientIdentifiers) {
        this.patientIdentifiers = patientIdentifiers;
    }

}
