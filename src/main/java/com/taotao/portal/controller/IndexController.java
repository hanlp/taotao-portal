package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;

	// 展示首页
	@RequestMapping("/index")
	public String showIndex(Model model) {
		String str = contentService.getContentList(89);
		model.addAttribute("ad1", str);
		return "index";
	}
}
