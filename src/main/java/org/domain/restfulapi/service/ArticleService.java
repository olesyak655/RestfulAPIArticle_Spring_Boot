package org.domain.restfulapi.service;
import java.util.List;

import org.domain.restfulapi.entity.Article;
import org.domain.restfulapi.exception.ArticleNotFoundException;

public interface ArticleService {
	public List<Article> findAll();
	public Article findById(long id) throws ArticleNotFoundException;
	public Article save(Article article);
	public Article update(Article article) throws ArticleNotFoundException;
	public void deleteById(long id) throws ArticleNotFoundException;
	
}
