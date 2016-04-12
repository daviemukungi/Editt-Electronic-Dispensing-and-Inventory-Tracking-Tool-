package org.msh.fdt.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by kenny on 3/17/15.
 */
@Entity
@Table(name = "patient_view", schema = "", catalog = "fdt")
public class PatientView {
    private int id;
    private int serviceTypeId;
    private String patientStatus;
    private Integer patientStatusId;
    private Timestamp visitDate;
    private String serviceType;
    private String sex;
    private Timestamp dateOfBirth;

    @Basic
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "service_type_id", nullable = false, insertable = true, updatable = true)
    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Basic
    @Column(name = "patient_status", nullable = true, insertable = true, updatable = true, length = 45)
    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }

    @Basic
    @Column(name = "patient_status_id", nullable = true, insertable = true, updatable = true)
    public Integer getPatientStatusId() {
        return patientStatusId;
    }

    public void setPatientStatusId(Integer patientStatusId) {
        this.patientStatusId = patientStatusId;
    }

    @Basic
    @Column(name = "visit_date", nullable = true, insertable = true, updatable = true)
    public Timestamp getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }

    @Basic
    @Column(name = "service_type", nullable = true, insertable = true, updatable = true, length = 45)
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "sex", nullable = true, insertable = true, updatable = true, length = 6)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true, insertable = true, updatable = true)
    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatientView that = (PatientView) o;

        if (id != that.id) return false;
        if (serviceTypeId != that.serviceTypeId) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (patientStatus != null ? !patientStatus.equals(that.patientStatus) : that.patientStatus != null)
            return false;
        if (patientStatusId != null ? !patientStatusId.equals(that.patientStatusId) : that.patientStatusId != null)
            return false;
        if (serviceType != null ? !serviceType.equals(that.serviceType) : that.serviceType != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (visitDate != null ? !visitDate.equals(that.visitDate) : that.visitDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + serviceTypeId;
        result = 31 * result + (patientStatus != null ? patientStatus.hashCode() : 0);
        result = 31 * result + (patientStatusId != null ? patientStatusId.hashCode() : 0);
        result = 31 * result + (visitDate != null ? visitDate.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}
