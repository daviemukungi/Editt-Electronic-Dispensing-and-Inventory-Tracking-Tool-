package org.msh.fdt.controller;

import org.msh.fdt.dto.PersonInfo;
import org.msh.fdt.model.Visit;
import org.msh.fdt.service.PersonService;
import org.msh.fdt.util.JTableResponse;
import org.msh.fdt.util.ReferenceDataListing;
import org.msh.fdt.util.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by kenny on 4/3/14.
 *
 * This Controller performs  actions related to a Person
 * This include
 *      - Creating a new patient
 *      - Updating patient information
 *      - Dispensing
 *      - Person Search list
 *      - Loading Visit information
 *      - Loading Expected Patients on a date
 */
@Controller
@RequestMapping("/person")
public class PersonController extends BaseController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/json/add", method = RequestMethod.POST)
    public @ResponseBody Object addPerson(@RequestBody RequestWrapper requestWrapper) {
        try {
            requestWrapper.getPerson().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPerson().setUuid(UUID.randomUUID().toString());

            requestWrapper.getPatient().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPatient().setUuid(UUID.randomUUID().toString());

            requestWrapper.getPersonAddress().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPersonAddress().setUuid(UUID.randomUUID().toString());

            requestWrapper.getPatientIdentifier().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPatientIdentifier().setUuid(UUID.randomUUID().toString());
            PersonInfo info = personService.savePerson(requestWrapper.getPerson(), requestWrapper.getPatient(), requestWrapper.getPersonAddress(), requestWrapper.getService(), requestWrapper.getPatientIdentifier(), requestWrapper.getPatientServiceTypeList());

            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @RequestMapping(value = "/json/update", method = RequestMethod.POST)
    public @ResponseBody Object updatePerson(@RequestBody RequestWrapper requestWrapper) {
        try {
            /*requestWrapper.getPerson().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPerson().setUuid(UUID.randomUUID().toString());

            requestWrapper.getPatient().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPatient().setUuid(UUID.randomUUID().toString());

            requestWrapper.getPersonAddress().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getPersonAddress().setUuid(UUID.randomUUID().toString());*/
            PersonInfo pi = personService.updatePerson(requestWrapper.getPerson(), requestWrapper.getPatient(), requestWrapper.getPersonAddress(), requestWrapper.getService(), requestWrapper.getPatientIdentifier(), requestWrapper.getPatientServiceTypeList());
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    @RequestMapping(value="/json/dispense/{accountId}", method = RequestMethod.POST)
    public @ResponseBody String dispense(@RequestBody RequestWrapper requestWrapper, @PathVariable("accountId") Integer accountId) {

        try {
            Visit visit = requestWrapper.getVisit();
            visit.setCreatedOn(new Timestamp(new Date().getTime()));
            visit.setUuid(UUID.randomUUID().toString());

            personService.saveTransactions(visit, requestWrapper.getPatientTransactionItems(), requestWrapper.getTransaction(), requestWrapper.getTransactionItems(), accountId);

            return "saved";

        } catch (Exception e) {


            return "Error " + e.getMessage();
        }
    }

    @RequestMapping(value="/json/getPerson/{id}", method = RequestMethod.POST)
    public @ResponseBody RequestWrapper getPerson(@PathVariable("id") Integer id) {
        try {
            if (id != -1) {
                RequestWrapper requestWrapper = new RequestWrapper();
                requestWrapper.setPerson(personService.getPerson(id));
                requestWrapper.setPersonAddress(personService.getPersonAddress(id));
                requestWrapper.setPatient(personService.getPatient(id));
                requestWrapper.setPatientIdentifier(personService.getPatientIdentifier(requestWrapper.getPatient().getId()));
                requestWrapper.setPatientIdentifiers(personService.getPatientIdentifiers(requestWrapper.getPatient().getId()));
                requestWrapper.setPatientServiceTypeList(personService.getPatientServiceType(requestWrapper.getPatient().getId()));
                return requestWrapper;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value="/json/personSearchList", method = RequestMethod.POST)
    public @ResponseBody List<PersonInfo> personSearchList() {
        try {
            List<PersonInfo> personList = personService.getAllPerson();
            return personList;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    @RequestMapping(value="/json/loadVisit/{patientId}/{accountId}", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataListing loadVisit(@PathVariable("patientId") Integer patientId, @PathVariable("accountId") Integer accountId) {
        try {
            ReferenceDataListing dataListing = new ReferenceDataListing();
            dataListing.setObjectList(personService.listVisit(patientId, accountId));
            return dataListing;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value="/json/loadLastVisit/{patientId}", method = RequestMethod.POST)
    public @ResponseBody
    Visit loadLastVisit(@PathVariable("patientId") Integer patientId) {
        try {
            return personService.getLastVisit(patientId);
        } catch (Exception e) {
            return null;
        }
    }
    @RequestMapping(value = "/json/loadPatientPrescription/{patientId}", method = RequestMethod.GET)
    public @ResponseBody
    ReferenceDataListing loadPatientPrescription(@PathVariable("patientId") Integer patientId){
        try{
            ReferenceDataListing referenceDataListing = new ReferenceDataListing();
            referenceDataListing.setPrescriptionList(personService.getPatientPrescription(patientId));
            return referenceDataListing;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
  @RequestMapping(value="/json/voidDispense/{visitId}/{patientTransactionItemId}/{transactionItemId}/{transactionId}/{accountId}/{patientId}", method = RequestMethod.POST)
    public @ResponseBody
  ReferenceDataListing voidDispense(@PathVariable("visitId") Integer visitId,
                       @PathVariable("patientTransactionItemId") Integer patientTransactionItemId,
                       @PathVariable("transactionItemId") Integer transactionItemId,
                       @PathVariable("transactionId") Integer transactionId,
                       @PathVariable("patientId") Integer patientId,
                       @PathVariable("accountId") Integer accountId) {
        try {
            personService.voidDispense(visitId,patientTransactionItemId,transactionId,transactionItemId);
            ReferenceDataListing dataListing = new ReferenceDataListing();
            dataListing.setObjectList(personService.listVisit(accountId,patientId));
            return dataListing;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value="/json/loadExpectedPatients/{date}/{servicearea}", method = RequestMethod.POST)
    public @ResponseBody
    JTableResponse loadExpectedPatients(@PathVariable("date") String date,@PathVariable("servicearea") String servicearea) {
        try {
            JTableResponse res = new JTableResponse();
            res.setRecord(personService.loadExpectedPatients(date,servicearea));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value="/json/loadSeenPatients/{service_area}", method = RequestMethod.POST)
    public @ResponseBody
    JTableResponse loadSeenPatients(@PathVariable("service_area") String service_area) {
        try {
            JTableResponse res = new JTableResponse();
            res.setRecord(personService.loadSeenPatients(service_area));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
