package kr.co.ictedu.movie.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.BoardCommVO;
import kr.co.ictedu.movie.vo.BoardVO;
import kr.co.ictedu.movie.vo.MemberVO;
import kr.co.ictedu.movie.vo.PageVO;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardCommService boardCommService;
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PageVO pageVO;

    BoardController(BoardCommService boardCommService) {
        this.boardCommService = boardCommService;
    }

	@PostMapping("/boardAdd")
	public ResponseEntity<?> boardAdd(@RequestBody BoardVO vo, HttpServletRequest request, HttpSession session){
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		vo.setReip(request.getRemoteAddr());
		vo.setBnickname(member.getNickname());
		System.out.println("bnickname: " + vo.getBnickname());
		System.out.println("title: " + vo.getTitle());
		System.out.println("content: " + vo.getContent());
		try {
		boardService.add(vo);
		return ResponseEntity.ok().body("게시글 등록 성공");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 실패");
	}
	@RequestMapping("/list")
	public Map<String, Object> boardList(@RequestParam Map<String, String> paramMap, HttpServletRequest request){
		System.out.println("Method => " + request.getMethod());
		String cPage = paramMap.get("cPage");
		System.out.println("searchType: " + paramMap.get("searchType"));
		System.out.println("searchValue: " + paramMap.get("searchValue"));
		System.out.println("*******************");
		
		int totalCnt = boardService.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);
		System.out.println("TotalCount: " + pageVO.getTotalRecord());
		System.out.println("*******************");
		
		int totalPage = (int) Math.ceil(totalCnt/ (double) pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		System.out.println("TotalPage: " + pageVO.getTotalPage());
		System.out.println("*******************");
		
		int totalBlock = (int) Math.ceil(totalPage/ (double) pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		System.out.println("TotalBlock: " + pageVO.getTotalBlock());
		System.out.println("*******************");
		
		if (cPage != null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}
		System.out.println("cPage: " + pageVO.getNowPage());
		System.out.println("*******************");
		
		pageVO.setBeginPerPage((pageVO.getNowPage() -1) * pageVO.getNumPerPage() +1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage() + pageVO.getNumPerPage() -1);
		System.out.println("beginPerPage = " + pageVO.getBeginPerPage());
		System.out.println("endPerPage = " + pageVO.getEndPerPage());
		System.out.println("*******************");
		
		Map<String, Object> response = new HashMap<>();
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<BoardVO> list = boardService.list(map);
		System.out.println("List Size => " + list.size());
		
		int startPage = (int)((pageVO.getNowPage() -1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() +1;
		int endPage = startPage + pageVO.getPagePerBlock() -1;
		
		if(endPage > pageVO.getTotalPage()) {
			endPage = pageVO.getTotalPage();
		}
		System.out.println("startPage = " + startPage);
		System.out.println("endPage = " + endPage);
		response.put("data", list);
		
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage()); 
		response.put("currentPage", pageVO.getNowPage()); 
		response.put("startPage", startPage); 
		response.put("endPage", endPage); 
		
		return response;
	}
	@GetMapping("/detail")
	public BoardVO detail(@RequestParam("num") int num) {
		return boardService.detail(num);
	}
	@GetMapping("/commList")
	public List<BoardCommVO> listBoardComm(@RequestParam("num") int num){
		return boardCommService.listComment(num);
	}
	@PostMapping("/commAdd")
	public ResponseEntity<?> boardComm(@RequestBody BoardCommVO vo){
		System.out.println("getUcode: " + vo.getUcode());
		System.out.println("getUnickname: " + vo.getUnickname());
		System.out.println("getUcontent: " + vo.getUcontent());
		System.out.println("getReip:" + vo.getReip());
		boardCommService.addComment(vo);
		return ResponseEntity.ok().body(1);
	}
	
	// 로그인한 유저가 작성한 게시글만 조회 (마이페이지용)
	@GetMapping("/mylist")
	public List<BoardVO> myBoardList(HttpSession session) {
	    MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
	    if (loginMember == null) {
	        throw new RuntimeException("로그인 필요");
	    }
	    return boardService.listByWriter(loginMember.getNickname());
	}

}
