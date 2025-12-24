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
}
