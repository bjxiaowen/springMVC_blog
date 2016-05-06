package com.zeng.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.directwebremoting.json.types.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.utils.Page;
import com.zeng.entity.Article;
import com.zeng.entity.Attend;
import com.zeng.entity.Collection;
import com.zeng.entity.Groups;
import com.zeng.entity.Remark;
import com.zeng.entity.Reproduce;
import com.zeng.entity.User_info;
import com.zeng.manager.ArticleManager;
import com.zeng.manager.UserManager;

@Controller
// @RequestMapping
public class UserController {

	// 设置用户信息显示每页用户数
	private final int pageSize = 5;

	@Autowired
	private UserManager userManager;
	@Autowired
	private ArticleManager articleManager;

	// 登陆二次处理
	@RequestMapping(value = "/dologin")
	public void dologin(
			@RequestParam(value = "errorMsg", required = false) String errorMsg,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		if (errorMsg != null) {
			response.getWriter().print("fail");
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			String userName = userDetails.getUsername();
			User_info user = userManager.getUserByUserName(userName);
			System.out.println("**************\n" + user.getId()
					+ user.getUserName() + "/n*****************");
			request.getSession().setAttribute("user", user);
		}
	}

	// 进入list界面
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "toshow")
	public String toshow() {
		return "list";
	}

	// 进入主页
	// 1：展示博客内容
	@RequestMapping(value = "/main")
	public ModelAndView mainView(
			@RequestParam(value = "pageNow", required = false) Integer pageNow) {
		ModelAndView mav = new ModelAndView("main");
		pageNow = pageNow == null ? 1 : pageNow;
		List<Article> list = articleManager.getArticle(pageNow, 3);
		if (list == null) {
			mav.addObject("errorMsg", "当前没有任何文章");
		}
		mav.addObject("article", list);
		return mav;
	}

	// ajax读取文章展示
	@RequestMapping(value = "/ajaxShowArticle")
	public void ajaxShowArticle(
			@RequestParam(value = "pageNow") Integer pageNow,
			HttpServletResponse response) throws IOException {
		List<Article> list = articleManager.getArticle(pageNow + 3, 1);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if (list == null) {
			jsonObject.put("id", "0");
		} else {
			Article article = list.get(0);
			// ajax拼装输出,需要 作者、时间、标题、内容、赞/评论/转发/收藏量、id
			// JSONArray jsonArray = new JSONArray();
			jsonObject.put("id", article.getId());
			jsonObject.put("releaseDate", article.getReleaseDate());
			jsonObject.put("belongUserName", article.getBelongUserName());
			jsonObject.put("belongUserId", article.getBelongUserid());
			jsonObject.put("title", article.getTitle());
			jsonObject.put("content", article.getContent());
			jsonObject.put("praiseNum", article.getPraiseNum());
			jsonObject.put("remarkNum", article.getRemarkNum());
			jsonObject.put("reproduceNum", article.getReproduceNum());
			jsonObject.put("collectNum", article.getCollectNum());
			jsonObject.put("imgsName", article.getImgsName());
			jsonObject.put("musicName",article.getMusic());
			jsonObject.put("headImgName", article.getHeadImgName());
			out.print(jsonObject);
			System.out.println(jsonObject + "\n\n\n");
		}
		out.close();
	}
	//读取音乐
	@RequestMapping(value="/showMusic")
	public void showMusic(@RequestParam("articleid") Integer articleid,HttpServletResponse response){
		byte[] data = null;
		Article article = articleManager.getArticlebyId(articleid);
		data = article.getMusic();
		try {
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//读取视频
	@RequestMapping(value="/showVideo")
	public void showVideo(@RequestParam("articleid") Integer articleid,HttpServletResponse response){
		byte[] data = null;
		Article article = articleManager.getArticlebyId(articleid);
		data = article.getVideo();
		try {
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 读取图片
	@RequestMapping(value = "/showImgs")
	public void showImgs(@RequestParam("articleid") Integer articleid,
			@RequestParam(value = "imgKind", required = false) String imgKind,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// System.out.println("\n\n\n"+articleid+"\n******************\n");
		byte[] data = null;
		if (imgKind == null) {
			Article article = articleManager.getArticlebyId(articleid);
			data = article.getImgs();
		} else {
			User_info user = userManager.getUserByUserId(articleid);
			data = user.getHeadImg();
		}
		response.setContentType("img/jpeg");
		try {
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 添加文章
	@RequestMapping(value = "/addArticle")
	@PreAuthorize("hasRole('ROLE_USER')")
	public void addArticle(Article article,
			@RequestParam(value = "fileName") String fileName,
			@RequestParam(value = "fileType") String fileType,
			@RequestParam(value = "file") MultipartFile file)
			throws IOException {
		System.out.println("\n\\n\n" + article.getContent() + "\nn\nn");
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			if (fileType.equals("imgs")) {
				article.setImgs(bytes);
				article.setImgsName(fileName);
			} else if (fileType.equals("music")) {
				article.setMusic(bytes);
				article.setMusicName(fileName);
			} else if (fileType.equals("video")) {
				article.setVideo(bytes);
				article.setVideoName(fileName);
			}
		}
		articleManager.addArticle(article, userDetails.getUsername());
	}

	// 点赞
	@RequestMapping(value = "/addPraise")
	@PreAuthorize("hasRole('ROLE_USER')")
	public void addPraise(@RequestParam("articleid") Integer articleid,
			HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		PrintWriter out = response.getWriter();
		String userName = userDetails.getUsername();
		if (articleManager.addPraise(userName, articleid)) {
			out.print("success");
		} else {
			out.print("fail");
		}
		out.close();
	}

	// 添加评论
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_BOSS')")
	@RequestMapping(value = "/addRemark", method = RequestMethod.POST)
	public void addRemark(@RequestParam(value = "articleid") Integer articleid,
			Remark remark, HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		articleManager.addRemark(articleid, userName, remark);
		PrintWriter out = response.getWriter();
		out.print("success");
		out.close();
	}

	// 分页展示评论（json）
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/showRemark")
	public void showRemark(HttpServletResponse response,
			@RequestParam(value = "pageNow", required = false) Integer pageNow,
			@RequestParam(value = "articleid") Integer articleid)
			throws IOException {
		pageNow = (pageNow == null ? 1 : pageNow);
		PrintWriter out = response.getWriter();
		List<Remark> list = articleManager.listRemarkbyPage(pageNow, pageSize,
				articleid);
		if (list == null) {
			out.print("none");
		} else {
			Page page = new Page(pageNow, this.pageSize,
					articleManager.getRemarkCount(articleid));
			if (page.getPageNow() > page.getTotalPage()) {
				page.setPageNow(page.getTotalPage());
			}
			// 拼装json 1:list,2:page
			JSONArray jsonArray = new JSONArray();
			for (Remark remark : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("remarker", remark.getRemarker());
				jsonObject.put("content", remark.getContent());
				jsonObject.put("releaseDate", remark.getReleaseDate());
				jsonArray.add(jsonObject);
			}
			// System.out.println(jsonArray+"\n\n\n");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("remark", jsonArray);
			map.put("page", page);
			JSONObject jsonObject = JSONObject.fromObject(map);
			// System.out.println(jsonObject.toString());//输出
			out.print(jsonObject.toString());
		}
		out.close();
	}

	// 用户名验证
	@RequestMapping(value = "/checkUserName")
	public void checkUserName(
			@RequestParam(value = "userName") String userName,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		if (userManager.getUserByUserName(userName) == null) {
			out.print("true");
		} else {
			out.print("false");
		}
		out.close();
	}

	// 转载
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "reproduce")
	// 获取参数：文章id、转载区域、转发时间
	public void reproduce(
			Reproduce reproduce,
			@RequestParam(value = "personalMsg", required = false) String personalMsg,
			@RequestParam(value = "frienduserName", required = false) String frienduserName,
			@RequestParam(value = "content", required = false) String content,
			HttpServletResponse response) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		// 先看看是否转载过（暂时忽略）
		if (frienduserName == null) {
			// 添加评论
			Remark remark = new Remark();
			remark.setContent(content);
			articleManager
					.addRemark(reproduce.getArticleid(), userName, remark);
		} else {
			// 给好友发私信
			// articleManager
		}
		articleManager.addReproduce(userName, reproduce);
	}

	// 收藏
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "collection")
	// 添加的标签（类别）拓展json接收
	public void collection(
			@RequestParam(value = "tagName", required = false) String tagName,
			HttpServletResponse response,
			@RequestParam(value = "articleid") Integer articleid)
			throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PrintWriter out = response.getWriter();
		// true则收藏成功，false表示已收藏
		if (articleManager.addCollection(userName, articleid)) {
			out.print("success");
		} else {
			out.print("fail");
		}
		out.close();
	}

	// 取消收藏
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "cancel_collect")
	public void cancel_collect(
			@RequestParam(value = "articleid") Integer articleid,
			HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		articleManager.cancel_Collection(userName, articleid);
		PrintWriter out = response.getWriter();
		out.write("success");
		out.close();
	}

	//检查是否已关注
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "checkAttend")
	public void attend(HttpServletResponse response,@RequestParam(value = "friendid") Integer friendid)
			throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PrintWriter out = response.getWriter();
		//true表示已存在
		if (articleManager.checkAttend(userName, friendid)) {
			out.print("fail");
		} else {
			out.print("success");
		}
		out.close();
	}
	//为关注的好友添加分组
	// 关注可选择分组，不选择择添加到默认分组。参数：组名、关注的人
	@RequestMapping(value="addAttendwithGroup")
	public void addAttendwithGroup(Attend attend,HttpServletResponse response) throws IOException{
		System.out.print("\n"+attend+"\n");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userid = userManager.getUserByUserName(userDetails.getUsername()).getId();
		attend.setUserid(userid);
		articleManager.addAttenderGroup(attend);
		response.getWriter().print("添加分组成功！");
	}
	// 取消关注
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "cancel_attend")
	public void cancel_attend(
			@RequestParam(value = "friendid") Integer friendid,
			HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		articleManager.cancel_attend(userName, friendid);
		PrintWriter out = response.getWriter();
		out.write("success");
		out.close();
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "articleManage")
	public void listArticle(@RequestParam(value = "parent") String parent,
			HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PrintWriter out = response.getWriter();
		if (parent.equals("文章管理")) {
			out.print("[{text : \'我的收藏\',leaf : false,}, {text : \'我的文章\',leaf : false,}]");
			System.out.println("\n\n文章管理");
		} else if (parent.equals("我的收藏")) {
			List<Collection> list = articleManager.getAllcollection(userName);
			if (list.size() == 0) {
				out.print("text:\'收藏为空',leaf :true");
			} else {// 拼装json，需要文章id、文章名
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonobject = new JSONObject();
				for (Collection collection : list) {
					jsonobject.put("text", collection.getArticleName());
					jsonobject.put("id", collection.getArticleid());
					jsonobject.put("leaf", "true");
					jsonArray.add(jsonobject);
				}
				out.print(jsonArray.toString());
				System.out.println(jsonArray);
			}
		} else if (parent.equals("我的文章")) {
			List<Article> list = articleManager.getAllarticle(userName);
			if (list.size() == 0) {
				out.print("text:\'文章为空',leaf :true");
			} else {
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonobject = new JSONObject();
				for (Article article : list) {
					jsonobject.put("text", article.getTitle());
					jsonobject.put("id", article.getId());
					jsonobject.put("leaf", "true");
					jsonArray.add(jsonobject);
				}
				out.print(jsonArray.toString());
				System.out.println(jsonArray);
			}
		}
		out.close();
	}

	// 好友管理
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "friendManage")
	public void listFriend(HttpServletResponse response) throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Attend> list = articleManager.getAllattend(userName);
		PrintWriter out = response.getWriter();
		if (list.size() == 0) {
			JSONObject json = new JSONObject();
			json.put("text", "暂无关注");
			json.put("leaf", "true");
			out.print(json.toString());
			System.out.println("列表为空！\n" + json.toString());
		} else {
			// 开始json拼装
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonobject = new JSONObject();
			for (Attend attend : list) {
				jsonobject.put("text", attend.getFriendName());
				jsonobject.put("id", attend.getFriendid());
				jsonobject.put("leaf", "true");
				jsonArray.add(jsonobject);
			}
			out.print(jsonArray.toString());
			System.out.println("测试列表");
			System.out.println(jsonArray);

		}
		out.close();
	}
	// 系统管理（需要管理员权限）分页显示用户
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "showUserByPage")
	public void showUserByPage(HttpServletResponse response,
			@RequestParam(value = "start", required = false) int pageNow,
			@RequestParam(value = "limit", required = false) int pageSize)
			throws IOException {
		List<User_info> list = userManager.getUserByPage(pageNow, pageSize);
		int totalProperty = userManager.getAllcount();
		// 开始json拼装
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (list.size() > 0) {
			for (User_info user : list) {
				jsonObject.put("id", user.getId());
				jsonObject.put("name", user.getName());
				jsonObject.put("userName", user.getUserName());
				jsonObject.put("password", user.getPassword());
				jsonObject.put("birthday", user.getBirthday());
				jsonObject.put("email", user.getEmail());
				jsonObject.put("gender", user.getGender());
				jsonObject.put("QQ", user.getQQ());
				jsonObject.put("phoneNumber", user.getPhoneNumber());
				jsonObject.put("location", user.getProvince());
				jsonArray.add(jsonObject);
			}
		}
		JSONObject Str = new JSONObject();
		Str.put("totalProperty", totalProperty);
		Str.put("root", jsonArray);
		response.getWriter().print(Str.toString());
		System.out.println(Str);
	}

	@RequestMapping(value = "toShowArticleByPage")
	public String toShowArticleByPage() {
		return "showArticleByPage";
	}

	@RequestMapping(value = "toShowUserByPage")
	public String toShowUserByPage() {
		return "showUserByPage";
	}

	// 系统管理（需要管理员权限)分页显示文章
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "showArticleByPage")
	public void showArticleByPage(HttpServletResponse response,
			@RequestParam(value = "start", required = false) int pageNow,
			@RequestParam(value = "limit", required = false) int pageSize)
			throws IOException {
		List<Article> list = articleManager.getAllarticleByPage(pageNow,
				pageSize);
		int totalProperty = articleManager.getArticleCount();
		// 开始json拼装
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (list.size() > 0) {
			for (Article article : list) {
				jsonObject.put("id", article.getId());
				jsonObject.put("title", article.getTitle());
				jsonObject.put("belongUserName", article.getBelongUserName());
				jsonObject.put("content", article.getContent());
				jsonObject.put("releaseDate", article.getReleaseDate());
				jsonObject.put("isReproduce", article.getReproduceNum());
				jsonObject.put("praiseNum", article.getPraiseNum());
				jsonObject.put("collectNum", article.getCollectNum());
				jsonObject.put("remarkNum", article.getRemarkNum());
				jsonObject.put("reproduceNum", article.getReproduceNum());
				jsonArray.add(jsonObject);
			}
		}
		JSONObject Str = new JSONObject();
		Str.put("totalProperty", totalProperty);
		Str.put("root", jsonArray);
		response.getWriter().print(Str.toString());
		System.out.println(jsonArray);
		System.out.println(Str);

	}

	@RequestMapping(value = "showArticle/{articleid}")
	public ModelAndView showArticle1(
		//	@RequestParam(value = "articleid") Integer articleid,
			@PathVariable(value = "articleid") Integer articleid,
			HttpServletResponse response) throws IOException {
		Article article = articleManager.getArticlebyId(articleid);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showArticle1");
		mav.addObject(article);
		return mav;
	}

	// 用户数据修改
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "modifyUser")
	public void modifyUser(@RequestParam(value = "data") String data,
			HttpServletResponse response) throws IOException {
		JSONArray jsonArray = JSONArray.fromObject(data);
		System.out.println(jsonArray);
		List<User_info> users = JSONArray.toList(jsonArray, User_info.class);
		userManager.modifyUser(users);
		response.getWriter().print("成功修改！");
	}

	// 用户数据删除（支持批量）
	@RequestMapping(value = "deleteUser")
	public void deleteUser(@RequestParam(value = "ids") String ids,
			HttpServletResponse response) throws IOException {
		JSONObject jsonObject = JSONObject.fromObject(ids);
		System.out.print(jsonObject.toString() + "\n");
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			userManager.delUser((Integer) jsonObject.get(keys.next()));
		}
		response.getWriter().print("删除成功！");
	}

	/*
	 * // 查看关注列表
	 * 
	 * @RequestMapping(value = "listAttend") public void listAttend() {
	 * UserDetails userDetails = (UserDetails) SecurityContextHolder
	 * .getContext().getAuthentication().getPrincipal(); String userName =
	 * userDetails.getUsername(); List<User_info> list =
	 * articleManager.getAllattend(userName); //开始json拼装 }
	 * 
	 * // 查看收藏列表
	 * 
	 * @RequestMapping(value = "listCollection") public void listCollection() {
	 * UserDetails userDetails = (UserDetails) SecurityContextHolder
	 * .getContext().getAuthentication().getPrincipal(); String userName =
	 * userDetails.getUsername(); List<Article> list =
	 * articleManager.getAllcollection(userName); //开始json拼装 }
	 */
	// 查看个人博录（含转载）
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "per_article")
	public void list_per_article() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Article> list = articleManager.getAllarticle(userName);
		// 开始json拼装
	}

	// 进入聊天室页面
	@RequestMapping(value = "tochat")
	public ModelAndView tochar(@RequestParam(value = "userName") String userName) {
		ModelAndView mav = new ModelAndView("chat");
		User_info user = userManager.getUserByUserName(userName);
		System.out.println("\n\nuserName:" + userName);
		mav.addObject("user", user);
		return mav;
	}

	// 获取个人信息
	@RequestMapping(value = "getPerInfo")
	public void getPerINfo(@RequestParam(value = "userid") int userid,
			HttpServletResponse response) throws IOException {
		User_info user = userManager.getUserByUserId(userid);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", user.getName());
		jsonObject.put("id", user.getId());
		jsonObject.put("phoneNumber", user.getPhoneNumber());
		System.out.println(jsonObject);
		response.getWriter().print(jsonObject);
	}

	// 获取分组列表
	@RequestMapping(value = "getGroups")
	public void gerGroups(HttpServletResponse response,@RequestParam(value="limit",required = false)Integer limit,
			@RequestParam(value="start",required=false)Integer start) throws IOException {
		start = (start == null ? 0 : start);
		limit = (limit == null ? 5 : limit);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Groups> groups = articleManager.getGroupsByPage(userName,start,limit);
		int count = articleManager.getGroupsCount(userName);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for(Groups group :groups){
			jsonObject.put("id", group.getId());
			jsonObject.put("name", group.getGroupName());
			jsonArray.add(jsonObject);
		}
		JSONObject Str = new JSONObject();
		Str.put("totalProperty",count);
		Str.put("data",jsonArray);
			response.getWriter().print(Str);
			System.out.println(Str);
		}

	//添加新分组
	@RequestMapping(value="addGroups")
	public void addGroups(@RequestParam(value="groupName")String groupName,HttpServletResponse response) throws IOException{
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetail.getUsername();
		articleManager.addGroup(userName,groupName);
		response.getWriter().print("添加分组成功！");
	}
	
	// 分页查询展示
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_BOSS')")
	// @RequestMapping(value = "/list", method = RequestMethod.GET)
	// public ModelAndView list(@RequestParam("pageNow") int pageNow,
	// @RequestParam("orderType") String orderType,
	// @RequestParam("sortInverse") int sortInverse) {
	// ModelAndView mav = new ModelAndView();
	// List<User_info> list = userManager.listByPage(pageNow, this.pageSize,
	// orderType, sortInverse);
	// Page page = new Page(pageNow, this.pageSize, userManager.getAllcount());
	// if (page.getPageNow() > page.getTotalPage()) {
	// page.setPageNow(page.getTotalPage());
	// }
	// mav.addObject("orderType", orderType);
	// mav.addObject("sortInverse", sortInverse);
	// mav.addObject("page", page);
	// mav.addObject("user", list);
	// mav.setViewName("userlist");
	// return mav;
	// }
	// 登录权限分页
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexView() {
		return "index";
	}

	// 权限不足界面
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedView() {
		return "accessDenied";
	}

	// 用户注册
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(User_info user, HttpServletResponse response)
			throws IOException {
		if (userManager.getUserByUserName(user.getUserName()) != null) {
			response.getWriter().print("fail");
		} else {
			userManager.addUser(user);
			response.getWriter().print("success");
		}
		System.out.println("userName:\n\n" + user.getUserName());
	}

	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST)
	public void checkUserName(HttpServletResponse response,
			@RequestParam("userName") String userName) throws IOException {
		PrintWriter out = response.getWriter();
		User_info user = userManager.getUserByUserName(userName);
		if (user != null) {
			out.print("true");
		} else {
			out.print("false");
		}
	}

	// 退出登录
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut(HttpSession session) {
		session.removeAttribute("user");
		return "loginOut_succ";
	}

	// 删除用户
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		userManager.delUser(id);
		mav.setViewName("delete_succ");
		return mav;
	}

	// 进入用户信息修改界面
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView toupdate(@RequestParam("userName") String userName) {
		ModelAndView mav = new ModelAndView();
		User_info u = userManager.getUserByUserName(userName);
		mav.setViewName("update");
		mav.addObject("user", u);
		return mav;
	}

	// 修改用户信息
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(User_info user) {
		ModelAndView mav = new ModelAndView();
		if (userManager.updateUser(user)) {
			mav.setViewName("update_succ");
			return mav;
		} else {
			mav.addObject("id", user.getId());
			mav.setViewName("update_fail");
			return mav;
		}
	}

	// 高级查询
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BOSS')")
	@RequestMapping(value = "/advancedSearch")
	public ModelAndView advancedSearch(@RequestParam("pageNow") int pageNow,
			User_info user) {
		Page page;
		ModelAndView mav = new ModelAndView();
		List<User_info> list = userManager.advancedSearch(pageNow,
				this.pageSize, user);
		List<User_info> listtemp = userManager.advancedSearch(1, this.pageSize,
				user);
		if (listtemp == null) {
			page = new Page(pageNow, this.pageSize, 0);
		} else {
			page = new Page(pageNow, this.pageSize, listtemp.size());
		}
		if (page.getPageNow() > page.getTotalPage()) {
			page.setPageNow(page.getTotalPage());
		}

		if (list == null) {
			mav.addObject("errorMsg", "不存在满足条件的用户！");
		}
		mav.addObject("name", user.getName());
		mav.addObject("userName", user.getUserName());
		mav.addObject("gender", user.getGender());
		mav.addObject("email", user.getEmail());
		mav.addObject("page", page);
		mav.addObject("userAdvance", list);
		mav.setViewName("UserSearchAdvance");
		return mav;
	}

	// 简单查询
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BOSS')")
	@RequestMapping(value = "/search")
	public ModelAndView searchUser(@RequestParam("keywords") String keywords,
			@RequestParam("informationType") String informationType,
			@RequestParam("SearchType") String SearchType,
			@RequestParam("pageNow") int pageNow) {
		ModelAndView mav = new ModelAndView();
		Page page;
		// id只为精确查找
		if (informationType.equals("id") || informationType.equals("gender")) {
			User_info u = userManager.findUserByAccurateInformation(keywords,
					informationType);
			mav.addObject("usearch", u);
			if (null == u) {
				mav.addObject("errorMsg", "没有这个用户！");
			}
			mav.setViewName("UserSearchAccurate");
		}
		// 用户名和名称分模糊和精确查找
		else if (SearchType.equals("vague")) {
			List<User_info> list = userManager.findUserByVagueInformation(
					pageNow, this.pageSize, keywords, informationType);
			List<User_info> listtemp = userManager.findUserByVagueInformation(
					1, userManager.getAllcount(), keywords, informationType);
			if (listtemp == null) {
				page = new Page(pageNow, this.pageSize, 0);
			} else {
				page = new Page(pageNow, this.pageSize, listtemp.size());
			}
			if (page.getPageNow() > page.getTotalPage()) {
				page.setPageNow(page.getTotalPage());
			}
			if (null == list) {
				mav.addObject("errorMsg", "不存在满足条件的用户！");
			}
			mav.addObject("page", page);
			mav.addObject("userVague", list);
			mav.addObject("keywords", keywords);
			mav.addObject("informationType", informationType);
			mav.addObject("SearchType", SearchType);
			mav.setViewName("UserSearchVague");
		} else {
			User_info u = userManager.findUserByAccurateInformation(keywords,
					informationType);
			mav.addObject("usearch", u);
			if (null == u) {
				mav.addObject("errorMsg", "没有这个用户！");
			}
			mav.setViewName("UserSearchAccurate");
		}
		return mav;
	}

}