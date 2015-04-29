package org.domain.restfulapi.repository;

import java.util.List;

import org.domain.restfulapi.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	public List<Article> findAllByOrderByDateCreateAsc();
}
