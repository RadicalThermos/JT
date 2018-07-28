package com.jt.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.redis.RedisService;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;

@Service
public class ItemService {

	@Autowired
	private HttpClientService client;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public Item getItem(Long itemId){
		//添加缓存逻辑
		//创建key
		Item item = null;
		String key = "ITEM_"+itemId;
		if(redisService.exists(key)){
			//缓存存在,直接获取
			String itemStr = redisService.get(key);
			//转成对象
			try {
				item = MAPPER.readValue(itemStr, Item.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//缓存不存在,利用HttpClient发送跨域请求
			String url = "http://manage.jt.com/item/"+itemId;
			String itemStr;
			try {
				itemStr = client.doGet(url);
				item = MAPPER.readValue(itemStr, Item.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item;
	}

}
