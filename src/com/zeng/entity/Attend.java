package com.zeng.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="attend")
public class Attend {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private Integer friendid;
	
	@Column
	private Integer userid;
	
	@Column(insertable = false, nullable = false, updatable = false)
	private Integer groupId;
	@Column
	private String friendName;
	@Column
	private String nickName;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "groupId")
	private Groups group;
	
	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Integer getFriendid() {
		return friendid;
	}

	public void setFriendid(Integer friendid) {
		this.friendid = friendid;
	}


	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "Attend [id=" + id + ", friendid=" + friendid + ", userid="
				+ userid + ", groupId=" + groupId + ", friendName="
				+ friendName + ", nickName=" + nickName + "]";
	}

}
