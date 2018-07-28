package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.Itemcat;
import com.jt.manage.pojo.ItemcatResult;
import com.jt.manage.service.ItemcatService;
import com.jt.manage.util.ItemcatUtil;

@RestController
public class ItemcatController {

	@Autowired
	private ItemcatService itemcatService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
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
	@RequestMapping("web/itemcat/all")
	public String findAllItemcat(String callback) {
		List<Itemcat> itemcatList = itemcatService.findAllItemcat();
		ItemcatResult result = ItemcatUtil.allItemcat(itemcatList);
		//对象转json
		try {
			String resultStr = MAPPER.writeValueAsString(result);
			return callback+"("+resultStr+")";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
