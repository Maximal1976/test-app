package com.maxhryhlevych.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;
import com.maxhryhlevych.app.model.Announcement;
import com.maxhryhlevych.app.repo.AnnouncementRepo;

@Service("similar")
public class SimilarAnnouncements {
	
	@Autowired
	private AnnouncementRepo repo;
		
	public SimilarAnnouncements() {
		super();
	}

	private Optional<Announcement> post;
	
	
	private String convertJSONtoString(Announcement optional) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(optional);
	}
	
	private String convertJSONtoString(List<Announcement> announcement) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(announcement);
	}

	
	public List<Optional<Announcement>> getSimilarAnnouncements(Long id) throws JsonProcessingException {
		Announcement announcementById = repo.findById(id).get();
		if (announcementById != null) {
			String title = JsonPath.from(convertJSONtoString(announcementById)).get("title");
			String description = JsonPath.from(convertJSONtoString(announcementById)).get("description");
			formatTheSimilarList(title, description);
			findThreeSimilar();
		} 
		return threeSimilar;
	}

	private List<Optional<Announcement>> findSimilar = new ArrayList<Optional<Announcement>>();
	private List<Optional<Announcement>> threeSimilar = new ArrayList<Optional<Announcement>>();
	
	private void format(String field, Set<String> wordsOfField) {
		Pattern pattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher matcherOfField = pattern.matcher(field);
        
        while (matcherOfField.find())
        	wordsOfField.add(matcherOfField.group().toLowerCase());
	}
	
    private void formatTheSimilarList(String title, String description) throws JsonProcessingException {      
    	Set<String> wordsOfTitle = new TreeSet<String>();
    	format(title, wordsOfTitle);
    	Set<String> wordsOfDescription = new TreeSet<String>();
    	format(description, wordsOfDescription);

    	String findedTitles;
    	String findedDescriptions;
    	Set<Integer> idsOfTitles = new HashSet<Integer>();
    	Set<Integer> idsOfDescriptions = new HashSet<Integer>();
        
    	for (String wordOfTitle : wordsOfTitle) {
    		findedTitles = convertJSONtoString(repo.findByTitle(wordOfTitle));
    		idsOfTitles.addAll(JsonPath.from(findedTitles).get("id"));
    	}
    	
    	for (String wordOfDescription : wordsOfDescription) {
    		findedDescriptions = convertJSONtoString(repo.findByDescription(wordOfDescription));
    		idsOfDescriptions.addAll(JsonPath.from(findedDescriptions).get("id"));
    	}

    	idsOfDescriptions.retainAll(idsOfTitles);
            	
    	for (Integer acceptableId : idsOfDescriptions) {
    		long finalId = acceptableId;
    		findSimilar.add(repo.findById(finalId));
    	}	
    }
    
    
    private void findThreeSimilar() {
		int sizeOfSimilarList = findSimilar.size();
		int cycleCount;
		
		if (sizeOfSimilarList < 3) 
			cycleCount = sizeOfSimilarList;
		else 
			cycleCount = 3;
		
		for (int i = 0; i < cycleCount; i++) {
			post = findSimilar.get(sizeOfSimilarList - 1);
			threeSimilar.add(post);
			sizeOfSimilarList--;
		}
	}
}
