package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.pojo.TbItemExample.*;

import com.taotao.service.ItemService;

//商品管理Service
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper  itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired 
	//为创建好的模版添加参数
	private TbItemParamItemMapper itemParamItemMapper;
	@Override
	public TbItem  getItemById(long itemId){
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			TbItem item = list.get(0);
			return item;
		}
		
		return null;
	}
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample  example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}
	
	@Override//添加商品及商品描述
	public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
		//item补全
		// 生成商品ID
		//生成商品ID，毫秒加三位随机数
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId,desc);
		
		if(result.getStatus()!=200){
			throw new Exception();
		}
		
		//添加规格参数调用
	 result = insertItemParamItem(itemId,itemParam);
		return TaotaoResult.ok();
	}
	//添加商品描述，需要Itemdesc那个表，所以需要把相应的Mapper注入进来
	private TaotaoResult insertItemDesc(Long itemId,String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
		
	}
	
	//添加自己编写的规格参数数据值
	private TaotaoResult insertItemParamItem(Long itemId,String itemParam){
		//创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
		
	}

}
