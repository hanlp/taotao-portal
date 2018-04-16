package com.taotao.portal.controller;

import java.util.List;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@RequestMapping("/order-cart")
	public String showCartItem(HttpServletRequest request, Model model) {
		// 获取购物车里的信息
		List<CartItem> list = cartService.getItemCartList(request);
		model.addAttribute("cartList", list);
		return "order-cart";
	}

	// 保存订单信息
	@RequestMapping("/create")
	public String createOrder(Order order, Model model, HttpServletRequest request) {
		// 获取用户信息
		TbUser users = (TbUser) request.getAttribute("users");
		long userid = users.getId();
		// 设置用户ID到order表中
		order.setUserId(userid);
		String orderId = orderService.saveOrder(order);
		model.addAttribute("orderId", orderId);// 订单号
		model.addAttribute("payment", order.getPayment());// 总价格
		// 收货时间
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}

}
