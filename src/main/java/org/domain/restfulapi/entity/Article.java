package org.domain.restfulapi.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name="article")
public class Article implements Serializable {

	private long id;
	private String titleArticle;
	private String bodyArticle;
	private String author;
	private DateTime dateCreate;
	private DateTime dateUpdate;
	
	private int version;
	
	public Article() {
    }
	
	public Article(long id, String titleArticle, String bodyArticle,
			String author, DateTime dateCreate, DateTime dateUpdate) {
		super();
		this.id = id;
		this.titleArticle = titleArticle;
		this.bodyArticle = bodyArticle;
		this.author = author;
		this.dateCreate = dateCreate;
		this.dateUpdate = dateUpdate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id")
	public long getId() {
		return id;
	}
	
	@Column(name="title_article")
	public String getTitleArticle() {
		return titleArticle;
	}
	
	@Column(name="body_Article")
	public String getBodyArticle() {
		return bodyArticle;
	}
	
	@Column(name="author")
	public String getAuthor() {
		return author;
	}
	
	@Column(name="date_create") 
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDateCreate() {
		return dateCreate;
	}
	
	@Column(name="date_update") 
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getDateUpdate() {
		return dateUpdate;
	}
	
	@Version
	@Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setTitleArticle(String titleArticle) {
		this.titleArticle = titleArticle;
	}

	public void setBodyArticle(String bodyArticle) {
		this.bodyArticle = bodyArticle;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setDateCreate(DateTime dateCreate) {
		this.dateCreate = dateCreate;
	}

	public void setDateUpdate(DateTime dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        this.dateCreate = now;
        this.dateUpdate = now;
    }

    @PreUpdate
    public void preUpdate() {
    	this.dateUpdate = DateTime.now();
    }
	
	@Override
	public String toString() {
		return "Article [id=" + id + ", titleArticle=" + titleArticle
				+ ", bodyArticle=" + bodyArticle + ", author=" + author
				+ ", dateCreate=" + dateCreate + ", dateUpdate="
				+ dateUpdate + "]";
	}

	public void update(Article article) {
		this.titleArticle = article.titleArticle;
		this.bodyArticle = article.bodyArticle;
		this.author = article.author;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((titleArticle == null) ? 0 : titleArticle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (titleArticle == null) {
			if (other.titleArticle != null)
				return false;
		} else if (!titleArticle.equals(other.titleArticle))
			return false;
		return true;
	}
}

