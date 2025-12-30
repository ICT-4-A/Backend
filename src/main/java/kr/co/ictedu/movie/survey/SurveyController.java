package kr.co.ictedu.movie.survey;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.movie.vo.PageVO;
import kr.co.ictedu.movie.vo.SurveyVO;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

	@Autowired
	private SurveyService surveyService;

	@PostMapping("/addsurvey")
	public ResponseEntity<String> saveSurvey(@RequestBody SurveyVO vo) {
		surveyService.saveSurvey(vo);
		System.out.println("sub: " + vo.getSub());
		System.out.println("title: " + vo.getContents().get(0).getSurveytitle());
		return ResponseEntity.ok("success");
	}

	@GetMapping("/latest")
	public ResponseEntity<SurveyVO> getLatestSurvey() {
		SurveyVO surveyVO = surveyService.findBySNUM(surveyService.maxSurveyNum());
		if (surveyVO != null) {
			return ResponseEntity.ok(surveyVO);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	// 설문 결과 조회
	@GetMapping("/result/{num}")
	public ResponseEntity<SurveyVO> getSurveyResult(@PathVariable("num") Long num) {
		SurveyVO surveyVO = surveyService.findBySNUM(num);
		if (surveyVO != null) {
			return ResponseEntity.ok(surveyVO);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<SurveyVO>> getSurveyList() {
		List<SurveyVO> list = surveyService.getSurveyList();
	    return ResponseEntity.ok(list);
	}
	
	@GetMapping("/allList")
	public ResponseEntity<List<SurveyVO>> getAllSurvey() {
		List<SurveyVO> surveyList = surveyService.getSurveyList();
		if (surveyList != null) {
			return ResponseEntity.ok(surveyList);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PostMapping("/updateCount")
	public ResponseEntity<String> incrementSurveyCount(@RequestBody Map<String, Object> payload) {

	    System.out.println("payload = " + payload);
	    Object surveyNumObj = payload.get("surveyNum");
	    Object surveyTitleObj = payload.get("surveytitle");
	    if (surveyNumObj == null || surveyTitleObj  == null) {
	        return ResponseEntity.badRequest().body("필수 값 누락");
	    }
	    Long surveyNum = Long.valueOf(surveyNumObj.toString());
	    String surveytitle = surveyTitleObj .toString();
	    surveyService.incrementSurveyCount(surveyNum, surveytitle);
	    return ResponseEntity.ok("투표 성공");
	}

	@GetMapping("/listPaged")
	public ResponseEntity<List<SurveyVO>> getSurveyListPaged(@RequestParam(defaultValue = "1") int page) {
	    PageVO pageVO = new PageVO();
	    pageVO.setNowPage(page);

	    List<SurveyVO> list = surveyService.getSurveyListPaged(pageVO);
	    return ResponseEntity.ok(list);
	}
}
