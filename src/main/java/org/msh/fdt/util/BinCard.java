package org.msh.fdt.util;

import org.msh.fdt.model.Transaction;

import java.util.List;
import java.util.Map;

/**
 * Created by kenny on 6/30/14.
 */
public class BinCard {

    private Map<Integer, List<Transaction>> batches;

    public Map<Integer, List<Transaction>> getBatches() {
        return batches;
    }

    public void setBatches(Map<Integer, List<Transaction>> batches) {
        this.batches = batches;
    }
}
