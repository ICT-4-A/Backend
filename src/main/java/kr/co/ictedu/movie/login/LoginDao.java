package kr.co.ictedu.movie.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.movie.vo.MemberVO;

@Mapper
public interface LoginDao {
	@Select("SELECT member_num,nickname,member_genre, 1 CNT FROM MEMBER WHERE email=#{email} AND PASSWORD = #{password}")
	Map<String, Object> loginCheck(MemberVO vo);
	
	
	@Select("SELECT count(*) FROM MEMBER WHERE email=#{email}")
	   int emailCheck (String email);
	
	//PasswordLess
	
    // Login Check
    MemberVO checkPassword(MemberVO userinfo);
    
    // Search for User Information
    MemberVO getUserInfo(MemberVO userinfo);
    
    // Password Update
    void updatePassword(MemberVO userinfo);
    
    // User Registration
    void createUserInfo(MemberVO userinfo);
    
    // User Deletion
    void withdrawUserInfo(MemberVO userinfo);
    
    // Password Change
    void changepw(MemberVO userinfo);
}
