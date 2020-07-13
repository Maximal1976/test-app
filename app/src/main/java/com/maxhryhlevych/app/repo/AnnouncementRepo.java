package com.maxhryhlevych.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxhryhlevych.app.model.Announcement;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {
	@Query("select a from Announcement a where title like %?1%")
	List<Announcement> findByTitle(String title);
	
	@Query("select a from Announcement a where description like %?1%")
	List<Announcement> findByDescription(String description);
}
