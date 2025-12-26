package kr.co.ictedu.movie.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.BoardCommVO;

@Mapper
public interface BoardCommDao {
	void addComment(BoardCommVO comment);
	List<BoardCommVO> listComment(int num);
}
