package com.ben.trial.pet.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.ben.trial.pet.model.Pet;
import com.ben.trial.pet.repository.PetRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class PetBatchConfig {

	@Autowired
	private JobBuilderFactory jbFactory;
	@Autowired
	private StepBuilderFactory sbFactory;
	@Autowired
	private PetRepository petRepository;
	
	public PetBatchConfig() {	}

	@Bean
	public FlatFileItemReader<Pet> petFileRead(){
		FlatFileItemReader<Pet> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/Data.txt"));
		itemReader.setName("PetReader");
		itemReader.setLineMapper(lineMapper());
		
		return itemReader;
	}
	
	@Bean
	public LineMapper<Pet> lineMapper(){
		DefaultLineMapper<Pet> dlMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer dlTokenizer = new DelimitedLineTokenizer();
		dlTokenizer.setDelimiter("\t");
		dlTokenizer.setStrict(false);
		dlTokenizer.setNames("Id", "Name", "Animal", "Price");
		
		BeanWrapperFieldSetMapper<Pet> fieldsetMapper = new BeanWrapperFieldSetMapper<>();
		fieldsetMapper.setTargetType(Pet.class);
	
		dlMapper.setLineTokenizer(dlTokenizer);
		dlMapper.setFieldSetMapper(fieldsetMapper);
		
		return dlMapper;
	}
	
	@Bean
	public PetProcessor processor(){
		return new PetProcessor();
	}
	
	@Bean
	public RepositoryItemWriter<Pet> petFileWrite(){
		RepositoryItemWriter<Pet> writer = new RepositoryItemWriter<>();
		writer.setRepository(petRepository);
		writer.setMethodName("save");
		
		return writer;
	}
	
	@Bean
	public Step step1() {
		return sbFactory.get("txt-step").<Pet,Pet>chunk(5)
				.reader(petFileRead())
				.processor(processor())
				.writer(petFileWrite())
				.build();
	}
	
	@Bean
	public Job runJob() {
		return jbFactory.get("importPet").flow(step1()).end().build();
	}
	
//	public TaskExecutor taskExecutor() {
//		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//		asyncTaskExecutor.setConcurrencyLimit(10);
//		return taskExecutor();
//	}
}
