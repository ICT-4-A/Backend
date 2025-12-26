package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;



@Alias("lvo")
@Setter
@Getter
public class LoginLoggerVO {
	private int num;
	private String idn;
	private String reip, uagent;
	private String sstime, eetime;
	private String status;
}
