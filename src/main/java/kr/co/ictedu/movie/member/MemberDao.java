package kr.co.ictedu.movie.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.movie.vo.MemberVO;

@Mapper
public interface MemberDao {

	void insertMember(MemberVO vo);
	
	@Select("select count(*) from member where email=#{email}")
	int countByEmail(String email);
	
	@Select("select count(*) cnt from member where nickname=#{nickname}") //닉네임 중복검사 쿼리
	int checkNickname(String email);
}
