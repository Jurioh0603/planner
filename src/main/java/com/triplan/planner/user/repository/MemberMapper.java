package com.triplan.planner.user.repository;

import com.triplan.planner.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	//회원가입
	public int insertMember(UserDto userDto);
	//회원가입-중복아이디 체크
	public int insertCheck(UserDto userDto) throws Exception;
	//로그인 조회
	public UserDto selectLogin(String id);
	//트랜잭션테스트용-회원가입
	public int insertMember2();
	//회원정보조회
	public UserDto selectMember(String id);
	//SNS 회원 정보 조회
	public UserDto selectSnsUser(UserDto userDto);
	//비밀번호 찾기
	public UserDto selectPassword(UserDto userDto);
	//비밀번호 변경
	public int updatePassword(UserDto userDto);
}
