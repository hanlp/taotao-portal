package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.TbItemInfo;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 查询商品基本信息
	@RequestMapping("/item/{itemId}")
	public String getItemById(@PathVariable long itemId, Model model) {
		TbItemInfo tbItem = itemService.geTbItemById(itemId);
		model.addAttribute("item", tbItem);
		return "item";

	}

	// 商品详情信息展示
	@RequestMapping("/item/desc/{itemId}")
	@ResponseBody
	public String getItemDesc(@PathVariable long itemId) {
		return itemService.getItemDesc(itemId);
	}

	// 商品规格参数信息展示
	@RequestMapping(value = "/item/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8;")
	@ResponseBody
	public String getItemParam(@PathVariable long itemId) {
		return itemService.getItemParam(itemId);
	}

}
