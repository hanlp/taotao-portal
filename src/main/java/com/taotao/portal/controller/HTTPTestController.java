package com.taotao.portal.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.util.TaotaoResult;

@Controller
public class HTTPTestController {

	/*
	 * @RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public TaotaoResult getPost() { return TaotaoResult.ok(); }
	 */
	@RequestMapping(value = "/httpclient/post", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE
			+ ";charset=utf-8;")
	@ResponseBody
	public String getPost(String username, String pwd) {
		return "username=" + username + "\tpwd=" + pwd;
	}
}
