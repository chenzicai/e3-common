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
		//1.��AuthenticationTokenת��ΪUsernamePasswordToken
		UsernamePasswordToken uPasswordToken=(UsernamePasswordToken) token;
		String username=uPasswordToken.getUsername();
		System.out.println("------------"+username);
		//4.���û������ڣ�������׳�UnknownAccountException�쳣
		if("unknown".equals(username)) {
			throw new UnknownAccountException("�û������ڣ�");
		}
		//5.�����û���Ϣ������������Ƿ���Ҫ�׳�������AuthenticationException�쳣
		/*if("monster".equals(username)) {
			throw new LockedAccountException("�û�������");
		}*/
		//6.�����û������������AuthenticationInfo ���󲢷���
		//1).principal:��֤��ʵ����Ϣ��������username,Ҳ���������ݱ��Ӧ��ʵ�������
		Object principal=username;
		//2).credentials:����
		Object credentials=null;
		//2).��ǰrealm�����name,���ø����getName() ��������
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
