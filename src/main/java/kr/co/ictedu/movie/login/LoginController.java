package kr.co.ictedu.movie.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String doLogin(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent, @RequestBody MemberVO vo) {
		Map<String, Object> result = loginservice.loginCheck(vo);
		System.out.println("result =>" + result);
		System.out.println("email: " + vo.getEmail());
		System.out.println("password: " + vo.getPassword());
		if (result != null && result.get("CNT") != null) {
			int cnt = ((Number) result.get("CNT")).intValue();
			if (cnt == 1) {
				vo.setNickname(result.get("NICKNAME").toString());
				session.setAttribute("loginMember", vo);
				return "success";
			}
		}
		return "fail";
	}



	@GetMapping("/dologout")
	public String doLogout(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent) {
		System.out.println("로그아웃 처리 완료");
		session.invalidate();
		return "logout";
	}
	
	@GetMapping("/session")
	public MemberVO session(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		
		if(loginMember != null) {
			System.out.println("SessionGet :"+loginMember.getNickname());
			System.out.println(loginMember.getNickname());
			loginMember.setPassword(null);
		}
		return loginMember;
	}
	
	
}
