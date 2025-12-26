package kr.co.ictedu.movie.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.movie.vo.MemberVO;

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberservice;
	
	@PostMapping("/signup")
	// Json 형식으로 보내야 하기 때문에 RequestBody 를 붙여준다
	public ResponseEntity<?> memberjoin(@RequestBody MemberVO vo){
		System.out.println("nick:"+vo.getNickname());
		System.out.println("getGenre:"+vo.getGenre());
		System.out.println("getEmail:"+vo.getEmail());
		System.out.println("getPassword:"+vo.getPassword());
		memberservice.create(vo);
		return ResponseEntity.ok().build();
	}
}
