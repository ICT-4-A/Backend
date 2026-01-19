package kr.co.ictedu.movie.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.movie.vo.MemberVO;

@Mapper
public interface LoginDao {
	@Select("SELECT member_num, nickname FROM member WHERE email = #{email} AND password = #{password}")
	Map<String, Object> loginCheck(MemberVO vo);
	
}
