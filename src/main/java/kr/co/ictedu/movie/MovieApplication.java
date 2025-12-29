package kr.co.ictedu.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Maven,Gradle 방식 -> 의존관계 라이브러리들을 자동으로 가져오고 관리
//maven 방식 :Xml 방식
//gradle 방식 : javascript 방식
@SpringBootApplication
public class MovieApplication {

	public static void main(String[] args) {
		//SpringContainer가 시작및 종료
		SpringApplication.run(MovieApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer crosConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Cros Allow Origin 실행!");
				registry.addMapping("/**")
				.allowedOrigins("http://192.168.0.21:3001","http://192.168.0.21:3000","http://localhost:3001","http://localhost:3000")
				.allowedHeaders("*")
				.allowCredentials(true)
				.allowedMethods("*").maxAge(3600);
			}
		};
	}

}
