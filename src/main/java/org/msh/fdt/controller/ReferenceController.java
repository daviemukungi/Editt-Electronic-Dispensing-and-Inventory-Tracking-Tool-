package org.msh.fdt.controller;

import org.msh.fdt.model.*;
import org.msh.fdt.service.*;
import org.msh.fdt.util.DataWrapper;
import org.msh.fdt.util.JTableResponse;
import org.msh.fdt.util.ReferenceDataListing;
import org.msh.fdt.util.ReferenceDataWrapper_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by kenny on 4/8/14.
 *
 * This is the main endpoint for loading of reference data.
 * This controller contains functions for listing, creating, updating and deleting of various reference data.
 * It also lists various reference data required to perform a task e.g. Visit refernce data e.t.c
 *
 */

@Controller
@RequestMapping("/reference")
public class ReferenceController extends BaseController {

    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private StocksService stocksService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;

    @Autowired
    private PersonService personService;

    /**
     *  Account functions
     * @return
     */

    @RequestMapping(value="/json/listAccount", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listAccount() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<Account> accounts = referenceService.accountList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(accounts);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }
 @RequestMapping(value="/json/listPatientIdentifiers", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listPatientIdentifiers() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<PatientIdentifier> patientIdentifiers = referenceService.listPatientIdentifiers();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(patientIdentifiers);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createAccount", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createAccount(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getAccount().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getAccount().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveAccount(dataWrapper.getAccount());
            dataWrapper.getAccount().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getAccount());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteAccount/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteAccount(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteAccount(id, userId);

            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateAccount", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateAccount(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateAccount(dataWrapper.getAccount());
            response.setResult("OK");
            response.setRecord(dataWrapper.getAccount());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }
    /**
     *  Account Type Functions
     * @return
     */
    @RequestMapping(value="/json/accountTypeList", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listAccountType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<AccountType> accountTypes = referenceService.accountTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(accountTypes);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/saveAccountType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse saveAccountType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getAccountType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getAccountType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveAccountType(dataWrapper.getAccountType());
            dataWrapper.getAccountType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getAccountType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateAccountType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateAccountType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateAccountType(dataWrapper.getAccountType());

            response.setResult("OK");
            response.setRecord(dataWrapper.getAccountType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }


    @RequestMapping(value = "/json/deleteAccountType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteAccountType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteAccountType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    @RequestMapping(value = "/json/saveservicetype", method = RequestMethod.POST)
    public void saveServiceType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        try {
            referenceService.saveServiceType(dataWrapper.getServiceType());
        } catch (Exception e) {

        }
    }

    @RequestMapping(value="/json/listRegimenStatus", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listRegimenStatus() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<RegimenStatus> regimenStatusList = referenceService.regimenStatusList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(regimenStatusList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createRegimenStatus", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createRegimenStatus(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getRegimenStatus().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getRegimenStatus().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveRegimenStatus(dataWrapper.getRegimenStatus());
            dataWrapper.getRegimenStatus().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenStatus());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateRegimenStatus", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateRegimenStatus(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateRegimenStatus(dataWrapper.getRegimenStatus());

            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenStatus());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteRegimenStatus/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteRegimenStatus(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteRegimenStatus(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Regimen Type functions
     * @return
     */
    @RequestMapping(value="/json/listRegimenType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listRegimenType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<RegimenType> regimenTypes = referenceService.regimenTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(regimenTypes);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/saveRegimenType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse saveRegimenType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getRegimenType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getRegimenType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveRegimenType(dataWrapper.getRegimenType());
            dataWrapper.getRegimenType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenType());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateRegimenType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateRegimenType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateRegimenType(dataWrapper.getRegimenType());

            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteRegimenType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteRegimenType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteRegimenType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Dosage functions   List/Create/Edit/
     *
     */
    @RequestMapping(value = "/json/dosageList", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDosage() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<Dosage> dosageList = referenceService.dosageList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(dosageList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDosage", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDosage(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getDosage().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getDosage().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveDosage(dataWrapper.getDosage());
            dataWrapper.getDosage().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDosage());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateDosage", method = RequestMethod.POST)
    public @ResponseBody JTableResponse saveDosage(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDosage(dataWrapper.getDosage());

            response.setResult("OK");
            response.setRecord(dataWrapper.getDosage());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDosage/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDosage(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDosage(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Dispensing Unit functions List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listDispensingUnit", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDispensingUnit() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<DispensingUnit> dispensingUnitList = referenceService.dispensingUnitList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(dispensingUnitList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDispensingUnit", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDispensingUnit(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getDispensingUnit().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getDispensingUnit().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveDispensingUnit(dataWrapper.getDispensingUnit());
            dataWrapper.getDispensingUnit().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDispensingUnit());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateDispensingUnit", method = RequestMethod.POST)
    public @ResponseBody JTableResponse saveDispensingUnit(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDispensingUnit(dataWrapper.getDispensingUnit());
            response.setResult("OK");
            response.setRecord(dataWrapper.getDispensingUnit());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDispensingUnit/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDispensingUnit(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDispensingUnit(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Generic Name functions List/Create/Update
     * @return
     */
    @RequestMapping(value = "/json/listGenericName", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listGenericName() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<GenericName> genericNameList = referenceService.genericNameList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(genericNameList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createGenericName", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createGenericName(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getGenericName().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getGenericName().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveGenericName(dataWrapper.getGenericName());
            dataWrapper.getGenericName().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getGenericName());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateGenericName", method = RequestMethod.POST)
    public @ResponseBody JTableResponse saveGenericName(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateGenericName(dataWrapper.getGenericName());
            response.setResult("OK");
            response.setRecord(dataWrapper.getGenericName());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteGenericName/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteGenericName(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteGenericName(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Duration Unit functions List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listDistrict", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDistrict() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<District> districtList = referenceService.districtList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(districtList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDistrict", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDistrict(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            District district = new District();
            district.setName(dataWrapper.getDistrict().getName());
            district.setCode(dataWrapper.getDistrict().getCode());
            district.setUuid(UUID.randomUUID().toString());
            district.setRegionId(dataWrapper.getDistrict().getRegionId());
            district.setCreatedOn(new Timestamp(new Date().getTime()));
            district.setCreatedBy(dataWrapper.getDistrict().getCreatedBy());
            Integer id = referenceService.saveDistrict(district);
            dataWrapper.getDistrict().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDistrict());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateDistrict", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateDistrict(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDistrict(dataWrapper.getDistrict());
            response.setResult("OK");
            response.setRecord(dataWrapper.getDistrict());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDistrict/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDistrict(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDistrict(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Identifier Type functions List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listIdentifierType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listIdentifierType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<IdentifierType> identifierTypes = referenceService.identifierTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(identifierTypes);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createIdentifierType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createIdentifierType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getIdentifierType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getIdentifierType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveIdentifierType(dataWrapper.getIdentifierType());
            dataWrapper.getIdentifierType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getIdentifierType());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateIdentifierType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateIdentifierType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateIdentifierType(dataWrapper.getIdentifierType());
            response.setResult("OK");
            response.setRecord(dataWrapper.getIdentifierType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteIdentifierType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteIdentifierType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteIdentifierType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Location functions List/Create/Update
     *
     */


    /**
     *  Patient source functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listPatientSource", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listPatientSource() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<PatientSource> patientSourceList = referenceService.patientSourceList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(patientSourceList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createPatientSource", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createPatientSource(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            PatientSource source = new PatientSource();
            source.setName(dataWrapper.getPatientSource().getName());
            source.setDescription(dataWrapper.getPatientSource().getDescription());
            source.setCreatedBy(dataWrapper.getPatientSource().getCreatedBy());
            source.setUuid(UUID.randomUUID().toString());
            source.setCreatedOn(new Timestamp(new Date().getTime()));
            Integer id = referenceService.savePatientSource(source);
            dataWrapper.getPatientSource().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getPatientSource());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updatePatientSource", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updatePatientSource(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updatePatientSource(dataWrapper.getPatientSource());
            response.setResult("OK");
            response.setRecord(dataWrapper.getPatientSource());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deletePatientSource/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deletePatientSource(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deletePatientSource(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Service Type functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listServiceType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listServiceType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<ServiceType> serviceTypeList = referenceService.serviceTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(serviceTypeList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createServiceType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createServiceType(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            ServiceType type = new ServiceType();
            type.setUuid(UUID.randomUUID().toString());
            type.setName(dataWrapper.getServiceType().getName());
            type.setService_area(dataWrapper.getServiceType().getService_area());
            type.setCreatedBy(dataWrapper.getServiceType().getCreatedBy());
            type.setDescription(dataWrapper.getServiceType().getDescription());
            type.setCreatedOn(new Timestamp(new Date().getTime()));
            Integer id = referenceService.saveServiceType(type);
            dataWrapper.getServiceType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getServiceType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateServiceType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateServiceType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateServiceType(dataWrapper.getServiceType());
            response.setResult("OK");
            response.setRecord(dataWrapper.getServiceType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteServiceType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteServiceType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteServiceType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Support Organization functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listSupportingOrganization", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listSupportingOrganization() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<SupportingOrganization> supportingOrganizations = referenceService.supportingOrganizationList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(supportingOrganizations);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createSupportingOrganization", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createSupportingOrganization(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            SupportingOrganization org = new SupportingOrganization();
            org.setName(dataWrapper.getSupportingOrganization().getName());
            org.setDescription(dataWrapper.getSupportingOrganization().getDescription());
            org.setCreatedBy(dataWrapper.getSupportingOrganization().getCreatedBy());
            org.setUuid(UUID.randomUUID().toString());
            org.setCreatedOn(new Timestamp(new Date().getTime()));
            Integer id = referenceService.saveSupportingOrganization(org);
            dataWrapper.getSupportingOrganization().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getSupportingOrganization());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateSupportingOrganization", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateSupportingOrganization(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateSupportingOrganization(dataWrapper.getSupportingOrganization());
            response.setResult("OK");
            response.setRecord(dataWrapper.getSupportingOrganization());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteSupportingOrganization/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteSupportingOrganization(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteSupportingOrganization(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Transaction type functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listTransactionType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listTransactionType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<TransactionType> transactionTypeList = referenceService.transactionTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(transactionTypeList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createTransactionType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createTransactionType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getTransactionType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getTransactionType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveTransactionType(dataWrapper.getTransactionType());
            dataWrapper.getTransactionType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getTransactionType());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateTransactionType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateTransactionType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateTransactionType(dataWrapper.getTransactionType());
            response.setResult("OK");
            response.setRecord(dataWrapper.getTransactionType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteTransactionType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteTransactionType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteTransactionType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Region functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listRegion", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listRegion() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<Region> transactionTypeList = referenceService.regionList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(transactionTypeList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createRegion", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createRegion(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {

            Region region = new Region();
            region.setId(dataWrapper.getRegion().getId());
            region.setName(dataWrapper.getRegion().getName());
            region.setUuid(UUID.randomUUID().toString());
            region.setCreatedBy(dataWrapper.getRegion().getCreatedBy());
            region.setCreatedOn(new Timestamp(new Date().getTime()));
            Integer id = referenceService.saveRegion(region);
            dataWrapper.getRegion().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegion());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Region
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateRegion", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateRegion(@RequestBody DataWrapper dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            Region region = new Region();
            region.setId(dataWrapper.getRegion().getId());
            region.setName(dataWrapper.getRegion().getName());
            region.setCreatedBy(dataWrapper.getRegion().getCreatedBy());
            region.setCreatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateRegion(region);
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegion());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteRegion/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteRegion(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteRegion(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Patient Status functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listPatientStatus", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listPatientStatus() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<PatientStatus> patientStatusList = referenceService.patientStatusList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(patientStatusList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createPatientStatus", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createPatientStatus(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getPatientStatus().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getPatientStatus().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.savePatientStatus(dataWrapper.getPatientStatus());
            dataWrapper.getPatientStatus().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getPatientStatus());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Patient Status
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updatePatientStatus", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updatePatientStatus(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updatePatientStatus(dataWrapper.getPatientStatus());
            response.setResult("OK");
            response.setRecord(dataWrapper.getPatientStatus());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deletePatientStatus/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deletePatientStatus(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deletePatientStatus(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Indication functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listIndication", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listIndication() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<Indication> indicationList = referenceService.indicationList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(indicationList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createIndication", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createIndication(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getIndication().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getIndication().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveIndication(dataWrapper.getIndication());
            dataWrapper.getIndication().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getIndication());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Indication
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateIndication", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateIndication(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateIndication(dataWrapper.getIndication());
            response.setResult("OK");
            response.setRecord(dataWrapper.getIndication());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteIndication/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteIndication(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteIndication(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Regimen Change Reason functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listRegimenChangeReason", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listRegimenChangeReason() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<RegimenChangeReason> changeReasons = referenceService.regimenChangeReasonList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(changeReasons);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createRegimenChangeReason", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createRegimenChangeReason(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getRegimenChangeReason().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getRegimenChangeReason().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveRegimenChangeReason(dataWrapper.getRegimenChangeReason());
            dataWrapper.getRegimenChangeReason().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenChangeReason());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Regimen Change Reason
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateRegimenChangeReason", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateRegimenChangeReason(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getRegimenChangeReason().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateRegimenChangeReason(dataWrapper.getRegimenChangeReason());
            response.setResult("OK");
            response.setRecord(dataWrapper.getRegimenChangeReason());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteRegimenChangeReason/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteRegimenChangeReason(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteRegimenChangeReason(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Visit Type functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listVisitType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listVisitType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<VisitType> visitTypeList = referenceService.visitTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(visitTypeList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createVisitType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createVisitType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getVisitType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getVisitType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveVisitType(dataWrapper.getVisitType());
            dataWrapper.getVisitType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getVisitType());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Visit type
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateVisitType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateVisitType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getVisitType().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateVisitType(dataWrapper.getVisitType());
            response.setResult("OK");
            response.setRecord(dataWrapper.getVisitType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteVisitType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteVisitType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteVisitType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Drug Category functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listDrugCategory", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDrugCategory() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<DrugCategory> drugCategoryList = referenceService.drugCategoryList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(drugCategoryList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDrugCategory", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDrugCategory(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getDrugCategory().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getDrugCategory().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveDrugCategory(dataWrapper.getDrugCategory());
            dataWrapper.getDrugCategory().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugCategory());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given drug category
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateDrugCategory", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateDrugCategory(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDrugCategory(dataWrapper.getDrugCategory());
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugCategory());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDrugCategory/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDrugCategory(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDrugCategory(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Drug Form functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listDrugForm", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDrugForm() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<DrugForm> drugFormList = referenceService.drugFormList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(drugFormList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDrugForm", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDrugForm(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getDrugForm().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getDrugForm().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveDrugForm(dataWrapper.getDrugForm());
            dataWrapper.getDrugForm().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugForm());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given drug form
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateDrugForm", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateDrugForm(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDrugForm(dataWrapper.getDrugForm());
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugForm());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDrugForm/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDrugForm(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDrugForm(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     *  Drug Type functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listDrugType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listDrugType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<DrugType> drugTypeList = referenceService.drugTypeList();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(drugTypeList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createDrugType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createDrugType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getDrugType().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getDrugType().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveDrugType(dataWrapper.getDrugType());
            dataWrapper.getDrugType().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugType());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given drug form
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateDrugType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateDrugType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateDrugType(dataWrapper.getDrugType());
            response.setResult("OK");
            response.setRecord(dataWrapper.getDrugType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteDrugType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteDrugType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteDrugType(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value="/json/patient/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listPatientReferences(HttpServletRequest request) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setServiceTypeList(referenceService.serviceTypeList());
            listing.setPatientSourceList(referenceService.patientSourceList());
            listing.setSupportingOrganizationList(referenceService.supportingOrganizationList());
            listing.setDistrictList(referenceService.districtList());
            listing.setIdentifierTypeList(referenceService.identifierTypeList());
            listing.setPatientIdentifierList(referenceService.listPatientIdentifiers());
            listing.setRegimenList(stocksService.listRegimen(false));
            listing.setPatientStatusList(referenceService.patientStatusList());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This functions loads the reference data required to fill in a visit form and dispense drugs
     * @param accountId
     * @return
     */
    @RequestMapping(value="/json/visit/listReferences/{accountId}/{patientId}", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listVisitReferences(@PathVariable("accountId") Integer accountId, @PathVariable("patientId") Integer patientId) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setIndicationList(referenceService.indicationList());
            listing.setPatientStatusList(referenceService.patientStatusList());
            listing.setRegimenChangeReasonList(referenceService.regimenChangeReasonList());
            listing.setVisitTypeList(referenceService.visitTypeList());
            listing.setRegimenList(stocksService.listRegimen(false));
            listing.setDosageList(referenceService.dosageList());
            listing.setDrugFormList(referenceService.drugFormList());
            listing.setDrugCategoryList(referenceService.drugCategoryList());
            listing.setDispensingUnitList(referenceService.dispensingUnitList());
            listing.setDrugTypeList(referenceService.drugTypeList());
            listing.setDrugList(stocksService.validDrugs());
            listing.setRegimenDrugList(referenceService.listRegimenDrug());
            listing.setBatchList(stocksService.listBatch(null, accountId));
            listing.setFamilyPlanningMethodChangeReasonList(referenceService.listFamilyPlanningMethodChangeReason());
            listing.setFamilyPlanningMethodList(referenceService.listFamilyPlanningMethod());
            listing.setInsulinList(referenceService.listInsulin());
            listing.setBloodSugarLevelList(referenceService.listBloodSugarLevel());

            Visit v = personService.getLastVisit(patientId);
            org.msh.fdt.model.Patient p = personService.getPatientById(patientId);
            if(p != null)
                listing.setPatientStatusId(p.getPatientStatusId());
            if(v != null) {
                listing.setHeight(v.getHeight());

                listing.setNextAppointmentDate(v.getNextAppointmentDate());
                if(v.getRegimenId() != null)
                    listing.setRegimenID(v.getRegimenId());
            }

            listing.setAge(personService.getPersonAge(patientId));
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/json/regimen/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listRegimenReferences() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setRegimenTypeList(referenceService.regimenTypeList());
            listing.setServiceTypeList(referenceService.serviceTypeList());
            listing.setRegimenStatusList(referenceService.regimenStatusList());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/json/drug/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listDrugReferences() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setGenericNameList(referenceService.genericNameList());
            listing.setDrugCategoryList(referenceService.drugCategoryList());
            listing.setDrugFormList(referenceService.drugFormList());
            listing.setDrugTypeList(referenceService.drugTypeList());
            listing.setDosageList(referenceService.dosageList());
            listing.setDispensingUnitList(referenceService.dispensingUnitList());
            listing.setServiceTypeList(referenceService.serviceTypeList());
            listing.setCdrrCategoryList(referenceService.listCDRRCategory());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/json/commoditySetup/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing commoditySetup() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setDrugList(stocksService.listDrug());
            listing.setServiceTypeList(referenceService.serviceTypeList());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function loads the reference data required for Issue/Receive form
     * @return
     */
    @RequestMapping(value="/json/issue/listReferences/{accountId}", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listIssueReceiveReferences(@PathVariable("accountId") Integer accountId) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setTransactionTypeList(referenceService.transactionTypeList());
            listing.setDrugList(stocksService.validDrugs());
            listing.setAccountList(referenceService.accountList());
            listing.setDispensingUnitList(referenceService.dispensingUnitList());
            listing.setBatchList(stocksService.listBatch(null, accountId));
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function loads the stores
     * @return
     */
    @RequestMapping(value="/json/login/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listStores() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setAccountList(referenceService.storeList());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function loads the reference data required for Stock taking form
     * @return
     */
    @RequestMapping(value="/json/stocks/listReferences/{accountId}/{userID}", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listStockTakingReferences(@PathVariable("accountId") Integer accountId, @PathVariable("userID") Integer userId) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setDrugList(stocksService.listDrug());
            listing.setBatchList(stocksService.listBatch(null, accountId));
            String sheetNo = stocksService.createStockTake(listing.getBatchList(), userId, accountId);
            listing.setSheetNo(sheetNo);
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function reloads the batch information for drugs in the specified account.
     *  This is used for reloading drugs after stock is received when dispensing drugs
     * @param accountId
     * @return
     */
    @RequestMapping(value="/json/visit/listBatch/{accountId}", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listBatchReferences(@PathVariable("accountId") Integer accountId) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setBatchList(stocksService.listBatch(null, accountId));
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function reloads the batch information for drugs in the specified account.
     *  This is used for reloading drugs after stock is received when dispensing drugs
     * @param accountId
     * @return
     */
    @RequestMapping(value="/json/viewbin/listBinCardReferences/{accountId}", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listBinCardReferences(@PathVariable("accountId") Integer accountId) {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setBatchList(stocksService.listBatch(null, accountId));
            listing.setDispensingUnitList(referenceService.dispensingUnitList());
            listing.setDrugList(stocksService.listDrug());
            listing.setTransactionTypeList(referenceService.transactionTypeList());
            listing.setAccountList(referenceService.accountList());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function reloads the batch information for drugs in the specified account.
     *  This is used for reloading drugs after stock is received when dispensing drugs
     * @param
     * @return
     */
    @RequestMapping(value="/json/reports/listReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listReportsReferences() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setDrugList(stocksService.listDrug());
            listing.setServiceTypeList(referenceService.serviceTypeList());
            listing.setPatientStatusList(referenceService.patientStatusList());
            listing.setSuppliers(referenceService.listAccounts("Supplier"));
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This function lists Drugs, Regimens and RegimenDrug Combinations
     * @param
     * @return
     */
    @RequestMapping(value="/json/regimen/drugInRegimenReference", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listDrugInRegimen() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setDrugList(stocksService.validDrugs());
            listing.setRegimenList(stocksService.listRegimen(false));
            listing.setRegimenDrugList(referenceService.listRegimenDrug());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Drug Form functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/loadSecretQuestionAnswer/{userId}", method = RequestMethod.POST)
    public @ResponseBody
    JTableResponse loadSecretQuestionAnswer(@PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            User user = loginService.getUser(userId);
            response.setResult("OK");
            User u = new User();
            u.setSecretAnswer(user.getSecretAnswer());
            u.setSecretQuestion(user.getSecretQuestion());
            System.out.println("Found " + user.getUsername());
            response.setRecord(u);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Drug Form functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/saveSecretQuestionAnswer", method = RequestMethod.POST)
    public @ResponseBody
    JTableResponse loadSecretQuestionAnswer(@RequestBody User user) {
        JTableResponse response = new JTableResponse();
        try {
            User u = loginService.getUser(user.getId());
            u.setSecretQuestion(user.getSecretQuestion());
            u.setSecretAnswer(user.getSecretAnswer());
            adminService.updateUser(u);
            response.setResult("OK");
            response.setRecord(user);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }


    /**
     *  Regimen Change Reason functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listFPMethods", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listFPMethods() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<FamilyPlanningMethod> changeReasons = referenceService.listFamilyPlanningMethod();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(changeReasons);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createFPMethods", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createFPMethods(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getPlanningMethod().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getPlanningMethod().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveFamilyPlanningMethod(dataWrapper.getPlanningMethod());
            dataWrapper.getPlanningMethod().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getPlanningMethod());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Family Planning Method
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateFPMethod", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateFPMethod(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getPlanningMethod().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateFamilyPlanningMethod(dataWrapper.getPlanningMethod());
            response.setResult("OK");
            response.setRecord(dataWrapper.getPlanningMethod());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteFPMethod/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteFPMethod(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteFamilyPlanningMethod(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }



    /**
     *  Family planning Method Change Reason functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listFPMethodChangeReason", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listFPMethodChangeReason() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<FamilyPlanningMethodChangeReason> changeReasons = referenceService.listFamilyPlanningMethodChangeReason();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(changeReasons);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createFPMethodChangeReason", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createFPMethodChangeReason(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getChangeReason().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getChangeReason().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveFamilyPlanningMethodChangeReason(dataWrapper.getChangeReason());
            dataWrapper.getChangeReason().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getChangeReason());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Family Planning Method
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateFPMethodChangeReason", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateFPMethodChangeReason(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getChangeReason().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateFamilyPlanningMethodChangeReason(dataWrapper.getChangeReason());
            response.setResult("OK");
            response.setRecord(dataWrapper.getChangeReason());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteFPMethodChangeReason/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteFPMethodChangeReason(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteFamilyPlanningMethodChangeReason(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    @RequestMapping(value="/json/listSatelliteDataReferences", method = RequestMethod.POST)
    public @ResponseBody ReferenceDataListing listSatelliteDataReferences() {
        ReferenceDataListing listing = new ReferenceDataListing();
        try {
            listing.setDrugList(stocksService.listDrug());
            listing.setServiceTypeList(referenceService.serviceTypeList());
            listing.setFacilityList(referenceService.listFacility());
            return listing;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Insulin Type functions  List/Create/Update
     *
     */
    @RequestMapping(value = "/json/listInsulinType", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listInsulinType() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<Insulin> insulinList = referenceService.listInsulin();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(insulinList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createInsulinType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createInsulinType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getInsulin().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getInsulin().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveInsulin(dataWrapper.getInsulin());
            dataWrapper.getInsulin().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getInsulin());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Visit type
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateInsulinType", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateInsulinType(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getInsulin().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateInsulin(dataWrapper.getInsulin());
            response.setResult("OK");
            response.setRecord(dataWrapper.getVisitType());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteInsulinType/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteInsulinType(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteInsulin(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Random Blood Sugar Level functions  List/Create/Update/Delete
     *
     */
    @RequestMapping(value = "/json/listRandomBloodSugarLevel", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listRandomBloodSugarLevel() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<BloodSugarLevel> bloodSugarLevelList = referenceService.listBloodSugarLevel();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(bloodSugarLevelList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createRandomBloodSugarLevel", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createRandomBloodSugarLevel(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getBloodSugarLevel().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getBloodSugarLevel().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveBloodSugarLevel(dataWrapper.getBloodSugarLevel());
            dataWrapper.getBloodSugarLevel().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getBloodSugarLevel());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    /**
     *  Updates the given Visit type
     * @param dataWrapper
     * @return
     */
    @RequestMapping(value = "/json/updateRandomBloodSugarLevel", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateRandomBloodSugarLevel(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getBloodSugarLevel().setUpdatedOn(new Timestamp(new Date().getTime()));
            referenceService.updateBloodSugarLevel(dataWrapper.getBloodSugarLevel());
            response.setResult("OK");
            response.setRecord(dataWrapper.getBloodSugarLevel());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteRandomBloodSugarLevel/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteRandomBloodSugarLevel(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteBloodSugarLevel(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }

    /**
     *  Dosage functions   List/Create/Edit/
     *
     */
    @RequestMapping(value = "/json/cdrrCatgoryList", method = RequestMethod.POST)
    public @ResponseBody
    ReferenceDataWrapper_ listCDRRCategoryList() {
        ReferenceDataWrapper_ dataWrapper = new ReferenceDataWrapper_();
        try {
            List<CdrrCategory> cdrrCatgeoryList = referenceService.listCDRRCategory();
            dataWrapper.setResult("OK");
            dataWrapper.setRecords(cdrrCatgeoryList);
            return dataWrapper;
        } catch (Exception e) {
            dataWrapper.setResult("Error");
            e.printStackTrace();
            return dataWrapper;
        }
    }

    @RequestMapping(value = "/json/createCDRRCategory", method = RequestMethod.POST)
    public @ResponseBody JTableResponse createCDRRCategory(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            dataWrapper.getCdrrCategory().setCreatedOn(new Timestamp(new Date().getTime()));
            dataWrapper.getCdrrCategory().setUuid(UUID.randomUUID().toString());
            Integer id = referenceService.saveCDRRCategory(dataWrapper.getCdrrCategory());
            dataWrapper.getCdrrCategory().setId(id);
            response.setResult("OK");
            response.setRecord(dataWrapper.getCdrrCategory());
            return response;
        } catch (Exception e) {
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/updateCDRRCategory", method = RequestMethod.POST)
    public @ResponseBody JTableResponse updateCDRRCategory(@RequestBody ReferenceDataWrapper_ dataWrapper) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.updateCDRRCategory(dataWrapper.getCdrrCategory());

            response.setResult("OK");
            response.setRecord(dataWrapper.getCdrrCategory());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            return response;
        }
    }

    @RequestMapping(value = "/json/deleteCDRRCategory/{id}/{userId}", method = RequestMethod.POST)
    public @ResponseBody JTableResponse deleteCDRRCategory(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        JTableResponse response = new JTableResponse();
        try {
            referenceService.deleteCDRRCategory(id, userId);
            response.setResult("OK");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult("ERROR");
            response.setMessage("Error deleting record.");
            return response;
        }
    }
}
