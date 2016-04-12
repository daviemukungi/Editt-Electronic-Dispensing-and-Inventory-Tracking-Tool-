package org.msh.fdt.dao;

import org.msh.fdt.model.*;
import org.msh.fdt.util.Batch;

import java.util.List;

/**
 * Created by kenny on 4/9/14.
 */
public interface StocksDAO {

    public Integer saveTransaction(Transaction transaction);

    public Integer saveTransactionItem(TransactionItem transactionItem);

    public Integer saveBatchTransactionItem(BatchTransactionItem batchTransactionItem);

    public Integer saveRegimen(Regimen regimen);

    public Integer saveDrug(Drug drug);

    public List<Regimen> listRegimen(boolean all);

    public List<Drug> listDrugs();

    public List<Drug> listDrugs(Integer serviceTypeId);

    public List<Drug> listDrugs(Integer serviceTypeId, boolean cdrr);

    public List<Drug> validDrugs();

    public List<Batch> listBatch(String sheetNo, Integer accountId, boolean expired, boolean shortDated);

    public List<Batch> listBatch();

    public List<Regimen> listRegimenObj();

    public void updateDrug(Drug drug);

    public void updateRegimen(Regimen regimen);

    public void deleteDrug(Integer drugId, Integer userId);

    public void deleteRegimen(Integer regimenId, Integer userId);

    public Integer saveStockTakeSheet(StockTakeSheet stockTakeSheet);

    public void saveStockTakeItem(StockTakeItem stockTakeItem);

    public List<StockTakeItem> getStockTakeSheetItems(String sheetNo);

    public String createStockTake(List<Batch> batchList, Integer userId, Integer accountId);

    public StockTakeSheet getStockTakeSheet(String sheetNo);

    public void updateStockTakeItem(StockTakeItem item);

    public Integer getAccountId(String accountType);

    public List<Object> getExpiredDrugs();

    public List<Transaction> listTransactions(Integer accountId, Integer drugId);

    public List<TransactionItem> listTransactionsItems(Integer accountId, Integer drugId);

    public List listDailyConsumption(String date1, String date2);

    List listDailyConsumptionDispensing(String date1, String date2, String AreaService,String PayerType);

    public Integer getTransactionTypeId(String type);

    public List<BatchTransactionItem> listBatchTransactionsItems(Integer accountId, Integer drugId);

    public void saveRegimenDrug(Integer regimenId, List drugIds);

    public List<TransactionItem> listTransactions(List<Transaction> transactions);

    public List getTotalStock(Integer accountId, Integer drugId);

}
