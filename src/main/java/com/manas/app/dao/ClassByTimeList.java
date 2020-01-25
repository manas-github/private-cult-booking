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
public class ClassByTimeList {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDisableGroup() {
		return disableGroup;
	}
	public void setDisableGroup(boolean disableGroup) {
		this.disableGroup = disableGroup;
	}
	public List<Classes> getClasses() {
		return classes;
	}
	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}
	String id;
	boolean disableGroup;
	List<Classes> classes;
	
}
