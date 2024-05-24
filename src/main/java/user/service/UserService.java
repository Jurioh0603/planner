package user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.login.mapper.MemberMapper;
import kr.co.login.vo.LoginVo;
import kr.co.login.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//회원가입-저장처리
	@Transactional
	public int insertMember(MemberVo memberVo) {
		
		log.info(">>>>>>>>>>>>>>>>>>>> 회원가입 정보 : "+memberVo.toString());
		
		log.info("1. 암호화 전 비밀번호는 ? : "+memberVo.getPassword().toString());
		// 암호화 모듈로 들어가서 리턴된 값이 String encodePw에 들어감
		String encodePw = passwordEncoder.encode(memberVo.getPassword());
		log.info("2. 암호화 후 비밀번호는 ? : "+encodePw.toString());
		
		//암호화한 비밀번호를 dto에 탑재
		memberVo.setPassword(encodePw);
		
		//정상 회원가입
		int result = memberMapper.insertMember(memberVo);
		
		//비정상 회원가입(고의 에러발생)
		//memberMapper.insertMember2();
		
		return result;
	}
	
	//회원가입-중복아이디 체크
	public int insertCheck(MemberVo memberVo) throws Exception{
		return memberMapper.insertCheck(memberVo);
	}
	
	//관리자페이지-회원목록 조회
	public List<MemberVo> selecMemberList(){
		return memberMapper.selecMemberList();
	}

	//관리자페이지-회원상태 변경
	public int updateStatus(MemberVo memberVo) {
		return memberMapper.updateStatus(memberVo);
	}
	
	//회원정보 조회
	public MemberVo selectLogin(MemberVo memberVo) {
		
		log.info(":::: MemberService.selectLogin ::::");;
		log.info(":::: 일반회원 비밀번호 검증시작 1. 로그인 페이지에서 입력한 비밀번호 값 : "+memberVo.getPassword());
		String frontPassword = memberVo.getPassword();
		
		MemberVo loginInfo = new LoginVo();

		//회원 아이디로 정보 조회 
		loginInfo = memberMapper.selectLogin(memberVo.getId());
		
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
	public MemberVo selectSnsUser(String snsId, String snsType) {
		MemberVo paramVo = new MemberVo();
		paramVo.setSnsId(snsId);
		paramVo.setSnsType(snsType);
		return memberMapper.selectSnsUser(paramVo);
	}

}
