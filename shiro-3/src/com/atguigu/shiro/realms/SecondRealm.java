package com.atguigu.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.shiro.pojo.Users;
import com.atguigu.shiro.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class SecondRealm extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("------------secondreaml");
		//1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken uPasswordToken=(UsernamePasswordToken) token;
		String username=uPasswordToken.getUsername();
		System.out.println("------------"+username);
		//4.若用户不存在，则可以抛出UnknownAccountException异常
		if("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在！");
		}
		//5.根据用户信息的情况，决定是否需要抛出其他的AuthenticationException异常
		/*if("monster".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}*/
		//6.根据用户情况，来构建AuthenticationInfo 对象并返回
		//1).principal:认证的实体信息，可以是username,也可以是数据表对应的实体类对象
		Object principal=username;
		//2).credentials:密码
		Object credentials=null;
		//2).当前realm对象的name,调用父类的getName() 方法即可
		String realmName=getName();
		ByteSource credentialsSalt=ByteSource.Util.bytes(username);
		
		if("admin".equals(username)) {
			credentials="ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
		}else if ("user".equals(username)) {
			credentials="073d4c3ae812935f23cb3f2a71943f49e082a718";
		}
		
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo("secondRealmName", credentials, credentialsSalt, realmName);
		return info;	
		
	}
	
	public static void main(String[] args) {
		String hashAlgorithmName="SHA1";
		Object credentials="123456";
		Object salt=ByteSource.Util.bytes("admin");
		int hashIterations=1024;
		
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
