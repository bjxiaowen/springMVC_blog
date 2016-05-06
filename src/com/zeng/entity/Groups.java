package com.zeng.entity;
//·Ö×é
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="groups")
public class Groups {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String groupName;
	@Column(insertable = false, nullable = false, updatable = false)
	private Integer belongUserid;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User_role.class,mappedBy = "user_info")
	private Set<Attend> attend;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "belongUserid")
	private User_info user_info;
	public User_info getUser_info() {
		return user_info;
	}

	public void setUser_info(User_info user_info) {
		this.user_info = user_info;
	}

	public Set<Attend> getAttend() {
		return attend;
	}

	public void setAttend(Set<Attend> attend) {
		this.attend = attend;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBelongUserid() {
		return belongUserid;
	}

	public void setBelongUserid(Integer belongUserid) {
		this.belongUserid = belongUserid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
