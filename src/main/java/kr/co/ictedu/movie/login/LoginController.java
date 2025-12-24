package kr.co.ictedu.movie.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.MemberVO;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	@Autowired
	private LoginService loginservice;
	@PostMapping("/dologin")
	public String dologin(HttpSession session, HttpServletRequest request, 
			@RequestHeader("User-Agent") String userAgent, @RequestBody MemberVO vo) {
		Map<String, Object> result = loginservice.loginCheck(vo);
		System.out.println("result =>" + result);
		
		if(result != null && result.get("CNT") != null) {
			int cnt = ((Number) result.get("CNT")).intValue();
			if(cnt ==1) {
				System.out.println("세션 처리 완료!");
				vo.setNickname(result.get("NICKNAME").toString());
				session.setAttribute("loginMember", vo);
				return "success";
			}
		}
		
		return "fail";
	}
}
