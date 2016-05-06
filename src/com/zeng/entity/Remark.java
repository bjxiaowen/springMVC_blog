package com.zeng.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
@Entity
@Table(name="remark")
public class Remark {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Type(type="text")  
	@Column( nullable=true)
	private String content;
	
	@Column(length = 32)
	private String releaseDate;
	
	@Column(length = 32)
	private String remarker;
	
	@Column(insertable = false, nullable = false , updatable = false)
	private Integer remarkArticleid;
	
	@Column(insertable = false, nullable = false , updatable = false)
	private Integer Remarkerid;
	
	@OneToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name="remarkArticleid")
	Article article;
	
	@OneToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name="Remarkerid")
	User_info user_info;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getRemarkArticleid() {
		return remarkArticleid;
	}

	public void setRemarkArticleid(Integer remarkArticleid) {
		this.remarkArticleid = remarkArticleid;
	}

	public Integer getRemarkerid() {
		return Remarkerid;
	}

	public void setRemarkerid(Integer remarkerid) {
		Remarkerid = remarkerid;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User_info getUser_info() {
		return user_info;
	}

	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRemarker() {
		return remarker;
	}

	public void setRemarker(String remarker) {
		this.remarker = remarker;
	}

}
