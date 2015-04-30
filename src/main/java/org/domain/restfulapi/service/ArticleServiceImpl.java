package org.domain.restfulapi.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.domain.restfulapi.entity.Article;
import org.domain.restfulapi.exception.ArticleNotFoundException;
import org.domain.restfulapi.repository.ArticleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ArticleService")
@Repository
@Transactional
public class ArticleServiceImpl implements ArticleService {
	
	private Log log = LogFactory.getLog(ArticleServiceImpl.class); 
	
	public ArticleRepository articleRepository;
	
	public ArticleServiceImpl() {
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Article> findAll() {
		List<Article> articles = (List<Article>) articleRepository.findAllByOrderByDateCreateAsc();
		return articles;
	}
	@Transactional(readOnly = true, rollbackFor = {ArticleNotFoundException.class})
	@Override
	public Article findById(long id) throws ArticleNotFoundException {
		Article article = articleRepository.findOne(id);
		return article;
	}
	@Transactional
	@Override
	public Article save(Article article) {
		Article newArticle = articleRepository.save(article);
		return newArticle;
	}
	
	@Transactional(rollbackFor = {ArticleNotFoundException.class})
	public Article update(long id, Article updated) throws ArticleNotFoundException {
		Article articleForUpdate = findById(id);
		articleForUpdate.update(updated);
		return articleForUpdate;
	}
	
	@Transactional(rollbackFor = {ArticleNotFoundException.class})
	@Override
	public void deleteById(long id) throws ArticleNotFoundException {
		Article articleForDelete = findById(id);
		articleRepository.delete(articleForDelete);

	}
	
	

}
