package kr.co.ictedu.movie.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.BoardVO;

@Service
public class BoardService {
	@Autowired 
	private BoardDao boardDao;
	public void add(BoardVO vo) {
		boardDao.add(vo);
	}
	public List<BoardVO> list(Map<String, String> map) {
		return boardDao.list(map);
	}
	void hit(int num) {
		boardDao.hit(num);
	}
	public BoardVO detail(int num) {
		hit(num);
		return boardDao.detail(num);
	}
	public int totalCount(Map<String, String> map) {
		return boardDao.totalCount(map);
	}
	public void delete(int num) {
		boardDao.delete(num);
	}

	// 로그인한 유저가 작성한 게시글만 조회
	public List<BoardVO> listByWriter(String nickname) {
	    return boardDao.listByWriter(nickname);
	}

}
