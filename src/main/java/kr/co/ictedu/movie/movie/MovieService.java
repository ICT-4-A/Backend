package kr.co.ictedu.movie.movie;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	// 로그인한 사용자의 영화 기록만 불러오기
	public List<MovieFormVO> listByWriter(String writer) {
	    return movieformdao.listByWriter(writer); 
	}
	
	// 로그인한 유저 본인의 영화 기록 장르 필터링
	public Map<String, Integer> getGenreStats(String nickname) {
	    List<MovieFormVO> list = movieformdao.listByWriter(nickname);
	    Map<String, Integer> stats = new HashMap<>();
	    stats.put("액션", 0);
	    stats.put("코미디", 0);
	    stats.put("로맨스", 0);
	    stats.put("공포/스릴러", 0);
	    stats.put("SF/판타지", 0);
	    stats.put("애니메이션", 0);

	    for (MovieFormVO vo : list) {
	        Set<String> genres = mapGenre(vo.getGenre());
	        for (String g : genres) {
	            stats.put(g, stats.get(g) + 1);
	        }
	    }
	    return stats;
	}

	// DB에 저장된 영화 장르를 통계용 장르(6종)로 변환
	private Set<String> mapGenre(String raw) {
	    Set<String> result = new HashSet<>();
	    if (raw == null) return result;
	    
	    String[] genres = raw.split(",");
	    for (String g : genres) {
	        g = g.trim();
	        if (g.equals("액션")) result.add("액션");
	        if (g.equals("코미디")) result.add("코미디");
	        if (g.equals("로맨스")) result.add("로맨스");
	        if (g.equals("애니메이션")) result.add("애니메이션");
	        if (g.equals("공포") || g.equals("스릴러")) {
	            result.add("공포/스릴러");
	        }
	        if (g.equals("SF") || g.equals("판타지")) {
	            result.add("SF/판타지");
	        }
	    }
	    return result;
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
	public List<MovieVO> movielist(){
		return movieformdao.movielist();
	}
}
