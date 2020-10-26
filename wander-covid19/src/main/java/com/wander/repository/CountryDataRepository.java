package com.wander.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wander.entity.CountryData;

@Repository
public interface CountryDataRepository extends JpaRepository<CountryData, Long>{
	
	@Query("FROM CountryData ORDER BY createdDate DESC")
	public List<CountryData> findLatestCovidData();

}
