package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyContentvo")
public class SurveyContentVO {
	private Long subcode;
	private String surveytitle;
	private Integer surveycnt;
}
