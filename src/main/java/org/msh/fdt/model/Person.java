package org.msh.fdt.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by kenny on 8/19/14.
 */
@Entity
@Table(name = "person", schema = "", catalog = "fdt")
public class Person {
    private int id;
    private String surname;
    private String firstName;
    private String otherNames;
    private String sex;
    private Timestamp dateOfBirth;
    private Byte dateOfBirthEstimated;
    private Integer birthDistrictId;
    private String legacyPk;
    private String uuid;
    private int createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private byte voided;
    private Integer voidedBy;
    private Timestamp voidedOn;
    private String voidReason;
    private User userByUpdatedBy;
    private User userByCreatedBy;
    private User userByVoidedBy;
    private Collection<User> usersById;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "surname", nullable = true, insertable = true, updatable = true, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "first_name", nullable = true, insertable = true, updatable = true, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "other_names", nullable = true, insertable = true, updatable = true, length = 45)
    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
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

    @Basic
    @Column(name = "date_of_birth_estimated", nullable = true, insertable = true, updatable = true)
    public Byte getDateOfBirthEstimated() {
        return dateOfBirthEstimated;
    }

    public void setDateOfBirthEstimated(Byte dateOfBirthEstimated) {
        this.dateOfBirthEstimated = dateOfBirthEstimated;
    }

    @Basic
    @Column(name = "birth_district_id", nullable = true, insertable = true, updatable = true)
    public Integer getBirthDistrictId() {
        return birthDistrictId;
    }

    public void setBirthDistrictId(Integer birthDistrictId) {
        this.birthDistrictId = birthDistrictId;
    }

    @Basic
    @Column(name = "legacy_pk", nullable = true, insertable = true, updatable = true, length = 45)
    public String getLegacyPk() {
        return legacyPk;
    }

    public void setLegacyPk(String legacyPk) {
        this.legacyPk = legacyPk;
    }

    @Basic
    @Column(name = "uuid", nullable = false, insertable = true, updatable = true, length = 36)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "created_by", nullable = false, insertable = true, updatable = true)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "updated_by", nullable = true, insertable = true, updatable = true)
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Basic
    @Column(name = "voided", nullable = false, insertable = true, updatable = true)
    public byte getVoided() {
        return voided;
    }

    public void setVoided(byte voided) {
        this.voided = voided;
    }

    @Basic
    @Column(name = "voided_by", nullable = true, insertable = true, updatable = true)
    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    @Basic
    @Column(name = "voided_on", nullable = true, insertable = true, updatable = true)
    public Timestamp getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Timestamp voidedOn) {
        this.voidedOn = voidedOn;
    }

    @Basic
    @Column(name = "void_reason", nullable = true, insertable = true, updatable = true, length = 255)
    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (createdBy != person.createdBy) return false;
        if (id != person.id) return false;
        if (voided != person.voided) return false;
        if (birthDistrictId != null ? !birthDistrictId.equals(person.birthDistrictId) : person.birthDistrictId != null)
            return false;
        if (createdOn != null ? !createdOn.equals(person.createdOn) : person.createdOn != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth != null) return false;
        if (dateOfBirthEstimated != null ? !dateOfBirthEstimated.equals(person.dateOfBirthEstimated) : person.dateOfBirthEstimated != null)
            return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (legacyPk != null ? !legacyPk.equals(person.legacyPk) : person.legacyPk != null) return false;
        if (otherNames != null ? !otherNames.equals(person.otherNames) : person.otherNames != null) return false;
        if (sex != null ? !sex.equals(person.sex) : person.sex != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (updatedBy != null ? !updatedBy.equals(person.updatedBy) : person.updatedBy != null) return false;
        if (updatedOn != null ? !updatedOn.equals(person.updatedOn) : person.updatedOn != null) return false;
        if (uuid != null ? !uuid.equals(person.uuid) : person.uuid != null) return false;
        if (voidReason != null ? !voidReason.equals(person.voidReason) : person.voidReason != null) return false;
        if (voidedBy != null ? !voidedBy.equals(person.voidedBy) : person.voidedBy != null) return false;
        if (voidedOn != null ? !voidedOn.equals(person.voidedOn) : person.voidedOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (otherNames != null ? otherNames.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (dateOfBirthEstimated != null ? dateOfBirthEstimated.hashCode() : 0);
        result = 31 * result + (birthDistrictId != null ? birthDistrictId.hashCode() : 0);
        result = 31 * result + (legacyPk != null ? legacyPk.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + createdBy;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updatedOn != null ? updatedOn.hashCode() : 0);
        result = 31 * result + (int) voided;
        result = 31 * result + (voidedBy != null ? voidedBy.hashCode() : 0);
        result = 31 * result + (voidedOn != null ? voidedOn.hashCode() : 0);
        result = 31 * result + (voidReason != null ? voidReason.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByUpdatedBy() {
        return userByUpdatedBy;
    }

    public void setUserByUpdatedBy(User userByUpdatedBy) {
        this.userByUpdatedBy = userByUpdatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public User getUserByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "voided_by", referencedColumnName = "id", insertable=false, updatable=false)
    public User getUserByVoidedBy() {
        return userByVoidedBy;
    }

    public void setUserByVoidedBy(User userByVoidedBy) {
        this.userByVoidedBy = userByVoidedBy;
    }

    @OneToMany(mappedBy = "personByPersonId")
    public Collection<User> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<User> usersById) {
        this.usersById = usersById;
    }
}
