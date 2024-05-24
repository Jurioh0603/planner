package user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LoginDto extends MemberDto {
	private String loginIp;
    private String loginDate;	
    
}
