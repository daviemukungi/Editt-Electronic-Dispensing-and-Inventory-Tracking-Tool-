package org.msh.fdt.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.msh.fdt.dao.LoginDAO;
import org.msh.fdt.dao.ReferenceDAO;
import org.msh.fdt.dao.ReportsDAO;
import org.msh.fdt.dao.StocksDAO;
import org.msh.fdt.model.*;
import org.msh.fdt.util.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by kenny on 4/9/14.
 */

@Service
public class StocksServiceImpl implements StocksService {

    @Autowired
    private StocksDAO stocksDAO;

    @Autowired
    private ReferenceDAO referenceDAO;

    @Autowired
    private ReportsDAO reportsDAO;

    @Autowired
    private LoginDAO loginDAO;

    @Override
    @Transactional
    public Integer saveRegimen(Regimen regimen) {
        return stocksDAO.saveRegimen(regimen);
    }

    @Override
    @Transactional
    public Integer saveDrug(Drug drug) {
        return stocksDAO.saveDrug(drug);
    }

    @Override
    @Transactional
    public List<Regimen> listRegimen(boolean all) {
        return stocksDAO.listRegimen(all);
    }

    @Override
    @Transactional
    public void saveTransactions(Transaction transaction, List<TransactionItem> transactionItems, List<BatchTransactionItem> batchTransactionItems, Integer sourceId) {
        Integer transactionId = stocksDAO.saveTransaction(transaction);
        for(int i = 0; i < transactionItems.size(); i++) {
            TransactionItem txItemIn = transactionItems.get(i);
            txItemIn.setCreatedOn(new Timestamp(new Date().getTime()));
            txItemIn.setUuid(UUID.randomUUID().toString());
            txItemIn.setTransactionId(transactionId);
            Integer itemId = stocksDAO.saveTransactionItem(txItemIn);

            TransactionItem txItemOut = new TransactionItem();
            txItemOut.setCreatedOn(new Timestamp(new Date().getTime()));
            txItemOut.setUuid(UUID.randomUUID().toString());
            txItemOut.setTransactionId(transactionId);
            txItemOut.setUnitsOut(txItemIn.getUnitsIn());
            txItemOut.setBatchNo(txItemIn.getBatchNo());
            txItemOut.setAccountId(sourceId);
            txItemOut.setDrugId(txItemIn.getDrugId());
            txItemOut.setCreatedBy(txItemIn.getCreatedBy());
            stocksDAO.saveTransactionItem(txItemOut);

            if(batchTransactionItems != null) {
                BatchTransactionItem btItem = batchTransactionItems.get(i);
                btItem.setCreatedOn(new Timestamp(new Date().getTime()));
                btItem.setUuid(UUID.randomUUID().toString());
                btItem.setTransactionItemId(itemId);
                stocksDAO.saveBatchTransactionItem(btItem);
            }
        }
    }

    @Override
    @Transactional
    public void saveStockTake(StockTakeSheet stockTakeSheet, List<StockTakeItem> stockTakeItemList) {
        Integer id = stocksDAO.saveStockTakeSheet(stockTakeSheet);
        for(int i = 0; i < stockTakeItemList.size(); i++) {
            StockTakeItem item = stockTakeItemList.get(i);
            item.setCreatedOn(new Timestamp(new Date().getTime()));
            item.setUuid(UUID.randomUUID().toString());
            item.setStockTakeSheetId(id);
            stocksDAO.saveStockTakeItem(item);
        }
    }

    @Override
    @Transactional
    public List listDrug() {
        return stocksDAO.listDrugs();
    }

    @Override
    @Transactional
    public List<Drug> validDrugs() {
        return stocksDAO.validDrugs();
    }

    @Override
    @Transactional
    public List<Regimen> listRegimenObj() {
        return stocksDAO.listRegimenObj();
    }

    @Override
    @Transactional
    public void updateDrug(Drug drug) {
        stocksDAO.updateDrug(drug);
    }

    @Override
    @Transactional
    public void updateRegimen(Regimen regimen) {
        stocksDAO.updateRegimen(regimen);
    }

