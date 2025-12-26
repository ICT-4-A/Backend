package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE MEMBER(
//member_num NUMBER CONSTRAINT member_num_pk PRIMARY key,
//email VARCHAR2(100) NOT NULL UNIQUE,
//PASSWORD VARCHAR2(255) NOT null,
//nickname VARCHAR2(50) NOT NULL UNIQUE,
//member_genre VARCHAR2(50)
//);
@Alias("mem") // Alias 는 mybatis mapper에서 사용하기 위해서 이름을 줄이는 기능 
@Setter
@Getter
public class MemberVO {
	private int num;
	private String email;
	private String password;
	private String nickname;
	private String genre;
}
