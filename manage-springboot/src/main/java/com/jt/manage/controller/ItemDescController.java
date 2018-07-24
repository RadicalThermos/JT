package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;

@RestController
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping("item/query/item/desc/{itemDescId}")
	public SysResult findItemDescById(@PathVariable Long itemDescId){
		try {
			ItemDesc itemDesc = itemDescService.findDescMapperById(itemDescId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}
}
