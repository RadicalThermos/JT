package com.jt.web.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.redis.RedisService;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.ItemDesc;

@Service
public class ItemDescService {

	@Autowired
	private HttpClientService client;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public ItemDesc getItemDesc(Long itemId) {
		// 引入缓存逻辑
		// 创建key
		ItemDesc itemDesc = null;
		String key = "ITEM_DESC_" + itemId;
		// 判断key值是否存在
		if (redisService.exists(key)) {
			// 缓存存在,从缓存中获取
			String itemDescStr = redisService.get(key);
			// 解析字符串,生成对象
			try {
				itemDesc = MAPPER.readValue(itemDescStr, ItemDesc.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 缓存不存在,跨域请求manage
			String url = "http://manage.jt.com/itemDesc/" + itemId;
			try {
				String itemDescStr = client.doGet(url);
				itemDesc = MAPPER.readValue(itemDescStr, ItemDesc.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return itemDesc;
	}

}
