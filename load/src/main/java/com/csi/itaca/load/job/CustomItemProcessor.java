package com.csi.itaca.load.job;

import com.csi.itaca.load.domain.DataIn;
import com.csi.itaca.load.domain.DataOut;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<DataIn,DataOut> {

    @Override
    public DataOut process(DataIn item) throws Exception {
        DataOut dataOut = new DataOut();
        dataOut.setNAME(item.getNAME().toUpperCase());
        dataOut.setDESCRIPTION(item.getDESCRIPTION().toUpperCase());
        return dataOut;
    }

}