    @Override
    @Transactional
    public List<Batch> listBatch(String sheetNo, Integer accountId) {
        return stocksDAO.listBatch(sheetNo, accountId, false, false);
    }

    @Override
    @Transactional
    public List<Batch> listBatch() {
        return stocksDAO.listBatch();
    }

    @Override
    @Transactional
    public List<StockTakeItem> listStockItems(String sheetNo) {
        return stocksDAO.getStockTakeSheetItems(sheetNo);
    }

    @Override
    @Transactional
    public void deleteDrug(Integer drugId, Integer userId) {
        stocksDAO.deleteDrug(drugId, userId);
    }

    @Override
    @Transactional
    public void deleteRegimen(Integer regimenId, Integer userId) {
        stocksDAO.deleteRegimen(regimenId, userId);
    }

    @Override
    @Transactional
    public String createStockTake(List<Batch> batchList, Integer userId, Integer accountId) {
        return stocksDAO.createStockTake(batchList, userId, accountId);
    }

    @Override
    @Transactional
    public StockTakeSheet getStockTakeSheet(String sheetNo) {
        return stocksDAO.getStockTakeSheet(sheetNo);
    }


    @Override
    @Transactional
    public void adjustAccounts(List<StockTakeItem> stockTakeItemList, Integer accountID, Integer userID) {

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String d = df.format(Calendar.getInstance().getTime());
        for(int i = 0; i < stockTakeItemList.size(); i++) {
            StockTakeItem takeItem = stockTakeItemList.get(i);
            TransactionItem txItemSys = new TransactionItem();
            if(takeItem.getPacks() == null) {
               continue;
            }
            BigDecimal physicalCount = BigDecimal.valueOf((takeItem.getPacks() * Integer.parseInt(takeItem.getPackSize())) + takeItem.getLooseQty());
            BigDecimal variance = physicalCount.subtract(takeItem.getSystemCount());
            //BigDecimal unitsChange = BigDecimal.ZERO;

            Integer transactionTypeId = null;

            TransactionItem txItemPhy = new TransactionItem();

            if(variance.compareTo(BigDecimal.ZERO) == 0) { //There is no difference btwn system/physical count
                continue; // We skip this entry, no adjustments to be made
            } else if(variance.compareTo(BigDecimal.ZERO) < 0) { //Physical count is less than system count
                txItemPhy.setUnitsIn(variance.multiply(new BigDecimal(-1))); // Add Physical count
                txItemSys.setUnitsOut(variance.multiply(new BigDecimal(-1)));  //Reduce System count
                TransactionType type = referenceDAO.getTransactionType("Ajustment (-)");
                transactionTypeId = type.getId();
            } else if(variance.compareTo(BigDecimal.ZERO) > 0) { //Physical  count is more than system count
                txItemPhy.setUnitsOut(variance);  // Reduce Physical count
                txItemSys.setUnitsIn(variance); // Add System count
                TransactionType type = referenceDAO.getTransactionType("Ajustment (+)");
                transactionTypeId = type.getId();
            }

            Transaction tx = new Transaction();
            tx.setCreatedOn(new Timestamp(new Date().getTime()));
            tx.setUuid(UUID.randomUUID().toString());
            tx.setCreatedBy(userID);
            tx.setDate(new Timestamp(new Date().getTime()));
            tx.setTransactionTypeId(transactionTypeId);
            tx.setComments("Stock Taking " + d);
            Integer txId = stocksDAO.saveTransaction(tx);

            Integer stockTakeAccountId = stocksDAO.getAccountId("STOCK TAKE");

            txItemPhy.setBatchNo(takeItem.getBatchNo());
            txItemPhy.setDrugId(takeItem.getDrugId());
            txItemPhy.setAccountId(stockTakeAccountId);
            txItemPhy.setTransactionId(txId);

            txItemPhy.setUuid(UUID.randomUUID().toString());
            txItemPhy.setCreatedBy(userID);
            txItemPhy.setCreatedOn(new Timestamp(new Date().getTime()));
            stocksDAO.saveTransactionItem(txItemPhy);


            txItemSys.setAccountId(accountID);
            txItemSys.setTransactionId(txId);

            txItemSys.setDrugId(takeItem.getDrugId());
            txItemSys.setBatchNo(takeItem.getBatchNo());
            txItemSys.setUuid(UUID.randomUUID().toString());
            txItemSys.setCreatedOn(new Timestamp(new Date().getTime()));
            txItemSys.setCreatedBy(userID);
            stocksDAO.saveTransactionItem(txItemSys);

            stocksDAO.updateStockTakeItem(takeItem);
        }
    }

