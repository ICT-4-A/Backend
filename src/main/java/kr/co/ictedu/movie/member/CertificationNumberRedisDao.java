package kr.co.ictedu.movie.member;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificationNumberRedisDao {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void saveCertifiRedisNumber(String email, String authCode) {
		stringRedisTemplate.opsForValue().set(email, authCode,Duration.ofSeconds(10000));
		stringRedisTemplate.opsForValue().set(email +":attempt","0",Duration.ofSeconds(10000));
	}
	
	public String getCertifiRedisNumber(String email) {
		return stringRedisTemplate.opsForValue().get(email);
	}
	
	public void deleteCertifiRedisNumber(String email) {
		stringRedisTemplate.delete(email);
		stringRedisTemplate.delete(email +":attempt");
	}
	
	public boolean hasKey(String email) {
		return stringRedisTemplate.hasKey(email);
	}
	
	public void increaseAttempt(String email) {
		stringRedisTemplate.opsForValue().increment(email + ":attempt");
	}
	
	public int getAttempt(String email) {
		String attemptStr = stringRedisTemplate.opsForValue().get(email + ":attempt");
		return attemptStr == null ? 0 : Integer.parseInt(attemptStr);
	}
	
	
}
