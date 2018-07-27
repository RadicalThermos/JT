package com.jt.sso.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.util.MD5Util;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	/**
	 * 注册用户
	 * @param user
	 */
	public void regist(User user) {
		//注册用户(缺创建时间,更新时间)
		//判断用户名是否重复
		User username_user = new User();
		username_user.setUsername(user.getUsername());
		if(userMapper.selectOne(username_user)!=null){
			throw new RuntimeException("用户名已存在");
		}
		//判断手机号是否重复
		User phone_user = new User();
		phone_user.setPhone(user.getPhone());
		if(userMapper.selectOne(phone_user)!=null){
			throw new RuntimeException("手机号已存在");
		}
		//判断邮箱是否重复
		User email_user = new User();
		email_user.setEmail(user.getEmail());
		if(userMapper.selectOne(email_user)!=null){
			throw new RuntimeException("邮箱已存在");
		}
		//执行注册逻辑
		user.setPassword(MD5Util.md5(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}


}
