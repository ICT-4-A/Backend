package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("mcomm")
public class MovieCommVO {
	private int num;
	private int mcode;
	private String mnickname;
	private String mcontent;
	private String mregdate;
	private String reip;
}
