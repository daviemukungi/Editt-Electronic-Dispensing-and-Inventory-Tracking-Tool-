package org.msh.fdt.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kenny on 5/13/14.
 */
public class Batch {
    private String batch_no;
    private Date expiry_date;
    private BigDecimal stockInBatch = BigDecimal.ZERO;
    private Integer drugId;
    private Integer packSize;
    private Integer numberOfPacks;
    private String dispensingUnitName;
    private String drugName;
    private Integer daysFromExpiry;
    private Integer looseQty;
    private Integer packs;
    private Integer totalQty;

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public BigDecimal getStockInBatch() {
        return stockInBatch;
    }

    public void setStockInBatch(BigDecimal stockInBatch) {
        this.stockInBatch = stockInBatch;
    }

    public Integer getPackSize() {
        return packSize;
    }

    public void setPackSize(Integer packSize) {
        this.packSize = packSize;
    }

    public String getDispensingUnitName() {
        return dispensingUnitName;
    }

    public void setDispensingUnitName(String dispensingUnitName) {
        this.dispensingUnitName = dispensingUnitName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getNumberOfPacks() {
        return numberOfPacks;
    }

    public void setNumberOfPacks(Integer numberOfPacks) {
        this.numberOfPacks = numberOfPacks;
    }

    public void setDaysFromExpiry(Integer daysFromExpiry) {
        this.daysFromExpiry = daysFromExpiry;
    }

    public Integer getDaysFromExpiry() {
        return daysFromExpiry;
    }

    //Kelvin
    public Integer getLooseQty() {

        return looseQty;
    }

    public void setLooseQty(Integer looseQty) {

        this.looseQty = looseQty;
    }
    public Integer getPacks() {

        return packs;
    }

    public void setPacks(Integer packs) {

        this.packs = packs;
    }
    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }
}
