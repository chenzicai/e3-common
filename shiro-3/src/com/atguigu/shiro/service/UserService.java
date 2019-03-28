package com.atguigu.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.shiro.pojo.Users;

public interface UserService{

	public Users getUser();
	
}
