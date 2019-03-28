package com.atguigu.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.shiro.pojo.Users;
import com.atguigu.shiro.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ShiroRealm extends AuthorizingRealm {
	/*@Autowired
	private UserService userse;*/

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("------------firstreaml");
		//1.��AuthenticationTokenת��ΪUsernamePasswordToken
		UsernamePasswordToken uPasswordToken=(UsernamePasswordToken) token;
		String username=uPasswordToken.getUsername();
		//�������ݿ�ķ����������ݿ��в�ѯusername��Ӧ���û���¼
		System.out.println("�����ݿ��л�ȡusername��"+username+"����Ӧ���û���Ϣ.");
		//4.���û������ڣ�������׳�UnknownAccountException�쳣
		if("unknown".equals(username)) {
			throw new UnknownAccountException("�û������ڣ�");
		}
		//5.�����û���Ϣ������������Ƿ���Ҫ�׳�������AuthenticationException�쳣
		/*if("monster".equals(username)) {
			throw new LockedAccountException("�û�������");
		}*/
		//6.�����û������������AuthenticationInfo ���󲢷���
		//������Ϣ�Ǵ����ݿ��л�ȡ
		//1).principal:��֤��ʵ����Ϣ��������username,Ҳ���������ݱ��Ӧ��ʵ�������
		Object principal=username;
		//2).credentials:����
		Object credentials=null;
		//2).��ǰrealm�����name,���ø����getName() ��������
		String realmName=getName();
		ByteSource credentialsSalt=ByteSource.Util.bytes(username);
		
		if("admin".equals(username)) {
			credentials="038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if ("user".equals(username)) {
			credentials="098d2c478e9c11555ce2823231e02ec1";
		}
		
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;	
		
	}
	
	public static void main(String[] args) {
		String hashAlgorithmName="MD5";
		Object credentials="123456";
		Object salt=ByteSource.Util.bytes("user");
		int hashIterations=1024;
		
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

	//��Ȩ�ᱻshiro�ص��ķ���
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.��PrincipalCollection �л�ȡ��¼�û�����Ϣ
		Object principal=principals.getPrimaryPrincipal();
		//2.���õ�¼���û�����Ϣ���û���ǰ�Ľ�ɫ��Ȩ��(������Ҫ��ѯ���ݿ�)
		Set<String> roles=new HashSet<>();
		roles.add("user");
		if("admin".equals(principal)) {
			roles.add("admin");
		}
		//3.����SimpleAuthorizationInfo����������roles����
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo(roles);
		//4.����SimpleAuthorizationInfo ����
		return info;
	}

}
