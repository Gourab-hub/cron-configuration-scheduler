package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/students")
    public List<Student> listAll(Model model) {
        List<Student> listStudents = studentRepository.findAll();
//        model.addAttribute("listStudents", listStudents);
           
        return listStudents;
    }
	

}
