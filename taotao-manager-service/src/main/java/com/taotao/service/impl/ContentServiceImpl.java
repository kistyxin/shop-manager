package com.taotao.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired 
	private TbContentMapper contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		try{
		//添加缓存调用同步逻辑(因为进行了新的插入，要删掉原来的缓存)
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL +content.getCategoryId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	
		}

}