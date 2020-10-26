package com.wander.rs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wander.dto.CountryDataDTO;
import com.wander.service.CovidDataApiService;

@RestController
@RequestMapping("covidapi/")
public class CovidAPIController {

	private Logger logger = LogManager.getLogger(CovidAPIController.class);

	@Autowired
	private CovidDataApiService covidDataApiService;

	@GetMapping("latestCovidData")
	@PreAuthorize("hasRole('ROLE_USER')")
	public CountryDataDTO latestCovidData() {
		if (logger.isDebugEnabled()) {
			logger.debug("Inside userAccount method");
		}
		return covidDataApiService.getLatestCovidData();
	}

}
