package com.mariannadreams.service;

import com.mariannadreams.model.News;
import com.mariannadreams.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    public List<News> getAllNews() {
        return newsRepository.findAllByOrderByPublishedDateDescIdDesc();
    }

    @Transactional(readOnly = true)
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News article not found with id: " + id));
    }

    @Transactional
    public News saveNews(News news) {
        if (news.getPublishedDate() == null) {
            news.setPublishedDate(java.time.LocalDateTime.now());
        }
        return newsRepository.save(news);
    }

    @Transactional
    public News updateNews(Long id, News updatedNews) {
        News existingNews = getNewsById(id);
        existingNews.setTitle(updatedNews.getTitle());
        existingNews.setContent(updatedNews.getContent());
        existingNews.setImageUrl(updatedNews.getImageUrl());
        
        if (updatedNews.getPublishedDate() != null) {
            existingNews.setPublishedDate(updatedNews.getPublishedDate());
        }
        
        return newsRepository.save(existingNews);
    }

    @Transactional
    public void deleteNews(Long id) {
        News news = getNewsById(id);
        newsRepository.delete(news);
    }
}
