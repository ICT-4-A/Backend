package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("mfvo")
@Setter
@Getter
public class MovieFormVO {
	private int num;
	private int movie_id;
	private String writer, toge_writer; //기존 작업자 id, 공동 작업자 id
	private String simple_review, review; // 한줄평, 일반 평가
	private float rate;
	private int hit;
	private String created_at, updated_at, deleted_at;
	private String title;
	private String poster;
	private String genre;
	
	private String writer_name;
	private String toge_writer_name;  // 공동작업자
}
