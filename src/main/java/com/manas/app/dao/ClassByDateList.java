package com.manas.app.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassByDateList {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<ClassByTimeList> getClassByTimeList() {
		return classByTimeList;
	}
	public void setClassByTimeList(List<ClassByTimeList> classByTimeList) {
		this.classByTimeList = classByTimeList;
	}

	String id;
	List<ClassByTimeList> classByTimeList;
}
