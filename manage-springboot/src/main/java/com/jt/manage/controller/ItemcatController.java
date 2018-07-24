package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.redis.RedisService;
import com.jt.manage.pojo.Itemcat;
import com.jt.manage.service.ItemcatService;

@RestController
public class ItemcatController {

	@Autowired
	private ItemcatService itemcatService;

	@RequestMapping("findAllItemcat")
	public List<Itemcat> findAllItemcat() {
		return itemcatService.findAllItemcat();
	}
	/**
	 * 商品分类树展示
	 */
	@RequestMapping("item/cat/list")
	public List<Itemcat> findListByParentId(@RequestParam(defaultValue = "0") Long id) {
		return itemcatService.findListByParentId(id);
	}
}
