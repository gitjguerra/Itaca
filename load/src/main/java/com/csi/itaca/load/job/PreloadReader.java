package com.csi.itaca.load.job;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

// TODO: Change for Itaca file
public class PreloadReader {

    public static FlatFileItemReader<Customer> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();

        reader.setResource(new ClassPathResource(pathToFile));

        /*

        // Code for file eith separator character
        reader.setLineMapper(new DefaultLineMapper<Customer>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "firstName", "lastName" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
                    {
                        setTargetType(Customer.class);
                    }
                });
            }
        });
        */

        // PRUEBA FIXED LENGTH
        reader.setLineMapper(productLineMapper());

        return reader;
    }

    private static LineMapper<Customer> productLineMapper() {
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<Customer>();
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

    public static FieldSetMapper<Customer> productFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }
}