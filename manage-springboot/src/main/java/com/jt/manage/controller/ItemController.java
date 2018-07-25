package com.jt.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.PicUploadResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/query")
	public EasyUIResult findItemList(Integer page, Integer rows) {
		return itemService.findItemList(page, rows);
	}

	@RequestMapping("/item/save")
	public SysResult saveItem(Item item, String desc) {
		try {
			itemService.saveItem(item, desc);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}

	@RequestMapping("/item/update")
	public SysResult updateItem(Item item, String desc) {
		try {
			itemService.updateItem(item, desc);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}

	@RequestMapping("/item/delete")
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}

	@RequestMapping("/pic/upload")
	public PicUploadResult uploadPic(MultipartFile uploadFile, HttpServletRequest request) {
		PicUploadResult result = itemService.uploadPic(uploadFile, request);
		return result;
	}
}




















