package com.zeng.entity;

import java.util.Arrays;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user_info")
public class User_info {

	// @Id
	// @GeneratedValue(generator="native")
	// @GenericGenerator(name = "native",strategy="native")
	// @Column(length=32)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	@Size(min = 5, max = 32, message = "账号长度应该在5和30之间")
	@Column(length = 32)
	private String userName;

//	@Length(max = 32, min = 4, message = "4-30位字符")
//	@Pattern(regexp = "^\\w+$", message = "4-30位字符")
	@Column(length = 32)
	private String password;
	//@Length(max = 15, min = 1, message = "1-15位字符")
	@Column(length = 32)
	private String name;
	@Column(length = 32)
	private String gender;
//	@Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$", message = "邮箱格式不正确")
	@Column(length = 32)
	private String email;
	@Column(length = 32)
	private String birthday;
	@Column(length = 32)
	private String province;
	@Column(length = 32)
	private String city;
	@Column
	private Integer QQ;
	@Column
	private Integer  phoneNumber;
	
	@Column
	private String headImgName;
	@Lob
	@Column(columnDefinition = "mediumblob")
	private byte[] headImg;
	
	public User_info(String userName, String name, String password) {
		this.userName = userName;
		this.password = password;
		this.name = name;
	}

	public User_info() {
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User_role.class,mappedBy = "user_info")
	private Set<User_role> user_role;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Article.class,mappedBy = "user_info" )
	private Set<Article> article;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Article.class,mappedBy = "user_info" )
	private Set<Groups> group;
	
	public Set<Groups> getGroup() {
		return group;
	}

	public void setGroup(Set<Groups> group) {
		this.group = group;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<User_role> getUser_role() {
		return user_role;
	}

	public void setUser_role(Set<User_role> user_role) {
		this.user_role = user_role;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Article> getArticle() {
		return article;
	}

	public void setArticle(Set<Article> article) {
		this.article = article;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getQQ() {
		return QQ;
	}

	public void setQQ(Integer qQ) {
		QQ = qQ;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getHeadImgName() {
		return headImgName;
	}

	public void setHeadImgName(String headImgName) {
		this.headImgName = headImgName;
	}

	public byte[] getHeadImg() {
		return headImg;
	}

	public void setHeadImg(byte[] headImg) {
		this.headImg = headImg;
	}

	@Override
	public String toString() {
		return "User_info [id=" + id + ", userName=" + userName + ", password="
				+ password + ", name=" + name + ", gender=" + gender
				+ ", email=" + email + ", birthday=" + birthday + ", province="
				+ province + ", city=" + city + ", QQ=" + QQ + ", phoneNumber="
				+ phoneNumber + ", headImgName=" + headImgName + ", headImg="
				+ Arrays.toString(headImg) + ", user_role=" + user_role
				+ ", article=" + article + ", group=" + group + "]";
	}



}
