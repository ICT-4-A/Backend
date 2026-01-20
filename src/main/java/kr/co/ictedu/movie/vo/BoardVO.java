package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Alias("bvo")
@Getter
@Setter
public class BoardVO {
	private int num;
	private String title;
	private String bnickname;
	private String content;
	private int hit;
	private String reip;
	private String bdate;
	
}
