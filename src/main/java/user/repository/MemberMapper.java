package user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.login.vo.MemberVo;

@Mapper
public interface MemberMapper {
	//회원가입
	public int insertMember(MemberVo memberVo);
	//회원가입-중복아이디 체크
	public int insertCheck(MemberVo memberVo) throws Exception;
	//회원목록 조회
	public List<MemberVo> selecMemberList();
	//회원상태 수정
	public int updateStatus(MemberVo memberVo); 
	//로그인 조회
	public MemberDto selectLogin(String id);
	//트랜잭션테스트용-회원가입
	public int insertMember2();
	//회원정보조회
	public MemberVo selectMember(String id);
	//SNS 회원 정보 조회
	public MemberVo selectSnsUser(MemberVo memberVo);

}
