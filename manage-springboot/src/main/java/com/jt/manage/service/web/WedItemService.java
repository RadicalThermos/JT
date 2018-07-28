package com.jt.manage.service.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.redis.RedisService;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

@Service
public class WedItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public Item getItem(Long itemId) {
		//引入缓存逻辑
		//创建key
		String key = "ITEM_"+itemId;
		Item item =null;
		if(redisService.exists(key)){
			//缓存存在,即直接从缓存获取
			String itemStr = redisService.get(key);
			//解析字符串,获取对象
			try {
				item = MAPPER.readValue(itemStr, Item.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//缓存不存在,查数据库,然后将对象存入缓存
			item = itemMapper.selectByPrimaryKey(itemId);
			try {
				String itemStr = MAPPER.writeValueAsString(item);
				redisService.set(key, itemStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return item;
	}
	public ItemDesc getItemDesc(Long itemId) {
		//引入缓存逻辑
		//创建key值
		String key = "ITEM_DESC_"+itemId;
		ItemDesc itemDesc = null;
		if(redisService.exists(key)){
			//缓存存在,直接获取
			String itemDescStr = redisService.get(key);
			//解析字符串
			try {
				itemDesc = MAPPER.readValue(itemDescStr, ItemDesc.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//缓存不存在,从数据库获取
			itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
			//转换字符串.存入数据库
			try {
				String itemDescStr = MAPPER.writeValueAsString(itemDesc);
				redisService.set(key, itemDescStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return itemDesc;
	}

}























