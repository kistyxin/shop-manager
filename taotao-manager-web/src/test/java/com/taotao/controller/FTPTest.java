package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {
	@Test
	public void testFtpClient() throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
	
		//创建ftp连接。默认是21端口
		ftpClient.connect("192.168.234.129",21);
		//登录ftp服务器，使用用户名和密码
		ftpClient.login("ftpuser", "root");
		//上传文件
		//读取文本文件
		FileInputStream inputStream = new FileInputStream(new File("shupian.jpg"));
		
		//设置上传的路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传文件的格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数：服务器端文件名
		//第二个参数：上传文件的inputStream
		ftpClient.storeFile("1121.jpg", inputStream);
		inputStream.close();
		//关闭连接
		ftpClient.logout();
	}
	@Test
	public void testFtpUtil() throws Exception{
		FileInputStream inputStream = new FileInputStream(new File("shupian.jpg"));
	    FtpUtil.uploadFile("192.168.234.129", 21, "ftpuser", "root", "/home/ftpuser/www/images", "/2016/11/2", "hello1.jpg", inputStream);
	}

}
