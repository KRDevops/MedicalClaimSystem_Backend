
package com.hcl.mediclaim.dto;

public class HospitalResponseDto {

	private Long hospitalId;
	private String hospitalName;
	private String address;
	private String city;
	private String country;
	public Long getHospitalId() {
		return hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
