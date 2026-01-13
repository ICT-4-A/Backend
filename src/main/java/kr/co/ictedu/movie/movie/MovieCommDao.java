package kr.co.ictedu.movie.movie;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.MovieCommVO;

@Mapper
public interface MovieCommDao {
	void addMcomment(MovieCommVO comment);
	List<MovieCommVO> commentList(int num);
}
