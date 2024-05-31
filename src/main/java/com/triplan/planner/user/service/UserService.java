package com.triplan.planner.user.service;

import com.triplan.planner.user.common.SendMail;
import com.triplan.planner.user.dto.LoginDto;
import com.triplan.planner.user.dto.UserDto;
import com.triplan.planner.user.repository.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;


@Slf4j
@Service
public class UserService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//회원가입-저장처리
	@Transactional
	public int insertMember(UserDto userDto) {
		
		log.info(">>>>>>>>>>>>>>>>>>>> 회원가입 정보 : "+userDto.toString());
		
		log.info("1. 암호화 전 비밀번호는 ? : "+userDto.getPassword().toString());
		// 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
		String encodePw = passwordEncoder.encode(userDto.getPassword());
		log.info("2. 암호화 후 비밀번호는 ? : "+encodePw.toString());
		
		//암호화한 비밀번호를 dto에 탑재
		userDto.setPassword(encodePw);
		
		//정상 회원가입
		int result = memberMapper.insertMember(userDto);
		
		//비정상 회원가입(고의 에러발생)
		//memberMapper.insertMember2();
		
		return result;
	}
	
	//회원가입-중복아이디 체크
	public int insertCheck(UserDto userDto) throws Exception{
		return memberMapper.insertCheck(userDto);
	}
	//회원정보 조회
	public UserDto selectLogin(UserDto userDto) {
		
		log.info(":::: MemberService.selectLogin ::::");;
		log.info(":::: 일반회원 비밀번호 검증시작 1. 로그인 페이지에서 입력한 비밀번호 값 : "+userDto.getPassword());
		String frontPassword = userDto.getPassword();
		
		UserDto loginInfo = new LoginDto();

		//회원 아이디로 정보 조회 
		loginInfo = memberMapper.selectLogin(userDto.getMemberId());
		
		if(loginInfo != null) {
			
			//데이터베이스에 저장된 암호화 비밀번호
			String databasePassword = loginInfo.getPassword();
		
			log.info(":::: 일반회원 비밀번호 검증시작 2. 회원 테이블에서 조회한 암호화한 값 : "+loginInfo.getPassword());

			//내가 입력한 평문 비밀번호랑  회원정보에있는 비밀번호(암호화한것)을 비교함
			if (!passwordEncoder.matches(frontPassword, databasePassword)) {
				loginInfo = null;
				log.info(":::: 비밀번호 검증실패, 서로 다릅니다.");
			}else {
				log.info(":::: 비밀번호 검증완료, 성공");
			}
		}
		
		return loginInfo;
	}
	
	//sns회원정보 조회
	public UserDto selectSnsUser(String snsId, String snsType) {
		UserDto paramDto = new UserDto();
		paramDto.setSnsId(snsId);
		paramDto.setSnsType(snsType);
		return memberMapper.selectSnsUser(paramDto);
	}

	//사용자 신규 비밀번호 업데이트
	public boolean passwordUpdate(UserDto userDto) {

		boolean result = true;

		UserDto memberInfo = memberMapper.selectPassword(userDto);

		//아이디,이메일로 조회시 회원정보가 있다면
		if(memberInfo != null) {

			//신규비밀번호 생성
			String newPassword = generateRandomPassword(8);

			//비밀번호 암호화
			String encodePw = passwordEncoder.encode(newPassword);
			memberInfo.setPassword(encodePw);

			//신규비밀번호로 업데이트 수행
			memberMapper.updatePassword(memberInfo);

			//받는사람의 이메일 주소
			String toEmailAddr = memberInfo.getEmail();
			String subject = "신규 비밀번호 안내";
			String content = "변경된 신규 비밀번호는 "+newPassword+" 입니다.";

			//이메일 전송
			SendMail.sendEmailToMember(toEmailAddr, subject, content);

			result = true;

		}else {

			result = false;

		}

		return result;
	}

	//소문자+숫자 조합 8자리 난수생성
	public static String generateRandomPassword(int length) {
		// 비밀번호에 사용할 문자와 숫자 집합
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		// 지정된 길이만큼 랜덤한 문자 선택
		for (int i = 0; i < length; i++) {
			password.append(chars.charAt(random.nextInt(chars.length())));
		}

		return password.toString();
	}

}
