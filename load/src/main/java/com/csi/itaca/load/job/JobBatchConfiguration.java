package com.csi.itaca.load.job;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dao.PreloadDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadFieldDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import com.csi.itaca.load.model.dto.*;
import com.csi.itaca.load.repository.PreloadFieldDefinitionRepository;
import com.csi.itaca.load.repository.PreloadRowTypeRepository;
import com.csi.itaca.load.utils.Constants;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PreloadFieldDefinitionRepository preloadFieldDefinitionRepository;

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
    private Long idRowType = 0L;
    private int cont = 0;
    private String loadedSuccessfully = "1";
    private String fileLoadId = "";
    private String id_field_definition = "";
    private int line_number = 0;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<PreloadData> itemReader,
                   ItemProcessor<PreloadData, PreloadData> itemProcessor,
                   ItemWriter<PreloadData> PreloadItemWriter) throws Exception {

        Step step_preload = stepBuilderFactory.get("preload-data-step")
                .<PreloadData, PreloadData>chunk(2)
                .reader(itemReader(WILL_BE_INJECTED, WILL_BE_INJECTED, WILL_BE_INJECTED))
                .processor(processor())
                .writer(new PreloadWriter(preloadDataDao))
                .listener(jobCompletionNotificationListener)
                .faultTolerant()  // Revisar ???  y agregar en LD_ERROR
                .skip(DataIntegrityViolationException.class)
                .build();
        return jobBuilderFactory.get("preload-data-step")
                .incrementer(new RunIdIncrementer())
                .start(step_preload)          // Preload
                //.next(step_load)            // Load
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<PreloadData, PreloadData> processor() {
        return new ItemProcessor<PreloadData, PreloadData>() {
            @Override
            public PreloadData process(PreloadData preloadData) throws Exception {
                // TODO: Load preload process id
                Long preloadId = preloadData.getPreloadDataId();
                // TODO: Load file id
                Long loadFileId = Long.valueOf(fileLoadId);
                //loadedSuccessfully = loadedSuccessfully;
                // TODO: find Id Row Type
                //Long idRowType = preloadRowTypeRepository.findPreloadRowTypeEntity();
                Long rowType = idRowType;
                // TODO: Line Number
                line_number = line_number + 1;
                Long lineNumber = Long.valueOf(line_number);
                
                String dataCol1 = preloadData.getDataCol1();
                String dataCol2 = preloadData.getDataCol2();
                String dataCol3 = preloadData.getDataCol3();
                String dataCol4 = preloadData.getDataCol4();
                String dataCol5 = preloadData.getDataCol5();
                String dataCol6 = preloadData.getDataCol6();
                String dataCol7 = preloadData.getDataCol7();
                String dataCol8 = preloadData.getDataCol8();
                String dataCol9 = preloadData.getDataCol9();
                String dataCol10 = preloadData.getDataCol10();
                String dataCol11 = preloadData.getDataCol11();
                String dataCol12 = preloadData.getDataCol12();
                String dataCol13 = preloadData.getDataCol13();
                String dataCol14 = preloadData.getDataCol14();
                String dataCol15 = preloadData.getDataCol15();
                String dataCol16 = preloadData.getDataCol16();
                String dataCol17 = preloadData.getDataCol17();
                String dataCol18 = preloadData.getDataCol18();
                String dataCol19 = preloadData.getDataCol19();
                String dataCol20 = preloadData.getDataCol20();

                final PreloadData fixedCustomer = new PreloadData(preloadId, loadFileId, loadedSuccessfully, rowType, lineNumber,
                        dataCol1, dataCol2, dataCol3, dataCol4, dataCol5, dataCol6, dataCol7, dataCol8, dataCol9, dataCol10,
                        dataCol11, dataCol12, dataCol13, dataCol14, dataCol15, dataCol16, dataCol17, dataCol18, dataCol19, dataCol20);
                return fixedCustomer;
            }
        };
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PreloadData> itemReader(@Value("#{jobParameters['fullPathFileName']}")
                                                              String pathToFile,
                                                      @Value("#{jobParameters['id_load_process']}")
                                                              String id_load_process,
                                                      @Value("#{jobParameters['id_load_file']}")
                                                              String id_load_file) throws Exception {

        FlatFileItemReader<PreloadData> reader = new FlatFileItemReader<>();

        reader.setResource(new ClassPathResource(pathToFile));
        reader.setLineMapper(preloadLineMapper(id_load_process, id_load_file));
        fileLoadId = id_load_file;
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
        int cont = 1;

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);

        // TODO: Cambiar los JdbcTemplate por repository's  y eliminar el sufijo _OLD

        // Database connection direct
        /*
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<PreloadRowTypeDTO_OLD> rowTypes = jdbcTemplate.query("select ld_preload_row_type.* from ld_load_process, ld_preload_file, ld_preload_row_type " +
                "WHERE ld_load_process.LOAD_PROCESS_ID = " + id_load_process + " " +
                "AND ld_load_process.preload_definition_id = ld_preload_file.preload_definition_id " +
                "AND ld_preload_file.preload_file_id = ld_preload_row_type.preload_file_id", new BeanPropertyRowMapper(PreloadRowTypeDTO_OLD.class));
        */
        // TODO:  Put the id_load_process in the repository query
        List<PreloadRowTypeEntity>  rowTypes = preloadRowTypeRepository.findPreloadRowTypeEntityList();
        for(PreloadRowTypeEntity rowType : rowTypes)
        {
            // TODO:  put the idRowType in the repository query
            idRowType = rowType.getPreloadRowTypeId().longValue();
            List<PreloadFieldDefinitionEntity>  fieldDefinitions = preloadFieldDefinitionRepository.findFieldDefinitionEntityList();
            for(PreloadFieldDefinitionEntity fieldDefinition : fieldDefinitions)
            {
                fields.put( fieldDefinition.getName(), fieldDefinition.getLength() );
            }
            // TODO: DELETE - old code for management objects with jdbcTemplate
            /*
            List<PreloadFieldDefinitionDTO_OLD> fieldDefinitions = jdbcTemplate.query("SELECT LENGTH, NAME FROM " +
                    "LD_PRELOAD_FIELD_DEFINITION WHERE PRELOAD_ROW_TYPE_ID = " + idRowType, new BeanPropertyRowMapper(PreloadFieldDefinitionDTO_OLD.class));
            for(PreloadFieldDefinitionDTO_OLD fieldDefinition : fieldDefinitions)
            {
                fields.put( fieldDefinition.getName(), fieldDefinition.getLength() );
            }
            */

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