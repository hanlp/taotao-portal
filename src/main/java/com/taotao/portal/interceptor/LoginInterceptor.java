package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.impl.UserServiceImpl;
import com.taotao.util.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserServiceImpl userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1.如何判断?这个用户是否登录
		// 思考: 1) session 缺点是session不能在多个服务器中实现共享.
		// 2)从cookie中取信息, 生成的token码可以保存到cookie端.
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser users = userService.getByTokenUser(token);
		// 判断 如果users 里是否有信息,返回true 否则返回false;
		// 没有信息
		if (users == null) {
			// 跳转登录页面 request.getContextPath() 获取到当前页面路径
			response.sendRedirect(
					userService.SSO_BASE_URL + userService.SSO_LOGIN_PAGE + "?redirect=" + request.getRequestURL());

			return false;
		}
		request.setAttribute("users", users);// 把用户信息存到request作用域里.
		// 通过
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
