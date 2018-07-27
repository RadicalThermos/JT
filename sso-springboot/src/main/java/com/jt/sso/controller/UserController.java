package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userservice;
	@RequestMapping("/user/register")
	public SysResult checkLogin(User user){
		try {
			userservice.regist(user);
			return SysResult.oK(user.getUsername());
		} catch (Exception e) {
			return SysResult.build(201, e.getMessage());
		}
	}
}
