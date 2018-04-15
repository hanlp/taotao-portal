package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class TbItemInfo extends TbItem {
	// 展示图片
	public String[] getImages() {
		if (getImage() != null) {
			String[] images = getImage().split(",");
			return images;
		}
		return null;
	}
}
