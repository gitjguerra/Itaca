package com.csi.itaca.load.job;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PreloadDataDao preloadDataDao;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("preload-processor")
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener(preloadDataDao))
                .flow(step1())
                .end()
                .build();
    }

    // TODO: Change de file hardcode to
        // @Value("#{jobParameters[fullPathFileName]}")
        // @Value("#{jobParameters[id_load_process]}")
        // @Value("#{jobParameters[id_load_file]}")
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("preload-data-step")
                .<PreloadData, PreloadData>chunk(2)
                .reader(PreloadReader.reader(new ClassPathResource("itaca_preload.txt").getFilename(),"1", "1"))
                .processor(new PreloadProcessor())
                .writer(new PreloadWriter(preloadDataDao))
                .build();
    }
}