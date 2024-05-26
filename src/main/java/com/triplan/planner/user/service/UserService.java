package com.triplan.planner.user.service;

import com.triplan.planner.user.dto.LoginDto;
import com.triplan.planner.user.dto.UserDto;
import com.triplan.planner.user.repository.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
