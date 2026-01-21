package kr.co.ictedu.movie.movie;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.MovieFormVO;
import kr.co.ictedu.movie.vo.MovieVO;

@Mapper
public interface MovieFormDao {

	List<MovieVO> search(Map<String, Object> map);

	List<MovieFormVO> list(Map<String, String> map); // 무비 폼 전체 가져오기

	List<MovieFormVO> listByWriter(String writer); // 로그인한 사용자의 영화 기록

	int totalCount(Map<String, String> map); // 페이징 처리 할 때 쓰기

	void addMovieform(MovieFormVO movieform); // 무비 폼 추가

	void hit(int num); // 조회수 가져오기

	MovieFormVO detail(int num);

	void delete(int num);

	List<MovieVO> movielist();
	
	MovieVO getMovie(int num);
	
	List<MovieFormVO> listByMovie(int movieId); // 특정 영화(movieId)의 모든 기록 가져오기

}
