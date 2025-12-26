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
}
