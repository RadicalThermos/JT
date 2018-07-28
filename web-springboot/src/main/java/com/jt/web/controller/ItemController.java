package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemDescService;
import com.jt.web.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping("/items/{itemId}")
	public String getItem(@PathVariable Long itemId,Model model){
		//从工具类中获取httpClient对象
		Item item = itemService.getItem(itemId);
		model.addAttribute("item", item);
		ItemDesc itemDesc = itemDescService.getItemDesc(itemId);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
}
