package org.msh.fdt.controller;

import org.msh.fdt.model.Drug;
import org.msh.fdt.model.Regimen;
import org.msh.fdt.service.ReferenceService;
import org.msh.fdt.service.StocksService;
import org.msh.fdt.util.Batch;
import org.msh.fdt.util.JTableResponse;
import org.msh.fdt.util.StocksRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.io.Console;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by kenny on 4/9/14.
 *
 * This is the main controller for stock related transactions
 * It also lists, creates, updated and deletes commodities and regimens.
 */

@Controller
@RequestMapping("/stocks")
public class StocksController extends BaseController {

    @Autowired
    private StocksService stocksService;
    @Autowired
    private ReferenceService referenceService;

    /**
     *  Saves given Transactions to the database
     * @param requestWrapper This contains the @link{Transactions} information
     *  and a list of the @link{TransactionItem} and also @link{BatchTransactionItem}
     *
     * @param sourceId The source of the transaction. This is where the items will be originating.
     * @return
     */
    @RequestMapping(value="/json/saveTransactions/{sourceId}", method = RequestMethod.POST)
    public @ResponseBody String saveStocksTransactions(@RequestBody StocksRequestWrapper requestWrapper, @PathVariable("sourceId") Integer sourceId) {
        try {
            requestWrapper.getTransaction().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getTransaction().setUuid(UUID.randomUUID().toString());
            stocksService.saveTransactions(requestWrapper.getTransaction(), requestWrapper.getTransactionItemList(), requestWrapper.getBatchTransactionItemList(), sourceId);
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value="/json/saveRegimen", method = RequestMethod.POST)
    public @ResponseBody String saveRegimen(@RequestBody StocksRequestWrapper requestWrapper) {
        try {
            requestWrapper.getRegimen().setCreatedOn(new Timestamp(new Date().getTime()));
            requestWrapper.getRegimen().setUuid(UUID.randomUUID().toString());
            Integer id = stocksService.saveRegimen(requestWrapper.getRegimen());
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Updates a given {@link org.msh.fdt.model.Regimen} in the @param requestWrapper
     * @param requestWrapper
     * @return
     */
    @RequestMapping(value="/json/updateRegimen", method = RequestMethod.POST)
    public @ResponseBody String updateRegimen(@RequestBody StocksRequestWrapper requestWrapper) {
        try {
            requestWrapper.getRegimen().setUpdatedOn(new Timestamp(new Date().getTime()));
            stocksService.updateRegimen(requestWrapper.getRegimen());
            return "updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Updates a given {@link org.msh.fdt.model.Drug} in the @param requestWrapper
     * @param requestWrapper
     * @return
     */
    @RequestMapping(value="/json/saveDrug", method = RequestMethod.POST)
    public @ResponseBody String saveDrug(@RequestBody StocksRequestWrapper requestWrapper) {
        try {
            requestWrapper.getDrug().setUuid(UUID.randomUUID().toString());
            requestWrapper.getDrug().setCreatedOn(new Timestamp(new Date().getTime()));
            Integer id = stocksService.saveDrug(requestWrapper.getDrug());
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Updates a given drug which is specified in the {@link org.msh.fdt.util.StocksRequestWrapper}
     * @param requestWrapper
     * @return
     */
    @RequestMapping(value="/json/updateDrug", method = RequestMethod.POST)
    public @ResponseBody String updateDrug(@RequestBody StocksRequestWrapper requestWrapper) {
        try {
            requestWrapper.getDrug().setUpdatedOn(new Timestamp(new Date().getTime()));
            stocksService.updateDrug(requestWrapper.getDrug());
            return "updated";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Creates a list of all regimens
     * @return
     */
    @RequestMapping(value="/json/listRegimen", method = RequestMethod.POST)
    public @ResponseBody
    List<Regimen> listRegimen() {
        try {
            List<Regimen> regimenList = stocksService.listRegimenObj();
            return regimenList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Creates a list of all drugs
     * @return
     */
    @RequestMapping(value="/json/listDrugs", method = RequestMethod.POST)
    public @ResponseBody
    List<Drug> listDrugs() {
        try {
            List<Drug> drugList = stocksService.listDrug();
            return drugList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Creates a list of all batches. This is constructed from thr batch transaction Items and the transactions
     * @return
     */
    @RequestMapping(value="/json/listBatch", method = RequestMethod.POST)
    public @ResponseBody
    List<Batch> listBatch() {
        try {
            List<Batch> batchList = stocksService.listBatch();
            return batchList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Deletes a {@link org.msh.fdt.model.Drug} with the given ID
     * @param drugId  The ID of the drug being deleted
     * @param userId The ID of the user who is deleting the drug
     * @return
     */
    @RequestMapping(value="/json/deleteDrug/{drugId}/{userId}", method = RequestMethod.POST)
    public @ResponseBody String deleteDrug(@PathVariable("drugId") Integer drugId, @PathVariable("userId") Integer userId) {
        try {
            stocksService.deleteDrug(drugId, userId);
            return "deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Deletes a {@link org.msh.fdt.model.Regimen} with the given ID
     * @param regimenId  The ID of the Regimen being deleted
     * @param userId The ID of the user who is deleting the drug
     * @return
     */
    @RequestMapping(value="/json/deleteRegimen/{regimenId}/{userId}", method = RequestMethod.POST)
    public @ResponseBody String deleteRegimen(@PathVariable("regimenId") Integer regimenId, @PathVariable("userId") Integer userId) {
        try {
            stocksService.deleteRegimen(regimenId, userId);
            return "deleted";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Saves a new Stock take
     * @param requestWrapper This is a @link{StocksRequestWrapper} which contains the @link{org.msh.fdt.model.StockTakeSheet} information and
     *    a list of @link{org.msh.fdt.model.StockTakeItem} items for the given Stock Take Sheet
     * @return
     */
    @RequestMapping(value="/json/saveStockTake", method = RequestMethod.POST)
    public @ResponseBody String saveStockTake(@RequestBody StocksRequestWrapper requestWrapper) {
        try {
            requestWrapper.getStockTakeSheet().setUuid(UUID.randomUUID().toString());
            requestWrapper.getStockTakeSheet().setCreatedOn(new Timestamp(new Date().getTime()));
            stocksService.saveStockTake(requestWrapper.getStockTakeSheet(), requestWrapper.getStockTakeItemList());
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *  Loads the stock variances for the given account ID
     * @param wrapper : The @link{StocksRequestWrapper} which contains the sheetNo for the stock take
     * @return
     */
    @RequestMapping(value="/json/getStockVariance", method = RequestMethod.POST)
    public @ResponseBody StocksRequestWrapper getStockVariance(@RequestBody StocksRequestWrapper wrapper) {
        try {
            String sheetNo = wrapper.getSheetNo();
            wrapper.setStockTakeSheet(stocksService.getStockTakeSheet(sheetNo));
            wrapper.setStockTakeItemList(stocksService.listStockItems(sheetNo));
            wrapper.setDrugList(stocksService.listDrug());
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Saves the stock variance
     *  Updates the different accounts
     * @param
     * @return
     */
    @RequestMapping(value="/json/saveStockVariance/{accountId}/{userId}", method = RequestMethod.POST)
    public @ResponseBody String saveStockVariance(@RequestBody StocksRequestWrapper wrapper, @PathVariable("accountId") Integer accountId, @PathVariable("userId") Integer userId) {
        try {
            stocksService.updateStockTakeSheetItems(wrapper.getStockTakeItemList(), accountId, userId);
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Saves the stock variance
     *  Updates the different accounts
     * @param
     * @return
     */
    @RequestMapping(value="/json/adjustStockVariance/{accountId}/{userId}", method = RequestMethod.POST)
    public @ResponseBody String adjustStockVariance(@RequestBody StocksRequestWrapper wrapper, @PathVariable("accountId") Integer accountId, @PathVariable("userId") Integer userId) {
        try {
            stocksService.adjustAccounts(wrapper.getStockTakeItemList(), accountId, userId);
            return "saved";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Initializes Bin Cards by loading drugs and Batch information.
     *  @return
     */
    @RequestMapping(value="/json/initBinCards/{accountId}", method = RequestMethod.POST)
    public @ResponseBody StocksRequestWrapper initBinCards(@PathVariable("accountId") Integer accountId) {
        try {
            StocksRequestWrapper requestWrapper = new StocksRequestWrapper();
            requestWrapper.setBatchList(stocksService.listBatch(null, accountId));
            requestWrapper.setDrugList(stocksService.listDrug());
            return requestWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Used to load Bin cards
     * @return
     */
    @RequestMapping(value="/json/viewBinCards/{accountId}/{drugId}", method = RequestMethod.POST)
    public @ResponseBody StocksRequestWrapper viewBinCards(@PathVariable("accountId") Integer accountId, @PathVariable("drugId") Integer drugId) {
        try {
            StocksRequestWrapper requestWrapper = new StocksRequestWrapper();
            requestWrapper.setTransactionItemsListMap(stocksService.listTransactionItems(accountId, drugId));
            requestWrapper.setTransactionMap(stocksService.listTransactions(accountId, drugId));
            requestWrapper.setBatchTransactionItemMap(stocksService.listBatchTransactionItems(accountId, drugId));
            requestWrapper.setDestinationTransactionItems(stocksService.listOtherTransactionItems(accountId, drugId));
            requestWrapper.setBatchList(stocksService.listDrugBatch(accountId, drugId));
            requestWrapper.setTotalStock(stocksService.totalStock(accountId, drugId));
            return requestWrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Used to load Bin cards
     * @return
     */
    @RequestMapping(value="/json/dailyConsumption", method = RequestMethod.GET)
    public @ResponseBody List dailyConsumption(ServletRequest request) {
        try {
            String date1 = request.getParameter("date1");
            String date2 = request.getParameter("date2");
            List data = stocksService.listConsumption( date1, date2);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Used to load daily consumption dispensing
     * @return
     */
    @RequestMapping(value="/json/dailyConsumptionDispension", method = RequestMethod.GET)
    public @ResponseBody List dailyConsumptionDispension(ServletRequest request) {
        try {
            String date1 = request.getParameter("date1");
            String date2 = request.getParameter("date2");
            String AreaService = request.getParameter("AreaService");
            String PayerType = request.getParameter("PayerType");
            //System.out.println(date1);
            List data = stocksService.listConsumptionDispensing(date1, date2, AreaService, PayerType);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  Saves Regimen-Drug Combination
     */
    @RequestMapping(value="/json/saveDrugRegimen/{regimenId}", method = RequestMethod.POST)
    public @ResponseBody
    JTableResponse saveDrugRegimen(@PathVariable("regimenId") Integer regimenId, @RequestBody List<Integer> drugId) {
        try {
            stocksService.saveRegimenDrug(regimenId, drugId);
            return new JTableResponse("OK", "Saved");
        } catch (Exception e) {
            return new JTableResponse("FAIL", "Error saving Regimen Drugs " + e.getMessage());
        }
    }

}
