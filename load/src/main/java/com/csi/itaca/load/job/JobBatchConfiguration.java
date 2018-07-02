package com.csi.itaca.load.job;

import com.csi.itaca.load.domain.DataIn;
import com.csi.itaca.load.domain.DataOut;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Bean
    public Job sqlExecuteJob(JobBuilderFactory jobs, @Qualifier("preloadDefinitionStep") Step step_preload_def, JobExecutionListener listener) {

        Job job = jobs.get("job1")
                .listener(listener)
                .flow(step_preload_def)
                .end()
                .build();

        return job;

    }

    @Bean
    @Qualifier("preloadDefinitionStep")
    public Step preloadDefinitionStep(StepBuilderFactory stepBuilderFactory, ItemReader<DataIn> readFile,
                                      ItemWriter<DataOut> writeJDBC, ItemProcessor<DataIn, DataOut> processor) {

        return stepBuilderFactory.get("preloadDefinitionStep")
                .<DataIn, DataOut> chunk(100)
                .reader(readFile)
                .processor(processor)
                .writer(writeJDBC)
                .build();
    }

}
