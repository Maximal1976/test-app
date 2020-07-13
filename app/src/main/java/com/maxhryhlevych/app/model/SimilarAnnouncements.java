package com.maxhryhlevych.app.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.restassured.path.json.JsonPath;
import com.maxhryhlevych.app.repo.AnnouncementRepo;

public class SimilarAnnouncements {
	
	private AnnouncementRepo repo;
	
	@Autowired
	private Announcement announcement;
	
	private Optional<Announcement> post;
	
	public SimilarAnnouncements() {
		formattingTheSimilarList();
		findThreeSimilar();
	}

	private List<Optional<Announcement>> findSimilar = new ArrayList<Optional<Announcement>>();
	
	private List<Optional<Announcement>> threeSimilar = new ArrayList<Optional<Announcement>>();
	

    public void formattingTheSimilarList() {
        Pattern pattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        
        Matcher matcherOfTitle = pattern.matcher(announcement.getTitle());
        Matcher matcherOfDescription = pattern.matcher(announcement.getDescription());
        
        SortedSet<String> wordsOfTitle = new TreeSet<>();
        SortedSet<String> wordsOfDescription = new TreeSet<>();

        while (matcherOfTitle.find())
        	wordsOfTitle.add(matcherOfTitle.group().toLowerCase());
        while (matcherOfDescription.find())
        	wordsOfDescription.add(matcherOfDescription.group().toLowerCase());

        for (String wordOfTitle : wordsOfTitle) {
        	
        	String findedTitles = repo.findByTitle(wordOfTitle).toString();
        	
        	for (String wordOfDescription : wordsOfDescription) {
        		
        		String findedDescriptions = repo.findByDescription(wordOfDescription).toString();
            	
            	List<Long> idsOfTitles = new ArrayList<Long>(JsonPath.from(findedTitles).get("id"));
            	List<Long> idsOfDescriptions = new ArrayList<Long>(JsonPath.from(findedDescriptions).get("id"));
            	idsOfDescriptions.retainAll(idsOfTitles);
            	
            	Set<Long> acceptableIds = new HashSet<Long>();
            	acceptableIds.addAll(idsOfDescriptions);
            	
            	for (Long acceptableId : acceptableIds)
            		findSimilar.add(repo.findById(acceptableId));
            }
        }
    }
    
    
    public void findThreeSimilar() {
		int sizeOfSimilarList = findSimilar.size();
		int cycleCount;
		
		if (sizeOfSimilarList < 3) {
			cycleCount = sizeOfSimilarList;
		} else {
			cycleCount = 3;
		}
		
		for (int i = 0; i < cycleCount; i++) {
			post = findSimilar.get(sizeOfSimilarList - 1);
			threeSimilar.add(post);
			sizeOfSimilarList--;
		}
	}
    
}
