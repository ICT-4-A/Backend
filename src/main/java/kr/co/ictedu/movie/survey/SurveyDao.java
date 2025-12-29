package kr.co.ictedu.movie.survey;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import kr.co.ictedu.movie.vo.PageVO;
import kr.co.ictedu.movie.vo.SurveyContentVO;
import kr.co.ictedu.movie.vo.SurveyResultVO;
import kr.co.ictedu.movie.vo.SurveyVO;

@Mapper
public interface SurveyDao {

	List<SurveyVO> getSurveyList();
	Long maxSurveyNum();
	List<SurveyResultVO> findBySNUM(Long num);
	List<Long> findAllSurveyNums(); // 설문 목록 테이블용
	List<SurveyVO> getSurveyListWithVotes(); // 설문 목록 전체 투표수
	List<SurveyVO> listPaged(@Param("page") PageVO page);
	int totalCount();

	void saveSurvey(SurveyVO vo);
	void saveSurveyContent(SurveyContentVO vo);
	void incrementSurveyCount(
		    @Param("surveyNum") Long surveyNum,
		    @Param("surveytype") String surveytype
	);
}
