package kr.co.ictedu.movie.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.MovieCommVO;

@Service
public class MovieCommService {
	@Autowired
	private MovieCommDao movieCommDao;
	
	public void addMcomment(MovieCommVO vo) {
		movieCommDao.addMcomment(vo);
	}
	public List<MovieCommVO> commentList(int num){
		return movieCommDao.commentList(num);
	}
}
