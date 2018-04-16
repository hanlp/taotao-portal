package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.portal.pojo.CartItem;
import com.taotao.util.TaotaoResult;

public interface CartService {

	public TaotaoResult addCart(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

	public List<CartItem> getItemCartList(HttpServletRequest request);
}
