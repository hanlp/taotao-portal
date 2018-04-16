package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Service
public class OrderServiceImpl implements OrderService {
	// 调用taotao-order项目的url
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String saveOrder(Order order) {
		// 1.得从cookie中取数据 key tt_token
		// 2.12312312453345 以这个数字为key值,去redis中换取用户信息
		// 3.json格式数据转换,
		// 4.获取到该用户的id
		// 5.order.setUserId(userId);

		// 调用order接口,返回数据
		String jsonData = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult taotaoResult = TaotaoResult.format(jsonData);// 类型转换
		if (taotaoResult.getStatus() == 200) {
			return taotaoResult.getData().toString();// 返回订单号
		}

		return null;
	}

}
