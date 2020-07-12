package com.maxhryhlevych.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxhryhlevych.app.model.Announcement;

@Repository
public interface AnnouncementRepo extends JpaRepository<Announcement, Long> {
	List<Announcement> findByTitleAndDescription(String title, String description);
}