    @Override
    @Transactional
    public File createStockOnHandReport(Integer accountId) {
        List<Batch> batchList = stocksDAO.listBatch(null, accountId, false, false);
        List<Object[]> data = reportsDAO.execute("SELECT d.name, du.name as `du`, d.pack_size, (COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, d.reorder_point, d.id FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id LEFT JOIN dispensing_unit du ON du.id = d.dispensing_unit_id where ti.account_id = " + accountId + " AND d.voided = 0 group by drug_id having diff > 0");
        String fileName = "stock-on-hand.pdf";

        List<Object[]> unitsInCost = reportsDAO.execute("(SELECT SUM(ti.total_cost), ti.drug_id FROM transaction_item ti WHERE ti.account_id = " + accountId + " AND ti.units_out IS NULL group by drug_id)");
        Map<Integer, Integer> costs = new HashMap<Integer, Integer>();
        for(Object[] o : unitsInCost) {
            if(o[0] != null) {
                Integer drugId = (Integer)o[1];
                costs.put(drugId, ((BigDecimal) o[0]).intValue());
            }
        }
        List<Object[]> unitsOutCost = reportsDAO.execute("(SELECT SUM(ti.total_cost), ti.drug_id FROM transaction_item ti WHERE ti.account_id = " + accountId + " AND ti.units_in IS NULL group by drug_id)");

        for(Object[] o : unitsOutCost) {
            Integer drugId = (Integer)o[1];
            if(o[0] == null) {
                continue;
            }
            if(costs.get(drugId) != null) {
                Integer cost = costs.get(drugId);
                cost = cost - ((BigDecimal)o[0]).intValue();
                costs.put(drugId, cost);
            } else {
                costs.put(drugId, ((BigDecimal) o[0]).intValue());
            }
        }
        File file = new File(fileName);
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph rtype = new Paragraph("Report on Inventory Status", headerStyle);
            rtype.setAlignment(Element.ALIGN_CENTER);
            rtype.setSpacingAfter(6);
            header.add(rtype);

            Paragraph date = new Paragraph(df.format(new Date()), headerStyle);

            date.setAlignment(Element.ALIGN_CENTER);
            header.add(date);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(8);
            table.setWidths(new int[]{4, 1, 1, 1, 1, 1, 1, 1});
            table.setWidthPercentage(100);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Commodities", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Packsize", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Stock in Units", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("SOH in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Safety stock", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Stock status", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Cost", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            for(int i = 0; i < data.size(); i++) {
                Object[] d = data.get(i);

                cell = new PdfPCell(new Phrase(String.valueOf(d[0]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);

                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(d[1]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(d[2]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);


                if(d[3] != null) {
                    BigDecimal stocks = (BigDecimal)d[3];
                    cell = new PdfPCell(new Phrase(String.valueOf(stocks.intValue()), headerStyle));
                } else {
                    cell = new PdfPCell(new Phrase("0", headerStyle));
                }

                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                Double packs = 0.0;
                if(d[2] == null || String.valueOf(d[2]).equalsIgnoreCase("null")) {

                } else {
                    Double packSize = Double.parseDouble(String.valueOf(d[2]));
                    Double stock = Double.parseDouble(String.valueOf(d[3]));

                    if (packSize != null && stock != null) {
                        packs = stock / packSize;
                    }
                }
                cell = new PdfPCell(new Phrase(String.valueOf(packs.intValue()), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                Double reorderPoint = 0.0;
                if(d[4] != null )
                    reorderPoint = Double.parseDouble(String.valueOf(d[4]));
                cell = new PdfPCell(new Phrase(String.valueOf(reorderPoint.intValue()), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("0", headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                Integer drugId = (Integer)d[5];
                Integer cost = 0;
                if(costs.get(drugId) != null) {
                    cost = costs.get(drugId);
                }
                cell = new PdfPCell(new Phrase(String.valueOf(cost), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateStockTakeSheetItems(List<StockTakeItem> stockTakeItemList, Integer accountID, Integer userId) {
        for(int i = 0; i < stockTakeItemList.size(); i++) {
            StockTakeItem takeItem = stockTakeItemList.get(i);
            stocksDAO.updateStockTakeItem(takeItem);
        }
        return true;
    }

    /**
     *  Creates a Map Transactions with Transaction id as key
     * @return
     */
    @Override
    @Transactional
    public Map<Integer, Transaction> listTransactions(Integer accountId, Integer drugId) {
        List<Transaction> tx = stocksDAO.listTransactions(accountId, drugId);
        Map<Integer, Transaction> transactionMap = new HashMap<Integer, Transaction>();
        Iterator iterator = tx.iterator();
        while (iterator.hasNext()) {
            Transaction t = (Transaction)iterator.next();
            Transaction nt = new Transaction();
            nt.setId(t.getId());

            nt.setCreator(loginDAO.getUser(t.getCreatedBy()).getUsername());// Added By Kelvin
            nt.setTransactionTypeId(t.getTransactionTypeId());
            nt.setReferenceNo(t.getReferenceNo());
            nt.setDate(t.getDate());
            nt.setComments(t.getComments());
            transactionMap.put(t.getId(), nt);
        }
        return transactionMap;
    }

    /**
     *  Creates a Map of Transactions sorted by Drug ID
     * @param accountId
     * @return
     */
    @Override
    @Transactional
    public Map<Integer, List<TransactionItem>> listTransactionItems(Integer accountId, Integer drugId) {
        List<TransactionItem> transactionItems = stocksDAO.listTransactionsItems(accountId,drugId);
        Map<Integer, List<TransactionItem>> trListMap = new HashMap<Integer, List<TransactionItem>>();
        Iterator iterator = transactionItems.iterator();
        while (iterator.hasNext()) {
            TransactionItem item = (TransactionItem)iterator.next();
            List<TransactionItem> items = trListMap.get(item.getDrugId());
            if(items == null) {
                items = new ArrayList<TransactionItem>();
            }
            items.add(item);
            trListMap.put(item.getDrugId(), items);

        }
        return trListMap;
    }


    /**
     *  Creates TransactionItems for the given drug which were for the store with the ID other than the given
     *  @param accountId
     * @param accountId
     * @param drugId
     * @return
     */
    @Override
    @Transactional
    public Map<Integer, TransactionItem> listOtherTransactionItems(Integer accountId, Integer drugId) {
        List<Transaction> transactions = stocksDAO.listTransactions(accountId, drugId);
        List<TransactionItem> transactionItems = stocksDAO.listTransactions(transactions);
        Map<Integer, TransactionItem> trListMap = new HashMap<Integer, TransactionItem>();

        Iterator iterator = transactionItems.iterator();
        while (iterator.hasNext()) {
            TransactionItem ti = (TransactionItem)iterator.next();
            if(ti.getAccountId() != accountId) {
                TransactionItem item = new TransactionItem();
                item.setUnitsIn(ti.getUnitsIn());
                item.setUnitsOut(ti.getUnitsOut());
                item.setAccountId(ti.getAccountId());
                trListMap.put(ti.getTransactionId(), item);
            }
        }

        return trListMap;
    }

    /**
     *  Creates a Map of Transactions sorted by Drug ID
     * @param accountId
     * @return
     */
    @Override
    @Transactional
    public Map<Integer, BatchTransactionItem> listBatchTransactionItems(Integer accountId, Integer drugId) {
        List<BatchTransactionItem> transactionItems = stocksDAO.listBatchTransactionsItems(accountId,drugId);
        Map<Integer, BatchTransactionItem> trListMap = new HashMap<Integer, BatchTransactionItem>();
        Iterator iterator = transactionItems.iterator();
        while (iterator.hasNext()) {
            BatchTransactionItem item = (BatchTransactionItem)iterator.next();
            trListMap.put(item.getTransactionItemId(), item);

        }
        return trListMap;
    }

    /**
     *  Returns a list of all the drugs which have been dispensed between the given dates.
     * @param date1
     * @param date2
     * @return
     */
    @Override
    @Transactional
    public List listConsumption( String date1, String date2) {
        return stocksDAO.listDailyConsumption( date1, date2);
    }

    /**
     *  Returns a list of all the drugs which have been dispensed between the given dates.
     * @param date1
     * @param date2
     * @param AreaService
     * @return
     */
    @Override
    @Transactional
    public List listConsumptionDispensing( String date1, String date2, String AreaService, String PayerType) {
        return stocksDAO.listDailyConsumptionDispensing(date1, date2, AreaService, PayerType);
    }



    @Override
    @Transactional
    public void saveRegimenDrug(Integer regimenId, List drugIds) {
        stocksDAO.saveRegimenDrug(regimenId, drugIds);
    }

    @Override
    @Transactional
    public HSSFWorkbook createStockTakeExcel(Integer accountId, Integer userId) {
        HSSFWorkbook myWorkBook = new HSSFWorkbook();
        HSSFSheet mySheet = null;
        HSSFRow myRow = null;
        HSSFCell myCell = null;
        HSSFCellStyle style = myWorkBook.createCellStyle();
        CellStyle headerStyle = myWorkBook.createCellStyle();
        headerStyle.setAlignment(CellStyle.ALIGN_JUSTIFY);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        List<Batch> batchList = listBatch(null, accountId);
        String sheetNo = createStockTake(batchList, userId, accountId);
        List<StockTakeItem> stockTakeItemList = stocksDAO.getStockTakeSheetItems(sheetNo);
        mySheet = myWorkBook.createSheet("Stock take");

        myRow = mySheet.createRow(0);
        myCell = myRow.createCell(0);
        myCell.setCellValue("Facility Name");
        myCell.setCellStyle(headerStyle);
        mySheet.setColumnWidth(0, (short) ((30 * 8) / ((double) 1 / 20)));
        mySheet.setColumnWidth(2, (short) ((30 * 8) / ((double) 1 / 20)));
        mySheet.setColumnWidth(3, (short) ((30 * 8) / ((double) 1 / 20)));
        mySheet.setColumnWidth(4, (short) ((30 * 8) / ((double) 1 / 20)));

        myCell = myRow.createCell(1);
        myCell.setCellValue(getFacilityName());
        myCell.setCellStyle(headerStyle);

        myRow = mySheet.createRow(1);

        myCell = myRow.createCell(0);
        myCell.setCellValue("Stock Date");

        Calendar c = Calendar.getInstance();
        myCell = myRow.createCell(1);
        myCell.setCellValue(df.format(c.getTime()));

        myCell = myRow.createCell(2);
        myCell.setCellValue("Counted by");

        myCell = myRow.createCell(3);
        myCell.setCellValue(loginDAO.getUser(userId).getUsername());

        myRow = mySheet.createRow(2);

        myCell = myRow.createCell(0);
        myCell.setCellValue("Location");

        myCell = myRow.createCell(1);
        myCell.setCellValue(referenceDAO.getAccount(accountId).getName());

        myCell = myRow.createCell(2);
        myCell.setCellValue("Sheet no");

        myCell = myRow.createCell(3);
        myCell.setCellValue(sheetNo);

        myRow = mySheet.createRow(3);

        myCell = myRow.createCell(0);
        myCell.setCellValue("Details");

        myRow = mySheet.createRow(4);

        myCell = myRow.createCell(0);
        myCell.setCellValue("");

        myCell = myRow.createCell(1);
        myCell.setCellValue("Item( Name - Strength - Units)");
        mySheet.setColumnWidth(1, (short) ((80 * 8) / ((double) 1 / 20)));

        myCell = myRow.createCell(2);
        myCell.setCellValue("System count");

        myCell = myRow.createCell(3);
        myCell.setCellValue("Batch No.");

        myCell = myRow.createCell(4);
        myCell.setCellValue("Expiry Date");

        myCell = myRow.createCell(5);
        myCell.setCellValue("Packs");

        myCell = myRow.createCell(6);
        myCell.setCellValue("Loose Qty");

        for(int i = 0; i < batchList.size(); i++) {
            Batch b = batchList.get(i);
            myRow = mySheet.createRow((i + 5));

            myCell = myRow.createCell(0);
            myCell.setCellValue(i + 1);

            myCell = myRow.createCell(1);
            myCell.setCellValue(b.getDrugName());

            myCell = myRow.createCell(2);
            myCell.setCellType(Cell.CELL_TYPE_NUMERIC);
            myCell.setCellValue(b.getStockInBatch().intValue());

            myCell = myRow.createCell(3);
            myCell.setCellType(Cell.CELL_TYPE_STRING);
            myCell.setCellValue(b.getBatch_no());

            myCell = myRow.createCell(4);
            myCell.setCellValue(df.format(b.getExpiry_date()));

            myCell = myRow.createCell(5);
            myCell.setCellValue("");
        }
        return myWorkBook;
    }

    public String getFacilityName() {
        Property facilityName = referenceDAO.getProperty("facility_name");
        if(facilityName != null) {
            return facilityName.getValue();
        } else {
            return "";
        }
    }

    @Override
    @Transactional
    public List totalStock(Integer accountId, Integer drugId) {
        return stocksDAO.getTotalStock(accountId, drugId);
    }

    @Override
    @Transactional
    public List<Batch> listDrugBatch(Integer accountId, Integer drugId) {
//        String sql = "SELECT d.name, ti.drug_id, ti.batch_no, ti.account_id,(COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, bt.date_of_expiry, bt.pack_size, sto.looseQty, sto.packs, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id LEFT JOIN batch_transaction_item bt ON bt.transaction_item_id = ti.id JOIN stock_take_item sto ON sto.drug_id = ti.drug_id WHERE looseQty is not null and ti.drug_id = " + drugId;
        String sql = "SELECT d.name, ti.drug_id, ti.batch_no, ti.account_id,(COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, bt.date_of_expiry, bt.pack_size, sto.looseQty, sto.packs, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id LEFT JOIN batch_transaction_item bt ON bt.transaction_item_id = ti.id JOIN stock_take_item sto ON sto.drug_id = ti.drug_id WHERE sto.created_on=(SELECT max(created_on) FROM stock_take_item) and ti.drug_id = " + drugId;
        if(accountId != -1) {
            sql += " AND ti.account_id = " + accountId;
        }
        sql += " group by ti.batch_no HAVING diff > 0";
        List data = reportsDAO.execute(sql);
        Iterator iterator = data.iterator();
        List<Batch> batchList = new ArrayList<Batch>();
        while(iterator.hasNext()) {
            Object[] d = (Object[])iterator.next();
            Batch batch = new Batch();
            batch.setDrugId(drugId);
            batch.setDrugName((String)d[0]);
            batch.setBatch_no((String)d[2]);
            batch.setStockInBatch((BigDecimal)d[4]);
            batch.setExpiry_date((Date)d[5]);
            batch.setPackSize((Integer)d[6]);

            //Kelvin
            batch.setLooseQty((Integer)d[7]);
            batch.setPacks((Integer)d[8]);
            if(batch.getLooseQty()!=null && batch.getPacks()!=null && batch.getPackSize()!=null) {
                batch.setTotalQty((batch.getPackSize() * batch.getPacks()) + batch.getLooseQty());
            }else {
                batch.setTotalQty(0);
            }
            batchList.add(batch);
        }
        return batchList;
    }
}
