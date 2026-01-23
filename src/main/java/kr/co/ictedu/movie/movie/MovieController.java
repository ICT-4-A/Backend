package kr.co.ictedu.movie.movie;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.BoardVO;
import kr.co.ictedu.movie.vo.MemberVO;
import kr.co.ictedu.movie.vo.MovieCommVO;
import kr.co.ictedu.movie.vo.MovieFormVO;
import kr.co.ictedu.movie.vo.MovieVO;
import kr.co.ictedu.movie.vo.PageVO;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieCommService movieCommService;
	
	@Autowired
	private MovieService movieservice;

	@Autowired
	private PageVO pageVO;

    MovieController(MovieCommService movieCommService) {
        this.movieCommService = movieCommService;
    }
	
    @PostMapping("/movieformadd")
    public ResponseEntity<?> formAdd(@RequestBody MovieFormVO vo, HttpServletRequest request){
        System.out.println("writer : "+vo.getWriter());
        System.out.println("togeWriter : "+vo.getToge_writer());
        System.out.println("simplereview : "+vo.getSimple_review());

        // 세션에서 MemberVO 가져오기
        HttpSession session = request.getSession();
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");

        if (loginMember != null) {
            vo.setWriter(loginMember.getNickname());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 유저 정보가 없습니다.");
        }

        try {
            System.out.println("=== 서비스 호출 전 ===");
            movieservice.addForm(vo);
            System.out.println("=== 서비스 호출 성공 ===");
            return ResponseEntity.ok().body("폼 등록 성공");
        } catch (Exception e) {
            System.out.println("=== 서비스 예외 발생 ===");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("폼 등록 실패: " + e.getMessage());
        }
    }
	
    @GetMapping("/me")
    public Map<String, String> me(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember"); 
        Map<String, String> res = new HashMap<>();
        if (loginMember != null) {
            res.put("nickname", loginMember.getNickname());
            res.put("member_num", String.valueOf(loginMember.getMember_num()));
            res.put("member_genre", loginMember.getMember_genre());
        }
        return res;
    }
	
	/* 로그인한 유저 본인의 영화 기록 */
    @GetMapping("/mylist")
    public Map<String, Object> myMovieList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        String nickname = loginMember != null ? loginMember.getNickname() : null;

        Map<String, Object> response = new HashMap<>();

        if (nickname != null) {
        	// 로그인 사용자 기록만 조회
            List<MovieFormVO> list = movieservice.listByWriter(nickname); 
            response.put("data", list);
            response.put("success", true);
        } else {
            response.put("data", Collections.emptyList());
            response.put("success", false);
        }
        return response;
    }
    
    /* 로그인한 유저 본인의 영화 기록 장르 필터링 (마이페이지 통계에 사용) */
    @GetMapping("/genre-stats")
    public Map<String, Integer> genreStats(HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            throw new RuntimeException("로그인 필요");
        }
        return movieservice.getGenreStats(loginMember.getNickname());
    }

    
	@GetMapping("/list") 
	public Map<String, Object> boardList(@RequestParam Map<String, String> paramMap, HttpServletRequest request){
		System.out.println("Method => " + request.getMethod());
		String cPage = paramMap.get("cPage");
		
		int totalCnt = movieservice.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);
		
		int totalPage = (int) Math.ceil(totalCnt/ (double) pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		
		int totalBlock = (int) Math.ceil(totalPage/ (double) pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		
		if (cPage != null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}
		System.out.println("cPage: " + pageVO.getNowPage());
		System.out.println("*******************");
		
		pageVO.setBeginPerPage((pageVO.getNowPage() -1) * pageVO.getNumPerPage() +1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage() + pageVO.getNumPerPage() -1);
		
		Map<String, Object> response = new HashMap<>();
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<MovieFormVO> list = movieservice.list(map);

		int startPage = (int)((pageVO.getNowPage() -1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() +1;
		int endPage = startPage + pageVO.getPagePerBlock() -1;
		
		if(endPage > pageVO.getTotalPage()) {
			endPage = pageVO.getTotalPage();
		}
		response.put("data", list);
		
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage()); 
		response.put("currentPage", pageVO.getNowPage()); 
		response.put("startPage", startPage); 
		response.put("endPage", endPage); 
		
		return response;
	}
	
	@GetMapping("/detail")
	public MovieFormVO detail(@RequestParam("num") int num) {
		return movieservice.detail(num);
	}
	
	@GetMapping("/search")
	public Map<String, Object> search(@RequestParam Map<String, Object> paramMap){
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> search = new HashMap<>();
		search.put("searchType", paramMap.get("searchType"));
		search.put("searchValue", paramMap.get("searchValue"));
		
		List<MovieVO> vo = movieservice.search(search);
		
		response.put("success", !vo.isEmpty());
		response.put("movie", vo);
		return response;
	}
	@GetMapping("/mcommList")
	public List<MovieCommVO> listMovieComm(@RequestParam("num") int num){
		return movieCommService.commentList(num);
	}
	
	@PostMapping("mcommAdd")
	public ResponseEntity<?> movieComm(@RequestBody MovieCommVO vo, HttpSession session, HttpServletRequest request){
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		vo.setMnickname(loginMember.getNickname());
		vo.setReip(request.getRemoteAddr());
		System.out.println("getmovie_form_num: " + vo.getNum());
		System.out.println("getwriter: "+vo.getMnickname());
		System.out.println("getContent: "+vo.getMcontent());
		movieCommService.addMcomment(vo);
		return ResponseEntity.ok().body(1);
	}
	
	@GetMapping("/movielist")
	public Map<String, Object> movielist(){
		Map<String, Object> response = new HashMap<>();
		List<MovieVO> movies = movieservice.movielist();
		response.put("success", true);
		response.put("movie", movies);
		return response;
	}
	
	@GetMapping(value = "/movieInfo", produces = "application/json")
	public ResponseEntity<MovieVO> movieDetail(@RequestParam("num") int num) {
	    MovieVO movie = movieservice.getMovie(num);
	    System.out.println("movieDetail 호출: " + movie);
	    if (movie != null) {
	        return ResponseEntity.ok(movie);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	// 특정 영화(movieId)의 모든 영화 기록 조회
	@GetMapping("/movieFormsByMovie")
	public Map<String, Object> getFormsByMovie(@RequestParam("num") int num) {
	    Map<String, Object> response = new HashMap<>();
	    List<MovieFormVO> forms = movieservice.listByMovie(num);
	    response.put("success", !forms.isEmpty());
	    response.put("forms", forms);
	    return response;
	}
	
	

}
