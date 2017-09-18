package com.ss.nitro.analytics.assetlc.domain;

import java.util.List;

public class MapDetails {
    private Coordinates Coordinates;
	private String Region;
	private String Country;
	private String State;
	private String City;
	private List<SubLocElement> Locations;
	
	public Coordinates getCoordinates() {
		return Coordinates;
	}
	public void setCoordinates(Coordinates coordinates) {
		Coordinates = coordinates;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public List<SubLocElement> getLocations() {
		return Locations;
	}
	public void setLocations(List<SubLocElement> locations) {
		Locations = locations;
	}
	
	
}
