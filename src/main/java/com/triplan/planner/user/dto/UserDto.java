package com.triplan.planner.user.dto;


import lombok.Data;

@Data
public class UserDto {
	
	private String 	memberId;	/*회원 아이디*/
	private String  nickname; 	/*닉네임*/
	private String 	password;	/*비밀번호*/
	private String	tel;		/*전화번호*/
	private String  gender;		/*성별*/
	private String 	name;		/*이름*/
	private String 	email;		/*이메일주소*/
	//private String 	regDate;	/*등록일*/
	private String 	grade; 		/*회원등급*/
	private String 	snsId;		/*SNS 고유 아이디*/
	private String 	snsType;	/*SNS 회원유형*/
	private String  mimg;		/*원본 프로필 이미지 파일명*/
	private String  mcopyimg;	/*저장 프로필 이미지 파일명*/
}
