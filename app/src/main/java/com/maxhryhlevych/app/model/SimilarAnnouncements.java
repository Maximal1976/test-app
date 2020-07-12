package com.maxhryhlevych.app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.maxhryhlevych.app.repo.AnnouncementRepo;

public class SimilarAnnouncements {
	
	private AnnouncementRepo repo;
	
	@Autowired
	private Announcement announcement;
	
	private Announcement post;
	
	public SimilarAnnouncements(List<Announcement> threeSimilar) {
		this.threeSimilar = threeSimilar;
	}

	public List<Announcement> findSimilar() {
		return repo.findByTitleAndDescription(announcement.getTitle(), announcement.getDescription());
	}
	
	private List<Announcement> threeSimilar = new ArrayList<Announcement>();
	
	public void findThreeSimilar() {
		int sizeOfSimilarList = findSimilar().size();
		int cycleCount;
		
		if (sizeOfSimilarList < 3) {
			cycleCount = sizeOfSimilarList;
		} else {
			cycleCount = 3;
		}
		
		for (int i = 0; i < cycleCount; i++) {
			post = findSimilar().get(sizeOfSimilarList - 1);
			threeSimilar.add(post);
			sizeOfSimilarList--;
		}
	}

}
