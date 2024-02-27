package com.weapon.shop.entity;

import com.weapon.shop.constant.Role;
import com.weapon.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter  @Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(unique = true) // 중복 저장 안됨
    private String email;

    @Column
    private String password;

    @Column
    private String zipCode; // 우편번호

    @Column
    private String addr1; // 기본주소

    @Column
    private String addr2; // 상세주소

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setZipCode(memberFormDto.getZipCode());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);

        member.setAddr1(memberFormDto.getAddr1());
        member.setAddr2(memberFormDto.getAddr2());
        member.setRole(Role.USER);
        return member;
    }

}
