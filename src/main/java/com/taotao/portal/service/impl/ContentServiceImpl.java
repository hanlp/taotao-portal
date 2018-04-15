package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${BASE_REST_URL}")
	private String BASE_REST_URL;
	@Value("${CONTENT_REST_URL}")
	private String CONTENT_REST_URL;

	@Override
	public String getContentList(long categoryId) {
		/*
		 * // 使用httpclient调用rest发布的服务 String strjson =
		 * HttpClientUtil.doGet(BASE_REST_URL + CONTENT_REST_URL + categoryId);
		 * 
		 * return strjson;
		 */
		// 发送http请求
		String jsonData = HttpClientUtil.doGet(BASE_REST_URL + CONTENT_REST_URL + categoryId);
		List resultlist = new ArrayList<>();
		// 判断返回数据是否为空
		if (!StringUtils.isBlank(jsonData)) { // !"".equals(jsonData)
			TaotaoResult taotaoResult = TaotaoResult.formatToList(jsonData, TbContent.class);
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			for (TbContent tbContent : list) {
				Map map = new HashMap();
				map.put("srcB", tbContent.getPic());
				map.put("height", 240);
				map.put("alt", tbContent.getContent());
				map.put("width", 670);
				map.put("src", tbContent.getPic2());
				map.put("heightB", 550);
				map.put("widthB", 240);
				map.put("href", tbContent.getUrl());
				resultlist.add(map);
			}
		}
		return JsonUtils.objectToJson(resultlist);
	}

}
