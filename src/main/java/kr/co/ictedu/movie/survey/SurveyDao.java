package kr.co.ictedu.movie.survey;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.movie.vo.SurveyContentVO;
import kr.co.ictedu.movie.vo.SurveyResultVO;
import kr.co.ictedu.movie.vo.SurveyVO;

@Mapper
public interface SurveyDao {

	List<SurveyVO> getSurveyList();
	Long maxSurveyNum();
	List<SurveyResultVO> findBySNUM(Long num);
	List<Long> findAllSurveyNums(); // 설문 목록 테이블용

	void saveSurvey(SurveyVO vo);
	void saveSurveyContentList(List<SurveyContentVO> list);
	void incrementSurveyCount(@Param("subcode") int subcode,
			@Param("surveytype") String surveytype);
}
