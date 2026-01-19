package kr.co.ictedu.movie.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.MemberVO;

@Service
public class LoginService {
	@Autowired
	private LoginDao logindao;
	
	public Map<String , Object> loginCheck(MemberVO vo){
		return logindao.loginCheck(vo);
	}

	public MemberVO checkPassword(MemberVO userinfo) {
		return logindao.checkPassword(userinfo);
	}
	
	public MemberVO getUserInfo(MemberVO userinfo) {
		return logindao.getUserInfo(userinfo);
	}
	
	public void updatePassword(MemberVO userinfo) {
		logindao.updatePassword(userinfo);
	}
	
	public void createUserInfo(MemberVO userinfo) {
		logindao.createUserInfo(userinfo);
	}
	
	public void withdrawUserInfo(MemberVO userinfo) {
		logindao.withdrawUserInfo(userinfo);
	}
	
	public void changepw(MemberVO userinfo) {
		logindao.changepw(userinfo);
	}
	

	
}
