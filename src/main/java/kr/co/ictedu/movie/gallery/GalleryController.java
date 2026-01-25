package kr.co.ictedu.movie.gallery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.ictedu.movie.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/gallery")
public class GalleryController {

	private final PageVO pageVO;
	@Autowired
	private GalleryService galleryService;
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadDir;
	
	GalleryController(PageVO pageVO){
		this.pageVO = pageVO;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addGallery(@ModelAttribute GalleryVO galleryVO,
			@RequestParam("images") MultipartFile[] images,
			HttpServletRequest request, HttpSession session){
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
	    if (member == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
	    }
	    galleryVO.setWriter(member.getNickname());
		galleryVO.setReip(request.getRemoteAddr());
		List<GalleryImageVO> imageList = new ArrayList<>();	
		try {
			for(MultipartFile file: images) {
				if(!file.isEmpty()) {
					String originalFilename = file.getOriginalFilename();
					File f = new File(uploadDir + "/gallery/", originalFilename);
					file.transferTo(f);
					GalleryImageVO imageVO = new GalleryImageVO();
					imageVO.setImagename(originalFilename);
					imageList.add(imageVO);
				}
			}
			
			
			galleryVO.setGetimlist(imageList);
			galleryService.add(galleryVO, imageList);
			System.out.println("정상적인 처리");
			return ResponseEntity.ok("갤러리 등록 성공");
		} catch (Exception e) {
			System.out.println("오류가 났음");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");
		}
	}
	@RequestMapping("/galleryList")
	public Map<String, Object> gelleryList(@RequestParam Map<String, String> paramMap,
			HttpServletRequest request){
System.out.println("Method =>" + request.getMethod());
		
		String cPage = paramMap.get("cpage");
		System.out.println("cpage" + cPage);
		System.out.println("serchType" + paramMap.get("serchType"));
		System.out.println("sertchValue" + paramMap.get("sertchValue"));
		System.out.println("*****************************");
		
		int totalCnt = galleryService.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);
		System.out.println("totalCount:" + pageVO.getTotalRecord());
		System.out.println("*****************************");
		
		int totalPage = (int) Math.ceil(totalCnt / (double) pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		System.out.println("TotalPage :" + pageVO.getTotalPage());
		System.out.println("***********************");
		
		int totalBlock = (int) Math.ceil(totalPage / (double) pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		System.out.println("TotalBlock :" + pageVO.getTotalBlock());
		System.out.println("***********************");
		
		if (cPage != null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}
		System.out.println("cPage:"+pageVO.getNowPage());
	
	System.out.println("********************************");
	pageVO.setBeginPerPage((pageVO.getNowPage() - 1) * pageVO.getNumPerPage() + 1);
	pageVO.setEndPerPage(pageVO.getBeginPerPage() + pageVO.getNumPerPage() - 1);
    System.out.println("5. beginPerPage = " + pageVO.getBeginPerPage());
    System.out.println("5. endPerPage = " + pageVO.getEndPerPage());
    System.out.println("********************************");
    
    Map<String, Object> response = new HashMap<>();

	Map<String, String> map = new HashMap<>(paramMap);
	map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
	map.put("end", String.valueOf(pageVO.getEndPerPage()));

	List<Map<String, Object>> list = galleryService.list(map);
	
	int startPage = (int) ((pageVO.getNowPage() - 1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() + 1;
	int endPage = startPage + pageVO.getPagePerBlock() - 1;
	
	if (endPage > pageVO.getTotalPage()) {
		endPage = pageVO.getTotalPage();
	}
	System.out.println("6. startPage = " + startPage);
	System.out.println("6. endPage = " + endPage);
	response.put("data", list); 
	response.put("totalItems", pageVO.getTotalRecord());
	response.put("totalPages", pageVO.getTotalPage()); 
	response.put("currentPage", pageVO.getNowPage()); 
	response.put("startPage", startPage); 
	response.put("endPage", endPage); 
	return response;
	}
	
	@GetMapping("/gdetail")
	public Map<String, Object> detail(@RequestParam("num") int num){
		return galleryService.detail(num);
	}

	@GetMapping("/mylist")
	public List<BoardVO> GalleryList(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		if (loginMember == null) {
			throw new RuntimeException("로그인 필요");

		}
		return galleryService.listByWriter(loginMember.getNickname());
	}

		}




