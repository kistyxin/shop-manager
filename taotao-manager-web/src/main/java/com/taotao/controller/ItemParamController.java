package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

//商品规格参数模版管理
@Controller
@RequestMapping("item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable  Long  itemCatId){
		TaotaoResult  result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
//接收cid和规格参数模板，创建TbItemParam对象，并调用Service添加到表中
    @RequestMapping("/save/{cid}")
    @ResponseBody
     public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData){
    	//创建pojo对象
    	TbItemParam itemParam = new TbItemParam();
    	itemParam.setItemCatId(cid);
    	itemParam.setParamData(paramData);
    	TaotaoResult result =itemParamService.insertItemParam(itemParam);
    	return result;
    }


	

}
