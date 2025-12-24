package kr.co.ictedu.movie.member;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.MemberVO;

@Mapper
public interface MemberDao {

	void insertMember(MemberVO vo);
}
