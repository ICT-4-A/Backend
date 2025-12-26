package kr.co.ictedu.movie.survey;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.movie.vo.SurveyContentVO;
import kr.co.ictedu.movie.vo.SurveyResultVO;
import kr.co.ictedu.movie.vo.SurveyVO;


@Service
public class SurveyService {
	@Autowired
	private SurveyDao surveyDao;
	public Long maxSurveyNum() {
		return surveyDao.maxSurveyNum();
	}
	@Transactional
	public void saveSurvey(SurveyVO vo) {
		surveyDao.saveSurvey(vo);
		char stype = 'A';
		List<SurveyContentVO> contentList = new ArrayList<>();
		for (SurveyContentVO c : vo.getContents()) {
			SurveyContentVO contentVO = new SurveyContentVO();
			contentVO.setSurveytitle(c.getSurveytitle());
			contentVO.setSurveytype(String.valueOf(stype));
			contentVO.setSurveycnt(0);
			contentList.add(contentVO);
			stype++;
		}
		surveyDao.saveSurveyContentList(contentList);
	}
	
	public List<SurveyVO> getSurveyList() {
	    List<SurveyVO> surveyList = new ArrayList<>();

	    List<Long> nums = surveyDao.findAllSurveyNums();

	    for (Long num : nums) {
	        List<SurveyResultVO> result = surveyDao.findBySNUM(num);
	        if (result.isEmpty()) continue;

	        SurveyVO surveyVO = new SurveyVO();
	        List<SurveyContentVO> contentVOList = new ArrayList<>();

	        SurveyResultVO first = result.get(0);
	        surveyVO.setNum(first.getSurveyNum());
	        surveyVO.setSub(first.getSurveySub());
	        surveyVO.setCode(first.getSurveyCode());
	        surveyVO.setSdate(first.getSurveyDate());

	        for (SurveyResultVO vo : result) {
	            SurveyContentVO contentVO = new SurveyContentVO();
	            contentVO.setSurveytype(vo.getSurveytype());
	            contentVO.setSurveytitle(vo.getSurveytitle());
	            contentVO.setSurveycnt(vo.getSurveycnt());
	            contentVOList.add(contentVO);
	        }

	        surveyVO.setContents(contentVOList);
	        surveyList.add(surveyVO);
	    }

	    return surveyList;
	}


	
	public SurveyVO findBySNUM(Long num){
		List<SurveyResultVO> result = surveyDao.findBySNUM(num);
		
		if(result == null) {
			return null;
		}
		SurveyVO surveyVO = new SurveyVO();
		List<SurveyContentVO> contentVOList = new ArrayList<>();
		
		SurveyResultVO resultVO = result.get(0);
		surveyVO.setNum(resultVO.getSurveyNum());
		surveyVO.setSub(resultVO.getSurveySub());
		surveyVO.setCode(resultVO.getSurveyCode());
		surveyVO.setSdate(resultVO.getSurveyDate());
		
		for (SurveyResultVO vo : result) {
			SurveyContentVO contentVO = new SurveyContentVO();
			contentVO.setSurveytype(vo.getSurveytype());
			contentVO.setSurveytitle(vo.getSurveytitle());
			contentVO.setSurveycnt(vo.getSurveycnt());
			contentVOList.add(contentVO);
		}
		surveyVO.setContents(contentVOList);
		return surveyVO;
	}
	public void incrementSurveyCount(int subcode, String surveytype) {
		surveyDao.incrementSurveyCount(subcode, surveytype);
	}	

}