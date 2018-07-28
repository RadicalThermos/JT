package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.web.WedItemService;

@RestController
public class WebItemController {

	@Autowired
	private WedItemService webItemService; 
	@RequestMapping("/item/{itemId}")
	public Item getItem(@PathVariable Long itemId){
		return webItemService.getItem(itemId);
	}
	@RequestMapping("/itemDesc/{itemId}")
	public ItemDesc getItemDesc(@PathVariable Long itemId){
		return webItemService.getItemDesc(itemId);
	}
}
