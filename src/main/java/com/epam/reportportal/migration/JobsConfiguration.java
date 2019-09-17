package com.epam.reportportal.migration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pavel Bortnik
 */
@Configuration
@EnableBatchProcessing
public class JobsConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	@Qualifier("migrateUsersStep")
	private Step migrateUserStep;

	@Autowired
	@Qualifier("migrateProjectsStep")
	private Step migrateProjectsStep;

	@Autowired
	@Qualifier("migrateBtsStep")
	private Step migrateBtsStep;

	@Bean
	public Job job() {
		return jobBuilderFactory.get("job").flow(migrateBtsStep)/*.next(migrateProjectsStep).next(migrateBtsStep)*/.end().build();
	}

}
