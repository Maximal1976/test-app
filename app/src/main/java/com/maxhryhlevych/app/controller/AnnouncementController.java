package com.maxhryhlevych.app.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxhryhlevych.app.model.Announcement;
import com.maxhryhlevych.app.repo.AnnouncementRepo;


@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {
	
	@Autowired
	private AnnouncementRepo repo;

	public AnnouncementController(AnnouncementRepo repo) {
		this.repo = repo;
	}
	
	@GetMapping
	public List<Announcement> findAllAnnouncements() {
		return repo.findAll();
	}
	
	@GetMapping("{id}")
	public Announcement getOne(@PathVariable("id") Announcement obj) {
		return obj;
	}
	
	@PostMapping
	public Announcement add(@RequestBody Announcement obj) {
		return repo.save(obj);
	}
	
	@PutMapping("{id}")
	public Announcement update(@PathVariable("id") Announcement dbObj, @RequestBody Announcement obj) {
		BeanUtils.copyProperties(obj, dbObj, "id", "dateAdded");
		
		return repo.save(dbObj);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") Announcement dbObj) {
		repo.delete(dbObj);
	}
	
}
