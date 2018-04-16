package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping("/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		cartService.addCart(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}

	// 避免重复提交
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}

	// 展示购物车信息
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.getItemCartList(request);
		model.addAttribute("cartList", list);
		return "cart";
	}

}
