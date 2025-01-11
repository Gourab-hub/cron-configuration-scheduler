package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Employee;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;


	// schedule a job to add object in DB (Every 5 sec)
	@Scheduled(fixedRate = 5000)
	public void add2DBJob() {
		Employee user = new Employee();
		user.setName("user" + new Random().nextInt(374483));
		dao.save(user);
		System.out.println("add service call in " + new Date().toString());
	}

	@Scheduled(cron = "0/15 * * * * *")
	public void fetchDBJob() {
		List<Employee> users = dao.findAll();
		System.out.println("fetch service call in " + new Date().toString());
		System.out.println("no of record fetched : " + users.size());
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void deleteAllDB() {
		dao.deleteAll();
		System.out.println("delete all records....");
	}

}