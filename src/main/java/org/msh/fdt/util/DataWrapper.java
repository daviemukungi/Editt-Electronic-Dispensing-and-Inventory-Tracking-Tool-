package org.msh.fdt.util;

import org.msh.fdt.dto.*;

/**
 * Created by kenny on 4/28/14.
 */
public class DataWrapper {

    private String id;

    private int test;

    private Region region;

    private PatientSource patientSource;

    private District district;

    private ServiceType serviceType;

    private SupportingOrganization supportingOrganization;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public PatientSource getPatientSource() {
        return patientSource;
    }

    public void setPatientSource(PatientSource patientSource) {
        this.patientSource = patientSource;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public SupportingOrganization getSupportingOrganization() {
        return supportingOrganization;
    }

    public void setSupportingOrganization(SupportingOrganization supportingOrganization) {
        this.supportingOrganization = supportingOrganization;
    }
}
