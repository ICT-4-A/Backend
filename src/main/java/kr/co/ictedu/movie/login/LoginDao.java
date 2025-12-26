package kr.co.ictedu.movie.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.movie.vo.MemberVO;

@Mapper
public interface LoginDao {
	@Select("SELECT nickname, COUNT(*) cnt FROM MEMBER WHERE email=#{email} AND PASSWORD = #{password} GROUP BY nickname")
	Map<String, Object> loginCheck(MemberVO vo);
	
}
