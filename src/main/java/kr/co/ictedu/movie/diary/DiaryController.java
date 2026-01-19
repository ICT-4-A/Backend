package kr.co.ictedu.movie.diary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.DiaryVO;
import kr.co.ictedu.movie.vo.MemberVO;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {
	@Autowired
	private DiaryService diaryService;
	
	@GetMapping("/my")
	public List<DiaryVO> myDiary(HttpSession session) {
	    MemberVO member = (MemberVO) session.getAttribute("loginMember");
	    if (member == null) {
	    	throw new ResponseStatusException(
	    			HttpStatus.UNAUTHORIZED, "로그인 필요");
	    }
	    return diaryService.getMyDiary(member.getNum());
	}
}
