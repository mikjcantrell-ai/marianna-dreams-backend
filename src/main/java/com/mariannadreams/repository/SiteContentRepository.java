package com.mariannadreams.repository;

import com.mariannadreams.model.SiteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SiteContentRepository extends JpaRepository<SiteContent, String> {
    List<SiteContent> findAllByOrderBySectionAscKeyAsc();
}
