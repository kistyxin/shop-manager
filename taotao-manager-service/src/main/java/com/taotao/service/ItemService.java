package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.common.pojo.*;


public interface ItemService {
	TbItem  getItemById(long itemId );
	EUDataGridResult  getItemList(int page,int rows);
	//添加商品以及商品描述
	TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;
	
	
	
	

}
