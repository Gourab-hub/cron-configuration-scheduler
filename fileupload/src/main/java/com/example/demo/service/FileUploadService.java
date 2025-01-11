package com.example.demo.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.AllFileDTO;

@Service
public class FileUploadService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AllFileDTO fileLoad(AllFileDTO fileDTO) {

		List<HashMap<String, String>> recordList = new ArrayList<>();
		Sheet sheet = fileDTO.getWorkbook().getSheetAt(fileDTO.getSheetNumber());
		String[] oemKeyList = { "name", "city" };
		int i = 1;
		for (int rn = 0; rn <= sheet.getLastRowNum(); rn++) {
			List<String> rowVal = new ArrayList<>();
			if (i >= fileDTO.getRowNumber()) {
				for (int cn = 0; cn < sheet.getRow(rn).getLastCellNum(); cn++) {
					rowVal.add(new DataFormatter().formatCellValue(sheet.getRow(rn).getCell(cn)));
				}
				HashMap<String, String> record = null;
				try {
					record = populateOEMRequestMap(rowVal, oemKeyList);
				} catch (Exception e) {
					// TODO: handle exception
				}
				recordList.add(record);
			}
			i++;
		}
		fileDTO.setRecordList(recordList);
		saveDBusingJdbc(fileDTO);
		return null;
	}

	private void saveDBusingJdbc(AllFileDTO fileDTO) {
       String sql="INSERT INTO public.student(id, name, city) VALUES (nextval('student_id_seq'), ?, ?)";
       jdbcTemplate.execute(sql,new PreparedStatementCallback<int[]>() {
		@Override
		public int[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			 tranformer(ps,fileDTO);
	    	 return ps.executeBatch();
		}

		private void tranformer(PreparedStatement ps, AllFileDTO fileDTO) throws SQLException {
			for (HashMap<String, String> record : fileDTO.getRecordList()) {
				ps.setString(1, record.get("name")); 
				ps.setString(2, record.get("city")); 
				ps.addBatch();
			}
			
		}});
		
	}

	private HashMap<String, String> populateOEMRequestMap(List<String> oemRequestString, String[] oemKeyList) {
		Map<String, String> oemRequestMap = new HashMap<>();
		for (int j = 0; j < oemKeyList.length; j++) {
			String value = "";
			if (j < oemRequestString.size()) {
				value = oemRequestString.get(j);
			}
			oemRequestMap.put(oemKeyList[j].trim(), value);
		}
		return (HashMap<String, String>) oemRequestMap;
	}

}
