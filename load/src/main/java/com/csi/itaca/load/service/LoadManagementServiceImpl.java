package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@SuppressWarnings("unchecked")
@Service
@EnableBatchProcessing
@Configuration
public class LoadManagementServiceImpl implements LoadManagementService {

    @Value("${batch.process.csvFile}")
    private String CSV_FILE;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    final static Logger logger = Logger.getLogger(LoadManagementServiceImpl.class);

    // begin reader, writer, and processor
    public FlatFileItemReader<PreloadDataDTO> csvPreloadReader() {
        FlatFileItemReader<PreloadDataDTO> reader = new FlatFileItemReader<PreloadDataDTO>();
        reader.setResource(new ClassPathResource(CSV_FILE));
        reader.setLineMapper(new DefaultLineMapper<PreloadDataDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "preloadDataId", "loadFileId", "loadedSuccessfully", "rowType", "lineNumber", "dataCol1", "dataCol2", "dataCol3" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<PreloadDataDTO>() {{
                setTargetType(PreloadDataDTO.class);
            }});
        }});
        return reader;
    }

    public ItemProcessor<PreloadDataDTO, PreloadDataDTO> csvPreloadProcessor() {
        return new PreloadProcessor();
    }

    public JdbcBatchItemWriter<PreloadDataDTO> csvPreloadWriter() {
        JdbcBatchItemWriter<PreloadDataDTO> csvAnimeWriter = new JdbcBatchItemWriter<PreloadDataDTO>();
        csvAnimeWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PreloadDataDTO>());
        csvAnimeWriter.setSql("INSERT INTO LD_PRELOAD_DATA (preloadDataId, loadFileId, loadedSuccessfully, rowType, lineNumber, dataCol1, dataCol2, dataCol3) " +
                "VALUES (:preloadDataId, :loadFileId, :loadedSuccessfully, :rowType, :lineNumber, :dataCol1, :dataCol2, :dataCol3)");
        csvAnimeWriter.setDataSource(dataSource);
        return csvAnimeWriter;
    }
    // finish reader, writer, and processor

    @Override
    public Step csvFileToDatabaseStep() {
        return stepBuilderFactory.get("csvFileToDatabaseStep")
                .<PreloadDataDTO, PreloadDataDTO>chunk(1)
                .reader(csvPreloadReader())
                .processor(csvPreloadProcessor())
                .writer(csvPreloadWriter())
                .build();
    }

    @Override
    public Job csvFileToDatabaseJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("csvFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep())
                .end()
                .build();
    }
}
