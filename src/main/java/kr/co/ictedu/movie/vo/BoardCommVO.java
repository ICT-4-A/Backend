package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("bcomm")
@Getter
@Setter
public class BoardCommVO {
	private int num;
	private int ucode;
	private String unickname;
	private String ucontent;
	private String reip;
	private String uregdate;
}
