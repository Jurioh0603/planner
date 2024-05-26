package user.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private String 	member_id;	/*회원 아이디*/
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


	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSnsId() {
		return snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public String getSnsType() {
		return snsType;
	}

	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}

	public String getMimg() {
		return mimg;
	}

	public void setMimg(String mimg) {
		this.mimg = mimg;
	}

	public String getMcopyimg() {
		return mcopyimg;
	}

	public void setMcopyimg(String mcopyimg) {
		this.mcopyimg = mcopyimg;
	}

	@Override
	public String toString() {
		return "UserDto{" +
				"member_id='" + member_id + '\'' +
				", nickname='" + nickname + '\'' +
				", password='" + password + '\'' +
				", tel='" + tel + '\'' +
				", gender='" + gender + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", grade='" + grade + '\'' +
				", snsId='" + snsId + '\'' +
				", snsType='" + snsType + '\'' +
				", mimg='" + mimg + '\'' +
				", mcopyimg='" + mcopyimg + '\'' +
				'}';
	}
}
