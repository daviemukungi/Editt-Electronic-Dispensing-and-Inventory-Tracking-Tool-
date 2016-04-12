package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by kenny on 10/27/14.
 */
@Entity
@Table(name = "patient_service_type", schema = "", catalog = "fdt")
@IdClass(PatientServiceTypePK.class)
public class PatientServiceType {
    private int patientId;
    private int serviceTypeId;
    private Timestamp timeStamp;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
    private ServiceType serviceTypeByServiceTypeId;

    @Id
    @Column(name = "patient_id", nullable = false, insertable = true, updatable = true)
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Id
    @Column(name = "service_type_id", nullable = false, insertable = true, updatable = true)
    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Id
    @Column(name = "time_stamp", nullable = false, insertable = true, updatable = true)
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Basic
    @Column(name = "start_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientServiceType that = (PatientServiceType) o;

        if (patientId != that.patientId) return false;
        if (serviceTypeId != that.serviceTypeId) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = patientId;
        result = 31 * result + serviceTypeId;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "service_type_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public ServiceType getServiceTypeByServiceTypeId() {
        return serviceTypeByServiceTypeId;
    }

    public void setServiceTypeByServiceTypeId(ServiceType serviceTypeByServiceTypeId) {
        this.serviceTypeByServiceTypeId = serviceTypeByServiceTypeId;
    }
}
