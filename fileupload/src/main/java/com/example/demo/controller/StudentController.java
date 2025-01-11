package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.AllFileDTO;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.FileUploadService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private FileUploadService fileUploadService;

	@GetMapping("/students")
	public List<Student> listAll(Model model) {
		List<Student> listStudents = studentRepository.findAll();
//        model.addAttribute("listStudents", listStudents);

		return listStudents;
	}

	@PostMapping(value = "/fileUpload", produces = "application/json")
	public @ResponseBody List<?> uploadExcelFile(@RequestParam("sheetNumber") Integer sheetNumber,
			@RequestParam("rowNumber") Integer rowNumber, @RequestParam("fileName") String fileName,
			HttpServletRequest httpRequest) throws IOException {

		AllFileDTO fileDTO = new AllFileDTO();
		fileDTO.setSheetNumber(sheetNumber);
		fileDTO.setRowNumber(rowNumber);
		InputStream in = httpRequest.getInputStream();
		String[] h1 = fileName.split("\\.");
		Workbook workbook = null;
		if (h1[h1.length - 1].equalsIgnoreCase("xlsx")) {
			workbook = new XSSFWorkbook(in);
		} else {
			workbook = new HSSFWorkbook(in);
		}
		fileDTO.setWorkbook(workbook);
		fileDTO = fileUploadService.fileLoad(fileDTO);
//		List<?> response = fileDTO.getResponse();
		return null;
	}

}
