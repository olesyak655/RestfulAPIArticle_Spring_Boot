package org.domain.restfulapi.controller;

import java.util.List;

import org.domain.restfulapi.entity.Article;
import org.domain.restfulapi.exception.ArticleNotFoundException;
import org.domain.restfulapi.service.ArticleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value="/articles")
@RestController
public class ArticleController {

	@Autowired 
	private ArticleService articleService;
	
	public ArticleController() {
		
	}
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public  List<Article> findAll() {
		return articleService.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public  Article findById(@PathVariable Long id) throws ArticleNotFoundException{
		Article article = articleService.findById(id);
		return article;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST) 
	@ResponseBody 
	public Article create(@RequestBody Article article) { 
		Article newArticle = articleService.save(article); 
		return newArticle;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) 
	@ResponseBody 
	public Article update(@PathVariable Long id, @RequestBody Article article) throws ArticleNotFoundException { 
		return articleService.update(id, article); 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
	public void delete(@PathVariable Long id) throws ArticleNotFoundException { 
		articleService.deleteById(id); 
	}
}
	
