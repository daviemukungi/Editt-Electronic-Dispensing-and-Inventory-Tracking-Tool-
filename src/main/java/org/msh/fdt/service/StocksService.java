package org.msh.fdt.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.msh.fdt.dao.StocksDAO;
import org.msh.fdt.model.*;
import org.msh.fdt.util.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 4/9/14.
 */
public interface StocksService {


    public Integer saveRegimen(Regimen regimen);

    public Integer saveDrug(Drug drug);

    public List<Regimen> listRegimen(boolean all);

    public List<Regimen> listRegimenObj();

    public List listDrug();

    public void saveTransactions(Transaction transaction, List<TransactionItem> transactionItems, List<BatchTransactionItem> batchTransactionItems, Integer sourceId);

    public void updateDrug(Drug drug);

    public void updateRegimen(Regimen regimen);

    public List<Batch> listBatch(String sheetNo, Integer accountId);

    public List<Batch> listBatch();

    public void deleteDrug(Integer drugId, Integer userId);

    public void deleteRegimen(Integer regimenId, Integer userId);

    public List<Drug> validDrugs();

    public void saveStockTake(StockTakeSheet stockTakeSheet, List<StockTakeItem> stockTakeItemList);

    public List<StockTakeItem> listStockItems(String sheetNo);

    public StockTakeSheet getStockTakeSheet(String sheetNo);

    public String createStockTake(List<Batch> batchList, Integer userId, Integer accountId);

    public void adjustAccounts(List<StockTakeItem> stockTakeItemList, Integer accountID, Integer userId);

    public File createStockOnHandReport(Integer accountId);

    public boolean updateStockTakeSheetItems(List<StockTakeItem> stockTakeItemList, Integer accountID, Integer userId);

    public Map<Integer, List<TransactionItem>> listTransactionItems(Integer accountId, Integer drugId);

    public Map<Integer, Transaction> listTransactions(Integer accountId, Integer drugId);

    public List listConsumption(String date1, String date2);
    public List listConsumptionDispensing(String date1, String date2, String AreaService, String PayerType);


    public Map<Integer, BatchTransactionItem> listBatchTransactionItems(Integer accountId, Integer drugId);

    public void saveRegimenDrug(Integer regimenId, List drugIds);

    public Map<Integer, TransactionItem> listOtherTransactionItems(Integer accountId, Integer drugId);

    public HSSFWorkbook createStockTakeExcel(Integer accountId, Integer userId);

    public List totalStock(Integer accountId, Integer drugId);

    public List<Batch> listDrugBatch(Integer accountId, Integer drugId);
}
