package com.atguigu.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.shiro.dao.UserDao;
import com.atguigu.shiro.pojo.Users;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	@Override
	public Users getUser() {
		// TODO Auto-generated method stub
		
		return userDao.getUser();
	}

}
