package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("mcomm")
public class MovieCommVO {
	private int comment_num;
	private int movie_form_num;
	private String writer;
	private String content;
	private String commdate;
}
