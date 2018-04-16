package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.util.CookieUtils;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Service
public class CartServiceImpl implements CartService {
	@Value("${BASE_REST_URL}")
	private String BASE_REST_URL;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;

	@Override
	public TaotaoResult addCart(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {

		// 1.得从cookie里取信息.
		List<CartItem> listCarItem = getCartList(request);
		CartItem cartItem = new CartItem();

		if (listCarItem != null || listCarItem.size() > 0) {
			// 2.判断,如果新增进来的数据在cookie中已经存在,把数量加上就可以了
			for (CartItem carts : listCarItem) {
				if (carts.getId() == itemId) {// 判断当前购物车中如果有该件商品
					carts.setNum(carts.getNum() + num);
					cartItem = carts;
					break;
				}
			}
		}

		// 1.通过商品的ID,查询出商品基本信息,去rest工程中查询
		String jsonData = HttpClientUtil.doGet(BASE_REST_URL + ITEM_INFO + itemId);
		// 把商品基本信息放到CartItem里
		// 类型转换
		TaotaoResult result = TaotaoResult.formatToPojo(jsonData, TbItem.class);
		if (result.getStatus() == 200) {
			TbItem tbItem = (TbItem) result.getData();
			// 把查询出的信息放到购物项中
			cartItem.setId(tbItem.getId());
			// 因为image字段里存的是多张图片,我们只需要展示出第一张就可以了.
			cartItem.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
			cartItem.setTitle(tbItem.getTitle());
			cartItem.setNum(num);
			cartItem.setPrice(tbItem.getPrice());
		}
		listCarItem.add(cartItem);// 存到list中.
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(listCarItem), true);// 转换成json存入到cookie
		return TaotaoResult.ok();
	}

	// 购物车里存的数据, 多条数据, 要给cookie里存放的是list类型数据
	private List<CartItem> getCartList(HttpServletRequest request) {
		// 从cookie中去数据
		String jsonData = CookieUtils.getCookieValue(request, "TT_CART", true);
		List<CartItem> list = null;
		if (jsonData == null) {
			return new ArrayList<>();
		}
		// 否则,把取出的数据进行转换,转换list格式,返回回去
		list = JsonUtils.jsonToList(jsonData, CartItem.class);
		// 返回购物车数据
		return list;
	}

	@Override
	public List<CartItem> getItemCartList(HttpServletRequest request) {
		List<CartItem> list = getCartList(request);
		return list;
	}

	//
}
