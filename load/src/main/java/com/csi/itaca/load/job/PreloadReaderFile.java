package com.csi.itaca.load.job;

import com.csi.itaca.load.domain.DataIn;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component("readFile")
@StepScope
public class PreloadReaderFile implements ItemReader<DataIn> {

    @Override
    public DataIn read() {
        return null;
    }
}
