package com.example.demo.DTO;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import lombok.Getter;
import lombok.Setter;


public class AllFileDTO {
	private Integer sheetNumber;
	private Integer rowNumber;
	private Workbook workbook;
	private List<?> response;
	private List<HashMap<String, String>> recordList;
	public List<HashMap<String, String>> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<HashMap<String, String>> recordList) {
		this.recordList = recordList;
	}
	public Integer getSheetNumber() {
		return sheetNumber;
	}
	public void setSheetNumber(Integer sheetNumber) {
		this.sheetNumber = sheetNumber;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	public List<?> getResponse() {
		return response;
	}
	public void setResponse(List<?> response) {
		this.response = response;
	}
	

}
