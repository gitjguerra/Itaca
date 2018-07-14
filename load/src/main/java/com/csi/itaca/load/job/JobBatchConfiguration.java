package com.csi.itaca.load.job;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dao.LoadRowOperationEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import com.csi.itaca.load.model.dto.PreloadData;
import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import com.csi.itaca.load.repository.LoadRowOperationRepository;
import com.csi.itaca.load.repository.PreloadRowTypeRepository;
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
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataIntegrityViolationException;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    private PreloadRowTypeRepository preloadRowTypeRepository;

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

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
                   ItemWriter<PreloadData> PreloadItemWriter) throws MalformedURLException {

        Step step_preload = stepBuilderFactory.get("preload-data-step")
                .<PreloadData, PreloadData>chunk(2)
                .reader(itemReader(WILL_BE_INJECTED, WILL_BE_INJECTED, WILL_BE_INJECTED))
                .processor(new PreloadProcessor())
                .writer(new PreloadWriter(preloadDataDao))
                .listener(jobCompletionNotificationListener)
                .faultTolerant()  // Revisar ???  y agregar en LD_ERROR
                .skip(DataIntegrityViolationException.class)
                .build();
        return jobBuilderFactory.get("preload-data-step")
                .incrementer(new RunIdIncrementer())
                .start(step_preload)          // Preload
                //.next(step_load)            // LOAD
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

        PreloadData preloadData = new PreloadData();
        preloadData.setRowType((long) 7);

        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource(pathToFile));
        reader.setLineMapper(preloadLineMapper(id_load_process, id_load_file));

        return reader;
    }

    public LineTokenizer preloadLineTokenizer(LinkedHashMap<String,Long> fields) {

        int cont = 0;
        int intLengthInit = 0;
        int min = 0;
        int max = 0;

        String[] strNames =  new String[fields.size()];
        Range[] strRanges = new Range[fields.size()];
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

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

    public PatternMatchingCompositeLineMapper preloadLineMapper(String id_load_process, String id_load_file) {
        PatternMatchingCompositeLineMapper lineMapper =
                new PatternMatchingCompositeLineMapper();

        LinkedHashMap<String,Long> fields = new LinkedHashMap<>();
        LinkedHashMap<String,Integer> characterMapper = new LinkedHashMap<>();
        Long idRowType = 0L;
        int cont = 1;

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);

        // TODO: Cambiar los JdbcTemplate por repository's
        //PreloadRowTypeEntity preloadRowTypeEntity = preloadRowTypeRepository.findOne(Long.valueOf(id_load_process));

        // Database connection direct
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PreloadRowTypeDTO> rowTypes = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                "WHERE ld_load_process.LOAD_PROCESS_ID = " + id_load_process + " " +
                "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                "AND ld_preload_file.preload_file_id. = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO.class));
        for(PreloadRowTypeDTO rowType : rowTypes)
        {
            idRowType = rowType.getPreloadRowTypeId().longValue();
            List<PreloadFieldDefinitionDTO> fieldDefinitions = jdbcTemplate.query("SELECT PRELOAD_FIELD_DEFINITION_ID, PRELOAD_ROW_TYPE_ID, COLUMN_NO, LENGTH, NAME, DESCRIPTION, PRELOAD_FIELD_TYPE_ID, REGEX, REQUIRED, REL_TYPE, REL_FIELD_DEFINITION_ID, REL_DB_TABLE_NAME, REL_DB_FIELD_NAME, ERROR_SEVERITY " +
                    "FROM LD_PRELOAD_FIELD_DEFINITION " +
                    "WHERE PRELOAD_ROW_TYPE_ID = " + idRowType, new BeanPropertyRowMapper(PreloadFieldDefinitionDTO.class));
            for(PreloadFieldDefinitionDTO fieldDefinition : fieldDefinitions)
            {
                fields.put( fieldDefinition.getName(), fieldDefinition.getLength() );
            }

            tokenizers.put(rowType.getIdentifierValue()+"*", preloadLineTokenizer(fields));
            characterMapper.put(rowType.getIdentifierValue(), cont++);
            fields.clear();
        }

        lineMapper.setTokenizers(tokenizers);

        Map<String, FieldSetMapper> mappers = new HashMap<>(2);

        for (Map.Entry<String, Integer> entry : characterMapper.entrySet()) {
            mappers.put(entry.getKey()+"*", preloadFieldSetMapper());
        }
        lineMapper.setFieldSetMappers(mappers);

        return lineMapper;
    }
}