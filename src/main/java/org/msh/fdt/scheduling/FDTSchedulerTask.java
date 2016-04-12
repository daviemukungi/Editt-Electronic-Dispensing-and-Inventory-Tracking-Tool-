package org.msh.fdt.scheduling;

import org.msh.fdt.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * Created by kenny on 1/31/15.
 */
public class FDTSchedulerTask {

    @Autowired
    private PersonService personService;
    public void updateUsers() {
        personService.updatePEPPatients();
        personService.updateLostToFollowupPatients();
    }
}
