package com.wander.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "countrydata")
public class CountryData implements Serializable {

	private static final long serialVersionUID = 2747406376333333322L;

	@Id
	@Column(name = "countrydataid")
	private Long countryDataId;

	@Column(name = "activecases")
	private int activeCases;

	@Column(name = "activecasesnew")
	private int activeCasesNew;

	@Column(name = "createddate")
	private Date createdDate;

	@Column(name = "deaths")
	private int deaths;

	@Column(name = "deathsnew")
	private int deathsNew;

	@Column(name = "lastupdatedatapify")
	private String lastUpdatedAtApify;

	@Column(name = "previousdaytests")
	private int previousDayTests;

	@Column(name = "recovered")
	private int recovered;

	@Column(name = "recoverednew")
	private int recoveredNew;

	@Column(name = "totalcases")
	private int totalCases;

	@OneToMany(mappedBy = "countryData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RegionData> regionData;

	public Long getCountryDataId() {
		return countryDataId;
	}

	public void setCountryDataId(Long countryDataId) {
		this.countryDataId = countryDataId;
	}

	public int getActiveCases() {
		return activeCases;
	}

	public void setActiveCases(int activeCases) {
		this.activeCases = activeCases;
	}

	public int getActiveCasesNew() {
		return activeCasesNew;
	}

	public void setActiveCasesNew(int activeCasesNew) {
		this.activeCasesNew = activeCasesNew;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getDeathsNew() {
		return deathsNew;
	}

	public void setDeathsNew(int deathsNew) {
		this.deathsNew = deathsNew;
	}

	public String getLastUpdatedAtApify() {
		return lastUpdatedAtApify;
	}

	public void setLastUpdatedAtApify(String lastUpdatedAtApify) {
		this.lastUpdatedAtApify = lastUpdatedAtApify;
	}

	public int getPreviousDayTests() {
		return previousDayTests;
	}

	public void setPreviousDayTests(int previousDayTests) {
		this.previousDayTests = previousDayTests;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getRecoveredNew() {
		return recoveredNew;
	}

	public void setRecoveredNew(int recoveredNew) {
		this.recoveredNew = recoveredNew;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public List<RegionData> getRegionData() {
		return regionData;
	}

	public void setRegionData(List<RegionData> regionData) {
		this.regionData = regionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + activeCases;
		result = prime * result + activeCasesNew;
		result = prime * result + ((countryDataId == null) ? 0 : countryDataId.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + deaths;
		result = prime * result + deathsNew;
		result = prime * result + ((lastUpdatedAtApify == null) ? 0 : lastUpdatedAtApify.hashCode());
		result = prime * result + previousDayTests;
		result = prime * result + recovered;
		result = prime * result + recoveredNew;
		result = prime * result + ((regionData == null) ? 0 : regionData.hashCode());
		result = prime * result + totalCases;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryData other = (CountryData) obj;
		if (activeCases != other.activeCases)
			return false;
		if (activeCasesNew != other.activeCasesNew)
			return false;
		if (countryDataId == null) {
			if (other.countryDataId != null)
				return false;
		} else if (!countryDataId.equals(other.countryDataId))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (deaths != other.deaths)
			return false;
		if (deathsNew != other.deathsNew)
			return false;
		if (lastUpdatedAtApify == null) {
			if (other.lastUpdatedAtApify != null)
				return false;
		} else if (!lastUpdatedAtApify.equals(other.lastUpdatedAtApify))
			return false;
		if (previousDayTests != other.previousDayTests)
			return false;
		if (recovered != other.recovered)
			return false;
		if (recoveredNew != other.recoveredNew)
			return false;
		if (regionData == null) {
			if (other.regionData != null)
				return false;
		} else if (!regionData.equals(other.regionData))
			return false;
		if (totalCases != other.totalCases)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CountryData [countryDataId=" + countryDataId + ", activeCases=" + activeCases + ", activeCasesNew="
				+ activeCasesNew + ", createdDate=" + createdDate + ", deaths=" + deaths + ", deathsNew=" + deathsNew
				+ ", lastUpdatedAtApify=" + lastUpdatedAtApify + ", previousDayTests=" + previousDayTests
				+ ", recovered=" + recovered + ", recoveredNew=" + recoveredNew + ", totalCases=" + totalCases + "]";
	}

}
