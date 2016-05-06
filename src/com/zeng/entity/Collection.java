package com.zeng.entity;
// ’≤ÿ
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="collection")
public class Collection {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(insertable = false, nullable = false , updatable = false)
	private Integer Articleid;
	
	@Column(insertable = false, nullable = false ,updatable = false)
	private Integer collectorid;

	@Column
	private String articleName;
	
	@OneToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name="Articleid" )
	Article article;

	@OneToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name="collectorid")
	User_info user_info;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleid() {
		return Articleid;
	}

	public void setArticleid(Integer articleid) {
		Articleid = articleid;
	}

	public Integer getCollectorid() {
		return collectorid;
	}

	public void setCollectorid(Integer collectorid) {
		this.collectorid = collectorid;
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

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

}
