package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE Movie(
//num NUMBER PRIMARY key,
//title VARCHAR2(100),
//director VARCHAR2(255),
//actor VARCHAR2(255),
//genre VARCHAR2(100),
//poster VARCHAR2(255),
//release_date DATE,
//created_at DATE,
//updated_at DATE,
//deleted_at date
//);
@Alias("mvo")
@Setter
@Getter
public class MovieVO {
	private int num;
	private String title;
	private String director;
	private String actor;
	private String genre;
	private String poster;
	private String release_date;
	private String created_at, updated_at, deleted_at;
	private double avg_rating;
	
}
