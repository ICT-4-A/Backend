package kr.co.ictedu.movie.movie;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.BoardVO;
import kr.co.ictedu.movie.vo.MovieFormVO;
import kr.co.ictedu.movie.vo.MovieVO;

@Service
public class MovieService {
	
	@Autowired
	private MovieFormDao movieformdao;
	
	public void addForm(MovieFormVO vo) {
		movieformdao.addMovieform(vo);
	}
	
	public List<MovieFormVO> list(Map<String, String> map){
		return movieformdao.list(map);
	}
	
	public List<MovieVO> search(Map<String, Object> map){
		return movieformdao.search(map);
	}
	
	
	void hit(int num) {
		movieformdao.hit(num);
	}
	public MovieFormVO detail(int num) {
		hit(num);
		return movieformdao.detail(num);
	}
	public int totalCount(Map<String, String> map) {
		return movieformdao.totalCount(map);
	}
	public void delete(int num) {
		movieformdao.delete(num);
	}
	
}
