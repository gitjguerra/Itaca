package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

// TODO: Change for Itaca file
public class PreloadReader {
    public static FlatFileItemReader<PreloadData> reader(@Value("#{jobParameters['fullPathFileName']}") String pathToFile) {

        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<PreloadData>();

        // 1. load_process_id
        //1.1. A record created in the ld_load_process table where load_process_id is associated.


        // 2. load_file_id
        //2.1. A record created in the ld_load_file table with same load_file_id and the same above load_process_id.


        // 1.1. Get a list of row types associated to this load:
        // select ld_preload_row_type.* from ld_load_process,
        //ld_preload_file, ld_preload_row_type
        //WHERE ld_load_process.PRELOAD_DEFINITION_ID = 1
        //AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id
        //AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id;


        // 2.1. Set ld_load_file.preload_start_time to the current time.

        // 2.2. Set ld_load_file.status_code to 200 indicating preload in progress.

        // 2.3. Determine file format type from file extension and choose appropriate file parser (CSV, Excel, TXT)
        String strFields = "'preload_data_id', 'load_file_id', 'loaded_successfully', 'created_timestamp', 'row_type', 'line_number', 'data_col1', 'data_col2', 'data_col3'";

        // 2.4. For each row in the file (loop):
        reader.setResource(new ClassPathResource(pathToFile));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<PreloadData>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{strFields});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<PreloadData>() {
                    {
                        setTargetType(PreloadData.class);
                    }
                });
            }
        });
        return reader;
    }

    private static LineMapper<PreloadData> productLineMapper() {
        DefaultLineMapper<PreloadData> mapper = new DefaultLineMapper<PreloadData>();
        mapper.setLineTokenizer(productLineTokenizer());
        mapper.setFieldSetMapper(productFieldSetMapper());
        return mapper;
    }

    public static LineTokenizer productLineTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        tokenizer.setColumns(new Range[] { new Range(1, 1), new Range(2, 5), new Range(6, 10) });
        tokenizer.setNames(new String[] { "id", "name", "description" });
        return tokenizer;
    }

    public static FieldSetMapper<PreloadData> productFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }
}