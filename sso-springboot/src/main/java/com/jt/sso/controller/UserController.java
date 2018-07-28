package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userservice;
	
	@RequestMapping("/user/check/{param}/{type}")
	public SysResult checkData(@PathVariable String param,@PathVariable Integer type){
		try {
			userservice.checkData(param,type);
			return SysResult.oK();
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}
	
	@RequestMapping("/user/register")
	public SysResult regist(User user){
		try {
			userservice.regist(user);
			return SysResult.oK(user.getUsername());
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}
}
