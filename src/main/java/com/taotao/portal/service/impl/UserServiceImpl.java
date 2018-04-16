package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.TaotaoResult;

@Service
public class UserServiceImpl implements UserService {
	// 调用sso系统中 服务,获取用户信息
	// SSO的基础URL
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;

	// 获取Token的url
	@Value("${SSO_TOKEN_URL}")
	private String SSO_TOKEN_URL;
	// 使用httpclient技术调用到

	// 首页
	@Value("${SSO_LOGIN_PAGE}")
	public String SSO_LOGIN_PAGE;

	@Override
	public TbUser getByTokenUser(String token) {
		// 请求SSO系统服务
		String jsonData = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_URL + token);
		// 类型转换
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, TbUser.class);
		// 数据判断
		if (result.getStatus() == 200) {
			// 返回User信息
			TbUser users = (TbUser) result.getData();
			return users;
		}
		return null;
	}

}
