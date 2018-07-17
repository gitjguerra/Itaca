package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dao.PreloadDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadFieldDefinitionEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import com.csi.itaca.load.model.dto.*;
import com.csi.itaca.load.repository.PreloadDefinitionRepository;
import com.csi.itaca.load.repository.PreloadFieldDefinitionRepository;
import com.csi.itaca.load.repository.PreloadRowTypeRepository;
import com.csi.itaca.load.service.LoadManagementBatchService;
import com.csi.itaca.load.service.LoadManagementServiceImpl;
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
import org.springframework.dao.DataIntegrityViolationException;

import javax.sql.DataSource;
import java.util.*;

@Configuration
@EnableBatchProcessing
public class JobBatchConfiguration {

    @Autowired
    PreloadDefinitionRepository preloadDefinitionRepository;

    @Autowired
    private PreloadFieldDefinitionRepository preloadFieldDefinitionRepository;

    @Autowired
    private PreloadRowTypeRepository preloadRowTypeRepository;

    @Autowired
    private JobCompletionNotificationListener listener;

    @Autowired
    private LoadManagementServiceImpl managementService;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LoadManagementBatchService batchService;

    // Is necessary for take the parameters
    private static final String WILL_BE_INJECTED = null;
    private Long preloadRowTypeId = 0L;
    private String fileLoadId = "";
    private int line_number = 0;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<PreloadDataDTO> itemReader,
                   ItemProcessor<PreloadDataDTO, PreloadDataDTO> itemProcessor,
                   ItemWriter<PreloadDataDTO> PreloadItemWriter) throws Exception {

        Step step_preload = stepBuilderFactory.get("preload-data-step")
                .<PreloadDataDTO, PreloadDataDTO>chunk(2)
                .reader(itemReader(WILL_BE_INJECTED, WILL_BE_INJECTED, WILL_BE_INJECTED))
                .listener(customReaderListener())
                .processor(processor())
                .writer(new PreloadWriter(batchService))
                .faultTolerant()  // TODO: falta agregar en ld_error_field
                .skipLimit(getSkipLimit())
                .skip(org.springframework.batch.item.file.FlatFileParseException.class)
                .build();
        return jobBuilderFactory.get("preload-data-step")
                .incrementer(new RunIdIncrementer())
                .start(step_preload)          // Preload
                //.next(step_load)            // Load
                .listener(listener)
                .build();
    }

    public Integer getSkipLimit(){

        // TODO: Create a query for find a limit
        //PreloadDefinitionEntity preloadDef = preloadDefinitionRepository.findByPreloadDefinitionEntity(1L);
        //Integer limit = preloadDef.getMaxPreloadHighErrors().intValue();
        Integer limit = 10;
        return limit;
    }

    @Bean
    public CustomReaderListener customReaderListener() {
        return new CustomReaderListener();
    }

    @Bean
    @StepScope
    public ItemProcessor<PreloadDataDTO, PreloadDataDTO> processor() {
        return new ItemProcessor<PreloadDataDTO, PreloadDataDTO>() {
            @Override
            public PreloadDataDTO process(PreloadDataDTO preloadData) throws Exception {
                Long preloadId = preloadData.getPreloadDataId();

                Long loadFileId = Long.valueOf(fileLoadId);
                LoadFileDTO loadFileDTO = new LoadFileDTO();
                loadFileDTO.setLoadFileId(loadFileId);

                String loadedSuccessfully = "1";
                line_number = line_number + 1;
                Long lineNumber = Long.valueOf(line_number);

                PreloadRowTypeEntity typeObject = preloadRowTypeRepository.findByIdentifierValue(preloadData.getDataCol1());
                Long rowTypeId = typeObject.getPreloadRowTypeId();
                PreloadRowTypeDTO preloadRowTypeDTO = new PreloadRowTypeDTO();
                preloadRowTypeDTO.setPreloadRowTypeId(rowTypeId);

                java.util.Date date = new java.util.Date();
                long t = date.getTime();
                java.sql.Date sqlDate = new java.sql.Date(t);
                java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

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

                final PreloadDataDTO fixedCustomer = new PreloadDataDTO(preloadId, loadFileDTO, loadedSuccessfully, sqlDate, preloadRowTypeDTO, lineNumber,
                        dataCol1, dataCol2, dataCol3, dataCol4, dataCol5, dataCol6, dataCol7, dataCol8, dataCol9, dataCol10,
                        dataCol11, dataCol12, dataCol13, dataCol14, dataCol15, dataCol16, dataCol17, dataCol18, dataCol19, dataCol20);
                return fixedCustomer;
            }
        };
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PreloadDataDTO> itemReader(@Value("#{jobParameters['fullPathFileName']}")
                                                              String pathToFile,
                                                      @Value("#{jobParameters['id_load_process']}")
                                                              String id_load_process,
                                                      @Value("#{jobParameters['id_load_file']}")
                                                              String id_load_file) throws Exception {

        FlatFileItemReader<PreloadDataDTO> reader = new FlatFileItemReader<>();
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

    public FieldSetMapper<PreloadDataDTO> preloadFieldSetMapper() {
        return new PreloadFieldSetMapper();
    }

    public PatternMatchingCompositeLineMapper preloadLineMapper(String id_load_process, String id_load_file) {
        PatternMatchingCompositeLineMapper lineMapper =
                new PatternMatchingCompositeLineMapper();

        LinkedHashMap<String,Long> fields = new LinkedHashMap<>();
        LinkedHashMap<String,Integer> characterMapper = new LinkedHashMap<>();
        int cont = 1;

        Map<String, LineTokenizer> tokenizers = new HashMap<>(3);

        List<PreloadRowTypeEntity>  rowTypes = managementService.rowTypesServices(Long.valueOf(id_load_process));
        for(PreloadRowTypeEntity rowType : rowTypes)
        {
            preloadRowTypeId = rowType.getPreloadRowTypeId().longValue();
            PreloadRowTypeEntity rowTypeEntity = new PreloadRowTypeEntity();
            rowTypeEntity.setPreloadRowTypeId(preloadRowTypeId);
            List<PreloadFieldDefinitionEntity>  fieldDefinitions = preloadFieldDefinitionRepository.findByPreloadRowTypeId(rowTypeEntity);
            for(PreloadFieldDefinitionEntity fieldDefinition : fieldDefinitions)
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