package kr.co.ictedu.movie.movie;

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
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.movie.vo.BoardVO;
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
	    
	    try {
	        System.out.println("=== 서비스 호출 전 ===");
	        movieservice.addForm(vo);
	        System.out.println("=== 서비스 호출 성공 ===");
	        return ResponseEntity.ok().body("폼 등록 성공");
	    } catch (Exception e) {
	        System.out.println("=== 서비스 예외 발생 ===");
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("폼 등록 실패: " + e.getMessage());
	    }
	}
	
	@RequestMapping("/list") //movie_form 의 값을 보여주는 list
	public Map<String, Object> boardList(@RequestParam Map<String, String> paramMap, HttpServletRequest request){
		System.out.println("Method => " + request.getMethod());
		String cPage = paramMap.get("cPage");
		System.out.println("searchType: " + paramMap.get("searchType"));
		System.out.println("searchValue: " + paramMap.get("searchValue"));
		System.out.println("*******************");
		
		int totalCnt = movieservice.totalCount(paramMap);
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
		List<MovieFormVO> list = movieservice.list(map);
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
	public ResponseEntity<?> movieComm(@RequestBody MovieCommVO vo){
		System.out.println("getmovie_form_num: " + vo.getMovie_form_num());
		System.out.println("getwriter: "+vo.getWriter());
		System.out.println("getContent: "+vo.getContent());
		movieCommService.addMcomment(vo);
		return ResponseEntity.ok().body(1);
	
	@GetMapping("/movielist")
	public Map<String, Object> movielist(){
		Map<String, Object> response = new HashMap<>();
		List<MovieVO> movies = movieservice.movielist();
		response.put("success", true);
		response.put("movie", movies);
		return response;
	}

}
