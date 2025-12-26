package kr.co.ictedu.movie.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.BoardCommVO;

@Service
public class BoardCommService {
	@Autowired
	private BoardCommDao boardCommDao;
	
	public void addComment(BoardCommVO vo) {
		boardCommDao.addComment(vo);
	}
	public List<BoardCommVO> listComment(int num){
		return boardCommDao.listComment(num);
	}
}
