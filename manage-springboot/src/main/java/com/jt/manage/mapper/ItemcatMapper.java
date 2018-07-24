package com.jt.manage.mapper;

import java.util.List;

import com.jt.common.mapper.MyMapper;
import com.jt.manage.pojo.Itemcat;

public interface ItemcatMapper extends MyMapper<Itemcat>{

	
	public List<Itemcat> findAllItemcat();

	
}
