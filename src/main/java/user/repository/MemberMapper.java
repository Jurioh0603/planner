package user.repository;

import org.apache.ibatis.annotations.Mapper;
import user.dto.UserDto;

import java.util.List;

@Mapper
public interface MemberMapper {
	//회원가입
	public int insertMember(UserDto userDto);
	//회원가입-중복아이디 체크
	public int insertCheck(UserDto userDto) throws Exception;
	//회원목록 조회
	public List<UserDto> selecMemberList();
	//회원상태 수정
	public int updateStatus(UserDto userDto);
	//로그인 조회
	public UserDto selectLogin(String id);
	//트랜잭션테스트용-회원가입
	public int insertMember2();
	//회원정보조회
	public UserDto selectMember(String id);
	//SNS 회원 정보 조회
	public UserDto selectSnsUser(UserDto userDto);

}
