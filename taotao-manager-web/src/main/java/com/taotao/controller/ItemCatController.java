package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;

//商品分类管理
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	//如果接收到的参数和你方法中定义的参数名称不同的话，那使用的注解就要是
	//@RequestParam,而不是PathVariable
	//括号里的传值，有参数就传参数，没参数就传默认值0
	private List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EUTreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}

}
