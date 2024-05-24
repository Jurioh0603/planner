package user.dto;

import lombok.Data;

@Data
public class MemberDto {
	
	private String 	member_id;	/*회원 아이디*/
	private String  nickname; 	/*닉네임*/
	private String 	password;	/*비밀번호*/
	private String	tel;		/*전화번호*/
	private String 	name;		/*이름*/
	private String 	email;		/*이메일주소*/
	//private String 	regDate;	/*등록일*/
	private String 	status; 	/*회원상태*/
	private String 	userRole;	/*회원권한*/
	private String 	snsId;		/*SNS 고유 아이디*/
	private String 	snsType;	/*SNS 회원유형*/

}
