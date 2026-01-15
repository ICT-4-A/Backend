package kr.co.ictedu.movie.login;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Locale;



import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    	//log.info("############### ApiInterceptor: postHandle");
    }
    
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		//log.info("############### ApiInterceptor: afterCompletion");
	}
}
