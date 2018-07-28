package com.jt.web.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemcatResult {
	//一级菜单的list,返回的是分类中parent_id=0的所有分类
	@JsonProperty("data")
	private List<ItemcatData> itemcats;

	public List<ItemcatData> getItemcats() {
		return itemcats;
	}

	public void setItemcats(List<ItemcatData> itemcats) {
		this.itemcats = itemcats;
	}

	

	
	
}
