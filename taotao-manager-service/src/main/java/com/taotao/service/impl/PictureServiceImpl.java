package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
//图片上传服务
@Service
public class PictureServiceImpl implements PictureService {
	//取出resource.properties文件里面的值
   @Value("${FTP_ADDRESS}")
   private String FTP_ADDRESS;
   @Value("${FTP_PORT}")
   private Integer FTP_PORT;
   @Value("${FTP_USERNAME}")
   private String FTP_USERNAME;
   @Value("${FTP_PASSWORD}")
   private String FTP_PASSWORD;
   @Value("${FTP_BASE_PATH}")
   private String FTP_BASE_PATH;
   @Value("${IMAGE_BASE_URL}")
   private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile){
		Map resultMap = new HashMap<>();
		try{
		//生成一个新的文件名
		//取原文件名
		String oldName = uploadFile.getOriginalFilename();
		//生成新文件名
		String newName = IDUtils.genImageName();
		newName = newName+oldName.substring(oldName.lastIndexOf(".")); 
		//图片上传
		String imagePath = new DateTime().toString("/yyyy/MM/dd");
		//filePath这按上传日期创建文件夹
		boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, imagePath, newName, uploadFile.getInputStream());
		//返回结果
		//上传失败
		if(!result) {
			 resultMap.put("error", 1);
			 resultMap.put("message", "文件上传失败");
			 return resultMap;
		}
		resultMap.put("error", 0);
		resultMap.put("url",IMAGE_BASE_URL+imagePath+"/"+newName);
		return resultMap;
		}catch(Exception e){
			resultMap.put("error", 1);
			resultMap.put("message","文件上传异常");
			return resultMap;
		}
	}

}
