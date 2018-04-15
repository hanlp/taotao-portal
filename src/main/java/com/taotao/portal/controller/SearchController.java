package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public String search(@RequestParam(value = "q") String queryString,
			@RequestParam(value = "page", defaultValue = "1") int page, Model model)
					throws UnsupportedEncodingException {
		queryString = new String(queryString.getBytes("ISO8859-1"), "utf-8");
		SearchResult result = searchService.search(queryString, page);
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("page", result.getCurPage());
		return "search";
	}
}
