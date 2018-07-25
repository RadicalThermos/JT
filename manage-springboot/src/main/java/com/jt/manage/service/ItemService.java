package com.jt.manage.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.PicUploadResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.util.PicTypeCheckout;
import com.jt.manage.util.UploadUtil;

@Service
public class ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	public EasyUIResult findItemList(Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List<Item> itemlist = itemMapper.selectAll();
		PageInfo<Item> pageInfo = new PageInfo<Item>(itemlist);
		EasyUIResult result = new EasyUIResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Transactional
	public void saveItem(Item item, String desc) {
		// 商品详情因为是大字段,所以另外放在一张表中
		// 对应一个pojo和一个mapper
		// 一个数据源,两个去向
		// 传过来的数据,并没有创建时间,修改时间,状态码
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDescMapper.insert(itemDesc);
	}

	@Transactional
	public void updateItem(Item item, String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		;
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Transactional
	public void deleteItem(Long[] ids) {
		if (ids != null && ids.length != 0) {
			for (Long id : ids) {
				Item item = new Item();
				item.setId(id);
				itemMapper.delete(item);
				ItemDesc itemDesc = new ItemDesc();
				itemDesc.setItemId(id);
				itemDescMapper.delete(itemDesc);
			}
		}

	}
	public PicUploadResult uploadPic(MultipartFile uploadFile, HttpServletRequest request) {
		// 创建返回结果
		PicUploadResult result = new PicUploadResult();
		// 处理图片上传的步骤
		// 判断是否为空
		if (uploadFile.isEmpty()) {
			// 为空直接返回
			result.setError(1);
			return result;
		}
		// 获取文件名称
		String filename = uploadFile.getOriginalFilename();
		String pictype = filename.substring(filename.lastIndexOf(".")+1);
		// 判断类型是否匹配
		if (!PicTypeCheckout.contentTypes.contains(pictype)) {
			result.setError(1);
			return result;
		}
		// 创建UUID
		String uuid = UUID.randomUUID().toString();
		// 创建图片存放路径
		String dateStr = new Date().toLocaleString().split(" ")[0].replace("-", "/");
		String uploadStr = "/images/" + dateStr;
		String path = request.getServletContext().getRealPath(uploadStr);
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		try {
			// 上传图片
			UploadUtil.uploadImage(uploadFile, uuid, true, 2200, path);
			// 获取图片url
			String url = "http://manage.jt.com" + uploadStr+"/"+uuid+"."+pictype;
			result.setError(0);
			result.setUrl(url);
			result.setWidth("2200");
			return result;
		} catch (Exception e) {
			// 上传失败,删除图片
			File picfile = new File(path);
			if(picfile.exists()){
				picfile.delete();
			}
			result.setError(0);
			return result;
		}
	}
}





















