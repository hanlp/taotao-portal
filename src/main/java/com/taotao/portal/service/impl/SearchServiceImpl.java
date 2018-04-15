package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.TaotaoResult;

@Service
public class SearchServiceImpl implements SearchService {
	@Value("${BATH_SEARCH_URL}")
	private String BATH_SEARCH_URL;

	@Override
	public SearchResult search(String queryString, int page) {
		// 1.传递参数
		// 2.通过httpclient技术,调用taotao-search工程
		// 3.从返回的数据进行转换,转换成需要pojo类型,供页面可以展示
		Map<String, String> map = new HashMap<>();
		// 向taotao-search工程中传递参数
		map.put("q", queryString);
		map.put("page", page + "");
		// 发送请求 返回的数据
		String jsonData = HttpClientUtil.doGet(BATH_SEARCH_URL, map);// &
		// 数据转换
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, SearchResult.class);
		// 判断是否返回数据
		if (result.getStatus() == 200) {
			// 取出taotaoresut封装的数据
			SearchResult searchResult = (SearchResult) result.getData();
			return searchResult;
		}

		return null;
	}

}
