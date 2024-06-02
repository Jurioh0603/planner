package com.triplan.planner.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LoginDto extends UserDto {
	private String loginIp;
    private String loginDate;	
    
}
