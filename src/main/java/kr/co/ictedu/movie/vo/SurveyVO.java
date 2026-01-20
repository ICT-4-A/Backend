package kr.co.ictedu.movie.vo;

import java.util.List;
import org.apache.ibatis.type.Alias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyvo")
public class SurveyVO {
    private Long num;
    private String sub;
    private Integer totalVotes;
    private String sdate;
    private List<SurveyContentVO> contents;
    private String snickname;
}
