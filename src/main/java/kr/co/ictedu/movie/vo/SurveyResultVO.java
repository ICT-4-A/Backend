package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyresultvo")
public class SurveyResultVO {
	private Long surveyNum;
	private String surveySub;
	private String surveyDate;
	private Long subCode;
	private String surveytitle;
	private Integer surveycnt;
}

