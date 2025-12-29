package kr.co.ictedu.movie.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberdao;
	
	public void create(MemberVO vo) {
		memberdao.insertMember(vo);
	}
	
	public int checkEmailDuplicate(String email) {
		return memberdao.countByEmail(email);
	}
	
	public int checkNickname(String Nickname) {
		return memberdao.checkNickname(Nickname);
	}
	
}
