package com.epam.reportportal.migration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
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
	private Step migrateTokensStep;

	@Autowired
	private Step migrateSettingsStep;

	@Autowired
	private Step migrateAuthStep;

	@Autowired
	private MigrationJobExecutionListener migrationJobExecutionListener;

	@Bean
	public Job job() {
		SimpleJobBuilder job = jobBuilderFactory.get("settingsMigrationJob")
				.listener(migrationJobExecutionListener)
				.start(migrateUserStep)
				.next(migrateTokensStep)
				.next(migrateProjectsStep)
				.next(migrateSettingsStep)
				.next(migrateAuthStep);
		return job.build();
	}

}
