package com.csi.itaca.load.job;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

// TODO: Change for Itaca file
public class PreloadReader {
    public static FlatFileItemReader<Customer> reader(@Value("#{jobParameters[fullPathFileName]}") String pathToFile) {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();

        reader.setResource(new ClassPathResource(pathToFile));
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
        return reader;
    }
}