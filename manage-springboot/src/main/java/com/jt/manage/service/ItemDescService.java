package com.jt.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemDescService {

	@Autowired
	private ItemDescMapper itemDescMapper;
	public ItemDesc findDescMapperById(Long itemDescId) {
		ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemDescId);
		return itemDesc;
	}

}
