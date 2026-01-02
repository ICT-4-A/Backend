package kr.co.ictedu.movie.login.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.movie.vo.LoginLoggerVO;
import kr.co.ictedu.movie.vo.MemberVO;

//@Component
//@Aspect
public class LoginLoginAdvice {

	@Autowired
	private MyLogDao myLogDao;

	private void createLoggin(String methodName, Object[] fd, ProceedingJoinPoint jp, String status) {
		LoginLoggerVO lvo = new LoginLoggerVO();
		if (fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest) {
			HttpSession session = (HttpSession) fd[0];
			HttpServletRequest request = (HttpServletRequest) fd[1];
			MemberVO vo = (MemberVO) session.getAttribute("loginMember");
			if(vo != null) {
				lvo.setIdn(vo.getEmail());
				lvo.setStatus(status);
				lvo.setReip(request.getRemoteAddr());
				String userAgent = request.getHeader("User-Agent");
				String parsedAgent = UserAgentUtils.parseAgent(userAgent);
				lvo.setUagent(parsedAgent);
				System.out.println("로그인 기록 : "+lvo);
				System.out.println("IDN: " + lvo.getIdn());
				System.out.println("Agent: " + lvo.getUagent());
				System.out.println("reip: " + lvo.getReip());
				System.out.println("status: " + lvo.getStatus());
				System.out.println("--------------------------------");
				myLogDao.addLoginLoggin(lvo);
			}
		}
	}
	
	@Around("execution(* kr.co.ictedu.movie.login.LoginController.doLog*(..))")
	public String loginLogger(ProceedingJoinPoint jp) {
		Object[] fd = jp.getArgs();
		String rpath = null;
		String methodName = jp.getSignature().getName();
		
		try {
			if(methodName.equals("doLogin")) {
				rpath = (String) jp.proceed();
				createLoggin(methodName, fd, jp, "login");
			}else if (methodName.equals("doLogout")) {
				createLoggin(methodName, fd, jp, "logout");
				rpath = (String) jp.proceed();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("return : " + rpath);
		return rpath;
	}
	

}
