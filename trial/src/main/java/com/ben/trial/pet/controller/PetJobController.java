package com.ben.trial.pet.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/petjob")
public class PetJobController {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	

	@PostMapping("/import")
	public void importFile2DBJob() {
		JobParameters jobParams = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();
		
		try {
			jobLauncher.run(job, jobParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
