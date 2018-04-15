package com.taotao.portal.service;

import com.taotao.portal.pojo.TbItemInfo;

public interface ItemService {

	public TbItemInfo geTbItemById(long itemId);

	public String getItemDesc(long itemId);

	public String getItemParam(long itemId);
}
