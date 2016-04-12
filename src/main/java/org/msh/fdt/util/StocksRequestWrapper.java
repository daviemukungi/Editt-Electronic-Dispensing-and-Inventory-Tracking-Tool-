package org.msh.fdt.util;

import org.msh.fdt.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 4/9/14.
 */
public class StocksRequestWrapper {

    private Transaction transaction;

    private List<TransactionItem> transactionItemList;

    private List<BatchTransactionItem> batchTransactionItemList;

    private Regimen regimen;

    private Drug drug;

    private StockTakeSheet stockTakeSheet;

    private List<StockTakeItem> stockTakeItemList;

    private List<Batch> batchList;

    private String sheetNo;

    private List<Drug> drugList;

    private List totalStock;

    private Map<Integer, List<TransactionItem>> transactionItemsListMap;

    private Map<Integer, Transaction> transactionMap;

    private Map<Integer, BatchTransactionItem> batchTransactionItemMap;

    private Map<Integer, TransactionItem> destinationTransactionItems;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public List<TransactionItem> getTransactionItemList() {
        return transactionItemList;
    }

    public void setTransactionItemList(List<TransactionItem> transactionItemList) {
        this.transactionItemList = transactionItemList;
    }

    public List<BatchTransactionItem> getBatchTransactionItemList() {
        return batchTransactionItemList;
    }

    public void setBatchTransactionItemList(List<BatchTransactionItem> batchTransactionItemList) {
        this.batchTransactionItemList = batchTransactionItemList;
    }

    public StockTakeSheet getStockTakeSheet() {
        return stockTakeSheet;
    }

    public void setStockTakeSheet(StockTakeSheet stockTakeSheet) {
        this.stockTakeSheet = stockTakeSheet;
    }

    public List<StockTakeItem> getStockTakeItemList() {
        return stockTakeItemList;
    }

    public void setStockTakeItemList(List<StockTakeItem> stockTakeItemList) {
        this.stockTakeItemList = stockTakeItemList;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    public Map<Integer, List<TransactionItem>> getTransactionItemsListMap() {
        return transactionItemsListMap;
    }

    public void setTransactionItemsListMap(Map<Integer, List<TransactionItem>> transactionItemsListMap) {
        this.transactionItemsListMap = transactionItemsListMap;
    }

    public Map<Integer, Transaction> getTransactionMap() {
        return transactionMap;
    }

    public void setTransactionMap(Map<Integer, Transaction> transactionMap) {
        this.transactionMap = transactionMap;
    }

    public Map<Integer, BatchTransactionItem> getBatchTransactionItemMap() {
        return batchTransactionItemMap;
    }

    public void setBatchTransactionItemMap(Map<Integer, BatchTransactionItem> batchTransactionItemMap) {
        this.batchTransactionItemMap = batchTransactionItemMap;
    }

    public Map<Integer, TransactionItem> getDestinationTransactionItems() {
        return destinationTransactionItems;
    }

    public void setDestinationTransactionItems(Map<Integer, TransactionItem> destinationTransactionItems) {
        this.destinationTransactionItems = destinationTransactionItems;
    }

    public List getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(List totalStock) {
        this.totalStock = totalStock;
    }
}
