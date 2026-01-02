package kr.co.ictedu.movie.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
@Alias("gvo")
@Getter
@Setter
public class GalleryVO {
	private Integer num;
	private String title;
	private String contents;
	private String writer;
	private String reip;
	private int hit;
	private String gdate;

	private List<GalleryImageVO> getimlist;
}
//num NUMBER CONSTRAINT gallery_num_pk PRIMARY key,
//title VARCHAR2(50),
//CONTENTS VARCHAR2(500),
//writer VARCHAR2(100),
//reip VARCHAR2(30),
//hit NUMBER DEFAULT 0,
//gdate DATE DEFAULT SYSDATE