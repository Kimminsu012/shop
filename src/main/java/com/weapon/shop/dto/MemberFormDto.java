package com.weapon.shop.dto;

import com.weapon.shop.constant.Role;
import com.weapon.shop.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter  @Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 값")
    private String name;
    @NotEmpty(message = "이메일은 필수 입력 값")
    @Email(message = "이메일 형식으로 입력")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 값")
    @Length(min = 6 , max = 16 , message = "비밀번호는 6~16자리")
    private String password;
    private String zipCode;
    @NotEmpty(message = "주소는 필수 입력 값")
    private String addr1;
    private String addr2;

    public static MemberFormDto createDto(Member member){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName(member.getName());
        memberFormDto.setEmail(member.getEmail());
        memberFormDto.setZipCode(member.getZipCode());
        memberFormDto.setAddr1(member.getAddr1());
        memberFormDto.setAddr2(member.getAddr2());
        return memberFormDto;
    }
}
