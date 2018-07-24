package com.jt.manage.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.redis.RedisService;
import com.jt.manage.mapper.ItemcatMapper;
import com.jt.manage.pojo.Itemcat;

@Service
public class ItemcatService {

	@Autowired
	private ItemcatMapper itemcatMapper;
	@Autowired
	private RedisService redisService;

	private static final ObjectMapper MAPPER = new ObjectMapper();
	public List<Itemcat> findAllItemcat() {
		// itemcatMapper.findAllItemcat();//手写的sql语句
		List<Itemcat> list = itemcatMapper.selectAll();
		return list;
	}

	public List<Itemcat> findListByParentId(Long id) {
		// 增加缓存逻辑
		String key = "ITEM_CAT_" + id;
		if (redisService.exists(key)) {
			// 如果存在,在redis中获取value,将json字符串转化成list对象返回
			String jsonStr = redisService.get(key);
			//将json转化成对象,又需要用到ObjectMapper,所以可以将此对象做成静态常量
			try {
				JsonNode jsonData = MAPPER.readTree(jsonStr);
				if(jsonData.isArray()&&jsonData.size()>0){
					List<Itemcat> itemcatList = MAPPER.readValue(jsonData.traverse(), 
							MAPPER.getTypeFactory().constructCollectionType(List.class, Itemcat.class));
					return itemcatList;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 如果不存在:查询数据库,将对象返回
			Itemcat itemcat = new Itemcat();
			itemcat.setParentId(id);
			List<Itemcat> itemcatList = itemcatMapper.select(itemcat);
			//返回结果前,先将对象存入缓存redis中
			//问题来了,redis只能存字符串,所以需要先将list转字符串(jeckson)
			//存储对象
			try {
				String listStr = MAPPER.writeValueAsString(itemcatList);
				redisService.set(key,listStr);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return itemcatList;
		}
		return null;
	}
}


















