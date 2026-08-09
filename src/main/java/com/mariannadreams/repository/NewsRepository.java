package com.mariannadreams.repository;

import com.mariannadreams.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    
    // Custom query method to get news ordered by publish date descending
    List<News> findAllByOrderByPublishedDateDesc();
}
