package org.msh.fdt.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.msh.fdt.model.*;
import org.msh.fdt.util.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kenny on 4/9/14.
 */
@Repository
public class StocksDAOImpl implements StocksDAO{

    @Autowired
    private SessionFactory sessionFactory;



    @Override
    public Integer saveTransaction(Transaction transaction) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(transaction);
        return id;
    }

    @Override
    public Integer saveTransactionItem(TransactionItem transactionItem) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(transactionItem);
        return id;
    }

    @Override
    public Integer saveRegimen(Regimen regimen) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(regimen);
        return id;
    }



    @Override
    public Integer saveDrug(Drug drug) {
        if(drug.getCdrrIndex() == null){
            drug.setCdrrIndex(BigDecimal.valueOf(1.00));
        }
        Integer id = (Integer)sessionFactory.getCurrentSession().save(drug);
        return id;
    }

    @Override
    public Integer saveStockTakeSheet(StockTakeSheet stockTakeSheet) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(stockTakeSheet);
        return id;
    }

    @Override
    public void saveStockTakeItem(StockTakeItem stockTakeItem) {
        sessionFactory.getCurrentSession().save(stockTakeItem);
    }

    /**
     *  List regimen with id and Name property only.
     *  This list is used for reference when adding other data
     * @return
     */
    @Override
    public List<Regimen> listRegimen(boolean all) {
        int visible = 0;
        Criteria c;
        if(!all) {
            c = sessionFactory.getCurrentSession().createCriteria(Regimen.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("name"), "name").add(Projections.property("serviceTypeId"), "serviceTypeId")).add(Restrictions.eq("visible", (byte)1)).setResultTransformer(Transformers.aliasToBean(Regimen.class));
        } else
            c = sessionFactory.getCurrentSession().createCriteria(Regimen.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("name"), "name").add(Projections.property("serviceTypeId"), "serviceTypeId")).setResultTransformer(Transformers.aliasToBean(Regimen.class));
        List<Regimen> regimenList = c.list();
        return regimenList;
    }

    @Override
    public Integer saveBatchTransactionItem(BatchTransactionItem batchTransactionItem) {
        Integer id = (Integer)sessionFactory.getCurrentSession().save(batchTransactionItem);
        return id;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Drug> listDrugs() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Drug.class, "drug").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("dhisId"), "dhisId").add(Projections.property("cdrrCategoryId"), "cdrrCategoryId").add(Projections.property("tracer"), "tracer").add(Projections.property("name"), "name").add(Projections.property("strength"), "strength").add(Projections.property("packSize"), "packSize").add(Projections.property("genericNameId"), "genericNameId").add(Projections.property("drugCategoryId"), "drugCategoryId").add(Projections.property("drugTypeId"), "drugTypeId").add(Projections.property("dispensingUnitId"), "dispensingUnitId").add(Projections.property("drugFormId"), "drugFormId").add(Projections.property("dosageId"), "dosageId").add(Projections.property("voided"), "voided").add(Projections.property("serviceTypeId"), "serviceTypeId")).addOrder(Order.asc("name")).setResultTransformer(Transformers.aliasToBean(Drug.class));

        List<Drug> drugList = c.list();
        return drugList;
    }

    /**
     *  Lists Commodities which are in use
     * @return
     */
    @Override
    public List<Drug> validDrugs() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Drug.class, "drug").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("dhisId"), "dhisId").add(Projections.property("name"), "name").add(Projections.property("strength"), "strength").add(Projections.property("packSize"), "packSize").add(Projections.property("quantity"), "quantity").add(Projections.property("duration"), "duration").add(Projections.property("genericNameId"), "genericNameId").add(Projections.property("drugCategoryId"), "drugCategoryId").add(Projections.property("tracer"), "tracer").add(Projections.property("drugTypeId"), "drugTypeId").add(Projections.property("dispensingUnitId"), "dispensingUnitId").add(Projections.property("drugFormId"), "drugFormId").add(Projections.property("dosageId"), "dosageId").add(Projections.property("voided"), "voided").add(Projections.property("serviceTypeId"), "serviceTypeId")).addOrder(Order.asc("name")).add(Restrictions.eq("voided", (byte) 0)).setResultTransformer(Transformers.aliasToBean(Drug.class));

        List<Drug> drugList = c.list();
        return drugList;
    }

    /**
     *  Creates list of batches.
     *  It fetches data from {@link org.msh.fdt.model.BatchTransactionItem}, {@link org.msh.fdt.model.Transaction} and
     *  {@link org.msh.fdt.model.TransactionItem}
     * @return
     */
    @Override
    public List<Batch> listBatch() {
        return listBatch(null, null, false, false);
    }

    /**
     *  Lists regimens with all the properties.
     * @return
     */
    public List<Regimen> listRegimenObj() {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Regimen.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("name"), "name")
        .add(Projections.property("line"), "line").add(Projections.property("code"), "code").add(Projections.property("regimenTypeId"), "regimenTypeId").add(Projections.property("serviceTypeId"), "serviceTypeId")
        .add(Projections.property("regimenStatusId"), "regimenStatusId").add(Projections.property("visible"), "visible").add(Projections.property("comments"), "comments")).setResultTransformer(Transformers.aliasToBean(Regimen.class));
        List<Regimen> regimenList = c.list();
        return regimenList;
    }

    @Override
    public void updateDrug(Drug drug) {
        Drug dr = (Drug)sessionFactory.getCurrentSession().get(Drug.class, drug.getId());
        if(dr != null) {
            dr.setName(drug.getName());
            dr.setStrength(drug.getStrength());
            dr.setGenericNameId(drug.getGenericNameId());
            dr.setKemsaName(drug.getKemsaName());
            dr.setSca1Name(drug.getSca1Name());
            dr.setSca2Name(drug.getSca2Name());
            dr.setSca3Name(drug.getSca3Name());
            dr.setDrugCategoryId(drug.getDrugCategoryId());
            dr.setDrugFormId(drug.getDrugFormId());
            dr.setDrugTypeId(drug.getDrugTypeId());
            dr.setDispensingUnitId(drug.getDispensingUnitId());
            dr.setPackSize(drug.getPackSize());
            dr.setReorderPoint(drug.getReorderPoint());
            dr.setDosageId(drug.getDosageId());
            dr.setQuantity(drug.getQuantity());
            dr.setDuration(drug.getDuration());
            dr.setComments(drug.getComments());
            dr.setUpdatedBy(drug.getUpdatedBy());
            dr.setUpdatedOn(new Timestamp(new Date().getTime()));
            dr.setVoided(drug.getVoided());
            dr.setServiceTypeId(drug.getServiceTypeId());
            dr.setCdrrCategoryId(drug.getCdrrCategoryId());
            dr.setTracer(drug.getTracer());
           // dr.setCdrrIndex(drug.getCdrrIndex());
            sessionFactory.getCurrentSession().update(dr);
        }
    }

    @Override
    public void updateRegimen(Regimen regimen) {
        Regimen reg = (Regimen)sessionFactory.getCurrentSession().get(Regimen.class, regimen.getId());
        if(reg != null) {
            reg.setName(regimen.getName());
            reg.setComments(regimen.getComments());
            reg.setCode(regimen.getCode());
            reg.setLine(regimen.getLine());
            reg.setRegimenTypeId(regimen.getRegimenTypeId());
            reg.setServiceTypeId(regimen.getServiceTypeId());
            reg.setRegimenStatusId(regimen.getRegimenStatusId());
            reg.setUpdatedBy(regimen.getUpdatedBy());
            reg.setUpdatedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(reg);
        }
    }

    @Override
    public void deleteDrug(Integer drugId, Integer userId) {
        Drug dr = (Drug)sessionFactory.getCurrentSession().get(Drug.class, drugId);
        if(dr != null) {
            dr.setVoided((byte)1);
            dr.setVoidedBy(userId);
            dr.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(dr);
        }
    }

    @Override
    public void deleteRegimen(Integer regimenId, Integer userId) {
        Regimen reg = (Regimen)sessionFactory.getCurrentSession().get(Regimen.class, regimenId);
        if(reg != null) {
            reg.setVoided((byte)1);
            reg.setVoidedBy(userId);
            reg.setVoidedOn(new Timestamp(new Date().getTime()));
            sessionFactory.getCurrentSession().update(reg);
        }
    }

    @Override
    public List<StockTakeItem> getStockTakeSheetItems(String sheetNo) {
        //String sql = "SELECT drug.name, stock_take_item.quantity, stock_take_item.expiry_date, stock_take_item.batch_no FROM drug LEFT JOIN stock_take_item ON drug.id = stock_take_item.drug_id JOIN stock_take_sheet ON stock_take_item.stock_take_sheet_id = stock_take_sheet.id WHERE stock_take_sheet.reference_no =  '" + sheetNo + "'";
        String sql = "SELECT stock_take_item.system_count, stock_take_item.packs, stock_take_item.looseQty,stock_take_item.pack_size,  stock_take_item.expiry_date, stock_take_item.batch_no, stock_take_item.drug_id, stock_take_item.id, stock_take_item.adjusted, drug.name FROM stock_take_item JOIN stock_take_sheet ON stock_take_item.stock_take_sheet_id = stock_take_sheet.id JOIN drug ON stock_take_item.drug_id = drug.id WHERE stock_take_sheet.reference_no =  '" + sheetNo + "' ORDER BY drug.name ASC";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        Iterator iter = resp.iterator();
        List<StockTakeItem> itemList = new ArrayList<StockTakeItem>();
        while (iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            StockTakeItem item = new StockTakeItem();
            item.setSystemCount((BigDecimal)row[0]);
            item.setPacks((Integer) row[1]);
            item.setLooseQty((Integer) row[2]);
            item.setPackSize(String.valueOf(row[3] == null ? 0 : row[3]));
            item.setExpiryDate((Timestamp) row[4]);
            item.setBatchNo((String) row[5]);
            item.setDrugId((Integer)row[6]);
            item.setId((Integer)row[7]);
            item.setAdjusted(((Boolean)row[8]) ? new Byte("1") : new Byte("0"));
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public List<Batch> listBatch(String sheetNo, Integer accountId, boolean expired, boolean shortDated) {
        if(accountId != null && accountId == -1)
            accountId = null;
        boolean sort = true;
        if(sort) {
            //return listSortedBatch(sheetNo, accountId, expired, shortDated);
        }

        String sql = "SELECT batch_transaction_item.no_of_packs, batch_transaction_item.pack_size, batch_transaction_item.date_of_expiry, transaction_item.batch_no, DATEDIFF(date(now()), date(batch_transaction_item.date_of_expiry)) FROM batch_transaction_item JOIN transaction_item ON batch_transaction_item.transaction_item_id = transaction_item.id left JOIN transaction ON transaction.id = transaction_item.transaction_id JOIN drug d ON transaction_item.drug_id = d.id WHERE d.voided = 0 AND ";
        int filters = 0;
        if(sheetNo != null || accountId != null || expired || shortDated)
            //sql += " WHERE ";
        if(sheetNo != null) {
            sql += " AND transaction_item.batch_no IN (SELECT batch_no FROM stock_take_item WHERE stock_take_sheet_id = (SELECT id FROM stock_take_sheet WHERE reference_no = '" + sheetNo + "') )";
            filters += 1;
        }
        if(accountId != null) {
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " transaction_item.account_id = " + accountId;
            filters += 1;
        }
        if(expired) {
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " date(batch_transaction_item.date_of_expiry) < date(now()) ";
            filters += 1;
        } else if(!expired && !shortDated) { //If short dated is true we do not need this
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " date(batch_transaction_item.date_of_expiry) > date(now()) ";
            filters += 1;
        }
        if(shortDated) {
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " date(batch_transaction_item.date_of_expiry) > date(now()) AND date(batch_transaction_item.date_of_expiry) < DATE_ADD(date(now()), INTERVAL 6 MONTH ) ";
        }
        sql += " ORDER BY batch_transaction_item.date_of_expiry ASC ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        Iterator iter = resp.iterator();
        Map<String, Batch> batchMap = new HashMap<String, Batch>();
        while (iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            Batch batch = new Batch();
            batch.setNumberOfPacks((Integer)row[0]);
            batch.setPackSize((Integer)row[1]);
            batch.setBatch_no((String) row[3]);
            batch.setExpiry_date((Date) row[2]);
            if(row[4] instanceof BigInteger) {
                batch.setDaysFromExpiry(row[4] != null ? ((BigInteger) row[4]).intValue() : null);
            } else if(row[4] instanceof Integer) {
                batch.setDaysFromExpiry(row[4] != null ? ((Integer) row[4]) : null);
            }
            if(batchMap.get(batch.getBatch_no().toLowerCase()) == null)
                batchMap.put(batch.getBatch_no(), batch);
        }

        boolean where = false;
        sql = "SELECT ti.units_in, ti.units_out, ti.drug_id,ti.batch_no, drug.name, dispensing_unit.name as unit,  bt.date_of_expiry FROM transaction_item ti LEFT JOIN batch_transaction_item bt ON ti.id = bt.transaction_item_id JOIN drug ON ti.drug_id = drug.id LEFT JOIN dispensing_unit ON drug.dispensing_unit_id = dispensing_unit.id WHERE drug.voided = 0 ";

        if(sheetNo != null) {
            sql += " AND ti.batch_no IN (SELECT batch_no FROM stock_take_item WHERE stock_take_sheet_id = (SELECT id FROM stock_take_sheet WHERE reference_no = '" + sheetNo + "') )";
        }

        if(accountId != null)
            sql += " AND ti.account_id = " + accountId;

        sql += " ORDER BY batch_no DESC";
        query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        resp = query.list();
        iter = resp.iterator();
        while(iter.hasNext()) {
            Object[] row = (Object[])iter.next();
            String batch_no = (String)row[3];
            Batch b = batchMap.get(batch_no);
            if(b != null) {
                BigDecimal unitsIn = (row[0] == null ? BigDecimal.ZERO : (BigDecimal)row[0]);
                BigDecimal unitsOut = (row[1] == null ? BigDecimal.ZERO : (BigDecimal)row[1]);

                BigDecimal current = unitsIn.subtract(unitsOut);
                b.setStockInBatch(current.add(b.getStockInBatch()));
                b.setDrugId((Integer)row[2]);
                b.setDrugName((String)row[4]);
                b.setDispensingUnitName((String)row[5]);
                batchMap.put(batch_no, b);

            }
        }
        List<Batch> batchList = new ArrayList<Batch>();
        batchList.addAll(batchMap.values());
        return batchList;
    }

    public List<Batch> listSortedBatch(String sheetNo, Integer accountId, boolean expired, boolean shortDated) {
        String sql = "SELECT d.name, ti.drug_id,  ti.batch_no, ti.account_id,(COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, bt.date_of_expiry, bt.pack_size, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out, du.name, DATEDIFF(date(now()), date(bt.date_of_expiry)) as expiry FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id LEFT JOIN dispensing_unit du ON d.dispensing_unit_id = du.id LEFT JOIN batch_transaction_item bt ON bt.transaction_item_id = ti.id ";
        int filters = 0;
        if(sheetNo != null || accountId != null || expired || shortDated)
            sql += " WHERE ";
        if(sheetNo != null) {
            sql += " ti.batch_no IN (SELECT batch_no FROM stock_take_item WHERE stock_take_sheet_id = (SELECT id FROM stock_take_sheet WHERE reference_no = '" + sheetNo + "') )";
            filters += 1;
        }
        if(accountId != null) {
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " ti.account_id = " + accountId;
            filters += 1;
        }

        if(shortDated) {
            if(filters > 0) {
                sql += " AND ";
            }
            sql += " date(bt.date_of_expiry) > date(now()) AND date(bt.date_of_expiry) < DATE_ADD(date(now()), INTERVAL 6 MONTH ) ";
        }

        sql += " group by ti.batch_no HAVING diff > 0 ";
        if(expired) {
            sql += " and expiry >= 0 ";
            filters += 1;
        } else {
            sql += " and (expiry < 0  OR bt.date_of_expiry IS NULL ) ";
        }

        sql += " ORDER BY bt.date_of_expiry ASC";

        List data = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        Iterator iterator = data.iterator();
        List<Batch> batchList = new ArrayList<Batch>();
        while(iterator.hasNext()) {
            Object[] d = (Object[])iterator.next();
            Batch batch = new Batch();
            batch.setDrugId((Integer)d[1]);
            batch.setDrugName((String)d[0]);
            batch.setBatch_no((String)d[2]);
            batch.setStockInBatch((BigDecimal)d[4]);
            batch.setExpiry_date((Date)d[5]);
            batch.setPackSize((Integer)d[6]);
            batch.setDispensingUnitName((String)d[9]);
            batch.setDaysFromExpiry(d[10] != null ? ((Integer)d[10]).intValue() : null);
            batchList.add(batch);
        }
        return batchList;
    }

    @Override
    public String createStockTake(List<Batch> batchList, Integer userId, Integer accountId) {
        StockTakeSheet takeSheet = new StockTakeSheet();
        takeSheet.setTakeDate(new Timestamp(new Date().getTime()));
        takeSheet.setUuid(UUID.randomUUID().toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        Random random = new Random(System.currentTimeMillis());
        int n = random.nextInt(100);
        takeSheet.setReferenceNo(date + "-" + n);
        takeSheet.setCreatedBy(userId);
        takeSheet.setCreatedOn(new Timestamp(new Date().getTime()));
        takeSheet.setMonthly((byte)1);
        takeSheet.setPackSize(String.valueOf(0));

        Integer takeSheetId = saveStockTakeSheet(takeSheet);
        for(int i = 0; i < batchList.size(); i++) {
            Batch batch = batchList.get(i);
            if(batch == null)
                continue;

            StockTakeItem takeItem = new StockTakeItem();
            takeItem.setStockTakeSheetId(takeSheetId);
            takeItem.setAccountId(accountId);
            takeItem.setBatchNo(batch.getBatch_no());
            takeItem.setDrugId(batch.getDrugId());
            if(batch.getExpiry_date() != null)
                takeItem.setExpiryDate(new Timestamp(batch.getExpiry_date().getTime()));
            takeItem.setSystemCount(batch.getStockInBatch());
            takeItem.setUuid(UUID.randomUUID().toString());
            takeItem.setCreatedBy(userId);
            takeItem.setCreatedOn(new Timestamp(new Date().getTime()));
            saveStockTakeItem(takeItem);
        }
        return takeSheet.getReferenceNo();
    }

    @Override
    public StockTakeSheet getStockTakeSheet(String sheetNo) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(StockTakeSheet.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("takeDate"), "takeDate")
                        .add(Projections.property("referenceNo"), "referenceNo").add(Projections.property("countedBy"), "countedBy").add(Projections.property("verifiedBy"), "verifiedBy").add(Projections.property("packSize"), "packSize")
        ).add(Restrictions.eq("referenceNo", sheetNo)).setResultTransformer(Transformers.aliasToBean(StockTakeSheet.class));
        List<StockTakeSheet> stockTakeSheets = c.list();
        if(stockTakeSheets.size() > 0)
            return stockTakeSheets.get(0);
        return null;
    }

    @Override
    public void updateStockTakeItem(StockTakeItem item) {
        StockTakeItem takeItem = (StockTakeItem)sessionFactory.getCurrentSession().get(StockTakeItem.class, item.getId());
        takeItem.setLooseQty(item.getLooseQty());
        takeItem.setPacks(item.getPacks());
        takeItem.setPackSize(item.getPackSize());
        takeItem.setAdjusted(item.getAdjusted());
        saveStockTakeItem(takeItem);
    }

    @Override
    public Integer getAccountId(String accountType) {
        String sql = "SELECT account.id, account.name FROM account WHERE account.name = '" + accountType + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        List resp = query.list();
        Iterator iter = resp.iterator();

        while(iter.hasNext()) {
            Object[] rows = (Object[])iter.next();
            Integer id = (Integer)rows[0];
            return id;
        }
        return null;
    }

    @Override
    public List<Object> getExpiredDrugs() {
        return null;
    }

    @Override
    public List<Transaction> listTransactions(Integer accountId, Integer drugId) {
        String sql = "SELECT t.* FROM transaction t JOIN transaction_item ti ON t.id = ti.transaction_id AND ti.account_id = " + accountId + " AND ti.drug_id = " + drugId;
        Map restrictions = new HashMap();
        if(accountId != -1) {
            restrictions.put("accountId", accountId);
        }
        restrictions.put("drugId", drugId);
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Transaction.class, "t").createAlias("transactionItemsById", "ti", Criteria.INNER_JOIN, Restrictions.allEq(restrictions)).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("transactionTypeId"), "transactionTypeId")
                        .add(Projections.property("referenceNo"), "referenceNo").add(Projections.property("date"), "date").add(Projections.property("createdBy"),"createdBy")
        ).setResultTransformer(Transformers.aliasToBean(Transaction.class));
        List<Transaction> transactions = c.list();
        return transactions;
    }


    @Override
    public List<TransactionItem> listTransactionsItems(Integer accountId, Integer drugId) {
        //List<TransactionItem> tItems = sessionFactory.getCurrentSession().createQuery(" FROM TransactionItem WHERE accountId = " + accountId " AND drugId = " + drugId).list();
        Criteria c = sessionFactory.getCurrentSession().createCriteria(TransactionItem.class).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("transactionId"), "transactionId").add(Projections.property("batchNo"), "batchNo")
                        .add(Projections.property("drugId"), "drugId").add(Projections.property("accountId"), "accountId").add(Projections.property("unitsIn"), "unitsIn").add(Projections.property("unitsOut"), "unitsOut")
        ).add(Restrictions.eq("drugId", drugId));
        if(accountId != -1) {
            c.add(Restrictions.eq("accountId", accountId));
        }
        c.setResultTransformer(Transformers.aliasToBean(TransactionItem.class));
        List<TransactionItem> transactionItems = c.list();

        return transactionItems;
    }

    @Override
    public List<BatchTransactionItem> listBatchTransactionsItems(Integer accountId, Integer drugId) {
        Map restrictions = new HashMap();
        if(accountId != -1) {
            restrictions.put("accountId", accountId);
        }
        restrictions.put("drugId", drugId);
        Criteria c = sessionFactory.getCurrentSession().createCriteria(BatchTransactionItem.class, "b").createAlias("transactionItemByTransactionItemId", "ti", Criteria.INNER_JOIN, Restrictions.allEq(restrictions)).setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("transactionItemId"), "transactionItemId")
                        .add(Projections.property("noOfPacks"), "noOfPacks").add(Projections.property("packSize"), "packSize").add(Projections.property("dateOfExpiry"), "dateOfExpiry")
        ).setResultTransformer(Transformers.aliasToBean(BatchTransactionItem.class));
        List<BatchTransactionItem> transactionItems = c.list();
        return transactionItems;
    }

    @Override
    public List listDailyConsumption(String date1, String date2) {

        String sql = "SELECT d.name, SUM(ti.units_in), t.date FROM transaction_item ti LEFT JOIN drug d ON  d.id = ti.drug_id JOIN transaction t ON ti.transaction_id = t.id WHERE ti.account_id = " + getAccountId("PATIENTS") + " AND t.transaction_type_id = " + getTransactionTypeId("Dispensed to Patients") + " AND (date(t.date) BETWEEN '" + date1 + "' AND '" + date2 + "') GROUP BY ti.drug_id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        List resp = query.list();

        return resp;
    }

    @Override
    public List listDailyConsumptionDispensing(String date1, String date2, String AreaService, String PayerType) {
        //
        String sql = "SELECT d.name, SUM(ti.units_in), t.date FROM transaction_item ti LEFT JOIN drug d ON  d.id = ti.drug_id JOIN transaction t ON ti.transaction_id = t.id WHERE ti.account_id = " + getAccountId("PATIENTS") + " AND t.transaction_type_id = " + getTransactionTypeId("Dispensed to Patients") + " AND (date(t.date) BETWEEN '" + date1 + "' AND '" + date2 + "') GROUP BY ti.drug_id";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        List resp = query.list();

        return resp;
    }


    @Override
    public Integer getTransactionTypeId(String type) {
        String sql = "SELECT id, name FROM transaction_type WHERE name = '" + type + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        List resp = query.list();
        Iterator iter = resp.iterator();

        while(iter.hasNext()) {
            Object[] rows = (Object[])iter.next();
            Integer id = (Integer)rows[0];
            return id;
        }
        return null;
    }

    @Override
    public void saveRegimenDrug(Integer regimenId, List drugIds) {
        deleteRegimenDrug(regimenId);

        Iterator iterator = drugIds.iterator();
        while(iterator.hasNext()) {
            RegimenDrug r = new RegimenDrug();
            r.setRegimenId(regimenId);
            r.setDrugId((Integer) iterator.next());
            sessionFactory.getCurrentSession().save(r);
        }
    }

    public void deleteRegimenDrug(Integer regimenId) {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE from RegimenDrug WHERE regimenId = " + regimenId);
        query.executeUpdate();
    }

    @Override
    public List<Drug> listDrugs(Integer serviceTypeId) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Drug.class, "drug").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("dhisId"), "dhisId").add(Projections.property("cdrrCategoryId"), "cdrrCategoryId").add(Projections.property("name"), "name").add(Projections.property("strength"), "strength").add(Projections.property("packSize"), "packSize").add(Projections.property("genericNameId"), "genericNameId").add(Projections.property("drugCategoryId"), "drugCategoryId").add(Projections.property("drugTypeId"), "drugTypeId").add(Projections.property("dispensingUnitId"), "dispensingUnitId").add(Projections.property("drugFormId"), "drugFormId").add(Projections.property("dosageId"), "dosageId").add(Projections.property("voided"), "voided").add(Projections.property("serviceTypeId"), "serviceTypeId").add(Projections.property("tracer"), "tracer")).add(Restrictions.eq("serviceTypeId", serviceTypeId)).setResultTransformer(Transformers.aliasToBean(Drug.class));

        List<Drug> drugList = c.list();
        return drugList;
    }

    @Override
    public List<Drug> listDrugs(Integer serviceTypeId, boolean cdrr) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Drug.class, "drug").setProjection(Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("dhisId"), "dhisId").add(Projections.property("cdrrCategoryId"), "cdrrCategoryId").add(Projections.property("name"), "name").add(Projections.property("strength"), "strength").add(Projections.property("packSize"), "packSize").add(Projections.property("genericNameId"), "genericNameId").add(Projections.property("drugCategoryId"), "drugCategoryId").add(Projections.property("drugTypeId"), "drugTypeId").add(Projections.property("dispensingUnitId"), "dispensingUnitId").add(Projections.property("drugFormId"), "drugFormId").add(Projections.property("dosageId"), "dosageId").add(Projections.property("voided"), "voided").add(Projections.property("serviceTypeId"), "serviceTypeId").add(Projections.property("cdrrIndex"), "cdrrIndex").add(Projections.property("tracer"), "tracer")).add(Restrictions.eq("serviceTypeId", serviceTypeId)).addOrder(Order.asc("cdrrCategoryId")).setResultTransformer(Transformers.aliasToBean(Drug.class));

        List<Drug> drugList = c.list();
        return drugList;
    }

    @Override
    public List<TransactionItem> listTransactions(List<Transaction> transactions) {
        String ids = "(";
        for(int i = 0; i < transactions.size(); i++) {
            if(i != 0)
                ids +=  ",";
            ids += transactions.get(i).getId();
        }
        ids += ")";
        System.out.println(ids);
        List<TransactionItem> transactionItems = sessionFactory.getCurrentSession().createQuery(" FROM TransactionItem WHERE transactionId IN " + ids).list();
        return transactionItems;
    }

    @Override
    public List getTotalStock(Integer accountId, Integer drugId) {
        //String sql = "SELECT ti.drug_id, ti.account_id,(COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id WHERE ti.drug_id = " + drugId + " ";
        String sql = "SELECT ti.drug_id, ti.account_id,(COALESCE(SUM(ti.units_in), 0) - COALESCE(SUM(ti.units_out), 0)) as diff, SUM(ti.units_in) as units_in, SUM(ti.units_out) as units_out FROM transaction_item ti JOIN transaction t ON ti.transaction_id = t.id JOIN drug d ON ti.drug_id = d.id WHERE ti.drug_id = " + drugId + " ";


        if(accountId != -1) {
            sql += " AND ti.account_id = " + accountId;
        } else {
            sql += " group by ti.account_id ";
        }
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List resp = query.list();
        Iterator iter = resp.iterator();
        List stock = new ArrayList();
        while(iter.hasNext()) {
            Object[] rows = (Object[])iter.next();
            BigDecimal id = (BigDecimal)rows[2];
            stock.add(id);
        }
        return stock;
    }
}
