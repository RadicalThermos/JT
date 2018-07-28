package com.jt.manage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jt.manage.pojo.Itemcat;
import com.jt.manage.pojo.ItemcatData;
import com.jt.manage.pojo.ItemcatResult;

public class ItemcatUtil {

	public static ItemcatResult allItemcat(List<Itemcat> cats){
			//声明一个存储的对象,然后构建对象
			ItemcatResult result=new ItemcatResult();
			//	List<Itemcat> cats=ItemcatMapper.selectAll();//查询所有菜单
			//这里我们引入一个map+list循环拼接的方式完成这个结构
			//获取当前菜单下的所有的子菜单行程一个数组,可以仔细研究一下思路,借鉴
			Map<Long,List<Itemcat>> map=new HashMap<Long,List<Itemcat>>();
			for(Itemcat Itemcat:cats){
				//从当前一个Itemcat中获取parentId
				if(!map.containsKey(Itemcat.getParentId())){
					//创建一个元素,元素内容,也就是说没有值的时候,创建一个
					map.put(Itemcat.getParentId(), new ArrayList<Itemcat>());
				}
				map.get(Itemcat.getParentId()).add(Itemcat);
			}
			//构建三级菜单结构
			List<Itemcat> ItemcatList1=map.get(0L);
			//为一级菜单构建它的所有子菜单
			List<ItemcatData> ItemcatDataList1 = new ArrayList<ItemcatData>();
			for(Itemcat Itemcat1:ItemcatList1){//遍历一级菜单
				ItemcatData ItemcatData1=new ItemcatData();
				ItemcatData1.setUrl("/products/"+Itemcat1.getId()+"/.html");
				ItemcatData1.setName("<a href='/products/"+Itemcat1.getId()+".html'>"+Itemcat1.getName()+"</a>");
				
				//遍历二级菜单,也需要拼接
				List<ItemcatData> ItemcatDataList2=new ArrayList<ItemcatData>();
				for(Itemcat Itemcat2:map.get(Itemcat1.getId())){
					ItemcatData ItemcatData2=new ItemcatData();
					ItemcatData2.setUrl("/products/"+Itemcat2.getId()+"/.html");
					ItemcatData2.setName(Itemcat2.getName());
					//遍历三级菜单
					
					List<String> ItemcatDataList3=new ArrayList<String>();
					for (Itemcat Itemcat3 : map.get(Itemcat2.getId())) {
						//三级菜单只是一个字符串和1,2级结构不同
						ItemcatDataList3.add("/produts/"+Itemcat3.getId()+".html|"+Itemcat3.getName());
					}
					ItemcatData2.setItems(ItemcatDataList3);
					ItemcatDataList2.add(ItemcatData2);
				}
				ItemcatData1.setItems(ItemcatDataList2);
				ItemcatDataList1.add(ItemcatData1);	//首页的菜单要求只返回14条
				if(ItemcatDataList1.size()>=14){
					break;
				}
			}
			result.setItemcats(ItemcatDataList1);
			return result;
			
		}
}	
