package com.zeng.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zeng.manager.ArticleManager;
import com.zeng.manager.UserManager;

@Controller
public class ToviewController {
/*	@Autowired
	private UserManager userManager;
	@Autowired
	private ArticleManager articleManager;*/
	
	@RequestMapping(value="toindexPanel")
	public String toindexPanel(){
		return "indexPanel";
	}

}
