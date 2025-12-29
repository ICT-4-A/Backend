package kr.co.ictedu.movie.survey;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.ictedu.movie.vo.PageVO;
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
	    Long surveyNum = surveyDao.maxSurveyNum();

	    char stype = 'A';
	    for (SurveyContentVO content : vo.getContents()) {
	        content.setSubcode(surveyNum);
	        content.setSurveycnt(0);
	        content.setSurveytype(String.valueOf(stype));

	        surveyDao.saveSurveyContent(content);
	        stype++;
	    }
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

	        int totalVotes = 0;
	        
	        for (SurveyResultVO vo : result) {
	            SurveyContentVO contentVO = new SurveyContentVO();
	            contentVO.setSurveytype(vo.getSurveytype());
	            contentVO.setSurveytitle(vo.getSurveytitle());
	            contentVO.setSurveycnt(vo.getSurveycnt());
	            contentVOList.add(contentVO);

	            totalVotes += vo.getSurveycnt();
	        }
	        surveyVO.setContents(contentVOList);
	        surveyVO.setTotalVotes(totalVotes); 
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

	public void incrementSurveyCount(Long surveyNum, String surveytype) {
	    surveyDao.incrementSurveyCount(surveyNum, surveytype);
	}
	
	public List<SurveyVO> getSurveyListPaged(PageVO page) {
	  
	    int totalRecord = surveyDao.totalCount(); 
	    page.setTotalRecord(totalRecord);

	    int totalPage = (int) Math.ceil((double) totalRecord / page.getNumPerPage());
	    page.setTotalPage(totalPage);

	    int begin = (page.getNowPage() - 1) * page.getNumPerPage() + 1;
	    int end = begin + page.getNumPerPage() - 1;
	    page.setBeginPerPage(begin);
	    page.setEndPerPage(end);

	    List<SurveyVO> surveyList = surveyDao.listPaged(page);

	    for (SurveyVO s : surveyList) {
	        SurveyVO fullSurvey = findBySNUM(s.getNum());
	        s.setContents(fullSurvey.getContents());
	        s.setTotalVotes(fullSurvey.getTotalVotes());
	    }
	    return surveyList;
	}
}