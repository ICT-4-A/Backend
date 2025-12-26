package kr.co.ictedu.movie.member;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailsender;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private CertificationNumberRedisDao certificationNumberRedisDao;

	private String authCode;

	// 이메일 중복 체크 - DB에 가입된 이메일 있는 지 없는 지 검색 : 1이면 있음
	public int duplicateEmail(String email) {
		int checkEmail = memberDao.countByEmail(email);
		return checkEmail > 0 ? 1 : 0;
	}

	// 6자리 난수를 사용해 인증코드 제작
	public void createAuthCode() {
		int length = 6;
		StringBuilder authCode = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int type = random.nextInt(3);
			switch (type) {
			case 0:
				authCode.append(random.nextInt(10));
				break;
			case 1:
				authCode.append((char) (random.nextInt(26) + 65)); // 아스키코드
				break;
			case 2:
				authCode.append((char) (random.nextInt(26) + 97));
				break;
			}
		}
		this.authCode = authCode.toString();
	}

	public void sendEmail(String toEmail) {
		createAuthCode();
		MimeMessage message = mailsender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("zhalrtjddn01@naver.com");// SMTP 설정 메일
			helper.setTo(toEmail);// 회원가입시 받아온 이메일
			helper.setSubject("ICTStudy의 X팀의 회원가입 인증번호 발송");
			StringBuilder body = new StringBuilder();
			body.append("<html><body>");
			body.append("<h1>ICTStudy의 X팀의 회원가입을 위한 인증번호</h1>");
			body.append("<p>회원가입을 완료하기 위해 아래의 인증코드를 입력해주세요.</p>");
			body.append("<p>인증코드: <strong>");
			body.append(authCode);
			body.append("</strong></p>");
			body.append("</body></html>");
			helper.setText(body.toString(), true);
			mailsender.send(message);
			System.out.println("인증코드 : " + authCode);
			certificationNumberRedisDao.saveCertifiRedisNumber(toEmail, authCode);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public boolean isVerify(String email, String authCode) {
		final int MAX_ATTEMPT = 3;

		// Redis에 인증번호가 없으면 false를 리턴
		if (!certificationNumberRedisDao.hasKey(email)) {
			return false;
		}

		int attemptCount = certificationNumberRedisDao.getAttempt(email);
		if (attemptCount >= MAX_ATTEMPT) {
			System.out.println("인증 실패 : 최대 인증 시도 초과");
			return false;
		}
		System.out.println("Redis의 이메일 : " + certificationNumberRedisDao.hasKey(email));
		System.out.println("Redis의 인증코드 : " + authCode);

		String savedCode = certificationNumberRedisDao.getCertifiRedisNumber(email);

		if (savedCode != null & savedCode.equals(authCode)) {
			certificationNumberRedisDao.deleteCertifiRedisNumber(email);
			return true;
		} else {
			certificationNumberRedisDao.increaseAttempt(email);
			System.out.println("인증 실패 (" + (attemptCount + 1) + "회)");
			return false;
		}
	}
}
