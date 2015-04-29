package org.domain.restfulapi.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Builder {
	private Article built;

	public Builder(String title) {
		built = new Article();
	}
	
	public Article build() {
		return built;
	}
	
	public Builder titleArticle(String title) {
		built.setBodyArticle(title);
		return this;
	}

	public Builder bodyArticle(String body) {
		built.setBodyArticle(body);
		return this;
	}
	
	public Builder author(String author) {
		built.setAuthor(author);
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
