package com.atguigu.shiro.service;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

public class ShiroService {
	@RequiresRoles({"admin"})
	public void getTime1() {
		System.out.println("ʱ��Ϊ��"+new Date());
		Session session=SecurityUtils.getSubject().getSession();
		Object val=session.getAttribute("Tom");
		System.out.println("ֵΪ��"+val);
	}
}
