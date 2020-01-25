package com.manas.app.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassesDao {

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ClassByDateList> getClassByDateList() {
		return classByDateList;
	}
	public void setClassByDateList(List<ClassByDateList> classByDateList) {
		this.classByDateList = classByDateList;
	}
	public List<ClassesDays> getDays() {
		return days;
	}
	public void setDays(List<ClassesDays> days) {
		this.days = days;
	}
	public AddressInfo getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}
	String title;
	List<ClassByDateList> classByDateList;
	List<ClassesDays> days;
	AddressInfo addressInfo;
}