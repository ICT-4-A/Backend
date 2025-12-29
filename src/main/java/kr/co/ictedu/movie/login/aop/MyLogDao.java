package kr.co.ictedu.movie.login.aop;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.movie.vo.LoginLoggerVO;

@Mapper
public interface MyLogDao {
	
	@Insert("INSERT INTO MYLOGINLOG VALUES (myloginlog_seq.NEXTVAL, #{idn}, #{reip},#{uagent},#{status},sysdate)")
	public void addLoginLoggin(LoginLoggerVO vo);
	
}
