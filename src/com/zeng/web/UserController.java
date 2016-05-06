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

	// �����û���Ϣ��ʾÿҳ�û���
	private final int pageSize = 5;

	@Autowired
	private UserManager userManager;
	@Autowired
	private ArticleManager articleManager;

	// ��½���δ���
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

	// ����list����
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "toshow")
	public String toshow() {
		return "list";
	}

	// ������ҳ
	// 1��չʾ��������
	@RequestMapping(value = "/main")
	public ModelAndView mainView(
			@RequestParam(value = "pageNow", required = false) Integer pageNow) {
		ModelAndView mav = new ModelAndView("main");
		pageNow = pageNow == null ? 1 : pageNow;
		List<Article> list = articleManager.getArticle(pageNow, 3);
		if (list == null) {
			mav.addObject("errorMsg", "��ǰû���κ�����");
		}
		mav.addObject("article", list);
		return mav;
	}

	// ajax��ȡ����չʾ
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
			// ajaxƴװ���,��Ҫ ���ߡ�ʱ�䡢���⡢���ݡ���/����/ת��/�ղ�����id
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
	//��ȡ����
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

	//��ȡ��Ƶ
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
	// ��ȡͼƬ
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

	// �������
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

	// ����
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

	// �������
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

	// ��ҳչʾ���ۣ�json��
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
			// ƴװjson 1:list,2:page
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
			// System.out.println(jsonObject.toString());//���
			out.print(jsonObject.toString());
		}
		out.close();
	}

	// �û�����֤
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

	// ת��
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "reproduce")
	// ��ȡ����������id��ת������ת��ʱ��
	public void reproduce(
			Reproduce reproduce,
			@RequestParam(value = "personalMsg", required = false) String personalMsg,
			@RequestParam(value = "frienduserName", required = false) String frienduserName,
			@RequestParam(value = "content", required = false) String content,
			HttpServletResponse response) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		// �ȿ����Ƿ�ת�ع�����ʱ���ԣ�
		if (frienduserName == null) {
			// �������
			Remark remark = new Remark();
			remark.setContent(content);
			articleManager
					.addRemark(reproduce.getArticleid(), userName, remark);
		} else {
			// �����ѷ�˽��
			// articleManager
		}
		articleManager.addReproduce(userName, reproduce);
	}

	// �ղ�
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "collection")
	// ��ӵı�ǩ�������չjson����
	public void collection(
			@RequestParam(value = "tagName", required = false) String tagName,
			HttpServletResponse response,
			@RequestParam(value = "articleid") Integer articleid)
			throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PrintWriter out = response.getWriter();
		// true���ղسɹ���false��ʾ���ղ�
		if (articleManager.addCollection(userName, articleid)) {
			out.print("success");
		} else {
			out.print("fail");
		}
		out.close();
	}

	// ȡ���ղ�
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

	//����Ƿ��ѹ�ע
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "checkAttend")
	public void attend(HttpServletResponse response,@RequestParam(value = "friendid") Integer friendid)
			throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PrintWriter out = response.getWriter();
		//true��ʾ�Ѵ���
		if (articleManager.checkAttend(userName, friendid)) {
			out.print("fail");
		} else {
			out.print("success");
		}
		out.close();
	}
	//Ϊ��ע�ĺ�����ӷ���
	// ��ע��ѡ����飬��ѡ������ӵ�Ĭ�Ϸ��顣��������������ע����
	@RequestMapping(value="addAttendwithGroup")
	public void addAttendwithGroup(Attend attend,HttpServletResponse response) throws IOException{
		System.out.print("\n"+attend+"\n");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userid = userManager.getUserByUserName(userDetails.getUsername()).getId();
		attend.setUserid(userid);
		articleManager.addAttenderGroup(attend);
		response.getWriter().print("��ӷ���ɹ���");
	}
	// ȡ����ע
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
		if (parent.equals("���¹���")) {
			out.print("[{text : \'�ҵ��ղ�\',leaf : false,}, {text : \'�ҵ�����\',leaf : false,}]");
			System.out.println("\n\n���¹���");
		} else if (parent.equals("�ҵ��ղ�")) {
			List<Collection> list = articleManager.getAllcollection(userName);
			if (list.size() == 0) {
				out.print("text:\'�ղ�Ϊ��',leaf :true");
			} else {// ƴװjson����Ҫ����id��������
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
		} else if (parent.equals("�ҵ�����")) {
			List<Article> list = articleManager.getAllarticle(userName);
			if (list.size() == 0) {
				out.print("text:\'����Ϊ��',leaf :true");
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

	// ���ѹ���
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
			json.put("text", "���޹�ע");
			json.put("leaf", "true");
			out.print(json.toString());
			System.out.println("�б�Ϊ�գ�\n" + json.toString());
		} else {
			// ��ʼjsonƴװ
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonobject = new JSONObject();
			for (Attend attend : list) {
				jsonobject.put("text", attend.getFriendName());
				jsonobject.put("id", attend.getFriendid());
				jsonobject.put("leaf", "true");
				jsonArray.add(jsonobject);
			}
			out.print(jsonArray.toString());
			System.out.println("�����б�");
			System.out.println(jsonArray);

		}
		out.close();
	}
	// ϵͳ������Ҫ����ԱȨ�ޣ���ҳ��ʾ�û�
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "showUserByPage")
	public void showUserByPage(HttpServletResponse response,
			@RequestParam(value = "start", required = false) int pageNow,
			@RequestParam(value = "limit", required = false) int pageSize)
			throws IOException {
		List<User_info> list = userManager.getUserByPage(pageNow, pageSize);
		int totalProperty = userManager.getAllcount();
		// ��ʼjsonƴװ
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

	// ϵͳ������Ҫ����ԱȨ��)��ҳ��ʾ����
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "showArticleByPage")
	public void showArticleByPage(HttpServletResponse response,
			@RequestParam(value = "start", required = false) int pageNow,
			@RequestParam(value = "limit", required = false) int pageSize)
			throws IOException {
		List<Article> list = articleManager.getAllarticleByPage(pageNow,
				pageSize);
		int totalProperty = articleManager.getArticleCount();
		// ��ʼjsonƴװ
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

	// �û������޸�
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "modifyUser")
	public void modifyUser(@RequestParam(value = "data") String data,
			HttpServletResponse response) throws IOException {
		JSONArray jsonArray = JSONArray.fromObject(data);
		System.out.println(jsonArray);
		List<User_info> users = JSONArray.toList(jsonArray, User_info.class);
		userManager.modifyUser(users);
		response.getWriter().print("�ɹ��޸ģ�");
	}

	// �û�����ɾ����֧��������
	@RequestMapping(value = "deleteUser")
	public void deleteUser(@RequestParam(value = "ids") String ids,
			HttpServletResponse response) throws IOException {
		JSONObject jsonObject = JSONObject.fromObject(ids);
		System.out.print(jsonObject.toString() + "\n");
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			userManager.delUser((Integer) jsonObject.get(keys.next()));
		}
		response.getWriter().print("ɾ���ɹ���");
	}

	/*
	 * // �鿴��ע�б�
	 * 
	 * @RequestMapping(value = "listAttend") public void listAttend() {
	 * UserDetails userDetails = (UserDetails) SecurityContextHolder
	 * .getContext().getAuthentication().getPrincipal(); String userName =
	 * userDetails.getUsername(); List<User_info> list =
	 * articleManager.getAllattend(userName); //��ʼjsonƴװ }
	 * 
	 * // �鿴�ղ��б�
	 * 
	 * @RequestMapping(value = "listCollection") public void listCollection() {
	 * UserDetails userDetails = (UserDetails) SecurityContextHolder
	 * .getContext().getAuthentication().getPrincipal(); String userName =
	 * userDetails.getUsername(); List<Article> list =
	 * articleManager.getAllcollection(userName); //��ʼjsonƴװ }
	 */
	// �鿴���˲�¼����ת�أ�
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "per_article")
	public void list_per_article() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		List<Article> list = articleManager.getAllarticle(userName);
		// ��ʼjsonƴװ
	}

	// ����������ҳ��
	@RequestMapping(value = "tochat")
	public ModelAndView tochar(@RequestParam(value = "userName") String userName) {
		ModelAndView mav = new ModelAndView("chat");
		User_info user = userManager.getUserByUserName(userName);
		System.out.println("\n\nuserName:" + userName);
		mav.addObject("user", user);
		return mav;
	}

	// ��ȡ������Ϣ
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

	// ��ȡ�����б�
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

	//����·���
	@RequestMapping(value="addGroups")
	public void addGroups(@RequestParam(value="groupName")String groupName,HttpServletResponse response) throws IOException{
		UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetail.getUsername();
		articleManager.addGroup(userName,groupName);
		response.getWriter().print("��ӷ���ɹ���");
	}
	
	// ��ҳ��ѯչʾ
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
	// ��¼Ȩ�޷�ҳ
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexView() {
		return "index";
	}

	// Ȩ�޲������
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedView() {
		return "accessDenied";
	}

	// �û�ע��
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

	// �˳���¼
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public String loginOut(HttpSession session) {
		session.removeAttribute("user");
		return "loginOut_succ";
	}

	// ɾ���û�
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") int id) {
		ModelAndView mav = new ModelAndView();
		userManager.delUser(id);
		mav.setViewName("delete_succ");
		return mav;
	}

	// �����û���Ϣ�޸Ľ���
	@PreAuthorize("hasRole('ROLE_BOSS')")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView toupdate(@RequestParam("userName") String userName) {
		ModelAndView mav = new ModelAndView();
		User_info u = userManager.getUserByUserName(userName);
		mav.setViewName("update");
		mav.addObject("user", u);
		return mav;
	}

	// �޸��û���Ϣ
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

	// �߼���ѯ
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
			mav.addObject("errorMsg", "�����������������û���");
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

	// �򵥲�ѯ
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_BOSS')")
	@RequestMapping(value = "/search")
	public ModelAndView searchUser(@RequestParam("keywords") String keywords,
			@RequestParam("informationType") String informationType,
			@RequestParam("SearchType") String SearchType,
			@RequestParam("pageNow") int pageNow) {
		ModelAndView mav = new ModelAndView();
		Page page;
		// idֻΪ��ȷ����
		if (informationType.equals("id") || informationType.equals("gender")) {
			User_info u = userManager.findUserByAccurateInformation(keywords,
					informationType);
			mav.addObject("usearch", u);
			if (null == u) {
				mav.addObject("errorMsg", "û������û���");
			}
			mav.setViewName("UserSearchAccurate");
		}
		// �û��������Ʒ�ģ���;�ȷ����
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
				mav.addObject("errorMsg", "�����������������û���");
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
				mav.addObject("errorMsg", "û������û���");
			}
			mav.setViewName("UserSearchAccurate");
		}
		return mav;
	}

}