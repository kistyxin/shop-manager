package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.*;
import com.taotao.service.ItemParamService;
//商品的规格参数模版
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;
	@Override
	//查找是否已经有模版数据
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample  example = new TbItemParamExample();
		Criteria  criteria  = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if(list!=null&&list.size()>0){
			return TaotaoResult.ok(list.get(0));
		}
		
		return TaotaoResult.ok();
	}
	@Override
	//插入填写的模版数据
	public TaotaoResult insertItemParam(TbItemParam itemParam) {
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//插入到规则参数模版表
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
		
	}
	

}
