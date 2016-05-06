package com.zeng.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(insertable = false, nullable = false ,updatable = false)
	private int belongUserid;

	@Column(nullable = false ,columnDefinition="INT default 0")
	private Integer praiseNum;
	
	@Column(nullable = false ,columnDefinition="INT default 0")
	private Integer collectNum;
	
	@Column(nullable = false ,columnDefinition="INT default 0")
	private Integer remarkNum;
	
	@Column(nullable = false ,columnDefinition="INT default 0")
	private Integer reproduceNum;
	
	@Column
	private String headImgName;
	
	@Column(length = 32)
	private String releaseDate;

	@Column(nullable = false ,columnDefinition="INT default 0")
	private Integer isReproduce;
	
	@Column(length = 32)
	private String belongUserName;
	
	public String getBelongUserName() {
		return belongUserName;
	}

	public void setBelongUserName(String belongUserName) {
		this.belongUserName = belongUserName;
	}

	@Column(length = 32)
	private String title;

	@Type(type="text")  
	@Column( nullable=true)
	private String content;

	@Column(length = 32)
	private String imgsName;
	
	@Column(length = 32)
	private String videoName;
	
	@Column(length = 32)
	private String musicName;
	@Lob
	@Column(columnDefinition = "mediumblob")
	private byte[] imgs;

	@Lob
	@Column(columnDefinition = "mediumblob")
	private byte[] video;
	
	@Lob
	@Column(columnDefinition = "mediumblob")
	private byte[] music;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	// JoinColumn��ʾ������
	@JoinColumn(name="belongUserid")
	private User_info user_info;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getBelongUserid() {
		return belongUserid;
	}

	public void setBelongUserid(int belongUserid) {
		this.belongUserid = belongUserid;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getImgs() {
		return imgs;
	}

	public void setImgs(byte[] imgs) {
		this.imgs = imgs;
	}

	public byte[] getVideo() {
		return video;
	}

	public void setVideo(byte[] video) {
		this.video = video;
	}

	public byte[] getMusic() {
		return music;
	}

	public void setHeadImgName(String headImgName) {
		this.headImgName = headImgName;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public Integer getRemarkNum() {
		return remarkNum;
	}

	public void setRemarkNum(Integer remarkNum) {
		this.remarkNum = remarkNum;
	}

	public void setMusic(byte[] music) {
		this.music = music;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public Integer getIsReproduce() {
		return isReproduce;
	}

	public void setIsReproduce(Integer isReproduce) {
		this.isReproduce = isReproduce;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public User_info getUser_info() {
		return user_info;
	}

	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}

	public String getImgsName() {
		return imgsName;
	}

	public void setImgsName(String imgsName) {
		this.imgsName = imgsName;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getMusicName() {
		return musicName;
	}

	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	public Integer getReproduceNum() {
		return reproduceNum;
	}

	public void setReproduceNum(Integer reproduceNum) {
		this.reproduceNum = reproduceNum;
	}

	public Object getHeadImgName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
