package com.csi.itaca.load.job;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dto.PreloadData;
import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PreloadDataDao preloadDataDao;

    // Is necessary for take the parameters
    private static final String WILL_BE_INJECTED = null;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<PreloadData> itemReader,
                   ItemProcessor<PreloadData, PreloadData> itemProcessor,
                   ItemWriter<PreloadData> PreloadItemWriter
                   ) throws MalformedURLException {

        Step step = stepBuilderFactory.get("preload-data-step")
                    .<PreloadData, PreloadData>chunk(2)
                    .reader(itemReader(WILL_BE_INJECTED, WILL_BE_INJECTED, WILL_BE_INJECTED))
                    .processor(new PreloadProcessor())
                    .writer(new PreloadWriter(preloadDataDao))
                    .build();
        return jobBuilderFactory.get("preload-data-step")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
        }

    @Bean
    @StepScope
    public FlatFileItemReader<PreloadData> itemReader(@Value("#{jobParameters['fullPathFileName']}")
                                                              String pathToFile,
                                                      @Value("#{jobParameters['id_load_process']}")
                                                              String id_load_process,
                                                      @Value("#{jobParameters['id_load_file']}")
                                                                  String id_load_file) throws MalformedURLException {
        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource(pathToFile));

        // Database connection direct
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PreloadRowTypeDTO> rowTypes = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                "WHERE ld_load_process.LOAD_PROCESS_ID = " + id_load_process + " " +
                "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO.class));
        LinkedHashMap<String,Long> fields = new LinkedHashMap<>();
        Long idRowType = 0L;
        Long length = 0L;
        for(PreloadRowTypeDTO rowType : rowTypes)
        {
            idRowType = rowType.getPreloadRowTypeId().longValue();
            List<PreloadFieldDefinitionDTO> fieldDefinitions = jdbcTemplate.query("SELECT PRELOAD_FIELD_DEFINITION_ID, PRELOAD_ROW_TYPE_ID, COLUMN_NO, LENGTH, NAME, DESCRIPTION, PRELOAD_FIELD_TYPE_ID, REGEX, REQUIRED, REL_TYPE, REL_FIELD_DEFINITION_ID, REL_DB_TABLE_NAME, REL_DB_FIELD_NAME, ERROR_SEVERITY " +
                    "FROM ITACA.LD_PRELOAD_FIELD_DEFINITION " +
                    "WHERE PRELOAD_ROW_TYPE_ID = " + idRowType, new BeanPropertyRowMapper(PreloadFieldDefinitionDTO.class));
            for(PreloadFieldDefinitionDTO fieldDefinition : fieldDefinitions)
            {
                fields.put( fieldDefinition.getName(), fieldDefinition.getLength() );
            }
            reader.setLineMapper(preloadLineMapper(fields));
            fields.clear();
            break;
        }

        return reader;
    }

    private LineMapper<PreloadData> preloadLineMapper(LinkedHashMap<String,Long> fields) {
        DefaultLineMapper<PreloadData> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(preloadLineTokenizer(fields));
        mapper.setFieldSetMapper(preloadFieldSetMapper());
        return mapper;
    }

    public LineTokenizer preloadLineTokenizer(LinkedHashMap<String,Long> fields) {

        int cont = 0;
        int intLengthInit = 0;
        int intLengthFinal = 0;
        int min = 0;
        int max = 0;

        String[] strNames =  new String[fields.size()];
        Range[] strRanges = new Range[fields.size()];
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

        //    CREATE A DINAMIC FIELDS RANGE
        for(String name: fields.keySet()){
            min = max + 1;
            intLengthInit = fields.get(name).intValue();
            max = intLengthInit + max;

            strRanges[cont] = new Range(min, max);
            strNames[cont] = name;
            cont++;
        }
        tokenizer.setStrict(false);
        tokenizer.setColumns(strRanges);
        tokenizer.setNames(strNames);
        return tokenizer;
    }

    public FieldSetMapper<PreloadData> preloadFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }

}