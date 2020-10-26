package com.wander.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "regiondata")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "countryData" })
public class RegionData implements Serializable {

	private static final long serialVersionUID = -8020892455935710171L;

	@Id
	@Column(name = "regiondataid")
	private Long regionDataId;

	@Column(name = "deceased")
	private int deceased;

	@Column(name = "newdeceased")
	private int newDeceased;

	@Column(name = "newinfected")
	private int newInfected;

	@Column(name = "recovered")
	private int recovered;

	@Column(name = "newrecovered")
	private int newRecovered;

	@Column(name = "region")
	private String region;

	@Column(name = "totalinfected")
	private int totalInfected;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "countryData_countryDataId", nullable = false)
	private CountryData countryData;

	@Column(name = "createddate")
	private Date createddate;

	public Long getRegionDataId() {
		return regionDataId;
	}

	public void setRegionDataId(Long regionDataId) {
		this.regionDataId = regionDataId;
	}

	public int getDeceased() {
		return deceased;
	}

	public void setDeceased(int deceased) {
		this.deceased = deceased;
	}

	public int getNewDeceased() {
		return newDeceased;
	}

	public void setNewDeceased(int newDeceased) {
		this.newDeceased = newDeceased;
	}

	public int getNewInfected() {
		return newInfected;
	}

	public void setNewInfected(int newInfected) {
		this.newInfected = newInfected;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getNewRecovered() {
		return newRecovered;
	}

	public void setNewRecovered(int newRecovered) {
		this.newRecovered = newRecovered;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getTotalInfected() {
		return totalInfected;
	}

	public void setTotalInfected(int totalInfected) {
		this.totalInfected = totalInfected;
	}

	public CountryData getCountryData() {
		return countryData;
	}

	public void setCountryData(CountryData countryData) {
		this.countryData = countryData;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryData == null) ? 0 : countryData.hashCode());
		result = prime * result + ((createddate == null) ? 0 : createddate.hashCode());
		result = prime * result + deceased;
		result = prime * result + newDeceased;
		result = prime * result + newInfected;
		result = prime * result + newRecovered;
		result = prime * result + recovered;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((regionDataId == null) ? 0 : regionDataId.hashCode());
		result = prime * result + totalInfected;
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
		RegionData other = (RegionData) obj;
		if (countryData == null) {
			if (other.countryData != null)
				return false;
		} else if (!countryData.equals(other.countryData))
			return false;
		if (createddate == null) {
			if (other.createddate != null)
				return false;
		} else if (!createddate.equals(other.createddate))
			return false;
		if (deceased != other.deceased)
			return false;
		if (newDeceased != other.newDeceased)
			return false;
		if (newInfected != other.newInfected)
			return false;
		if (newRecovered != other.newRecovered)
			return false;
		if (recovered != other.recovered)
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (regionDataId == null) {
			if (other.regionDataId != null)
				return false;
		} else if (!regionDataId.equals(other.regionDataId))
			return false;
		if (totalInfected != other.totalInfected)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegionData [regionDataId=" + regionDataId + ", deceased=" + deceased + ", newDeceased=" + newDeceased
				+ ", newInfected=" + newInfected + ", recovered=" + recovered + ", newRecovered=" + newRecovered
				+ ", region=" + region + ", totalInfected=" + totalInfected + ", createddate=" + createddate + "]";
	}

}
