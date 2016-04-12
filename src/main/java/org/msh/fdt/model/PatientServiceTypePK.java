package org.msh.fdt.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by kenny on 10/27/14.
 */
public class PatientServiceTypePK implements Serializable {
    private int patientId;
    private int serviceTypeId;
    private Timestamp timeStamp;

    @Column(name = "patient_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Column(name = "service_type_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Column(name = "time_stamp", nullable = false, insertable = true, updatable = true)
    @Id
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientServiceTypePK that = (PatientServiceTypePK) o;

        if (patientId != that.patientId) return false;
        if (serviceTypeId != that.serviceTypeId) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = patientId;
        result = 31 * result + serviceTypeId;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        return result;
    }
}
