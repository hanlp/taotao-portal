package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.TbItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${ITEM_INFO}")
	private String ITEM_INFO;// 商品基本信息

	@Value("${BASE_REST_URL}")
	private String BASE_REST_URL;

	@Value("${ITEM_DESC}") // 商品详细信息
	private String ITEM_DESC;

	// 商品规格参数
	@Value("${ITEM_PARAM}")
	private String ITEM_PARAM;

	@Override
	public TbItemInfo geTbItemById(long itemId) {
		// 1.调用rest工程中的服务
		String jsonData = HttpClientUtil.doGet(BASE_REST_URL + ITEM_INFO + itemId);
		// 2.类型转换
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItemInfo.class);
		// 判断调用的数据
		if (taotaoResult.getStatus() == 200) {
			TbItemInfo tbItem = (TbItemInfo) taotaoResult.getData();
			return tbItem;
		}
		return null;
	}

	// 商品详情展示
	@Override
	public String getItemDesc(long itemId) {
		// 1.调用rest工程中的服务
		String jsonData = HttpClientUtil.doGet(BASE_REST_URL + ITEM_DESC + itemId);
		// 2.类型转换
		TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItemDesc.class);
		// 判断调用的数据
		if (taotaoResult.getStatus() == 200) {
			TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
			return itemDesc.getItemDesc();// 商品详情
		}
		return null;
	}

	// 查询规格参数
	@Override
	public String getItemParam(long itemId) {
		// 1.调用rest工程中的服务
		String jsonData = HttpClientUtil.doGet(BASE_REST_URL + ITEM_PARAM + itemId);
		// 2.类型转换
		// 判断调用的数据
		if (!StringUtils.isBlank(jsonData)) {
			// 遍历json格式
			// 需要把json转换成为list类型, 泛型map
			List<Map> map = JsonUtils.jsonToList(jsonData, Map.class);
			StringBuffer sb = new StringBuffer();

			sb.append("<table class=\"Ptable\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\"\n");
			sb.append("    border=\"1\">\n");
			sb.append("    <tbody>\n");
			for (Map map2 : map) {// 遍历规格组
				sb.append("      <tr>\n");
				sb.append("        <th class=\"tdTitle\" colspan=\"2\">" + map2.get("group") + "</th>\n");
				sb.append("      </tr>\n");
				sb.append("      <tr>\n");
				sb.append("      </tr>\n");
				List<Map> m2 = (List<Map>) map2.get("params");
				for (Map map3 : m2) {// 遍历规格项目
					sb.append("      <tr>\n");
					sb.append("        <td class=\"tdTitle\">" + map3.get("k") + "</td>\n");
					sb.append("        <td>" + map3.get("v") + "</td>\n");
					sb.append("      </tr>\n");
				}
			}
			sb.append("    </tbody>\n");
			sb.append("  </table>");
			return sb.toString();
		}
		return null;
	}

}
