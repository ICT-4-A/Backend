package kr.co.ictedu.movie.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.BoardVO;

@Mapper
public interface BoardDao {
	void add(BoardVO vo);
	List<BoardVO> list(Map<String, String> map);
	void hit(int num);
	BoardVO detail(int num);
	void delete(int num);
	int totalCount(Map<String, String> map);
	List<BoardVO> listByWriter(String nickname); 
}
