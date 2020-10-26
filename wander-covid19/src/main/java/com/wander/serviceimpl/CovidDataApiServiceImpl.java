package com.wander.serviceimpl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wander.constant.APIConstants;
import com.wander.dto.CountryDataDTO;
import com.wander.entity.CountryData;
import com.wander.repository.CountryDataRepository;
import com.wander.service.CovidDataApiService;
import com.wander.utility.RegistrationUtility;

@Service
public class CovidDataApiServiceImpl implements CovidDataApiService {

	private Logger logger = LogManager.getLogger(CovidDataApiServiceImpl.class);

	@Autowired
	private CountryDataRepository countryDataRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CountryDataDTO getLatestCovidData() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetch latest covid-19 data");
		}
		Date date = new Date();
		try {
			CountryData countryData = restTemplate.getForObject(APIConstants.APIFY_COVID_DATA_URL, CountryData.class);
			if (countryData != null) {
				countryData.setCountryDataId(RegistrationUtility.getUniqueID());
				countryData.setCreatedDate(date);
				countryData.getRegionData().forEach(region -> {
					region.setRegionDataId(RegistrationUtility.getUniqueID());
					region.setCreateddate(date);
					region.setCountryData(countryData);
				});
				countryDataRepository.save(countryData);
				return mapToCountryDataDTO(countryData);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Unable to fetch latest covid-19 data from 3rd party API");
				}
				return getLatestCovidDataDB();
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Exception occured while fetching covid-19 data from 3rd party API");
			}
			return getLatestCovidDataDB();
		}
	}

	private CountryDataDTO getLatestCovidDataDB() {
		if (logger.isDebugEnabled()) {
			logger.debug("Fetching latest covid-19 data from DB");
		}
		List<CountryData> countryDataList = countryDataRepository.findLatestCovidData();
		return mapToCountryDataDTO(countryDataList.get(0));
	}

	private CountryDataDTO mapToCountryDataDTO(CountryData countryData) {
		if (logger.isDebugEnabled()) {
			logger.debug("Maping CountryData entity to CountryDataDTO");
		}
		return modelMapper.map(countryData, CountryDataDTO.class);
	}

}
