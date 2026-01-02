package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("glivo")
@Getter
@Setter
public class GalleryImageVO {
	private int gallerid;
	private String imagename;
}
